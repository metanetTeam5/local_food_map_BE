package com.metanet.amatmu.member.service;

import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.member.dto.MemberInfoDto;
import com.metanet.amatmu.member.dto.MemberRegisterDto;
import com.metanet.amatmu.member.dto.UpdateMemberInfoDto;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.model.Member;

import jakarta.servlet.http.HttpServletRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface IMemberService {

	Long selectMaxMemberNo();
	void registerMember(MemberRegisterDto memberDto, String role);
	void uploadMemberProfileImg(String email, MultipartFile file);
	boolean checkEmailDuplicate(String email);
	boolean checkNicknameDuplicate(String nickname);
	boolean checkPhoneNumberDuplicate(String phoneNumber);
	SingleMessageSentResponse sendAuthCode(String phoneNumber);
	boolean checkAuthCode(String phoneNumber, String code);
	Member selectMember(String email);
	String memberLogin(MemberLoginDto loginDto);
	String memberLogout(HttpServletRequest request);
	MemberInfoDto getMemberInfo(String email);
	MemberInfoDto updateMemberInfo(String email, UpdateMemberInfoDto updateMemberInfoDto);
	String deleteMember(String email, String password);
	String findEmail(String phoneNumber);
	SingleMessageSentResponse findPassword(String email, String phoneNumber);
}
