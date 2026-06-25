package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		// セキュリティ対象外の設定
		http.authorizeHttpRequests(authorize -> authorize
				.anyRequest().permitAll()
			)
			
		
			.formLogin(login -> login
				    .loginPage("/login")
				    .usernameParameter("userId")
				    .passwordParameter("password")
				    .defaultSuccessUrl("/")
				    .failureUrl("/login?error")
				    .permitAll()
				);
		
		// CSRFを無効化
		http.csrf(csrf -> csrf.disable());
		
		return http.build();

	}
}