package com.Spring.JwtAuthentication.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.JwtAuthentication.DTOs.LoginRequest;
import com.Spring.JwtAuthentication.DTOs.LoginResponse;
import com.Spring.JwtAuthentication.Service.JwtService;
import com.Spring.JwtAuthentication.Utils.JWTUtils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/jwt")
@Slf4j
public class JWTController {
		
	
	
	@Autowired
	private JwtService service;
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	
	@PreAuthorize("{hasRole('ADMIN')}")
	@GetMapping("/admin")
	public ResponseEntity<?> admin(){
		
		return ResponseEntity.ok("This is admin");
		
	}
	
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
public ResponseEntity<?> user(){
		
		return ResponseEntity.ok("This is user");
		
	}

	
	@PostMapping("/signIn")
	public ResponseEntity<?> login(@RequestBody LoginRequest req){
			
		Authentication auth;
			
		auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getName(),req.getPassword()));
		
		log.info("authenticationManager authenticated uses---->   "+ auth.getDetails());
		SecurityContextHolder.getContext().setAuthentication(auth);
		log.info("stored in Context......");
		UserDetails userDetails = (UserDetails)auth.getPrincipal();
		
//		List<String> roles = new ArrayList<String>();
//		userDetails.getAuthorities().forEach(ath->roles.add(ath.getAuthority()));
		
		List<String> roles = userDetails.getAuthorities().stream().map(ath->ath.getAuthority()).toList();
		log.info("roles of usess is "+ roles);
		String jwtToken = jwtUtils.generateToken(userDetails);
		log.info("token generation ->   "+ jwtToken);
		LoginResponse response = new LoginResponse(jwtToken,userDetails.getUsername(),roles);
		
		return ResponseEntity.ok(response);
	}
	
	
}	

















