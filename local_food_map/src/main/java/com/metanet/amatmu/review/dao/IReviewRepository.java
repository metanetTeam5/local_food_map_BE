package com.metanet.amatmu.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.review.model.Review;

@Repository
@Mapper
public interface IReviewRepository {
	Long			selectMaxReviewId();
	int				insertReview(Review review);
	Review			selectReviewByReservationId(Long reservationId);
	
	Review			selectReviewById(Long reviewId);
	int				updateReview(Review review);
	int				deleteReviewById(Long reviewId);
	List<Review>	selectReviewsByMemberId(Long memberId);
	List<Review>	selectReviewsByRestId(Long restId);
}
