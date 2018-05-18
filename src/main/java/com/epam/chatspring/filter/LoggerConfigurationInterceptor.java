package com.epam.chatspring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.epam.chatspring.model.User;

public class LoggerConfigurationInterceptor implements HandlerInterceptor{
	
	@Autowired
	private User currentUser;


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {

		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		MDC.clear();	
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		MDC.put("url", request.getRequestURI());
		HttpSession httpSession = request.getSession();
		String sessionId = httpSession.getId();
		MDC.put("sessionId", sessionId);
		if(currentUser.getName() != null){
			MDC.put("userName", currentUser.getName());
		} else {
			MDC.put("userName", "none");
		}
		return true;
	}

}
