package io.github.ztmark.learningspringboot2.controller;

import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.ztmark.learningspringboot2.service.ImageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Author: Mark
 * Date  : 2017/12/4
 */
@Controller
@RequestMapping("/images")
public class HomeController {

    private static final String FILENAME = "{filename:.+}";

    private final ImageService imageService;

    public HomeController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/" + FILENAME + "/raw", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<?>> onRawImage(@PathVariable String filename) {
        return imageService.findOneImage(filename)
                           .map(resource -> {
                               try {
                                   return ResponseEntity.ok()
                                                 .contentLength(resource.contentLength())
                                                 .body(new InputStreamResource(resource.getInputStream()));
                               } catch (IOException e) {
                                   return ResponseEntity.badRequest()
                                                 .body("Couldn't not found " + filename + " " + e.getMessage());
                               }

                           });
    }

    @PostMapping()
    public Mono<String> createFile(@RequestPart(name = "file") Flux<FilePart> files) {
        return imageService.createImage(files).then(Mono.just("redirect:/"));
    }

    @DeleteMapping(FILENAME)
    public Mono<String> deleteFile(@PathVariable String filename) {
        return imageService.deleteImage(filename).then(Mono.just("redirect:/"));
    }

    @GetMapping()
    public Mono<String> index(Model model) {
        model.addAttribute("images", imageService.findAllImages());
        return Mono.just("index");
    }
}
