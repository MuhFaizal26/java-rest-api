package com.domain.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome/springboot")
public class WelcomeController {

    @GetMapping
    public String welcome(){
        return "Selamat Datang di Spring Boot Rest Api";   
    }
    
}
