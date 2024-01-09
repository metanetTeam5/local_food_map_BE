package com.metanet.amatmu.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;

@Service
public class RestaurantService implements IRestaurantService{
	private IRestaurantRepository restaurantRepository;
	
	@Autowired
	public RestaurantService(IRestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@Override
	public Restaurant	selectRestaurantById(Long restId) {
		Restaurant restaurant = restaurantRepository.selectRestaurantByRestId(restId);
				
		if (restaurant == null) {
			throw new QueryFailedException("There is no Restaurant " + restId + ".");
		}
		return restaurant;
	}

}
