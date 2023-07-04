package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    http://localhost:8080/clients
    http://localhost:8080/clients/create
*/
@SpringBootApplication
public class SpringJdbcApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcApp.class, args);
    }
}