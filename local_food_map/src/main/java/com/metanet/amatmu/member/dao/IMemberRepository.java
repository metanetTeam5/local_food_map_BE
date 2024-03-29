package com.metanet.amatmu.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.member.dto.MemberEmailProfileDto;
import com.metanet.amatmu.member.dto.UpdateMemberInfoDto;
import com.metanet.amatmu.member.dto.UpdateMemberPasswordDto;
import com.metanet.amatmu.member.model.Member;

@Repository
@Mapper
public interface IMemberRepository {

	long selectMaxMemberNo();
	void registerMember(Member member);
	int checkEmailDuplicate(String email);
	int checkNicknameDuplicate(String nickname);
	int checkPhoneNumberDuplicate(String phoneNumber);
	Member selectMember(String email);
	Member selectMemberByPhoneNumber(String phoneNumber);
	int updateMemberInfo(Member member);
	int deleteMember(String email);
	int updateMemberProfileImg(MemberEmailProfileDto memberEmailProfileDto);
	int updateMemberPassword(UpdateMemberPasswordDto updateMemberInfoDto);

	Member	selectMemberById(Long membId);

	Member searchMemberByKakaoUserphonenumber(String phoneNumber);

}
