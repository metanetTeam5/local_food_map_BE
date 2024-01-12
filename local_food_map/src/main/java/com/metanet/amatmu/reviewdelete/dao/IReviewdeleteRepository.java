package com.metanet.amatmu.reviewdelete.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.reviewdelete.model.Reviewdelete;

@Repository
@Mapper
public interface IReviewdeleteRepository {
	Long				selectMaxReviewdeleteId();
	int					insertReviewdelete(Reviewdelete reviewdelete);
	List<Reviewdelete>	selectReviewdeletes();
	Review				deleteRequestedReview(Long reviewId);
	int					deleteReviewdeleteByReviewId(Long reviewId);
	Reviewdelete		selectReviewdeleteById(Long rdelId);
}
