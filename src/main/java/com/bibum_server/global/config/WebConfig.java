package com.bibum_server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://43.201.167.198:8080",
                        "http://43.201.167.198",
                        "http://localhost:3000",
                        "https://localhost:3000",
                        "https://todays-menu-front.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
        ;

    }

}
