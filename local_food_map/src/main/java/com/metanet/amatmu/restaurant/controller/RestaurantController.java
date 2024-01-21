package com.metanet.amatmu.restaurant.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.restaurant.model.EvaluateBmanDto;
import com.metanet.amatmu.restaurant.model.RestAndBmanCreateDto;
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
	
	
//	@PostMapping("/restaurant/register")
//	public ResponseEntity<Restaurant>	createRestaurant(@AuthenticationPrincipal User member, @RequestBody RestaurantDto restaurantDto) {
//		Restaurant restaurant = restaurantService.requestRegisterRestaurant(member, restaurantDto);
//		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
//	}
	
	@PostMapping("/restaurant/register")
	public ResponseEntity<Restaurant>	createRestaurant(
			@AuthenticationPrincipal User member, 
			@RequestPart("restaurant") RestaurantDto restaurantDto,
			@RequestPart(value = "file", required = false) MultipartFile file
			) {
		Restaurant restaurant;
		
		if (file != null) {
			restaurant = restaurantService.createRestaurant(member, restaurantDto, file);
		} else {
			restaurant = restaurantService.createRestaurant(member, restaurantDto);
		}
		return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
	}
	
	@GetMapping("/restaurant/{restId}")
	public ResponseEntity<Restaurant>	readRestaurantById(@PathVariable Long restId) {
		Restaurant	restaurant = restaurantService.selectRestaurantById(restId);
		
		return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}
	

	@DeleteMapping("/restaurant/delete/{restId}")
	public ResponseEntity<Restaurant>	deleteRestaurant(@PathVariable Long restId) {
		Restaurant restaurant = restaurantService.deleteRestaurant(restId);
		
		return new ResponseEntity<>(restaurant, HttpStatus.NO_CONTENT);
	}
	
	
	//bman status 가 requested 인것들 찾는 메서드
	@GetMapping("/bm/request/list")
	public ResponseEntity<List<Businessman>>	readBmanRequest() {
		List<Businessman>	bmanList = restaurantService.readBmanRequest();
		
		return new ResponseEntity<>(bmanList, HttpStatus.OK);
	}
	

//	//bman status 가 requested 인것들 찾아서 승인 또는 거절하는 메서드
	@PostMapping("/bm/request/evaluate")
	public ResponseEntity<String>	evaluateBmanRequest(@RequestBody EvaluateBmanDto evaluateBmanDto) {
		String	evalMessage = restaurantService.evaluateBmanRequest(evaluateBmanDto);
		
		return new ResponseEntity<>(evalMessage, HttpStatus.OK); 
	}
	
	@GetMapping("/restaurant/search/{searchKeyword}")
	public ResponseEntity<List<Restaurant>>	searchRestaurantsByKeyword(@PathVariable String searchKeyword) {
		List<Restaurant>	restaurants = restaurantService.searchRestaurantsByKeyword(searchKeyword);
		
		return new ResponseEntity<>(restaurants, HttpStatus.OK);
	}

	

}
