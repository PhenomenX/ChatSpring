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
import com.epam.chatspring.filter.LoggerConfigurationInterceptor;

@Configuration
@EnableWebMvc
@PropertySource("classpath:properties/messages.properties")
@PropertySource("classpath:properties/chat.properties")
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

	@Bean
	public LoggerConfigurationInterceptor loggerConfigurationInterceptor() {
		return new LoggerConfigurationInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggerConfigurationInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/messages").addPathPatterns("/users")
				.excludePathPatterns("/users/login").excludePathPatterns("/users/register");
	}

}
