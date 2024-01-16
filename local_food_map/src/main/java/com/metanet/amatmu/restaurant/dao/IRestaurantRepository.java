package com.metanet.amatmu.restaurant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.restaurant.model.EvaluateBmanDto;
import com.metanet.amatmu.restaurant.model.Restaurant;

@Repository
@Mapper
public interface IRestaurantRepository {
	Restaurant	selectRestaurantByRestId(Long restId);
	
	Long				selectMaxRestaurantId();
	int					insertRestaurant(Restaurant restaurant);
	int					deleteRestaurant(Long restId);
	List<Businessman>	selectBmanRequest();
	int					evaluateBmanRequest(EvaluateBmanDto evaluateBmanDto);
	
	List<Restaurant>	selectRestaurantsByKeyword(String searchKeyword);
	int					updateRestaurant(Restaurant restaurant);
}
