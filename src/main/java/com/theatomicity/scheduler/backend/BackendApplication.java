package com.theatomicity.scheduler.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BackendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
