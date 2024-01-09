package com.metanet.amatmu.favorite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metanet.amatmu.favorite.dao.IFavoriteRepository;
import com.metanet.amatmu.favorite.model.Favorite;
import com.metanet.amatmu.favorite.model.FavoriteDto;
import com.metanet.amatmu.favorite.service.IFavoriteService;
import com.metanet.amatmu.restaurant.model.Restaurant;

@RestController
public class FavoriteController {
	private IFavoriteService	favoriteService;
	
	@Autowired
	public FavoriteController(IFavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}
	
	@PostMapping("/restaurant/favorite")
	public ResponseEntity<Favorite>	createFavorite(@AuthenticationPrincipal User member, @RequestBody FavoriteDto favoriteDto) {
		Favorite	createdFavorite = favoriteService.insertFavorite(member, favoriteDto);
		
		return new ResponseEntity<>(createdFavorite, HttpStatus.CREATED);
	}

	@DeleteMapping("/restaurant/favorite")
	public ResponseEntity<Favorite>	deleteFavorite(@AuthenticationPrincipal User member, @RequestBody FavoriteDto favoriteDto) {
		Favorite	deletedFavorite = favoriteService.deleteFavorite(member, favoriteDto);
		
		return new ResponseEntity<>(deletedFavorite, HttpStatus.NO_CONTENT);
	}
	
//	@GetMapping("restuarant/favorite/list")
	@GetMapping("/restaurant/favorites")
	public ResponseEntity<List<Restaurant>>	readFavoriteRestaurants(@AuthenticationPrincipal User member) {
		List<Restaurant>	favoriteRestaurants = favoriteService.selectFavoriteRestaurants(member);

		return new ResponseEntity<>(favoriteRestaurants, HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/favorite/{restaurantId}")
	public	ResponseEntity<Boolean>	readFavoriteByRestIdAndMemberId(@AuthenticationPrincipal User member, @PathVariable Long restaurantId) {
		boolean	selectedFavorite = favoriteService.selectFavoriteByRestIdAndMembId(member, restaurantId);
		
		return new ResponseEntity<>(selectedFavorite, HttpStatus.OK);
	}
	

	

}
