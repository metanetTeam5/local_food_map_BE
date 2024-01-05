package com.metanet.amatmu.member.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.metanet.amatmu.member.dao.IMemberRepository;



@Component
public class MemberUserDetailsService implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IMemberRepository memberDao;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Member memberInfo = memberDao.selectMember(memberId);
		if (memberInfo==null) {
			throw new UsernameNotFoundException("["+ memberId +"] 사용자를 찾을 수 없습니다.");
		}
		
		String role = memberInfo.getRole();
		String[] roleArr = new String[1];
		roleArr[0] = role;
		logger.info(memberInfo.getEmail());

		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roleArr);

		return new MemberUserDetails(memberInfo.getEmail(), 
				memberInfo.getPassword(), authorities);
	}

}

