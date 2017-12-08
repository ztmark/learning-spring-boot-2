package io.github.ztmark.learningspringboot2.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import io.github.ztmark.learningspringboot2.domain.Image;
import reactor.core.publisher.Mono;

/**
 * Author: Mark
 * Date  : 2017/12/8
 */
public interface ImageRepository extends ReactiveCrudRepository<Image, String> {

    Mono<Image> findByName(String name);

}
