package io.github.ztmark.learningspringboot2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import com.sun.tracing.ProbeName;

import io.github.ztmark.learningspringboot2.dao.ChapterRepository;
import io.github.ztmark.learningspringboot2.domain.Chapter;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class LearningSpringBoot2Application {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Application.class, args);
    }


    @Bean
    CommandLineRunner init(ChapterRepository repository) {
        return args -> {
            Flux.just(
                    new Chapter("Quick Start with Java"),
                    new Chapter("Reactive Web with Spring Boot"),
                    new Chapter("... and more"),
                    new Chapter("Quick Start with Java"))
                .flatMap(repository::save)
                .subscribe(System.out::println);
        };
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}
