package com.metanet.amatmu.reservation.service;

import java.util.List;

import com.metanet.amatmu.reservation.dto.ReservationInsertDto;
import com.metanet.amatmu.reservation.model.Reservation;

public interface IReservationService {
	List<Reservation> getMemberReservationList(long memberId);
	
	Reservation insertMemberReservation(long memberId, ReservationInsertDto reservationDto);
}
