package com.metanet.amatmu.restaurant.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.restaurant.model.Restaurant;

@Repository
@Mapper
public interface IRestaurantRepository {
	Restaurant	selectRestaurantByRestId(Long restId);
}
