package com.metanet.amatmu.config.security;

import java.io.IOException;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	JwtTokenProvider jwtTokenProvider;
	RedisTemplate<String, String> redisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(request);

	    if (token != null && jwtTokenProvider.validateToken(token)) {
	      String isLogout = redisTemplate.opsForValue().get(token);
	      if (ObjectUtils.isEmpty(isLogout)) {

	        Authentication authentication = jwtTokenProvider.getAuthentication(token);
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	      }
	    }
	    filterChain.doFilter(request, response);
		
	}

}
