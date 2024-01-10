package com.metanet.amatmu.restaurant.service;

import static org.hamcrest.CoreMatchers.not;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.metanet.amatmu.businessman.dao.IBusinessmanRepository;
import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.EvaluateBmanDto;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.RestaurantDto;
import com.metanet.amatmu.restaurant.model.UpdateRegisterRestaurantDto;

@Service
public class RestaurantService implements IRestaurantService{
	private IRestaurantRepository	restaurantRepository;
	private IMemberRepository		memberRepository;
	private IBusinessmanRepository	businessmanRepository;
	
	@Autowired
	public RestaurantService(
			IRestaurantRepository restaurantRepository, 
			IMemberRepository memberRepository, 
			IBusinessmanRepository businessmanRepository) {
		this.restaurantRepository = restaurantRepository;
		this.memberRepository = memberRepository;
		this.businessmanRepository = businessmanRepository;
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
	public Restaurant	requestRegisterRestaurant(User member, RestaurantDto restaurantDto) {
		//가게 등록 자체가 제휴 요청이다.
		int			queryStatus = 0;
		Long		membId = memberRepository.selectMember(member.getUsername()).getMemberId();
		Long		restId = restaurantRepository.selectMaxRestaurantId() + 1;
		Restaurant	restaurant = new Restaurant(
				restId,
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
		// rest_id넣어주고 , bman_grant_date는그대로 , bman_status은wait->requested
		businessmanRepository.updateBmRequested(
				new UpdateRegisterRestaurantDto(restId, restaurantDto.getBusinessmanId())
				);
		
		return restaurant;
	}
	
	//bman status 가 requested 인것들 찾는 메서드
	public List<Businessman>	readBmanRequest() {
		List<Businessman> bmanList = restaurantRepository.selectBmanRequest();
		return bmanList;
	}
	

	//bman status 가 requested 인것들 찾아서 승인 또는 거절하는 메서드
	public String evaluateBmanRequest(EvaluateBmanDto evaluateBmanDto) {
		String	eval = evaluateBmanDto.getEval();
		int		queryStatus = 0;

		evaluateBmanDto.setGrantDate(new Date(System.currentTimeMillis()));
		queryStatus = restaurantRepository.evaluateBmanRequest(evaluateBmanDto);
		if (queryStatus > 0) {
			return eval;
		} else {
			throw new QueryFailedException("update query for evaluating Bman failed.");
		}
	}

	

	
	
	public Restaurant	deleteRestaurant(Long restId) {
//		int	queryStatus = 0;
//		
//		Restaurant deletedRestaurant = restaurantRepository.selectRestaurantByRestId(restId);
//		queryStatus = restaurantRepository.deleteRestaurant(restId);
//		if (queryStatus > 0) {
//			//businessman table도 삭제해야 하는데...
//			businessmanRepository.deleteBmByRestId(restId);
//			return deletedRestaurant;
//		} else {
//			throw new QueryFailedException("Failed to  delete the Restaurant");
//		}
		int	queryStatus = 0;
		
		Restaurant deletedRestaurant = restaurantRepository.selectRestaurantByRestId(restId);
		queryStatus = businessmanRepository.deleteBmByRestId(restId);
		if (queryStatus > 0) {
			//businessman table도 삭제해야 하는데...
			restaurantRepository.deleteRestaurant(restId);
			return deletedRestaurant;
		} else {
			throw new QueryFailedException("Failed to  delete the Businessman");
		}
	}

}
