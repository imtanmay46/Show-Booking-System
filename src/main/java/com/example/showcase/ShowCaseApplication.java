package com.example.showcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShowCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowCaseApplication.class, args);
    }
}