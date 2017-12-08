package io.github.ztmark.learningspringboot2.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import io.github.ztmark.learningspringboot2.domain.Image;

/**
 * Author: Mark
 * Date  : 2017/12/8
 */
@Component
public class InitDatabase {

    @Bean
    CommandLineRunner init(MongoOperations operations) {
        return args -> {
            operations.dropCollection(Image.class);

            operations.insert(new Image("1", "image one"));

            operations.insert(new Image("2", "image two"));

            operations.insert(new Image("3", "image three"));

            operations.findAll(Image.class).forEach(System.out::println);
        };
    }

}
