package com.metanet.amatmu.restaurant.service;

import static org.hamcrest.CoreMatchers.not;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.RestaurantDto;

@Service
public class RestaurantService implements IRestaurantService{
	private IRestaurantRepository	restaurantRepository;
	private IMemberRepository		memberRepository;
	
	@Autowired
	public RestaurantService(IRestaurantRepository restaurantRepository, IMemberRepository memberRepository) {
		this.restaurantRepository = restaurantRepository;
		this.memberRepository = memberRepository;
	}
	
	@Override
	public Restaurant	selectRestaurantById(Long restId) {
		Restaurant restaurant = restaurantRepository.selectRestaurantByRestId(restId);
				
		if (restaurant == null) {
			throw new QueryFailedException("There is no Restaurant " + restId + ".");
		}
		return restaurant;
	}
	
	@Override
	public Restaurant	insertRestaurant(User member, RestaurantDto restaurantDto) {
		int			queryStatus = 0;
		Long		membId = memberRepository.selectMember(member.getUsername()).getMemberId();
		Restaurant	restaurant = new Restaurant(
				restaurantRepository.selectMaxRestaurantId() + 1,
				restaurantDto.getRestName(),
				restaurantDto.getRestLocationX(),
				restaurantDto.getRestLocationY(),
				restaurantDto.getRestLocationName(),
				restaurantDto.getRestKeyword(),
				restaurantDto.getRestPhoneNumber(),
				restaurantDto.getRestOpenTime(),
				restaurantDto.getRestCloseTime(),
				restaurantDto.getRestCreateDate()
				);
		queryStatus = restaurantRepository.insertRestaurant(restaurant);
		if (queryStatus == 0) {
			throw new QueryFailedException("Failed to insert Restaurant : query error.");
		}
		//businessman 찾아가서 그 businessman의 restId칼럼에 현재 restID 넣어주는 업데이트 코드...
		return restaurant;
	}
	
	public Restaurant	deleteRestaurant(Long restId) {
		int	queryStatus = 0;
		
		Restaurant deletedRestaurant = restaurantRepository.selectRestaurantByRestId(restId);
		queryStatus = restaurantRepository.deleteRestaurant(restId);
		if (queryStatus > 0) {
			//businessman 찾아가서 그 businessman의 restId칼럼에 null로 바꿔주는 코드 ...
			return deletedRestaurant;
		} else {
			throw new QueryFailedException("Failed to  delete the Restaurant");
		}
	}

}
