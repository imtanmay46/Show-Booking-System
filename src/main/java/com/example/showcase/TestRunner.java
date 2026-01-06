package com.example.showcase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRunner {

    @Bean
    CommandLineRunner test() {
        return args -> {
            System.out.println("ShowCase application is running!");
        };
    }
}