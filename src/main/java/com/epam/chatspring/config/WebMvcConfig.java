package com.epam.chatspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.epam.chatspring.filter.AdminInterceptor;
import com.epam.chatspring.filter.AuthenticationInterceptor;
import com.epam.chatspring.filter.AuthorizationInterceptor;
import com.epam.chatspring.filter.XSSInterceptor;

@Configuration
@EnableWebMvc
@PropertySource("classpath:properties/config.properties")
@PropertySource("classpath:properties/SQL_Queries.properties")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:D:\\images\\");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new XSSInterceptor()).addPathPatterns("/users/login").addPathPatterns("/messages");
		registry.addInterceptor(new AuthorizationInterceptor()).addPathPatterns("/users/login");
		registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/messages")
				.addPathPatterns("/users").excludePathPatterns("/users/kick").excludePathPatterns("/users/unkick")
				.excludePathPatterns("/users/login");
		registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/users/kick").addPathPatterns("/users/unkick");
	}

}
