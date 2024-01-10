package com.metanet.amatmu.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.member.dto.MemberInfoDto;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.dto.MemberRegisterDto;
import com.metanet.amatmu.member.dto.MemberRegisterResultDto;
import com.metanet.amatmu.member.dto.UpdateMemberInfoDto;
import com.metanet.amatmu.member.model.MemberUserDetails;
import com.metanet.amatmu.member.service.IMemberService;

import jakarta.servlet.http.HttpServletRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	IMemberService memberService;
	
	@PostMapping("/register")
	public ResponseEntity<MemberRegisterResultDto> registerMember(@RequestBody MemberRegisterDto memberDto) {
		memberService.registerMember(memberDto, "ROLE_USER");
		return ResponseEntity.status(201).body(
				new MemberRegisterResultDto(memberDto.getEmail(), memberDto.getNickname(), memberDto.getName(), memberDto.getPhoneNumber()));
	}
	
	@PostMapping("/profileimg")
	public ResponseEntity<String> uploadMemberProfileImg(String email, MultipartFile file) {
		memberService.uploadMemberProfileImg(email, file);
		return ResponseEntity.ok("프로필사진 등록 완료");
	}
	
	@PutMapping("profileimg/update")
	public ResponseEntity<String> updateMemberProfileImg(@AuthenticationPrincipal MemberUserDetails member, MultipartFile file) {
		memberService.uploadMemberProfileImg(member.getUsername(), file);
		return ResponseEntity.ok("프로필사진 수정 완료");
	}
	
	@GetMapping("/checkemail")
	public ResponseEntity<String> checkEmailDuplicate(@RequestParam String email) {
		if (memberService.checkEmailDuplicate(email)) {
			return ResponseEntity.status(409).body("중복된 이메일");
		} else {
			return ResponseEntity.status(200).body("사용 가능한 이메일");
		}
	}
	
	@GetMapping("/checknickname")
	public ResponseEntity<String> checkNicknameDuplicate(@RequestParam String nickname) {
		if (memberService.checkNicknameDuplicate(nickname)) {
			return ResponseEntity.status(409).body("중복된 닉네임");
		} else {
			return ResponseEntity.status(200).body("사용 가능한 닉네임");
		}
	}
	
	@GetMapping("/checkphoneNumber")
	public ResponseEntity<String> checkPhoneNumberDuplicate(@RequestParam String phoneNumber) {
		if (memberService.checkPhoneNumberDuplicate(phoneNumber)) {
			return ResponseEntity.status(409).body("중복된 전화 번호");
		} else {
			return ResponseEntity.status(200).body("사용 가능한 전화 번호");
		}
	}
	
	@GetMapping("/sendauthcode/{phoneNumber}")
	public ResponseEntity<SingleMessageSentResponse> sendAuthCode(@PathVariable String phoneNumber) {
		return ResponseEntity.ok(memberService.sendAuthCode(phoneNumber));
	}
	
	@GetMapping("/checkauthcode")
	public ResponseEntity<String> checkAuthCode(@RequestParam String phoneNumber, @RequestParam String code) {
		if (memberService.checkAuthCode(phoneNumber, code)) {
			return ResponseEntity.ok("인증번호 일치");
		} else {
			return ResponseEntity.status(400).body("인증번호 불일치");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> memberLogin(@RequestBody MemberLoginDto loginDto) {
		return ResponseEntity.ok(memberService.memberLogin(loginDto));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> memberLogout(HttpServletRequest request) {
		return ResponseEntity.ok(memberService.memberLogout(request));
	}
	
	@GetMapping("/info")
	public ResponseEntity<MemberInfoDto> getMemberInfo(@AuthenticationPrincipal MemberUserDetails member) {
		return ResponseEntity.ok(memberService.getMemberInfo(member.getUsername()));
	}
	
	@PutMapping("/info/update")
	public ResponseEntity<MemberInfoDto> updateMemberInfo(
			@AuthenticationPrincipal User member,
			@RequestBody UpdateMemberInfoDto updateMemberInfoDto) {
		return ResponseEntity.ok(memberService.updateMemberInfo(member.getUsername(), updateMemberInfoDto));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteMember(@AuthenticationPrincipal MemberUserDetails member, @RequestBody Map<String, String> password) {
		return ResponseEntity.ok(memberService.deleteMember(member.getUsername(), password.get("password")));
	}
	
	@GetMapping("/find/email")
	public ResponseEntity<String> findEmail(@RequestParam String phoneNumber) {
		return ResponseEntity.ok(memberService.findEmail(phoneNumber));
	}
	
	@GetMapping("/find/password")
	public ResponseEntity<SingleMessageSentResponse> sendAuthCode(@RequestParam String email, @RequestParam String phoneNumber) {
		return ResponseEntity.ok(memberService.findPassword(email, phoneNumber));
	}
}
