package com.metanet.amatmu.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.member.model.Member;
import com.metanet.amatmu.reservation.dao.IReservationRepository;
import com.metanet.amatmu.reservation.model.Reservation;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReservationScheduling {

	@Autowired
	IReservationRepository resvDao;
	
	@Autowired
	IMemberRepository membDao;
	
	@Autowired
	IRestaurantRepository restDao;
	
	@Autowired
	SmsUtil sms;

	@Scheduled(cron = "0 0 22 * * *")
	public void sendReservationSms() {
		log.info("예약 문자 전송");

		List<Reservation> resvList = resvDao.selectResvByResvDate();
		
		for(Reservation resv : resvList) {
			Member memb = membDao.selectMemberById(resv.getMembId());
			Restaurant rest = restDao.selectRestaurantByRestId(resv.getRestId());
			sms.sendReservationInfo(memb.getPhoneNumber(), resv.getResvHour(), rest.getRestName());
		}
	}
}
