package com.gym_project.projeto_bulkhouse.Configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{


    public void addCorsMappings (CorsRegistry registry){
        registry.addMapping("/**")
        .allowedOrigins("http://100.27.199.128:3000","http://100.27.199.128","http://localhost","http://localhost:3000")
        .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
    }
}