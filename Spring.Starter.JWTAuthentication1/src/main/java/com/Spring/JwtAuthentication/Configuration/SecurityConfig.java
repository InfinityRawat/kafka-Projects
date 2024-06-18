package com.Spring.JwtAuthentication.Configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Spring.JwtAuthentication.Exception.JWTException;
import com.Spring.JwtAuthentication.Filters.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
		
	@Bean
	AuthTokenFilter authFilter() {
		
		return new AuthTokenFilter();
	}
	@Autowired
	private JWTException excep;
	
	@Bean
	
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(req-> req.requestMatchers("*/signIn").permitAll()
				.anyRequest().authenticated());
		
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.csrf(csrf->csrf.disable());
		http.exceptionHandling(ex->ex.authenticationEntryPoint(excep));
		http.addFilterBefore(authFilter(),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	UserDetailsService service(DataSource ds) {
		
		return new JdbcUserDetailsManager(ds);
	}
	
	@Bean
	PasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//     CommandLineRunner initData(UserDetailsService userDetailsService,DataSource ds) {
//        return args -> {
//            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;
//            UserDetails user1 = User.withUsername("user1")
//                    .password(encode().encode("password1"))
//                    .roles("USER")
//                    .build();
//            UserDetails admin = User.withUsername("admin")
//                    //.password(passwordEncoder().encode("adminPass"))
//                    .password(encode().encode("adminPass"))
//                    .roles("ADMIN")
//                    .build();
//
//            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(ds);
//            userDetailsManager.createUser(user1);
//            userDetailsManager.createUser(admin);
//        };
//	
//	}
	
    @Bean
     AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}



















