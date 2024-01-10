package com.metanet.amatmu.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate<String, String> redisTemplate;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf((csrf) -> csrf.disable())
		.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers("/member/register", "/member/login", "/member/checkemail", "/member/checknickname",
					"/member/checkphonenumber", "/member/profileimg", "/member/sendauthcode/**", "/member/checkauthcode",
					"/member/find/**").permitAll()
			.requestMatchers("/member/logout", "member/delete").authenticated()
			.requestMatchers("member/info", "member/info/update").hasRole("USER")
			.requestMatchers("/restaurant/favorite/**").hasRole("USER")
			.requestMatchers("/restaurant/favorites/**").hasRole("USER")
			.requestMatchers("/restaurant/**").hasRole("USER")
			.requestMatchers("/notice/**").hasRole("USER")
			.requestMatchers("/admin/notice/**").hasRole("ADMIN")

			.requestMatchers("/bm/register", "/bm/images", "/bm/login").permitAll()
			.requestMatchers("/bm/info/**").hasRole("BMAN")

		)
		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
	            UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
