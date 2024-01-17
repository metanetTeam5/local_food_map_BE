package com.metanet.amatmu.review.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.review.dto.ReviewImageCreateDto;
import com.metanet.amatmu.review.dto.ReviewResultDto;
import com.metanet.amatmu.review.dto.ReviewResultRestaurantDto;
import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.review.model.ReviewDto;

public interface IReviewService {
	Review			createReview(User member, Long reservationId, ReviewDto reviewDto);
	Boolean			checkDuplicateReview(Long reservationId);
	
	Review			getReviewById(Long reviewId);
	Review			updateReviewById(Long reviewId, ReviewDto reviewDto);
	Review			deleteReviewById(Long reviewId);
	List<ReviewResultDto>	getReviewsByMemberId(User member);
	List<ReviewResultRestaurantDto>	getReviewsByRestId(Long restId);
	List<Review>	getBmReviewsByRestId(Long restId);
	
	void			uploadReviewImg(Long reservationId, MultipartFile file);
	Review			createReviewWithImg(User member, Long reservationId, ReviewImageCreateDto reviewDto, MultipartFile file);
	Review 			createReviewWithImg(User member, Long reservationId, ReviewImageCreateDto reviewDto);
	
//	Review			requestDeleteReview(Long reviewId);
//	List<Review>	getDeleteReviewRequest(Long reviewId);
//	Review			deleteRequestedReview(Long reviewId);
//	
	//리뷰아이디로 리뷰 조회
//		Review	review = reviewService.getReviewById(reviewId);
		
			
	
	//회원의 자신의 리뷰 수정
//		Review review = reviewService.updateReviewById(reviewId);
			
	
	//회원의 자신의 리뷰 삭제
//		Review	review = reviewService.deleteReviewById(reviewId);
		
		
	
	
	//회원의 자신의 리뷰들 조회
//		List<Review>	reviews = reviewService.getReviewsByMemberId(member); 
		
	
	//해당 가게의 모든 리뷰들 
//		List<Review>	reviews = reviewService.getReviewsByRestId(restId); 
	
	//사업자의 본인가게 리뷰 목록 조회
//		List<Review>	reviews = reviewService.getBmReviewsByRestId(restId); 
	
	//사업자의 리뷰 삭제 요청
//		Review	review = reviewService.requestDeleteReview(reviewId);
	
	//관리자의 리뷰 삭제 요청 조회
//		List<Review>	reviews = reviewService.getDeleteReviewRequest(reviewId); 
	
	//관리자의 리뷰 삭제 관리 
//		Review	review = reviewService.deleteRequestedReview(reviewId);
		
	
	
}
