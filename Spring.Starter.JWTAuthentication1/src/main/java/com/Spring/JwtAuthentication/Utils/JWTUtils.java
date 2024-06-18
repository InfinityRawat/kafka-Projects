package com.Spring.JwtAuthentication.Utils;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Spring.JwtAuthentication.Repository.JwtRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JWTUtils {
	
	private JwtRepository repo;
	
	@Value("${spring.jwt.secret}")
	private String jwtSecret ;

	public String generateToken(UserDetails userDetails) {
		
		return Jwts.builder().setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()+ 10000 * 60 * 3))
					.signWith(key())
					.compact();
		
	}

	private Key key() {
			return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String getJwtFromHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		 
		log.info("Authorization header = {}",header);
		if(header!=null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}
	
		public String getUserName(String jwt) {
		
		  return Jwts.parserBuilder().setSigningKey(key())      
        .build().parseClaimsJws(jwt).getBody().getSubject();
        
	}

	public boolean validateJwtToken(String jwt) {
		
		try {
		 log.info("Validating User........");
		 Jws<Claims> parseClaimsJws = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt);
		 log.info("validate Token ..................."+ parseClaimsJws);
		return true;
		}
		catch(MalformedJwtException ex) {
			
			log.info("exception in validating token      "+ ex.getStackTrace());
			System.out.println(ex);
			return false;
		}
		 
	}

}
















