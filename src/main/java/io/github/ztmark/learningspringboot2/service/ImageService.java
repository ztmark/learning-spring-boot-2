package io.github.ztmark.learningspringboot2.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import io.github.ztmark.learningspringboot2.dao.ImageRepository;
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
    private final ImageRepository imageRepository;

    public ImageService(ResourceLoader resourceLoader, ImageRepository imageRepository) {
        this.resourceLoader = resourceLoader;
        this.imageRepository = imageRepository;
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
        return imageRepository.findAll();
    }

    public Mono<Resource> findOneImage(String fileName) {
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + fileName));
    }

    public Mono<Void> createImage(Flux<FilePart> files) {

        return files.flatMap(file -> {
            final Mono<Image> save = imageRepository.save(new Image(UUID.randomUUID().toString(), file.filename()));
            final Mono<Void> copy = Mono.just(Paths.get(UPLOAD_ROOT, file.filename()).toFile())
                                       .log("createImage-picktarget")
                                       .map(destFile -> {
                                           try {
                                               destFile.createNewFile();
                                               return destFile;
                                           } catch (IOException e) {
                                               throw new RuntimeException(e);
                                           }
                                       })
                                       .log("createImage-newfile")
                                       .flatMap(file::transferTo)
                                       .log("createImage-copy");
            return Mono.when(save, copy);
        }).then();

    }

    public Mono<Void> deleteImage(String fileName) {
        final Mono<Void> voidMono = imageRepository.findByName(fileName).flatMap(imageRepository::delete);
        final Mono<Object> mono = Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return Mono.when(voidMono, mono).then();
    }
}
