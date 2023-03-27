package com.daehan.board.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {

        String password = passwordEncoder().encode("1111");
        
        UserDetails user = User.builder().username("user").password(password).roles("USER").build();
        UserDetails manager = User.builder().username("manager").password(password).roles("MANAGER").build();
        UserDetails admin = User.builder().username("admin").password(password).roles("ADMIN").build();
        
        return new InMemoryUserDetailsManager(user, manager, admin);        
    }

    @Bean
    // BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체입니다.
    // Service에서 비밀번호를 암호화할 수 있도록 Bean으로 등록합니다.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // post method 동작을 위한 코드
			.authorizeHttpRequests()			
			.requestMatchers("/home").permitAll()             
			.requestMatchers("/user").hasRole("USER")
			.requestMatchers("/manager").hasRole("MANAGER")
        	.requestMatchers("/admin").hasRole("ADMIN")
        	.anyRequest().permitAll()
        	.and()
        	.formLogin();
		
		return http.build();		
    }
}