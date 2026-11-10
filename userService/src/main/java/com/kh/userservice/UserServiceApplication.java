package com.kh.userservice;

import com.kh.userservice.service.DbInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        DbInitializer.createOrUpdateTables();
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
