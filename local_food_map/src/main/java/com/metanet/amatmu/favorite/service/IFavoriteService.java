package com.metanet.amatmu.favorite.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.metanet.amatmu.favorite.model.Favorite;
import com.metanet.amatmu.favorite.model.FavoriteDto;
import com.metanet.amatmu.restaurant.model.Restaurant;

public interface IFavoriteService {

	Favorite			insertFavorite(User member, FavoriteDto favoriteDto);
//	List<Favorite>	selectFavoriteByMemberId(Long membId);
//	List<Favorite>	selectFavoriteByRestId(Long restId);
	Favorite			deleteFavorite(User member, FavoriteDto favoriteDto);
	boolean				selectFavoriteByRestIdAndMembId(User member, Long restId);
	List<Restaurant>	selectFavoriteRestaurants(User member);
}
