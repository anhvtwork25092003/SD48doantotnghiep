package com.example.booksstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BooksstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksstoreApplication.class, args);
    }

}
