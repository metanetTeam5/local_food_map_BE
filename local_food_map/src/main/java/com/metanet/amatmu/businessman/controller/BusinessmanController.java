package com.metanet.amatmu.businessman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.metanet.amatmu.businessman.dto.BmRestaurantInfoDto;
import com.metanet.amatmu.businessman.dto.BmReviewDto;
import com.metanet.amatmu.businessman.dto.BmUpdateRestaurantInfoDto;
import com.metanet.amatmu.businessman.dto.UpdateBmInfoDto;
import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.businessman.service.IBusinessmanService;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.dto.MemberLoginResultDto;
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
			MultipartFile registration, MultipartFile report, MultipartFile bankbook, MultipartFile residence) {
		return ResponseEntity.status(201).body(businessmanService.uploadBmImage(registration, report, bankbook, residence));
	}
	
	@PostMapping("/login")
	public ResponseEntity<MemberLoginResultDto> bmLogin(@RequestBody MemberLoginDto loginDto) {
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
	
	@GetMapping("/restaurant/info")
	public ResponseEntity<BmRestaurantInfoDto> getBmRestInfo(@AuthenticationPrincipal MemberUserDetails member) {
		return ResponseEntity.ok(businessmanService.getBmRestInfo(member));
	}
	
	@PutMapping("/update/restaurant/image/{restId}")
	public ResponseEntity<String> updateRestaurantImage(MultipartFile restImg, @PathVariable long restId) {
		return ResponseEntity.ok(businessmanService.updateRestaurantImage(restImg, restId));
	}
	
	@PutMapping("/restaurant/info/update")
	public ResponseEntity<String> updateRestaurantInfo(@RequestBody BmUpdateRestaurantInfoDto dto) {
		return ResponseEntity.ok(businessmanService.updateRestaurantInfo(dto));
	}
	
	@GetMapping("/review/list")
	public ResponseEntity<List<BmReviewDto>> getReviewList(@AuthenticationPrincipal MemberUserDetails member) {
		return ResponseEntity.ok(businessmanService.getReviewList(member));
	}
	
	@GetMapping("/admin/request/list")
	public ResponseEntity<List<Businessman>>	getBmanPartnershipRequests() {
		return ResponseEntity.ok(businessmanService.getBmanPartnershipRequests());
	}
	
	@GetMapping("/admin/accept/{businessmanId}")
	public ResponseEntity<String>	acceptBmanPartnership(@PathVariable Long businessmanId) {
		return ResponseEntity.ok(businessmanService.acceptBmanPartnership(businessmanId));
	}
	
}
