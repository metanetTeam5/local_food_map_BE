package com.metanet.amatmu.restaurant.service;

import com.metanet.amatmu.restaurant.model.Restaurant;

public interface IRestaurantService {
	Restaurant	selectRestaurantById(Long restId);
}
