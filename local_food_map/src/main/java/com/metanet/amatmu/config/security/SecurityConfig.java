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
			.requestMatchers("/member/register", "/member/login", "/member/checkEmail", "/member/checkNickname",
					"/member/checkPhoneNumber", "/member/profileImg").permitAll()
//			.requestMatchers("/admin/notice", "/admin/notice/{noticeId}").permitAll()
//			.requestMatchers("/restaurant/favorite", "/restaurant/favorite/{restaurantId}", "/restaurant/favorite/list").permitAll()
			.requestMatchers("/member/logout", "member/delete").authenticated()
			.requestMatchers("member/info", "member/info/update").hasRole("USER")
//			.requestMatchers("/restaurant/favorite/**", "/restaurant/favorite/list", "/restaurant/favorite/{restaurantId}").hasRole("USER")
			.requestMatchers("/restaurant/favorite").hasRole("USER")
			.requestMatchers("/restaurant/favorite/**","/restaurant/favorites/**").hasRole("USER")

			.requestMatchers("/admin/notice", "/admin/notice/{noticeId}").hasRole("ADMIN")
			.requestMatchers("/**").permitAll()
			
			
		)
		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate),
	            UsernamePasswordAuthenticationFilter.class);

	return http.build();
	}
}
