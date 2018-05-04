package com.epam.chatspring.config;

import org.springframework.context.annotation.Bean;
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
import com.epam.chatspring.filter.LoggerConfigurationInterceptor;
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
	
	@Bean
	public XSSInterceptor xssInterceptor() {
	    return new XSSInterceptor();
	}
	
	@Bean
	public AuthorizationInterceptor authorizationInterceptor() {
	    return new AuthorizationInterceptor();
	}
	
	@Bean
	public AuthenticationInterceptor authenticationInterceptor() {
	    return new AuthenticationInterceptor();
	}
	
	@Bean
	public AdminInterceptor adminInterceptor() {
	    return new AdminInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerConfigurationInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(xssInterceptor()).addPathPatterns("/users/login").addPathPatterns("/messages");
		registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/users/login");
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/messages")
				.addPathPatterns("/users").excludePathPatterns("/users/kick").excludePathPatterns("/users/unkick")
				.excludePathPatterns("/users/login").excludePathPatterns("/users/register");
		registry.addInterceptor(adminInterceptor()).addPathPatterns("/users/kick").addPathPatterns("/users/unkick");
	}

}
