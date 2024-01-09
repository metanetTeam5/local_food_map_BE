package com.metanet.amatmu.favorite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.favorite.dao.IFavoriteRepository;
import com.metanet.amatmu.favorite.model.Favorite;
import com.metanet.amatmu.favorite.model.FavoriteDto;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.member.model.Member;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;

@Service

public class FavoriteService implements IFavoriteService{
	private IFavoriteRepository		favoriteRepository;
	private IMemberRepository		memberRepository;
	private IRestaurantRepository	restaurantRepository;
	
	@Autowired
	public FavoriteService(IFavoriteRepository favoriteRepository, IMemberRepository memberRepository, IRestaurantRepository restaurantRepository) {
		this.favoriteRepository = favoriteRepository;
		this.memberRepository = memberRepository;
		this.restaurantRepository = restaurantRepository;
	}
	
	@Override
	public Favorite		insertFavorite(User member, FavoriteDto favoriteDto) {
//		Long		memberId = memberRepository.getMemberIdByEmail(member.getUsername());
		Long		memberId = memberRepository.selectMember(member.getUsername()).getMemberId();
		favoriteDto.setMembId(memberId);
		Favorite	favorite = favoriteRepository.selectFavoriteByRestIdAndMembId(favoriteDto);
		
//		Favorite	favorite = favoriteRepository.selectFavoriteByRestIdAndMembId(favoriteDto.getRestId(), memberId);
		int			queryStatus = 0;
		
		if (favorite == null) {
			favorite = new Favorite(favoriteRepository.selectMaxFavoriteId() + 1, memberId, favoriteDto.getRestId());
			queryStatus = favoriteRepository.insertFavorite(favorite);
			if (queryStatus == 0) {
				throw new QueryFailedException("Failed to insert Favorite. : query error.");
			}
			return favorite;
		} else {
			throw new QueryFailedException("Failed to insert Favorite. : favorite already exists.");
		}
	}
//
//	@Override
//	public List<Favorite>	selectFavoriteByMemberId(Long membId) {
//		return null;
//		
//	}
//	
//	@Override
//	public List<Favorite>	selectFavoriteByRestId(Long restId) {
//		return null;
//	}
	
	@Override
	public Favorite		deleteFavorite(User member, FavoriteDto favoriteDto) {
//		Favorite	favorite = favoriteRepository.selectFavoriteByRestIdAndMembId(favoriteDto);
//		Long		memberId = memberRepository.getMemberIdByEmail(member.getUsername());
		Long		memberId = memberRepository.selectMember(member.getUsername()).getMemberId();
		favoriteDto.setMembId(memberId);
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
	public boolean		selectFavoriteByRestIdAndMembId(User member, Long restId) {
//		Long		memberId = memberRepository.getMemberIdByEmail(member.getUsername());
		Long		memberId = memberRepository.selectMember(member.getUsername()).getMemberId();
		FavoriteDto	favoriteDto = new FavoriteDto(memberId, restId);
		Favorite	favorite = favoriteRepository.selectFavoriteByRestIdAndMembId(favoriteDto);
		
		if (favorite == null) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public List<Restaurant>	selectFavoriteRestaurants(User member) {
		//회원이메일 -> 회원아이디 -> 회원의favorite리스트 -> 가게아이디리스트 -> 가게정보리스트 
//		Long				memberId = memberRepository.getMemberIdByEmail(member.getUsername());
		Long		memberId = memberRepository.selectMember(member.getUsername()).getMemberId();
		List<Favorite>		favoriteList = favoriteRepository.selectFavoriteByMembId(memberId);
		List<Restaurant>	restaurantList = new ArrayList<>();
		
		for (Favorite favorite : favoriteList) {
			Restaurant restaurant = restaurantRepository.selectRestaurantByRestId(favorite.getRestId());
			restaurantList.add(restaurant);	
		}
		return restaurantList;
	}
	
}
