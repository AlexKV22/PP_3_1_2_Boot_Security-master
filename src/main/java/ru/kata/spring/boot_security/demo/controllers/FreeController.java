package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FreeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }
}
