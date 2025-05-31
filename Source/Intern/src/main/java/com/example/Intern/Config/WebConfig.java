package com.example.Intern.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cung cấp quyền truy cập vào thư mục ảnh
        registry.addResourceHandler("/photo/property/**")
                .addResourceLocations("file:/D:/Photo/");  // Cấu hình thư mục bên ngoài
    }
}