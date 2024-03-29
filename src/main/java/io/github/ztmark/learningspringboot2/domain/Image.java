package io.github.ztmark.learningspringboot2.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Mark
 * Date  : 2017/12/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Image {

    @Id
    private String id;
    private String name;

}
