package com.epam.chatspring.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.SimpleThreadScope;
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

//	@Bean
//	public CustomScopeConfigurer customScope() {
//		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
//		Map<String, Object> workflowScope = new HashMap<String, Object>();
//		workflowScope.put("session", new SimpleThreadScope());
//		configurer.setScopes(workflowScope);
//		return configurer;
//	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggerConfigurationInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/messages").addPathPatterns("/users/**")
				.excludePathPatterns("/users/login").excludePathPatterns("/users/register");
	}

}
