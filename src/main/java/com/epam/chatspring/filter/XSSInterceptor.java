package com.epam.chatspring.filter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

@Component
public class XSSInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		if (isValidRequest(request)) {
			return true;
		} else {
			response.sendError(406, "XSS injection was finded");
			return false;
		}

	}

	private boolean isValidRequest(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			String value = request.getParameter(paramName);
			if (!value.equals(HtmlUtils.htmlEscape(value))) {
				return false;
			}
		}
		return true;
	}

}
