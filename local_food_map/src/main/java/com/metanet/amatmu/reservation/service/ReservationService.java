package com.metanet.amatmu.reservation.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.reservation.dao.IReservationRepository;
import com.metanet.amatmu.reservation.dto.ReservationInsertDto;
import com.metanet.amatmu.reservation.model.Reservation;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;

@Service
public class ReservationService implements IReservationService{
	
	@Autowired
	IReservationRepository reservationDao;
	
	@Autowired
	IRestaurantRepository restaurantDao;

	@Override
	public List<Reservation> getMemberReservationList(long memberId) {
		return null;
	}

	@Override
	public Reservation insertMemberReservation(long memberId, ReservationInsertDto reservationDto) {
		Reservation reservation = new Reservation();
		
		reservation.setResvId(reservationDao.selectMaxReservationNo() + 1);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
		try {
			java.util.Date utilResvDate = formatter.parse(reservationDto.getResvDate());
			Date sqlResvDate = new Date(utilResvDate.getTime());
			reservation.setResvDate(sqlResvDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		reservation.setResvHeadCount(reservationDto.getResvHeadCount());
		reservation.setResvHour(reservationDto.getResvHour());
		reservation.setResvStatus("O");
		reservation.setResvRequirement(reservationDto.getResvRequirement());
		
		Restaurant restaurant = restaurantDao.selectRestaurantByRestId(reservationDto.getRestId());
		
		reservation.setResvPayAmount(restaurant.getRestDeposit() * reservationDto.getResvHeadCount());
		reservation.setMembId(memberId);
		reservation.setRestId(reservationDto.getRestId());
		
		int curRestMaxResv = restaurant.getRestMaxResv();
		restaurant.setRestMaxResv(curRestMaxResv - reservationDto.getResvHeadCount());
		
		reservationDao.insertReservation(reservation);
		restaurantDao.updateRestaurant(restaurant);
		
		return reservation;
	}
	
	
}
