package com.metanet.amatmu.review.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.member.dto.MemberRegisterDto;
import com.metanet.amatmu.member.service.MemberService;
import com.metanet.amatmu.review.dto.ReviewImageCreateDto;
import com.metanet.amatmu.review.dto.ReviewImageUpdateDto;
import com.metanet.amatmu.review.dto.ReviewResultDto;
import com.metanet.amatmu.review.dto.ReviewResultRestaurantDto;
import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.review.model.ReviewDto;
import com.metanet.amatmu.review.service.IReviewService;

import io.jsonwebtoken.security.PublicJwk;
//import io.lettuce.core.dynamic.ReactiveTypeAdapters.PublisherToRxJava1CompletableAdapter;

@RestController
public class ReviewController {
	private IReviewService reviewService;
	
	@Autowired
	public ReviewController(IReviewService reviewService) {
		this.reviewService = reviewService;
	}
	//이 메서드로 처음 들어온 다음 true 반환된다면 아래 post매핑으로 리디렉팅
//	@GetMapping("/review/reservation/{reservationId}")
//	public ResponseEntity<Boolean> checkDuplicateReview(@PathVariable Long reservationId) {
//		Boolean bool = reviewService.checkDuplicateReview(reservationId);
//		
//		return new ResponseEntity<>(bool, HttpStatus.OK);
//	}
	
	@PostMapping("/review/reservation/{reservationId}")
	public ResponseEntity<Review>	createReview(@AuthenticationPrincipal User member, @PathVariable Long reservationId, @RequestBody ReviewDto reviewDto) {
		Review	review = reviewService.createReview(member, reservationId, reviewDto);
		
		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}
	
	@PostMapping("/review/image/reservation/{reservationId}")
	public ResponseEntity<String>	uploadReviewImg(@PathVariable Long reservationId, MultipartFile file) {
		reviewService.uploadReviewImg(reservationId, file);
		return ResponseEntity.ok("리뷰 이미지 등록 완료 ");
	}
	
	@PostMapping("/reviewimage/reservation/{reservationId}")
	public ResponseEntity<Review>	createReviewWithImg(
			@AuthenticationPrincipal User member, 
			@PathVariable Long reservationId,
			@RequestPart("review") ReviewImageCreateDto reviewDto,
			@RequestPart(value = "file", required = false) MultipartFile file
 			){
		Review	review;
		
		System.out.println(file);
		
		if (file != null) {
			review = reviewService.createReviewWithImg(member, reservationId, reviewDto, file);
		}else {
			review = reviewService.createReviewWithImg(member, reservationId, reviewDto);
		}
		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}
	
//	@PostMapping("/reviewimage/reservation/{reservationId}")
//	public ResponseEntity<Review>	createReviewWithImg(
//			@AuthenticationPrincipal User member, 
//			@PathVariable Long reservationId,
//			@RequestPart("review") ReviewImageCreateDto reviewDto
// 			){
//		Review	review = reviewService.createReviewWithImg(member, reservationId, reviewDto);
//		
//		return new ResponseEntity<>(review, HttpStatus.CREATED);
//	}
//	
//	

	//리뷰아이디로 리뷰 조회
	@GetMapping("/review/search/{reviewId}")
	public ResponseEntity<Review>	getReviewById(@PathVariable Long reviewId) {
		Review	review = reviewService.getReviewById(reviewId);
		
		return new ResponseEntity<>(review, HttpStatus.OK);
	}
			
	
	//회원의 자신의 리뷰 수정
	@PutMapping("/review/update/{reviewId}")
	public ResponseEntity<Review> updateReviewWithImg(
			@AuthenticationPrincipal User member,
			@PathVariable Long reviewId,
			@RequestPart("review") ReviewImageUpdateDto reviewDto,
			@RequestPart(value = "file", required = false) MultipartFile file
			) {
		Review	review;
		
		if (file != null) {
			review = reviewService.updateReviewWithImg(member, reviewId,reviewDto, file);
		} else {
			review = reviewService.updateReviewWithImg(member, reviewId,reviewDto);
		}
		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}
	
//	@PutMapping("/review/update/{reviewId}")
//	public ResponseEntity<Review>	updateReviewById(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {
//		Review review = reviewService.updateReviewById(reviewId, reviewDto);
//		
//		return new ResponseEntity<>(review, HttpStatus.OK);
//	}	
	
	//회원의 자신의 리뷰 삭제
	@DeleteMapping("/review/delete/{reviewId}")
	public ResponseEntity<Review>	deleteReviewById(@PathVariable Long reviewId) {
		Review	review = reviewService.deleteReviewById(reviewId);
		
		return new ResponseEntity<>(review, HttpStatus.NO_CONTENT);
	}
	
	
	//회원의 자신의 리뷰들 조회
	@GetMapping("/review/myreviews")
	public ResponseEntity<List<ReviewResultDto>>	getReviewsByMemberId(@AuthenticationPrincipal User member) {
		List<ReviewResultDto>	reviews = reviewService.getReviewsByMemberId(member); 
		
		return new ResponseEntity<>(reviews, HttpStatus.OK);	
	}
	
	//해당 가게의 모든 리뷰들 
	@GetMapping("/review/restaurant/{restId}")
	public ResponseEntity<List<ReviewResultRestaurantDto>>	getReviewsByRestId(@PathVariable Long restId) {
		List<ReviewResultRestaurantDto>	reviews = reviewService.getReviewsByRestId(restId); 

		System.out.println("조회 결과 :" + reviews);
		
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
	
	//사업자의 본인가게 리뷰 목록 조회
	@GetMapping("/bm/review/list/{restId}")
	public ResponseEntity<List<Review>>	getBmReviewsByRestId(@PathVariable Long restId) {
		List<Review>	reviews = reviewService.getBmReviewsByRestId(restId); 
		
		
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
//	
//	
//	//사업자의 리뷰 삭제 요청
//	@GetMapping("/bm/review/delete/{reviewId}")
//	public ResponseEntity<Review>	requestDeleteReview(@PathVariable Long reviewId) {
//		Review	review = reviewService.requestDeleteReview(reviewId);
//		
//		return new ResponseEntity<>(review, HttpStatus.OK);
//	}
//	
//	//관리자의 리뷰 삭제 요청 조회
//	@GetMapping("/admin/review/delete/list")
//	public ResponseEntity<List<Review>>	getDeleteReviewRequest(@PathVariable Long reviewId) {
//		List<Review>	reviews = reviewService.getDeleteReviewRequest(reviewId); 
//		
//		return new ResponseEntity<>(reviews, HttpStatus.OK);
//	}
	
//	//관리자의 리뷰 삭제 관리 
//	@PostMapping("/admin/review/delete/{reviewDeleteId}")
//	public ResponseEntity<Review>	deleteRequestedReview(@PathVariable Long reviewId) {
//		Review	review = reviewService.deleteRequestedReview(reviewId);
//		
//		return new ResponseEntity<>(review, HttpStatus.OK);
//		
//	}
	
	

}
