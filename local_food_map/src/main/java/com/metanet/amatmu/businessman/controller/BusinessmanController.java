package com.metanet.amatmu.businessman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.businessman.dto.BmImageResultDto;
import com.metanet.amatmu.businessman.dto.BmInfoDto;
import com.metanet.amatmu.businessman.dto.BmRegisterDto;
import com.metanet.amatmu.businessman.dto.BmRegisterResultDto;
import com.metanet.amatmu.businessman.dto.UpdateBmInfoDto;
import com.metanet.amatmu.businessman.service.IBusinessmanService;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.model.MemberUserDetails;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bm")
public class BusinessmanController {
	
	@Autowired
	IBusinessmanService businessmanService;

	@PostMapping("/register")
	public ResponseEntity<BmRegisterResultDto> registerBm(@RequestBody BmRegisterDto bmDto) {
		businessmanService.registerBm(bmDto);
		return ResponseEntity.status(201).body(
				new BmRegisterResultDto(bmDto.getEmail(), bmDto.getNickname(), bmDto.getName(), 
						bmDto.getPhoneNumber(), bmDto.getCompanyName(), bmDto.getLicenseNumber(),
						bmDto.getAccount()));
	}
	
	@PostMapping("/images")
	public ResponseEntity<BmImageResultDto> uploadBmImage(
			MultipartFile registration, MultipartFile report, MultipartFile bankbook) {
		return ResponseEntity.status(201).body(businessmanService.uploadBmImage(registration, report, bankbook));
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> bmLogin(@RequestBody MemberLoginDto loginDto) {
		return ResponseEntity.ok(businessmanService.bmLogin(loginDto));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> bmLogout(HttpServletRequest request) {
		return ResponseEntity.ok(businessmanService.bmLogout(request));
	}
	
	@GetMapping("/info")
	public ResponseEntity<List<BmInfoDto>> getBmInfo(@AuthenticationPrincipal MemberUserDetails member) {
		return ResponseEntity.ok(businessmanService.getBmInfo(member.getUsername()));
	}
	
	@PutMapping("/info/update")
	public ResponseEntity<BmInfoDto> updateBmInfo(
			@AuthenticationPrincipal MemberUserDetails member,
			@RequestBody UpdateBmInfoDto updateBmInfoDto
			) {
		return ResponseEntity.ok(businessmanService.updateBmInfo(member, updateBmInfoDto));
	}
}
