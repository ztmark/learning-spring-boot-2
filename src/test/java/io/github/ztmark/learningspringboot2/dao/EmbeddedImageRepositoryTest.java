package io.github.ztmark.learningspringboot2.dao;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.ztmark.learningspringboot2.domain.Image;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Author: Mark
 * Date  : 2017/12/9
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class EmbeddedImageRepositoryTest {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private MongoOperations operations;

    @Before
    public void setUp() {
        operations.dropCollection(Image.class);
        operations.insert(new Image("1", "image one"));
        operations.insert(new Image("2", "image two"));
        operations.insert(new Image("3", "image three"));
    }

    @Test
    public void findAllShouldWork() {
        final Flux<Image> images = repository.findAll();
        StepVerifier.create(images)
                    .recordWith(ArrayList::new)
                    .expectNextCount(3)
                    .consumeRecordedWith(results -> {
                        assertThat(results).hasSize(3);
                        assertThat(results).extracting(Image::getName).contains("image one", "image two", "image three");
                    })
                    .expectComplete()
                    .verify();
    }

    @Test
    public void findByNameShouldWork() {
        final Mono<Image> image = repository.findByName("image one");
        StepVerifier.create(image)
                    .expectNextMatches(result -> {
                        assertThat(result.getName()).isNotEqualTo("image one");
                        assertThat(result.getId()).isEqualTo("1");
                        return true;
                    });
    }

}