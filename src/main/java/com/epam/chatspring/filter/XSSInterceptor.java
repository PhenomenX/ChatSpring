package com.epam.chatspring.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

@Component
public class XSSInterceptor implements HandlerInterceptor {

	@Value("${message.error.xss}")
	private String xssMessage;

	private static final Logger logger = Logger.getLogger(XSSInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		if (isValidRequest(request)) {
			return true;
		} else {
			response.setHeader("message", xssMessage);
			response.sendError(406, xssMessage);
			return false;
		}

	}

	private boolean isValidRequest(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		logger.debug("Checking request on xss attack");
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			String value = request.getParameter(paramName);
			if (!value.equals(HtmlUtils.htmlEscape(value))) {
				logger.debug("Xss attack was finded. Request wasn't executed.");
				return false;
			}
		}
		logger.debug("Request is valid");
		return true;
	}

}
