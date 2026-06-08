package com.luggage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.luggage.mapper")
public class LuggageStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuggageStorageApplication.class, args);
    }

}
