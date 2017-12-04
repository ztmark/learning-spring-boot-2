package io.github.ztmark.learningspringboot2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Mark
 * Date  : 2017/12/4
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String greeting() {
        return "Hello there";
    }

}
