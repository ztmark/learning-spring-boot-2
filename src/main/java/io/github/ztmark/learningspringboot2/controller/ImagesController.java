package io.github.ztmark.learningspringboot2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.ztmark.learningspringboot2.domain.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Author: Mark
 * Date  : 2017/12/7
 */
@RestController
public class ImagesController {

    private static final Logger logger = LoggerFactory.getLogger(ImagesController.class);

    @GetMapping("/images-test")
    Flux<Image> images() {
        return Flux.just(
                new Image(1, "image one"),
                new Image(2, "image two"),
                new Image(3, "image three")
        );
    }

    @PostMapping("/images-test")
    Mono<Void> create(@RequestBody Flux<Image> images) {
        return images.map(image -> {
            logger.info("We will save {} to a reactive database soon", image);
            return image;
        }).then();
    }

}
