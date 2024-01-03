package com.metanet.amatmu.favorite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.favorite.dao.IFavoriteRepository;
import com.metanet.amatmu.favorite.model.Favorite;
import com.metanet.amatmu.favorite.model.FavoriteDto;

@Service

public class FavoriteService implements IFavoriteService{
	private IFavoriteRepository favoriteRepository;
	
	@Autowired
	public FavoriteService(IFavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}
	
	@Override
	public Favorite		insertFavorite(FavoriteDto favoriteDto) {
		Favorite	favorite = favoriteRepository.selectFavoriteByRestIdAndMembId(favoriteDto);
		int			queryStatus = 0;
		
		if (favorite == null) {
			favorite = new Favorite(favoriteRepository.selectMaxFavoriteId() + 1, favoriteDto.getMembId(), favoriteDto.getRestId());
			queryStatus = favoriteRepository.insertFavorite(favorite);
			if (queryStatus == 0) {
				throw new QueryFailedException("Failed to insert Favorite. : query error.");
			}
			return favorite;
		} else {
			throw new QueryFailedException("Failed to insert Favorite. : favorite already exists.");
		}
	}

	@Override
	public List<Favorite>	selectFavoriteByMemberId(Long membId) {
		return null;
		
	}
	
	@Override
	public List<Favorite>	selectFavoriteByRestId(Long restId) {
		return null;
	}
	
	@Override
	public Favorite		deleteFavorite(FavoriteDto favoriteDto) {
		Favorite	favorite = favoriteRepository.selectFavoriteByRestIdAndMembId(favoriteDto);
		int			queryStatus = 0;
		
		if (favorite == null) {
			throw new QueryFailedException("Failed to delete the Favorite : there is no favorite you indicated.");
		}
		queryStatus = favoriteRepository.deleteFavorite(favorite);
		if (queryStatus > 0) {
			return favorite;
		} else {
			throw new QueryFailedException("Failed to delete the Favorite : query execution failure");
		}
	}
	
	@Override
	public boolean		selectFavoriteByRestIdAndMembId(FavoriteDto favoriteDto) {
		Favorite	favorite = favoriteRepository.selectFavoriteByRestIdAndMembId(favoriteDto);
		
		if (favorite == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
