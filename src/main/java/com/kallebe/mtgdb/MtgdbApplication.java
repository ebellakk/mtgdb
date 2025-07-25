package com.kallebe.mtgdb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MtgdbApplication {

    @Value("${cors.allowed.origin.patterns}")
    private String corsAllowedOriginPatterns;

    public static void main(String[] args) {
        SpringApplication.run(MtgdbApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns(corsAllowedOriginPatterns);
            }
        };
    }

}
