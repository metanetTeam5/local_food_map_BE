package com.metanet.amatmu.favorite.service;

import java.util.List;

import com.metanet.amatmu.favorite.model.Favorite;
import com.metanet.amatmu.favorite.model.FavoriteDto;

public interface IFavoriteService {
	Favorite		insertFavorite(FavoriteDto favoriteDto);
	List<Favorite>	selectFavoriteByMemberId(Long membId);
	List<Favorite>	selectFavoriteByRestId(Long restId);
	Favorite		deleteFavorite(FavoriteDto favoriteDto);
	boolean			selectFavoriteByRestIdAndMembId(FavoriteDto favoriteDto);
}
