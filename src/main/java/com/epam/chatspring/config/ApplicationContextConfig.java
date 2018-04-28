package com.epam.chatspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.epam.chatspring.dao.DAOFactory;
import com.epam.chatspring.dao.DBType;
import com.epam.chatspring.dao.MessageDAO;
import com.epam.chatspring.dao.UserDAO;

@Configuration

@ComponentScan("com.epam.chatspring.*")

public class ApplicationContextConfig {

	@Bean(name = "multipartResolver")
    public MultipartResolver getMultipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }
	
	@Bean
	public UserDAO userDAO(){
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		return userDAO;
	}
	
	@Bean(value = "messageDAO")
	public MessageDAO getMessageDAO(){
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		MessageDAO messageDAO = factory.getMessageDAO();
		return messageDAO;
	}
 
}