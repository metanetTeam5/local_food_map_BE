package com.metanet.amatmu.reservation.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.reservation.model.Reservation;

@Repository
@Mapper
public interface IReservationRepository {

   long selectMaxReservationNo();
   void insertReservation(Reservation reservation);
}