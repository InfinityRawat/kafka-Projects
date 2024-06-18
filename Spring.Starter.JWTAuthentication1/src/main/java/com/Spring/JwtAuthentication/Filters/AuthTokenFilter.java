package com.Spring.JwtAuthentication.Filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Spring.JwtAuthentication.Utils.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetails;
	@Autowired
	private JWTUtils utils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
        log.info("AuthTokenFilter called for URI: {}", request.getRequestURI());
        String userName = null;

        	String header = request.getHeader("Authorization");
        	
		try {
				String jwt = parseJwt(request);
				if(jwt!=null && utils.validateJwtToken(jwt)) {
					
					 userName = utils.getUserName(jwt);
					 
					 UserDetails loadUserByUsername = userDetails.loadUserByUsername(userName);
					 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loadUserByUsername,null,loadUserByUsername.getAuthorities());
					 log.info("UserName loadUSerBy userName - > "+authToken.getDetails());
					 
					 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					 SecurityContextHolder.getContext().setAuthentication(authToken);
				}
		}
		
		catch(Exception ex) {
			log.info("exception is ->  "+ex.getMessage());
			log.info("jwt token is ->  "+header);
		}
		
        filterChain.doFilter(request, response);

	}

	private String parseJwt(HttpServletRequest request) {
			
		return utils.getJwtFromHeader(request);
	}

}
