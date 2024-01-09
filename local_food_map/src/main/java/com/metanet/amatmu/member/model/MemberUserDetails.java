package com.metanet.amatmu.member.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberUserDetails extends User {

	private static final long serialVersionUID = 1L;
	
	private Long memberId;
	
	public MemberUserDetails(String username, String password, 
			Collection<? extends GrantedAuthority> authorities, Long memberId) {
		super(username, password, authorities);
		this.memberId = memberId;
	}
	
	public Long getMemberId() {
		return this.memberId;
	}
}
