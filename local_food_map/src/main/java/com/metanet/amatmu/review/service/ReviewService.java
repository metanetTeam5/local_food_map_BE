package com.metanet.amatmu.review.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.member.model.Member;
import com.metanet.amatmu.notice.model.Notice;
import com.metanet.amatmu.review.dao.IReviewRepository;
import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.review.model.ReviewDto;

import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeCheckerStateKt;


@Service
public class ReviewService implements IReviewService {
	private IReviewRepository reviewRepository;
	private IMemberRepository memberRepository;
	
	@Autowired
	public ReviewService(IReviewRepository reviewRepository, IMemberRepository memberRepository) {
		this.reviewRepository = reviewRepository;
		this.memberRepository = memberRepository;
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
				reviewDto.getRevwImg(), 
				reviewDto.getRestId(), 
				membId, 
				reservationId);
				
		queryStatus = reviewRepository.insertReview(createdReview);
		if (queryStatus == 0) {
			throw new QueryFailedException("Failed to insert review: query error");
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
	public List<Review>	getReviewsByMemberId(User member) {
		Long			memberId = memberRepository.selectMember(member.getUsername()).getMemberId();
		List<Review>	reviews = reviewRepository.selectReviewsByMemberId(memberId);
		
		return reviews;
	}
	
	@Override
	public List<Review>	getReviewsByRestId(Long restId) {
		List<Review>	reviews = reviewRepository.selectReviewsByRestId(restId);
		
		return reviews;
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
