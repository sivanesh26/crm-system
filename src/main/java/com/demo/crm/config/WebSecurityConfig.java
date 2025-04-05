package com.demo.crm.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				// Customers
				.requestMatchers(HttpMethod.GET, "/customers/**").hasAnyRole("ADMIN", "SUPPORT")
				.requestMatchers(HttpMethod.POST, "/customers/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/customers/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/customers/**").hasRole("ADMIN")

				// Sales
				.requestMatchers(HttpMethod.GET, "/sales/**").hasAnyRole("ADMIN", "SALES")
				.requestMatchers(HttpMethod.POST, "/sales/**").hasAnyRole("ADMIN", "SALES")
				.requestMatchers(HttpMethod.PUT, "/sales/**").hasAnyRole("ADMIN", "SALES")
				.requestMatchers(HttpMethod.DELETE, "/sales/**").hasRole("ADMIN")

				// Reports
				.requestMatchers(HttpMethod.GET, "/reports/**").hasRole("ADMIN")

				// Interaction
				.requestMatchers(HttpMethod.GET, "/interactions/**").hasAnyRole("ADMIN", "SALES", "SUPPORT")
				.requestMatchers(HttpMethod.POST, "/interactions/**").hasAnyRole("ADMIN", "SALES")
				.requestMatchers(HttpMethod.PUT, "/interactions/**").hasAnyRole("ADMIN", "SALES")
				.requestMatchers(HttpMethod.DELETE, "/interactions/**").hasRole("ADMIN")

				.anyRequest().authenticated()).csrf(csrf -> csrf.disable()).httpBasic(withDefaults());

		return http.build();
	}
}
