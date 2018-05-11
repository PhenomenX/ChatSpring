package com.epam.chatspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.epam.chatspring.filter.AuthenticationInterceptor;

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
	
	@Bean
	public AuthenticationInterceptor authenticationInterceptor() {
	    return new AuthenticationInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LoggerConfigurationInterceptor()).addPathPatterns("/**");
//		registry.addInterceptor(xssInterceptor()).addPathPatterns("/users/login").addPathPatterns("/messages");
//		registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/users/login");
//		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/messages")
//				.addPathPatterns("/users").excludePathPatterns("/users/kick").excludePathPatterns("/users/unkick")
//				.excludePathPatterns("/users/login").excludePathPatterns("/users/register");
//		registry.addInterceptor(adminInterceptor()).addPathPatterns("/users/kick").addPathPatterns("/users/unkick");
//		registry.addInterceptor(registrationInterceptor()).addPathPatterns("/users/register");
		
	}

}
