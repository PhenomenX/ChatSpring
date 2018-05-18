package com.epam.chatspring.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.UserDAO;
import com.epam.chatspring.dao.oracledb.OracleMessageDAO;
import com.epam.chatspring.dao.oracledb.OracleUserDAO;
import com.epam.chatspring.model.User;

@Configuration

@ComponentScan("com.epam.chatspring.*")

public class ApplicationContextConfig {

	@Bean(name = "multipartResolver")
	public MultipartResolver getMultipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		return resolver;
	}

	@Bean
	public FileSystemResource imageResource() {
		return new FileSystemResource("D://images//");
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}

	@Bean
	public DataSource myOracleDataSource() {
		Locale.setDefault(Locale.ENGLISH);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		dataSource.setUsername("SYSTEM");
		dataSource.setPassword("zver12");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(myOracleDataSource());
	}

	@Bean(name = "userDAO")
	public UserDAO userDAO() {
		UserDAO userDAO = new OracleUserDAO();
		return userDAO;
	}

	@Bean(value = "messageDAO")
	public MessageDAO getMessageDAO() {
		MessageDAO messageDAO = new OracleMessageDAO();
		return messageDAO;
	}

	@Bean
	@Scope("Singleton")
	public Map<String, User> onlineUsers() {
		return new HashMap<String, User>();
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public User currentUser() {
		return new User();
	}

}