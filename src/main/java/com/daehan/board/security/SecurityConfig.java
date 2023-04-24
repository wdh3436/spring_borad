package com.daehan.board.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig {

	private final AuthenticationSuccessHandler customSuccessHandler;
	private final AuthenticationFailureHandler customFailureHandler;
	private final UserDetailsService userDetailsService;
	
	/*	
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {

        String password = passwordEncoder().encode("1111");
        
        UserDetails user = User.builder().username("user").password(password).roles("USER").build();
        UserDetails manager = User.builder().username("manager").password(password).roles("MANAGER").build();
        UserDetails admin = User.builder().username("admin").password(password).roles("ADMIN").build();
        
        return new InMemoryUserDetailsManager(user, manager, admin);        
    }
	*/
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService);
	}
	
	private AccessDeniedHandler accessDeniedHandler() {
		CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
		accessDeniedHandler.setErrorPage("/denied");
		return accessDeniedHandler;
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }
	
	public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
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
			.csrf()
				.disable() // post method 동작을 위한 코드
			.authorizeHttpRequests()           
				.requestMatchers("/user").hasRole("USER")
				.requestMatchers("/manager").hasRole("MANAGER")
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().permitAll()
				.and()
        	.formLogin()
        		.loginPage("/login")                    // controller mapping
        		.loginProcessingUrl("/login_proc")      // th:action="@{/login_proc}"
        		.defaultSuccessUrl("/home")
        		.successHandler(customSuccessHandler)	// 성공시 수행할 핸들러
        		.failureHandler(customFailureHandler)	// 실패 핸들러
        		.permitAll();
		// 인증 거부 관련 처리
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        
		return http.build();		
    }    
}