package com.metanet.amatmu.businessman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.businessman.dao.IBusinessmanRepository;
import com.metanet.amatmu.businessman.dto.BmImageResultDto;
import com.metanet.amatmu.businessman.dto.BmInfoDto;
import com.metanet.amatmu.businessman.dto.BmRegisterDto;
import com.metanet.amatmu.businessman.dto.BmRestaurantInfoDto;
import com.metanet.amatmu.businessman.dto.BmUpdateRestImgDto;
import com.metanet.amatmu.businessman.dto.BmUpdateRestaurantInfoDto;
import com.metanet.amatmu.businessman.dto.UpdateBmInfoDto;
import com.metanet.amatmu.businessman.exception.BusinessmanErrorCode;
import com.metanet.amatmu.businessman.exception.BusinessmanException;
import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.config.security.JwtTokenProvider;
import com.metanet.amatmu.member.dto.MemberLoginDto;
import com.metanet.amatmu.member.dto.MemberLoginResultDto;
import com.metanet.amatmu.member.dto.MemberRegisterDto;
import com.metanet.amatmu.member.exception.MemberErrorCode;
import com.metanet.amatmu.member.exception.MemberException;
import com.metanet.amatmu.member.model.Member;
import com.metanet.amatmu.member.model.MemberUserDetails;
import com.metanet.amatmu.member.service.IMemberService;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.utils.S3Uploader;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BusinessmanService implements IBusinessmanService{
	
	@Autowired
	IBusinessmanRepository bmDao;
	
	@Autowired
	IMemberService memberService;
	
	@Autowired
	S3Uploader s3Uploader;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider provider;
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	IRestaurantRepository restDao;

	@Override
	public Long selectMaxBmNo() {
		return bmDao.selectMaxBmNo();
	}

	@Override
	public void registerBm(BmRegisterDto bmDto) {
		checkBmRegister(bmDto);
		
		try {
		
		MemberRegisterDto memberDto = new MemberRegisterDto();
		memberDto.setEmail(bmDto.getEmail());
		memberDto.setPassword(bmDto.getPassword());
		memberDto.setNickname(bmDto.getNickname());
		memberDto.setName(bmDto.getName());
		memberDto.setGender(bmDto.getGender());
		memberDto.setBirthDate(bmDto.getBirthDate());
		memberDto.setPhoneNumber(bmDto.getPhoneNumber());
		
		memberService.registerMember(memberDto, "ROLE_USER");
		
		Member member = memberService.selectMember(bmDto.getEmail());
		
		Businessman bm = new Businessman();
		bm.setBusinessmanId(selectMaxBmNo() + 1);
		bm.setMemberId(member.getMemberId());
		bm.setCompanyName(bmDto.getCompanyName());
		bm.setLicenseNumber(bmDto.getLicenseNumber());
		bm.setRegistration(bmDto.getRegistration());
		bm.setReport(bmDto.getReport());
		bm.setBankbook(bmDto.getBankbook());
		bm.setAccount(bmDto.getAccount());
		bm.setStatus("WAIT");
		
		bmDao.registerBm(bm);
		
		} catch(Exception e) {			
			memberService.deleteMember(bmDto.getEmail(), bmDto.getPassword());
			
			throw new BusinessmanException(BusinessmanErrorCode.REGISTER_FAILED);
		}
	}
	
	@Override
	public BmImageResultDto uploadBmImage(MultipartFile registration, MultipartFile report, MultipartFile bankbook) {
		String registrationUrl = s3Uploader.fileUpload(registration);
		String reportUrl = s3Uploader.fileUpload(report);
		String bankbookUrl = s3Uploader.fileUpload(bankbook);
		
		BmImageResultDto result = new BmImageResultDto();
		result.setRegistration(registrationUrl);
		result.setReport(reportUrl);
		result.setBankbook(bankbookUrl);
		
		return result;
	}

	@Override
	public boolean checkLicenseNumberDuplicate(String licenseNumber) {
		int result = bmDao.checkLicenseNumberDuplicate(licenseNumber);
		
		return !(result == 0);
	}
	
	@Override
	public MemberLoginResultDto bmLogin(MemberLoginDto loginDto) {
		Member member = memberService.selectMember(loginDto.getEmail());
		
		checkMemberNull(member);
		
		if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
			throw new MemberException(MemberErrorCode.WRONG_PASSWORD);
		}
		
		if (!"ROLE_BMAN".equals(member.getRole())) {
			throw new BusinessmanException(BusinessmanErrorCode.INVALID_ROLE);
		}
		
		String token = provider.generateToken(member);
		
		MemberLoginResultDto result = new MemberLoginResultDto();
		result.setUserId(member.getMemberId());
		result.setUserEmail(member.getEmail());
		result.setToken(token);
		result.setUserProfileImg(member.getProfileImg());
		
		return result;
	}
	
	@Override
	public String bmLogout(HttpServletRequest request) {
		String token = provider.resolveToken(request);
	    if (!provider.validateToken(token)) {
	      throw new MemberException(MemberErrorCode.EXPIRED_TOKEN);
	    }

	    var expiration = provider.getExpiration(token);
	    redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);

	    return "로그아웃 성공";
	}
	
	@Override
	public List<BmInfoDto> getBmInfo(String email) {
		Member member = memberService.selectMember(email);
		Long memberId = member.getMemberId();
		List<Businessman> bmList = new ArrayList<>();
		bmList = bmDao.getBmListByMemberId(memberId);
		List<BmInfoDto> infoList = new ArrayList<>();
		
		for (int i = 0; i < bmList.size(); i++) {
			Businessman bm = bmList.get(i);
			
			BmInfoDto info = new BmInfoDto();
			info.setBusinessmanId(bm.getBusinessmanId());
			info.setCompanyName(bm.getCompanyName());
			info.setLicenseNumber(bm.getLicenseNumber());
			info.setRegistration(bm.getRegistration());
			info.setReport(bm.getReport());
			info.setBankbook(bm.getBankbook());
			info.setCreateDate(bm.getCreateDate());
			info.setGrantDate(bm.getGrantDate());
			info.setAccount(bm.getAccount());
			info.setStatus(bm.getStatus());
			
			infoList.add(info);
		}
		
		return infoList;
	}
	
	@Override
	public BmInfoDto updateBmInfo(MemberUserDetails member, UpdateBmInfoDto updateBmInfoDto) {
		Businessman bm = bmDao.getBmByBmId(updateBmInfoDto.getBusinessmanId());
		
		if (bm == null) {
			throw new BusinessmanException(BusinessmanErrorCode.BM_NOT_FOUND);
		}
		
		if (bm.getMemberId() != member.getMemberId()) {
			throw new BusinessmanException(BusinessmanErrorCode.INVALID_ROLE);
		}
		
		bmDao.updateBmInfo(updateBmInfoDto);
		
		BmInfoDto info = new BmInfoDto();
		
		Businessman updateBm = bmDao.getBmByBmId(updateBmInfoDto.getBusinessmanId());
		
		info.setBusinessmanId(updateBm.getBusinessmanId());
		info.setCompanyName(updateBm.getCompanyName());
		info.setLicenseNumber(updateBm.getLicenseNumber());
		info.setRegistration(updateBm.getRegistration());
		info.setReport(updateBm.getReport());
		info.setBankbook(updateBm.getBankbook());
		info.setCreateDate(updateBm.getCreateDate());
		info.setGrantDate(updateBm.getGrantDate());
		info.setAccount(updateBm.getAccount());
		info.setStatus(updateBm.getStatus());
		
		return info;
	}
	
	@Override
	public BmRestaurantInfoDto getBmRestInfo(MemberUserDetails member) {
		Businessman bm = bmDao.getBmListByMemberId(member.getMemberId()).get(0);
		
		Restaurant rest = restDao.selectRestaurantByRestId(bm.getRestaurantId());
		
		BmRestaurantInfoDto dto = new BmRestaurantInfoDto();
		dto.setRestId(rest.getRestId());
		dto.setRestName(rest.getRestName());
		dto.setRestKeyword(rest.getRestKeyword());
		dto.setRestOpenTime(rest.getRestOpenTime());
		dto.setRestCloseTime(rest.getRestCloseTime());
		dto.setRestPhoneNumber(rest.getRestPhoneNumber());
		dto.setRestDeposit(rest.getRestDeposit());
		dto.setRestImg(rest.getRestImg());
		
		return dto;
	}
	
	@Override
	public String updateRestaurantImage(MultipartFile restImg, long restId) {
		String restImgUrl = s3Uploader.fileUpload(restImg);
		
		BmUpdateRestImgDto dto = new BmUpdateRestImgDto();
		dto.setRestId(restId);
		dto.setRestImg(restImgUrl);
		
		bmDao.updateRestaurantImage(dto);
		
		return restImgUrl;
	}
	
	@Override
	public String updateRestaurantInfo(BmUpdateRestaurantInfoDto dto) {
		bmDao.updateRestaurantInfo(dto);
		
		return "식당 정보 수정 완료";
	}

	private void checkBmRegister(BmRegisterDto bmDto) {
		if (bmDto.getCompanyName() == null || bmDto.getLicenseNumber() == null || 
				bmDto.getAccount() == null) {
			throw new BusinessmanException(BusinessmanErrorCode.INVALID_REGISTER);
		}
		
		if (checkLicenseNumberDuplicate(bmDto.getLicenseNumber())) {
			throw new BusinessmanException(BusinessmanErrorCode.LICENSE_DUPLICATED);
		}
	}
	
	private void checkMemberNull(Member member) {
		if (member == null) {
			throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
		}
	}
}
