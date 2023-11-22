package com.zerobase.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.zerobase.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {
	
	private final MemberService memberService;
	
	@Bean
	UserAuthenticationFailuerHandler getFailuerHandler() {
		return new UserAuthenticationFailuerHandler();
	}
	
	@Bean
	PasswordEncoder getPasswordEncoder() {
			
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		http
			.authorizeRequests()
			.antMatchers(
					"/"
					,"/member/register"
					, "/member/email-auth"
					, "/member/find/password"
					, "/member/reset/password"
					)
			.permitAll();
		
		http.authorizeRequests()
			.antMatchers("/member/info").hasRole("USER");
		
		http.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAuthority("ROLE_ADMIN");
		
		http.exceptionHandling()
			.accessDeniedPage("/error/denied");
		
		http
		.formLogin().loginPage("/member/login").defaultSuccessUrl("/")
			.failureHandler(getFailuerHandler())
				.permitAll();
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
				.logoutSuccessUrl("/")
					.invalidateHttpSession(true);
		
		return http.build();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService)
			.passwordEncoder(getPasswordEncoder());
		

	}

	
}
