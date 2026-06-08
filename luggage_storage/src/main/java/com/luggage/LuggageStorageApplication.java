package com.luggage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.luggage.mapper")
@EnableTransactionManagement
public class LuggageStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuggageStorageApplication.class, args);
    }

}
