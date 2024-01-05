package com.metanet.amatmu.member.service;

import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.member.dto.MemberInfoDto;
import com.metanet.amatmu.member.dto.MemberRegisterDto;
import com.metanet.amatmu.member.dto.UpdateMemberInfoDto;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.model.Member;

import jakarta.servlet.http.HttpServletRequest;

public interface IMemberService {

	Long selectMaxMemberNo();
	void registerMember(MemberRegisterDto memberDto);
	void uploadMemberProfileImg(String email, MultipartFile file);
	boolean checkEmailDuplicate(String email);
	boolean checkNicknameDuplicate(String nickname);
	boolean checkPhoneNumberDuplicate(String phoneNumber);
	String sendAuthCode(String phoneNumber);
	Member selectMember(String email);
	String memberLogin(MemberLoginDto loginDto);
	String memberLogout(HttpServletRequest request);
	MemberInfoDto getMemberInfo(String email);
	MemberInfoDto updateMemberInfo(String email, UpdateMemberInfoDto updateMemberInfoDto);
	String deleteMember(String email, String password);
}
