package io.github.ztmark.learningspringboot2.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import io.github.ztmark.learningspringboot2.domain.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Author: Mark
 * Date  : 2017/12/7
 */
@Service
public class ImageService {

    private static String UPLOAD_ROOT = "upload-dir";
    private final ResourceLoader resourceLoader;

    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    CommandLineRunner setUp() {
        return args -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));
            Files.createDirectories(Paths.get(UPLOAD_ROOT));
            FileCopyUtils.copy("Test file1", new FileWriter(UPLOAD_ROOT + "/avatar1.jpg"));
            FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/image2.jpg"));
            FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/image3.jpg"));
        };
    }

    public Flux<Image> findAllImages() {
        try {
            return Flux.fromIterable(Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
                       .map(path -> new Image(path.hashCode(), path.getFileName().toString()));
        } catch (IOException e) {
            return Flux.empty();
        }
    }

    public Mono<Resource> findOneImage(String fileName) {
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + fileName));
    }

    public Mono<Void> createImage(Flux<FilePart> files) {
        return files.flatMap(file -> file.transferTo(Paths.get(UPLOAD_ROOT, file.filename()).toFile())).then();
    }

    public Mono<Void> deleteImage(String fileName) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).then();
    }
}
