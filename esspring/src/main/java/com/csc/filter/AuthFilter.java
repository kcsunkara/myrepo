package com.csc.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.csc.service.AuthenticationService;

@Component("authFilter")
public class AuthFilter extends OncePerRequestFilter {

	private static final Logger logger = Logger.getLogger(AuthFilter.class);

	public static final String AUTHENTICATION_HEADER = "Authorization";
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logAllRequestHeaders(request); 

		String authCredentials = request.getHeader(AUTHENTICATION_HEADER);
		boolean authenticationStatus = authenticationService
				.authenticate(authCredentials);

		if (authenticationStatus) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	private void logAllRequestHeaders(HttpServletRequest request) {
		Enumeration eNames = request.getHeaderNames();
		logger.debug("REQUEST HEADERS START:");
		while (eNames.hasMoreElements()) {
			String name = (String) eNames.nextElement();
			String value = normalize(request.getHeader(name));
			logger.debug(name + " : " + value);
		}
		logger.debug("REQUEST HEADERS END:");
	}

	private String normalize(String value) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			sb.append(c);
			if (c == ';')
				sb.append("<br>");
		}
		return sb.toString();
	}
}
