package com.metanet.amatmu.review.service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.internal.S3AbortableInputStream;
import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.member.model.Member;
import com.metanet.amatmu.notice.model.Notice;
import com.metanet.amatmu.reservation.dao.IReservationRepository;
import com.metanet.amatmu.reservation.model.Reservation;
import com.metanet.amatmu.restaurant.dao.IRestaurantRepository;
import com.metanet.amatmu.restaurant.model.Restaurant;
import com.metanet.amatmu.review.dao.IReviewRepository;
import com.metanet.amatmu.review.dto.ReviewImageCreateDto;
import com.metanet.amatmu.review.dto.ReviewResultDto;
import com.metanet.amatmu.review.dto.ReviewResultRestaurantDto;
import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.review.model.ReviewDto;
import com.metanet.amatmu.utils.S3Uploader;

//import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeCheckerStateKt;


@Service
public class ReviewService implements IReviewService {
	private IReviewRepository		reviewRepository;
	private IMemberRepository		memberRepository;
	private IRestaurantRepository	restaurantRepository;
	private S3Uploader				s3Uploader;
	private IReservationRepository	reservationRepository;
	
	@Autowired
	public ReviewService(IReviewRepository reviewRepository, IMemberRepository memberRepository, IRestaurantRepository restaurantRepository, S3Uploader s3Uploader, IReservationRepository	reservationRepository) {
		this.reviewRepository = reviewRepository;
		this.memberRepository = memberRepository;
		this.restaurantRepository = restaurantRepository;
		this.s3Uploader = s3Uploader;
		this.reservationRepository = reservationRepository;
	}
	
	@Override
	public Boolean	checkDuplicateReview(Long reservationId) {
		//reservation 도메인을 이용하는 코드로 수정 필요
		Review	review = reviewRepository.selectReviewByReservationId(reservationId);
		
		if (review != null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	@Override
	public Review	createReview(User member, Long reservationId, ReviewDto reviewDto) {
		if (!checkDuplicateReview(reservationId)) {
			throw new QueryFailedException("only 1 review per reservation is allowed.");
		}
		
		Long	membId = memberRepository.selectMember(member.getUsername()).getMemberId();
		int		queryStatus = 0;
		Long	reviewId = reviewRepository.selectMaxReviewId() + 1;
		Review	createdReview = new Review(reviewId,
				reviewDto.getRevwStarRate(), 
				reviewDto.getRevwContent(), 
				new Date(System.currentTimeMillis()), 
//				reviewDto.getRevwImg(),
				"www.ex.com",
				reviewDto.getRestId(), 
				membId, 
				reservationId);
				
		queryStatus = reviewRepository.insertReview(createdReview);
		if (queryStatus == 0) {
			throw new QueryFailedException("Failed to insert review: query error");
		}
		return createdReview;
	}
	
//	reviewService.uploadReviewImg(reservationId, file);
	@Override
	public void uploadReviewImg(Long reservationId, MultipartFile file) {
		String	reviewImg = s3Uploader.fileUpload(file);
	}
	
	@Override
	public Review			createReviewWithImg(User member, Long reservationId, ReviewImageCreateDto reviewDto, MultipartFile file) {
		if (!checkDuplicateReview(reservationId)) {
			throw new QueryFailedException("only 1 review per reservation is allowed.");
		}
		
		Long		membId = memberRepository.selectMember(member.getUsername()).getMemberId();
		String		reviewImg = s3Uploader.fileUpload(file);
		int			queryStatus = 0;
		Long		reviewId = reviewRepository.selectMaxReviewId() + 1;
		Reservation	reservation = reservationRepository.selectReservationByResvId(reservationId);
		Review		createdReview = new Review(reviewId,
				reviewDto.getRevwStarRate(), 
				reviewDto.getRevwContent(), 
				new Date(System.currentTimeMillis()), 
	//						reviewDto.getRevwImg(),
	//						"www.ex.com",
				reviewImg,
				reviewDto.getRestId(), 
				membId, 
				reservationId);
	
		queryStatus = reviewRepository.insertReview(createdReview);
		if (queryStatus == 0) {
			throw new QueryFailedException("Failed to insert review: query error");
		} else {
			reservation.setResvStatus("Y");
			reservationRepository.updateReservation(reservation);
		}
		
		return createdReview;

	}

	
	@Override
	public Review			getReviewById(Long reviewId) {
		Review	review = reviewRepository.selectReviewById(reviewId);
		
		if (review == null) {
			throw new QueryFailedException("There is no review " + reviewId + ".");
		}
		return review;
	}
	
	@Override
	public Review			updateReviewById(Long reviewId, ReviewDto reviewDto) {
		int		queryStatus = 0;
		Review	review = reviewRepository.selectReviewById(reviewId);
		
		review.setRevwStarRate(reviewDto.getRevwStarRate());
		review.setRevwContent(reviewDto.getRevwContent());
		review.setRevwImg(reviewDto.getRevwImg());
		queryStatus = reviewRepository.updateReview(review);
		if (queryStatus > 0) {
			return review;
		} else {
			throw new QueryFailedException("Failed to update the Review " + reviewId + ".");
		}
	}
	
	@Override
	public Review			deleteReviewById(Long reviewId) {
		int		queryStatus = 0;
		Review	deletedReview = reviewRepository.selectReviewById(reviewId);
		
		queryStatus = reviewRepository.deleteReviewById(reviewId);
		if (queryStatus > 0) {
			return	deletedReview;
		} else {
			throw new QueryFailedException("Fail to delete the Review.");
		}
	}
	
	@Override
	public List<ReviewResultDto>	getReviewsByMemberId(User member) {
		Long			memberId = memberRepository.selectMember(member.getUsername()).getMemberId();
		List<Review>	reviews = reviewRepository.selectReviewsByMemberId(memberId);
		
		List<ReviewResultDto> result = new ArrayList<>();
		for (Review review : reviews) {
			ReviewResultDto resultDto = new ReviewResultDto();
			resultDto.setRevwId(review.getRevwId());
			resultDto.setRevwStarRate(review.getRevwStarRate());
			resultDto.setRevwContent(review.getRevwContent());
			resultDto.setRevwCreateDate(review.getRevwCreateDate());
			resultDto.setRevwImg(review.getRevwImg());
			resultDto.setRestId(review.getRestId());
			resultDto.setMembId(review.getMembId());
			resultDto.setResvId(review.getResvId());
			Restaurant restaurant = restaurantRepository.selectRestaurantByRestId(review.getRestId());
			resultDto.setRestName(restaurant.getRestName());
			result.add(resultDto);
		}
		
		return result;
	}
	
	@Override
	public List<ReviewResultRestaurantDto>	getReviewsByRestId(Long restId) {
		List<Review>	reviews = reviewRepository.selectReviewsByRestId(restId);
		
		List<ReviewResultRestaurantDto> result = new ArrayList<>();
		for (Review review : reviews) {
			ReviewResultRestaurantDto resultDto = new ReviewResultRestaurantDto();
			resultDto.setRevwId(review.getRevwId());
			resultDto.setRevwStarRate(review.getRevwStarRate());
			resultDto.setRevwContent(review.getRevwContent());
			resultDto.setRevwCreateDate(review.getRevwCreateDate());
			resultDto.setRevwImg(review.getRevwImg());
			resultDto.setRestId(review.getRestId());
			resultDto.setMembId(review.getMembId());
			resultDto.setResvId(review.getResvId());
			Restaurant restaurant = restaurantRepository.selectRestaurantByRestId(review.getRestId());
			resultDto.setRestName(restaurant.getRestName());
			Member member = memberRepository.selectMemberById(review.getMembId());
			resultDto.setMembNickname(member.getNickname());
			resultDto.setMembProfileImg(member.getProfileImg());
			result.add(resultDto);
		}
		return result;
	}
	
	@Override
	public List<Review>	getBmReviewsByRestId(Long restId) {
		List<Review>	reviews = reviewRepository.selectReviewsByRestId(restId);
		
		return reviews;
	}
	
	
//	@Override
//	public Review			requestDeleteReview(Long reviewId) {
//		
//		
//	}
//	@Override
//	public List<Review>	getDeleteReviewRequest(Long reviewId) {
//		
//	}
//	@Override
//	public Review			deleteRequestedReview(Long reviewId) {
//		
//	}
	
}
