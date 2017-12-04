package io.github.ztmark.learningspringboot2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.ztmark.learningspringboot2.dao.ChapterRepository;
import io.github.ztmark.learningspringboot2.domain.Chapter;
import reactor.core.publisher.Flux;

/**
 * Author: Mark
 * Date  : 2017/12/4
 */
@RestController
public class ChapterController {

    private final ChapterRepository repository;

    public ChapterController(ChapterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/chapters")
    public Flux<Chapter> listing() {
        return repository.findAll();
    }
}
