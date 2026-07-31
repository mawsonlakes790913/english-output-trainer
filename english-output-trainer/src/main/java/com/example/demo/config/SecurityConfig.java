package com.example.demo.config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		


        
        // セキュリティ対象外の設定
     	http.authorizeHttpRequests(authorize -> authorize
     			.requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                    ).permitAll()
     			.requestMatchers("/").permitAll()
     			.requestMatchers("/login").permitAll()
                .requestMatchers("/study", "/study/**").permitAll()
                .requestMatchers("/signup", "/signup/**").permitAll()
                .requestMatchers("/complete").permitAll()
                .requestMatchers("/user/canceled").permitAll()
                .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
             )
			
		
     	.formLogin(login -> login
     		    .loginPage("/login")
     		    .usernameParameter("userId")
     		    .passwordParameter("password")
     		    .defaultSuccessUrl("/", false)
     		    .failureHandler(new AuthenticationFailureHandler() {
     		        @Override
     		        public void onAuthenticationFailure(HttpServletRequest request,
     		                                            HttpServletResponse response,
     		                                            AuthenticationException exception)
     		                throws IOException {

     		            HttpSession session = request.getSession(true);
     		            session.setAttribute(
     		                    "loginErrorMessage",
     		                    "ユーザ名かパスワードが正しくありません。");

     		            response.sendRedirect("/login");
     		        }
     		    })
     		    .permitAll()
     		)
			
			.logout(logout -> logout
				    .logoutUrl("/logout")
				    .logoutSuccessHandler(new LogoutSuccessHandler() {
				        @Override
				        public void onLogoutSuccess(HttpServletRequest request,
				                                    HttpServletResponse response,
				                                    Authentication authentication)
				                throws IOException {

				            HttpSession session = request.getSession(true);
				            session.setAttribute("logoutMessage", "ログアウトしました。");

				            response.sendRedirect("/");
				        }
				    })
				)
            
            .rememberMe(remember -> remember
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(3600)
            );
        
		
		// CSRFを無効化
		//http.csrf(csrf -> csrf.disable());
		
		return http.build();

	}
}