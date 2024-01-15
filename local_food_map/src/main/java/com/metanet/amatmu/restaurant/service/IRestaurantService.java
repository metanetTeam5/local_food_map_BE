package com.metanet.amatmu.restaurant.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.restaurant.model.EvaluateBmanDto;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.RestaurantDto;

public interface IRestaurantService {
	Restaurant			selectRestaurantById(Long restId);
	Restaurant			requestRegisterRestaurant(User member, RestaurantDto restaurantDto);
	Restaurant			deleteRestaurant(Long restId);
	List<Businessman>	readBmanRequest();
	String				evaluateBmanRequest(EvaluateBmanDto evaluateBmanDto);
	
//	
//	void				searchRestaurant
////	List<Restaurant>	searchRestaurantstaurants(String restCategory, String restKeyword, String restName, String restStation);
////	List<Restaurant>	selectAllRestaurants();
	
}
