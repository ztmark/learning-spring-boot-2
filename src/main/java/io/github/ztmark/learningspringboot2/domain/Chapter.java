package io.github.ztmark.learningspringboot2.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Author: Mark
 * Date  : 2017/12/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Chapter {

    @Id
    private String id;
    private String name;

    public Chapter(String name) {
        this.name = name;
    }
}
