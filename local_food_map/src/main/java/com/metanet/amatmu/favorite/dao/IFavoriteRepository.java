package com.metanet.amatmu.favorite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.favorite.model.Favorite;
import com.metanet.amatmu.favorite.model.FavoriteDto;



@Repository
@Mapper
public interface IFavoriteRepository {
	int				insertFavorite(Favorite favorite);
	List<Favorite>	selectFavoriteByMemberId(Long membId);
	List<Favorite>	selectFavoriteByRestId(Long restId);
	int				deleteFavorite(Favorite favorite);
	Favorite		selectFavoriteByRestIdAndMembId(FavoriteDto favoriteDto);
	Long			selectMaxFavoriteId();
}
