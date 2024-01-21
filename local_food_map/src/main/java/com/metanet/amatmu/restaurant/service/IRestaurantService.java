package com.metanet.amatmu.restaurant.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.restaurant.model.EvaluateBmanDto;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.RestaurantDto;

public interface IRestaurantService {
	Restaurant			selectRestaurantById(Long restId);
//	Restaurant			requestRegisterRestaurant(User member, RestaurantDto restaurantDto);
	Restaurant			deleteRestaurant(Long restId);
	List<Businessman>	readBmanRequest();
	String				evaluateBmanRequest(EvaluateBmanDto evaluateBmanDto);
	
	List<Restaurant>	searchRestaurantsByKeyword(String searchKeyword);
	Restaurant			createRestaurant(User member,RestaurantDto restaurantDto,MultipartFile file);
	Restaurant			createRestaurant(User member,RestaurantDto restaurantDto);
}