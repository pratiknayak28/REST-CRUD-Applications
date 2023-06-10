package com.example.restcrudapplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Springboot REST CRUD Application API",
        version = "1.1"
))
public class RestcrudapplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestcrudapplicationApplication.class, args);
    }

}
