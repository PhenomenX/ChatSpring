package com.epam.chatspring.config;

import java.io.File;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.epam.chatspring.filter.AuthorizationFilter;
import com.epam.chatspring.filter.XSSPreventionFilter;

public class SpringWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(ApplicationContextConfig.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher",
				new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		fr.setInitParameter("encoding", "UTF-8");
		fr.setInitParameter("forceEncoding", "true");
		fr.addMappingForUrlPatterns(null, true, "/*");

		fr = servletContext.addFilter("XSSPreventionFilter", XSSPreventionFilter.class);
		fr.addMappingForUrlPatterns(null, true, "/users/login");
		fr.addMappingForUrlPatterns(null, true, "/messages");

		servletContext.addFilter("putFormFilter", HttpPutFormContentFilter.class).addMappingForUrlPatterns(null, true,
				"/*");

		servletContext.addFilter("authorizationFilter", AuthorizationFilter.class).addMappingForUrlPatterns(null, true,
				"/users/login");

		File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

		int maxUploadSizeInMb = 5 * 1024 * 1024;
		// register a MultipartConfigElement
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
				maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

		dispatcher.setMultipartConfig(multipartConfigElement);
	}

}