package com.metanet.amatmu.reservation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.reservation.model.Reservation;

@Repository
@Mapper
public interface IReservationRepository {

	long selectMaxReservationNo();
	void insertReservation(Reservation reservation);
	List<Reservation> selectReservationListByMembId(long membId);
	List<Reservation> selectReservationListByRestId(long restId);
	
	int updateReservation(Reservation reservation);
	Reservation selectReservationByResvId(long resvId);

  List<Reservation> selectResvByResvDate();
  int	updateReservationStatusToY(Reservation reservation);
}

