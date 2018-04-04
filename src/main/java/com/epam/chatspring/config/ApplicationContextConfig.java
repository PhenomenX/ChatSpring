package com.epam.chatspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration

@ComponentScan("com.epam.chatspring.*")

public class ApplicationContextConfig {

	@Bean(name = "multipartResolver")
    public MultipartResolver getMultipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }
 
}