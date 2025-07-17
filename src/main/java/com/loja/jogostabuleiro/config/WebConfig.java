package com.loja.jogostabuleiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:${user.home}/uploads/jogos}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configurar para servir arquivos de upload
        registry.addResourceHandler("/uploads/jogos/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}