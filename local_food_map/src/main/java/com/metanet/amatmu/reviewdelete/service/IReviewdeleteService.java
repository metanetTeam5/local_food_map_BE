package com.metanet.amatmu.reviewdelete.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.reviewdelete.model.Reviewdelete;
import com.metanet.amatmu.reviewdelete.model.ReviewdeleteDto;

public interface IReviewdeleteService {
	Reviewdelete		insertReviewdelete(Long reviewId, ReviewdeleteDto reviewdeleteDto);
	List<Reviewdelete>	getDeleteReviewRequest();
	Reviewdelete		deleteRequestedReview(Long reviewdeleteId);

}
