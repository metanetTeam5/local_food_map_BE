package com.metanet.amatmu.config.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.metanet.amatmu.member.model.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	private static MacAlgorithm alg = Jwts.SIG.HS256;
	private static final String SECRET_KEY = "amatmufoodlocalmapamatmufoodlocalmapamatmufoodlocalmap";
	private static SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60;
	
	@Autowired
	UserDetailsService userDetailService;
	
	@PostConstruct
	public void init() {
		log.debug("token provider bean created");
	}
	
	public String generateToken(Member member) {
		long now = System.currentTimeMillis();
		Claims claims = Jwts.claims()
				.subject(member.getEmail())
				.issuer(member.getName())
				.issuedAt(new Date(now))
				.expiration(new Date(now + ACCESS_TOKEN_EXPIRATION))
				.add("roles", member.getRole())
				.build();
		
		return Jwts.builder()
				.claims(claims)
				.signWith(key, alg)
				.compact();
	}
	
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser()
					.verifyWith(key).build()
					.parseSignedClaims(token);
			return !claims.getPayload().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getUserId(String token) {
		log.info(token);
		return Jwts.parser()
				.verifyWith(key).build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserId(token));
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public Long getExpiration(String token) {
	    Date expiration = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getExpiration();
	    long now = new Date().getTime();
	    return expiration.getTime() - now;
	  }
}
