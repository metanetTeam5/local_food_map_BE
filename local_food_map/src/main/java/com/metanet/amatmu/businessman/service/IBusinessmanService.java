package com.metanet.amatmu.businessman.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.businessman.dto.BmImageResultDto;
import com.metanet.amatmu.businessman.dto.BmInfoDto;
import com.metanet.amatmu.businessman.dto.BmRegisterDto;
import com.metanet.amatmu.businessman.dto.BmRestaurantInfoDto;
import com.metanet.amatmu.businessman.dto.BmReviewDto;
import com.metanet.amatmu.businessman.dto.BmUpdateRestaurantInfoDto;
import com.metanet.amatmu.businessman.dto.UpdateBmInfoDto;
import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.dto.MemberLoginResultDto;
import com.metanet.amatmu.member.model.MemberUserDetails;

import jakarta.servlet.http.HttpServletRequest;

public interface IBusinessmanService {

	Long selectMaxBmNo();
	void registerBm(BmRegisterDto bmDto);
	boolean checkLicenseNumberDuplicate(String licenseNumber);
	BmImageResultDto uploadBmImage(MultipartFile registration, MultipartFile report, MultipartFile bankbook);
	MemberLoginResultDto bmLogin(MemberLoginDto loginDto);
	String bmLogout(HttpServletRequest request);
	List<BmInfoDto> getBmInfo(String email);
	BmInfoDto updateBmInfo(MemberUserDetails member, UpdateBmInfoDto updateBmInfoDto);
	BmRestaurantInfoDto getBmRestInfo(MemberUserDetails member);
	String updateRestaurantImage(MultipartFile restImg, long restId);
	String updateRestaurantInfo(BmUpdateRestaurantInfoDto dto);
	List<BmReviewDto> getReviewList(MemberUserDetails member);
	List<Businessman>	getBmanPartnershipRequests();
	String				acceptBmanPartnership(Long businessmanId);
}
