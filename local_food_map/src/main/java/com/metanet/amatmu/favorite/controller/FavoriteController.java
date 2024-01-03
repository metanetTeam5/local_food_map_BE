package com.metanet.amatmu.favorite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metanet.amatmu.favorite.dao.IFavoriteRepository;
import com.metanet.amatmu.favorite.model.Favorite;
import com.metanet.amatmu.favorite.model.FavoriteDto;
import com.metanet.amatmu.favorite.service.IFavoriteService;

@RestController
@RequestMapping("/")
public class FavoriteController {
	private IFavoriteService	favoriteService;
	
	@Autowired
	public FavoriteController(IFavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}
	
	@PostMapping("/restaurant/favorite")
	public ResponseEntity<Favorite>	createFavorite(@RequestBody FavoriteDto favoriteDto) {
		Favorite	createdFavorite = favoriteService.insertFavorite(favoriteDto);
		
		return new ResponseEntity<>(createdFavorite, HttpStatus.CREATED);
	}

	@DeleteMapping("/restaurant/favorite")
	public ResponseEntity<Favorite>	deleteFavorite(@RequestBody FavoriteDto favoriteDto) {
		Favorite	deletedFavorite = favoriteService.deleteFavorite(favoriteDto);
		
		return new ResponseEntity<>(deletedFavorite, HttpStatus.NO_CONTENT);
	}
	
//	@GetMapping("restaurant/favorite/{restaurantId}")
//	public	ResponseEntity<Boolean>	readFavoriteByRestIdAndMemberId(@RequestBody FavoriteDto favoriteDto) {
//		boolean	selectedFavorite = favoriteService.selectFavoriteByRestIdAndMembId(favoriteDto);
//		
//		return new ResponseEntity<>(selectedFavorite, HttpStatus.OK);
//	}
	
//	@GetMapping("restuarant/favorite/list")
	
	

}
