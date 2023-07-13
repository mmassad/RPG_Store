package com.mauremar.item_shop.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientAPI {
    public static void main(String[] args) {
        var app = new SpringApplication(ClientAPI.class);

        app.run(args);
    }
}
