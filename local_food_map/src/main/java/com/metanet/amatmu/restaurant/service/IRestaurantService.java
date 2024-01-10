package com.metanet.amatmu.restaurant.service;

import org.springframework.security.core.userdetails.User;

import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.RestaurantDto;

public interface IRestaurantService {
	Restaurant	selectRestaurantById(Long restId);
	Restaurant	insertRestaurant(User member, RestaurantDto restaurantDto);
	Restaurant	deleteRestaurant(Long restId);
}
