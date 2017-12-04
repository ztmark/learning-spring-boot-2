package io.github.ztmark.learningspringboot2.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.github.ztmark.learningspringboot2.domain.Chapter;

/**
 * Author: Mark
 * Date  : 2017/12/4
 */
public interface ChapterRepository extends ReactiveMongoRepository<Chapter, String> {

}
