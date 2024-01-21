package com.metanet.amatmu.restaurant.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.businessman.dao.IBusinessmanRepository;
import com.metanet.amatmu.businessman.model.Businessman;
import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.BmanUpdateRestIdDto;
import com.metanet.amatmu.restaurant.model.EvaluateBmanDto;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.restaurant.model.RestaurantDto;
import com.metanet.amatmu.restaurant.model.UpdateRegisterRestaurantDto;
import com.metanet.amatmu.utils.S3Uploader;

@Service
public class RestaurantService implements IRestaurantService{
	private IRestaurantRepository	restaurantRepository;
	private IMemberRepository		memberRepository;
	private IBusinessmanRepository	businessmanRepository;
	private S3Uploader				s3Uploader;
	
	@Autowired
	public RestaurantService(
			IRestaurantRepository restaurantRepository, 
			IMemberRepository memberRepository, 
			IBusinessmanRepository businessmanRepository,
			S3Uploader				s3Uploader
			) {
		this.restaurantRepository = restaurantRepository;
		this.memberRepository = memberRepository;
		this.businessmanRepository = businessmanRepository;
		this.s3Uploader = s3Uploader;
	}
	
	@Override
	public Restaurant	selectRestaurantById(Long restId) {
		Restaurant restaurant = restaurantRepository.selectRestaurantByRestId(restId);
				
		if (restaurant == null) {
			throw new QueryFailedException("There is no Restaurant " + restId + ".");
		}
		return restaurant;
	}
	
//	@Override
//	public Restaurant	requestRegisterRestaurant(User member, RestaurantDto restaurantDto) {
//		//가게 등록 자체가 제휴 요청이다.
//		int			queryStatus = 0;
//		Long		membId = memberRepository.selectMember(member.getUsername()).getMemberId();
//		Long		restId = restaurantRepository.selectMaxRestaurantId() + 1;
//		Restaurant	restaurant = new Restaurant(
//				restId,
//				restaurantDto.getRestName(),
//				restaurantDto.getRestLocationX(),
//				restaurantDto.getRestLocationY(),
//				restaurantDto.getRestLocationName(),
//				restaurantDto.getRestKeyword(),
//				restaurantDto.getRestPhoneNumber(),
//				restaurantDto.getRestOpenTime(),
//				restaurantDto.getRestCloseTime(),
////				restaurantDto.getRestCreateDate(),
//				new Date(System.currentTimeMillis()),
//				restaurantDto.getRestStation(),
//				restaurantDto.getRestCategory(),
//				restaurantDto.getRestMaxResv(),
//				restaurantDto.getRestDeposit(),
//				null							//img 넣기?
//				);
//		queryStatus = restaurantRepository.insertRestaurant(restaurant);
//		if (queryStatus == 0) {
//			throw new QueryFailedException("Failed to insert Restaurant : query error.");
//		}
//		//businessman 찾아가서 그 businessman의 restId칼럼에 현재 restID 넣어주는 업데이트 코드...
//		// rest_id넣어주고 , bman_grant_date는그대로 , bman_status은wait->requested
//		businessmanRepository.updateBmRequested(
//				new UpdateRegisterRestaurantDto(restId, restaurantDto.getBusinessmanId())
//				);
//		
//		return restaurant;
//	}
//	
	//bman status 가 requested 인것들 찾는 메서드
	public List<Businessman>	readBmanRequest() {
		List<Businessman> bmanList = restaurantRepository.selectBmanRequest();
		return bmanList;
	}
	

	//bman status 가 requested 인것들 찾아서 승인 또는 거절하는 메서드
	public String evaluateBmanRequest(EvaluateBmanDto evaluateBmanDto) {
		String	eval = evaluateBmanDto.getEval();
		int		queryStatus = 0;
		
		if (eval == "GRANTED") {
			evaluateBmanDto.setGrantDate(new Date(System.currentTimeMillis()));
		}
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
	
	@Override
	public List<Restaurant>	searchRestaurantsByKeyword(String searchKeyword) {
		List<Restaurant>	restaurants = restaurantRepository.selectRestaurantsByKeyword(searchKeyword);
		
		return restaurants;
	}

	@Override
	public Restaurant			createRestaurant(User member,RestaurantDto restaurantDto,MultipartFile file) {
		//일단 식당 만들고, member -> bman -> rest_id 업데이트 해주
		Long		membId = memberRepository.selectMember(member.getUsername()).getMemberId();
		String		restImg = s3Uploader.fileUpload(file);
		int			queryStatus = 0;
		Long		restId = restaurantRepository.selectMaxRestaurantId() + 1;
		Restaurant	createdRestaurant = new Restaurant(
				restId,
				restaurantDto.getRestName(),
				restaurantDto.getRestLocationX(),
				restaurantDto.getRestLocationY(),
				restaurantDto.getRestLocationName(),
				restaurantDto.getRestKeyword(),
				restaurantDto.getRestPhoneNumber(),
				restaurantDto.getRestOpenTime(),
				restaurantDto.getRestCloseTime(),
				new Date(System.currentTimeMillis()),
				restaurantDto.getRestStation(),
				restaurantDto.getRestCategory(),
				restaurantDto.getRestMaxResv(),
				restaurantDto.getRestDeposit(),
				restImg
				);

		
		queryStatus = restaurantRepository.insertRestaurant(createdRestaurant);
		if (queryStatus == 0) {
			throw new QueryFailedException("Failed to insert review: query error");
		}
		businessmanRepository.updateBmRestaurantByMemberId(new BmanUpdateRestIdDto(membId, restId));
		return createdRestaurant;
	}
	
	@Override
	public Restaurant			createRestaurant(User member,RestaurantDto restaurantDto) {
		Long		membId = memberRepository.selectMember(member.getUsername()).getMemberId();
		String		restImg = "";
		int			queryStatus = 0;
		Long		restId = restaurantRepository.selectMaxRestaurantId() + 1;
		Restaurant	createdRestaurant = new Restaurant(
				restId,
				restaurantDto.getRestName(),
				restaurantDto.getRestLocationX(),
				restaurantDto.getRestLocationY(),
				restaurantDto.getRestLocationName(),
				restaurantDto.getRestKeyword(),
				restaurantDto.getRestPhoneNumber(),
				restaurantDto.getRestOpenTime(),
				restaurantDto.getRestCloseTime(),
				new Date(System.currentTimeMillis()),
				restaurantDto.getRestStation(),
				restaurantDto.getRestCategory(),
				restaurantDto.getRestMaxResv(),
				restaurantDto.getRestDeposit(),
				restImg
				);
		
		queryStatus = restaurantRepository.insertRestaurant(createdRestaurant);
		if (queryStatus == 0) {
			throw new QueryFailedException("Failed to insert review: query error");
		}
		businessmanRepository.updateBmRestaurantByMemberId(new BmanUpdateRestIdDto(membId, restId));
		return createdRestaurant;
	}


}
