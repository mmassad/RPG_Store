package com.mauremar.item_shop.server;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class ItemShopApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(ItemShopApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setDefaultProperties(Map.of("server.port", 9090));
        app.run(args);
    }
}
