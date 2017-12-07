package io.github.ztmark.learningspringboot2.service;

import java.time.Duration;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;

import io.github.ztmark.learningspringboot2.domain.Image;
import reactor.core.publisher.Flux;

/**
 * Author: Mark
 * Date  : 2017/12/7
 */
public class BlockImageService {

    private final ImageService imageService;

    public BlockImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    public List<Image> findAllImages() {
        return imageService.findAllImages().collectList().block(Duration.ofSeconds(10));
    }

    public Resource findOneImage(String fileName) {
        return imageService.findOneImage(fileName).block(Duration.ofSeconds(10));
    }

    public void createImage(List<FilePart> files) {
        imageService.createImage(Flux.fromIterable(files)).block();
    }

    public void deleteImage(String fileName) {
        imageService.deleteImage(fileName).block();
    }
}
