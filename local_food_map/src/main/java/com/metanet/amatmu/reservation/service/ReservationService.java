package com.metanet.amatmu.reservation.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.businessman.dao.IBusinessmanRepository;
import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.member.model.Member;
import com.metanet.amatmu.reservation.dao.IReservationRepository;
import com.metanet.amatmu.reservation.dto.BmReservationDto;
import com.metanet.amatmu.reservation.dto.ReservationInsertDto;
import com.metanet.amatmu.reservation.dto.ReservationResultDto;
import com.metanet.amatmu.reservation.dto.ReservationUpdateDto;
import com.metanet.amatmu.reservation.model.Reservation;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.review.dao.IReviewRepository;
import com.metanet.amatmu.review.model.Review;

@Service
public class ReservationService implements IReservationService{
	
	@Autowired
	IReservationRepository reservationDao;
	
	@Autowired
	IRestaurantRepository restaurantDao;
	
	@Autowired
	IReviewRepository reviewDao;
	
	@Autowired
	IBusinessmanRepository bmDao;
	
	@Autowired
	IMemberRepository membDao;

	@Override
	public List<ReservationResultDto> getMemberReservationList(long memberId) {
		List<Reservation> reservationList = reservationDao.selectReservationListByMembId(memberId);
		List<ReservationResultDto> result = new ArrayList<>();
		
		for (Reservation resv : reservationList) {
			ReservationResultDto resultDto = new ReservationResultDto();
			resultDto.setResvId(resv.getResvId());
			resultDto.setResvDate(resv.getResvDate());
			resultDto.setResvHeadCount(resv.getResvHeadCount());
			resultDto.setResvCreateDate(resv.getResvCreateDate());
			resultDto.setResvHour(resv.getResvHour());
			resultDto.setResvStatus(resv.getResvStatus());
			resultDto.setResvRequirement(resv.getResvRequirement());
			resultDto.setResvPayAmount(resv.getResvPayAmount());
			resultDto.setMembId(resv.getMembId());
			resultDto.setRestId(resv.getRestId());
			
			Restaurant restaurant = restaurantDao.selectRestaurantByRestId(resv.getRestId());
			resultDto.setRestName(restaurant.getRestName());
			resultDto.setRestImg(null);
			
			Review	review = reviewDao.selectReviewByReservationId(resv.getResvId());
			
			if (review != null) {
				resultDto.setReviewCreated(true);
				resultDto.setRevwId(review.getRevwId());
			} else {
				resultDto.setReviewCreated(false);
				resultDto.setRevwId(null);
			}
			result.add(resultDto);
		}
		return result;
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

	@Override
	public List<BmReservationDto> getBmReservationList(long membId) {
		List<Businessman> bmList = bmDao.getBmListByMemberId(membId);
		
		List<BmReservationDto> result = new ArrayList<>();
		
		for (Businessman bm : bmList) {
			List<Reservation> resvList = reservationDao.selectReservationListByRestId(bm.getRestaurantId());
			for (Reservation resv : resvList) {
				BmReservationDto resultDto = new BmReservationDto();
				resultDto.setResvId(resv.getResvId());
				resultDto.setMembId(resv.getMembId());
				Member member = membDao.selectMemberById(resv.getMembId());
				resultDto.setMembEmail(member.getEmail());
				resultDto.setHeadCount(resv.getResvHeadCount());
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
				String dateStr = fmt.format(resv.getResvDate());
				resultDto.setResvDate(dateStr + resv.getResvHour());
				resultDto.setPhoneNumber(member.getPhoneNumber());
				resultDto.setRequirement(resv.getResvRequirement());
				
				result.add(resultDto);
			}
		}
		return result;
	}
	
	
	@Override
	public Reservation updateMemberReservation(long memberId, long resvId, ReservationUpdateDto updateDto) {

	    Reservation reservation = reservationDao.selectReservationByResvId(resvId);

	    // 예약 세부 정보 업데이트
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		
		try {
			java.util.Date utilResvDate = formatter.parse(updateDto.getResvDate());
			Date sqlResvDate = new Date(utilResvDate.getTime());
			reservation.setResvDate(sqlResvDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    reservation.setResvHeadCount(updateDto.getResvHeadCount());
	    reservation.setResvHour(updateDto.getResvHour());
	    reservation.setResvRequirement(updateDto.getResvRequirement());
	    reservation.setResvPayAmount(updateDto.getResvPayAmount());

	    // 데이터베이스에서 예약 업데이트
	    reservationDao.updateReservation(reservation);

	    return reservation;
	}
	
	
}
