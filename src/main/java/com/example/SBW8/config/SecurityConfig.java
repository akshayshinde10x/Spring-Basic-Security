package com.example.SBW8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/home").permitAll()
				.anyRequest().authenticated()
			)
			.userDetailsService(userDetailsService())
			.httpBasic(httpBasic -> httpBasic.realmName("MyApp"));
			
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		 UserDetails user = User.builder()
	                .username("Test")
	                .password("{noop}password123")  //https://bcrypt-generator.com/
	                .build();
	        return new InMemoryUserDetailsManager(user);
	}	
}