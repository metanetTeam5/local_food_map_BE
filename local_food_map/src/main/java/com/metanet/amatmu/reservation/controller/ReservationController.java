package com.metanet.amatmu.reservation.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metanet.amatmu.member.model.MemberUserDetails;
import com.metanet.amatmu.reservation.dto.BmReservationDto;
import com.metanet.amatmu.reservation.dto.ReservationInsertDto;
import com.metanet.amatmu.reservation.dto.ReservationResultDto;
import com.metanet.amatmu.reservation.dto.ReservationUpdateDto;
import com.metanet.amatmu.reservation.model.Reservation;
import com.metanet.amatmu.reservation.service.IReservationService;

@RestController
@RequestMapping("/member/reservation")
public class ReservationController {

	@Autowired
	IReservationService reservationService;
	
	@GetMapping("/list")
	public ResponseEntity<List<ReservationResultDto>> getMemberReservationList(@AuthenticationPrincipal MemberUserDetails member) {
		return ResponseEntity.ok(reservationService.getMemberReservationList(member.getMemberId()));
	}
	
	@PostMapping("/insert")
	public ResponseEntity<Reservation> insertMemberReservation(
			@AuthenticationPrincipal MemberUserDetails member, @RequestBody ReservationInsertDto reservationDto
			) {
		return ResponseEntity.ok(reservationService.insertMemberReservation(member.getMemberId(), reservationDto));
	}
	
	@GetMapping("/bm/list")
	public ResponseEntity<List<BmReservationDto>> getBmReservationList(@AuthenticationPrincipal MemberUserDetails member) {
		return ResponseEntity.ok(reservationService.getBmReservationList(member.getMemberId()));
	}
	
	@PutMapping("/update/{resvId}")
	public ResponseEntity<Reservation> updateMemberReservation(
			@AuthenticationPrincipal MemberUserDetails member, @PathVariable long resvId,
			@RequestBody ReservationUpdateDto updateDto
			) {
		return ResponseEntity.ok(reservationService.updateMemberReservation(member.getMemberId(), resvId, updateDto));
	}
	
	@PostMapping("/cancel/{resvId}")
	public ResponseEntity<Reservation> cancelMemberReservation(
			@AuthenticationPrincipal MemberUserDetails member,
			@PathVariable long resvId
			) {
		return ResponseEntity.ok(reservationService.cancelMemberReservation(member.getMemberId(), resvId));
	}
	
	@PostMapping("/bm/visit/{resvId}")
	public ResponseEntity<Reservation> updateReservationVisit(
			@AuthenticationPrincipal MemberUserDetails member, @PathVariable long resvId, @RequestParam String status) {
		return ResponseEntity.ok(reservationService.updateReservationVisit(member, resvId, status));
	}
	
	@GetMapping("/info/{resvId}")
	public ResponseEntity<Reservation> getReservationInfo(@PathVariable long resvId) {
		return ResponseEntity.ok(reservationService.getReservationInfo(resvId));
	}
}
