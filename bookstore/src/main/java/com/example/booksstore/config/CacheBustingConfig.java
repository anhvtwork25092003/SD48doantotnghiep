package com.example.booksstore.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class CacheBustingConfig {

    @Bean
    public String cacheBustingValue() {
        // Sinh giá trị ngẫu nhiên để sử dụng trong URL để tránh cache
        return String.valueOf(new Random().nextInt(100000));
    }
}
