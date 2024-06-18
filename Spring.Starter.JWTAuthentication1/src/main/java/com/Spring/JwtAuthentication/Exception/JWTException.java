package com.Spring.JwtAuthentication.Exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JWTException implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
			
		
		log.info("this is Authentication Entry Point...");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		 final Map<String, Object> body = new HashMap<>();
	        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
	        body.put("error", "Unauthorized");
	        body.put("message", authException.getMessage());
	        body.put("path", request.getServletPath());

	        final ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(response.getOutputStream(), body);
	}

}
