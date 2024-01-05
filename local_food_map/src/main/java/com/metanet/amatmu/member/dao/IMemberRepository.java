package com.metanet.amatmu.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.member.dto.MemberEmailProfileDto;
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
	int updateMemberInfo(Member member);
	int deleteMember(String email);
	int updateMemberProfileImg(MemberEmailProfileDto memberEmailProfileDto);
}
