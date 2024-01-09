package com.metanet.amatmu.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.RestaurantDto;
import com.metanet.amatmu.restaurant.service.IRestaurantService;

@RestController
public class RestaurantController {
	private IRestaurantService	restaurantService;
	
	@Autowired
	public RestaurantController(IRestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	@PostMapping("/restaurant/register")
	public ResponseEntity<Restaurant>	createRestaurant(@AuthenticationPrincipal User member, @RequestBody RestaurantDto restaurantDto) {
		Restaurant restaurant = restaurantService.insertRestaurant(member, restaurantDto);
		
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}
	
	@GetMapping("/restaurant/{restId}")
	public ResponseEntity<Restaurant>	readRestaurantById(@PathVariable Long restId) {
		Restaurant	restaurant = restaurantService.selectRestaurantById(restId);
		
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}
	

}
