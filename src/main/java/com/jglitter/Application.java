package com.jglitter;

import org.springframework.boot.SpringApplication;

/**
 * The SpringBoot application.
 */
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Config.class);
        app.run(args);
    }
}
