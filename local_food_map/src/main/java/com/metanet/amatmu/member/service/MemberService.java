package com.metanet.amatmu.member.service;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.config.security.JwtTokenProvider;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.member.dto.MemberEmailProfileDto;
import com.metanet.amatmu.member.dto.MemberInfoDto;
import com.metanet.amatmu.member.dto.MemberRegisterDto;
import com.metanet.amatmu.member.dto.UpdateMemberInfoDto;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.exception.MemberErrorCode;
import com.metanet.amatmu.member.exception.MemberException;
import com.metanet.amatmu.member.model.Member;
import com.metanet.amatmu.utils.S3Uploader;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MemberService implements IMemberService{
	
	@Autowired
	IMemberRepository memberDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider provider;
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	S3Uploader s3Uploader;

	@Override
	public Long selectMaxMemberNo() {
		return memberDao.selectMaxMemberNo();
	}

	@Override
	public void registerMember(MemberRegisterDto memberDto) {
		checkRegister(memberDto);
		
		Member member = new Member();
		member.setMemberId(selectMaxMemberNo() + 1);
		member.setEmail(memberDto.getEmail());
		member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
		member.setNickname(memberDto.getNickname());
		member.setName(memberDto.getName());
		Character memberGender = null;
		if ("남성".equals(memberDto.getGender())) {
			memberGender = 'M';
		} else {
			memberGender = 'F';
		}
		member.setProfileImg("");
		member.setGender(memberGender);
		member.setBirthDate(memberDto.getBirthDate());
		member.setPhoneNumber(memberDto.getPhoneNumber());

		member.setRole("ROLE_USER");
		member.setIsDeleted('N');
		
		try {
			memberDao.registerMember(member);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			throw new MemberException(MemberErrorCode.REGISTER_FAILED);
		}
	}
	
	@Override
	public void uploadMemberProfileImg(String email, MultipartFile file) {
			String profileImg = s3Uploader.fileUpload(file);
			
			MemberEmailProfileDto memberEmailProfileDto = new MemberEmailProfileDto();
			memberEmailProfileDto.setEmail(email);
			memberEmailProfileDto.setProfileImg(profileImg);
			
			memberDao.updateMemberProfileImg(memberEmailProfileDto);
	}

	@Override
	public boolean checkEmailDuplicate(String email) {
		int result = memberDao.checkEmailDuplicate(email);
		
		return !(result == 0);
	}
	

	@Override
	public boolean checkPhoneNumberDuplicate(String phoneNumber) {
		int result = memberDao.checkPhoneNumberDuplicate(phoneNumber);
		
		return !(result == 0);
	}
	
	@Override
	public boolean checkNicknameDuplicate(String nickname) {
		int result = memberDao.checkNicknameDuplicate(nickname);
		
		return !(result == 0);
	}
	
	@Override
	public String sendAuthCode(String phoneNumber) {
		return null;
	}
	
	@Override
	public Member selectMember(String email) {
		return memberDao.selectMember(email);
	}

	@Override
	public String memberLogin(MemberLoginDto loginDto) {
		Member member = selectMember(loginDto.getEmail());
		if (member == null) {
			throw new MemberException(MemberErrorCode.EMAIL_NOT_EXISTS);
		}
		if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
			throw new MemberException(MemberErrorCode.WRONG_PASSWORD);
		}		
		
		return provider.generateToken(member);
	}
	

	@Override
	public String memberLogout(HttpServletRequest request) {
		String token = provider.resolveToken(request);
	    if (!provider.validateToken(token)) {
	      throw new MemberException(MemberErrorCode.EXPIRED_TOKEN);
	    }

	    var expiration = provider.getExpiration(token);
	    redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);

	    return "로그아웃 성공";
	}
	
	@Override
	public MemberInfoDto getMemberInfo(String email) {
		Member member = selectMember(email);
		MemberInfoDto memberInfo = new MemberInfoDto();
		memberInfo.setEmail(member.getEmail());
		memberInfo.setNickname(member.getNickname());
		memberInfo.setName(member.getName());
		memberInfo.setGender(member.getGender());
		memberInfo.setBirthDate(member.getBirthDate());
		memberInfo.setPhoneNumber(member.getPhoneNumber());
		memberInfo.setProfileImg(member.getProfileImg());
		memberInfo.setCreateDate(member.getCreateDate());
		return memberInfo;
	}
	
	@Override
	public MemberInfoDto updateMemberInfo(String email, UpdateMemberInfoDto updateMemberInfoDto) {
		Member member = selectMember(email);
		if (!passwordEncoder.matches(updateMemberInfoDto.getPassword(), member.getPassword())) {
			throw new MemberException(MemberErrorCode.WRONG_PASSWORD);
		}
		if (updateMemberInfoDto.getNewPassword() != null) {
			member.setPassword(passwordEncoder.encode(updateMemberInfoDto.getNewPassword()));
		}
		member.setNickname(updateMemberInfoDto.getNickname());
		member.setPhoneNumber(updateMemberInfoDto.getPhoneNumber());
		member.setProfileImg(updateMemberInfoDto.getProfileImg());
		
		memberDao.updateMemberInfo(member);
		
		MemberInfoDto memberInfo = new MemberInfoDto();
		memberInfo.setEmail(member.getEmail());
		memberInfo.setNickname(member.getNickname());
		memberInfo.setName(member.getName());
		memberInfo.setGender(member.getGender());
		memberInfo.setBirthDate(member.getBirthDate());
		memberInfo.setPhoneNumber(member.getPhoneNumber());
		memberInfo.setProfileImg(member.getProfileImg());
		memberInfo.setCreateDate(member.getCreateDate());
		return memberInfo;
	}
	
	@Override
	public String deleteMember(String email, String password) {
		Member member = selectMember(email);
		System.out.println(password);
		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new MemberException(MemberErrorCode.WRONG_PASSWORD);
		}
		
		memberDao.deleteMember(email);
		
		return "회원 탈퇴 완료";
	}
	
	private void checkRegister(MemberRegisterDto member) {
		if (member.getEmail() == null || member.getPassword() == null || member.getNickname() == null ||
			member.getName() == null || member.getGender() == null || member.getBirthDate() == null || 
			member.getPhoneNumber() == null) {
			throw new MemberException(MemberErrorCode.INVALID_REGISTER);
		}
		
		if (checkEmailDuplicate(member.getEmail())) {
			throw new MemberException(MemberErrorCode.EMAIL_DUPLICATED);
		}
		
		String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
	    Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	    Matcher matcher = pattern.matcher(member.getPassword());
	    
		if (!matcher.matches()) {
			throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
		}
		
		if (checkNicknameDuplicate(member.getNickname())) {
			throw new MemberException(MemberErrorCode.NICKNAME_DUPLICATED);
		}
		
		if (checkPhoneNumberDuplicate(member.getPhoneNumber())) {
			throw new MemberException(MemberErrorCode.PHONE_NUMBER_DUPLICATED);
		}
	}
}
