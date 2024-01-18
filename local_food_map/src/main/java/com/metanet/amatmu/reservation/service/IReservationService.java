package com.metanet.amatmu.reservation.service;

import java.util.List;

import com.metanet.amatmu.member.model.MemberUserDetails;
import com.metanet.amatmu.reservation.dto.BmReservationDto;
import com.metanet.amatmu.reservation.dto.ReservationInsertDto;
import com.metanet.amatmu.reservation.dto.ReservationResultDto;
import com.metanet.amatmu.reservation.dto.ReservationUpdateDto;
import com.metanet.amatmu.reservation.model.Reservation;

public interface IReservationService {
	List<ReservationResultDto> getMemberReservationList(long memberId);
	
	Reservation insertMemberReservation(long memberId, ReservationInsertDto reservationDto);
	List<BmReservationDto> getBmReservationList(long membId);
	
	Reservation updateMemberReservation(long memberId, long resvId, ReservationUpdateDto updateDto);
	Reservation cancelMemberReservation(long memberId, long resvId);
	
	Reservation updateReservationVisit(MemberUserDetails member, long resvId, String status);
	
	Reservation getReservationInfo(long resvId);
}
