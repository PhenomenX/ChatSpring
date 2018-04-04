package com.epam.chatspring.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

public class XSSPreventionFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (isValidRequest(httpRequest)) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			httpResponse.sendError(406, "XSS injection was finded");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

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
