package com.metanet.amatmu.reviewdelete.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.businessman.dao.IBusinessmanRepository;
import com.metanet.amatmu.exception.QueryFailedException;
import com.metanet.amatmu.member.dao.IMemberRepository;
import com.metanet.amatmu.review.dao.IReviewRepository;
import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.reviewdelete.dao.IReviewdeleteRepository;
import com.metanet.amatmu.reviewdelete.dto.ReviewBmanDto;
import com.metanet.amatmu.reviewdelete.model.Reviewdelete;
import com.metanet.amatmu.reviewdelete.model.ReviewdeleteDto;

@Service
public class ReviewdeleteService implements IReviewdeleteService {
	IReviewdeleteRepository	reviewdeleteRepository;
	IBusinessmanRepository	businessmanRepository;
	IReviewRepository		reviewRepository;
	
	@Autowired
	public ReviewdeleteService(IReviewdeleteRepository	reviewdeleteRepository, IBusinessmanRepository	businessmanRepository, IReviewRepository		reviewRepository) {
		this.reviewdeleteRepository = reviewdeleteRepository;
		this.businessmanRepository = businessmanRepository;
		this.reviewRepository = reviewRepository;
	}
	
	
	@Override
	public Reviewdelete	insertReviewdelete(Long reviewId, ReviewdeleteDto reviewdeleteDto) {
		int				queryStatus = 0;
		Long			reviewdeleteId = reviewdeleteRepository.selectMaxReviewdeleteId() + 1;
		Reviewdelete	reviewdelete = new Reviewdelete(reviewdeleteId, reviewdeleteDto.getBmanId(), reviewId);
		
		queryStatus = reviewdeleteRepository.insertReviewdelete(reviewdelete);
		if (queryStatus == 0) {
			throw new QueryFailedException("Failed to insert Reviewdelete: query error");
		}
		return reviewdelete;
	}
	
	@Override
	public List<ReviewBmanDto>	getDeleteReviewRequest() {
		List<Reviewdelete>	reviewdeletes = reviewdeleteRepository.selectReviewdeletes();
		List<ReviewBmanDto>	reviewBmanDtos = new ArrayList<>();
		
		for (Reviewdelete rv :reviewdeletes ) {
			Review			review = reviewRepository.selectReviewById(rv.getRevwId());
			ReviewBmanDto	rBmanDto = new ReviewBmanDto(rv.getRdelId(), rv.getBmanId(), rv.getRevwId(),
					review.getRevwContent(),	//revwContent
					review.getRevwStarRate()	//revwstarRate
					);
			reviewBmanDtos.add(rBmanDto);
		}
		
		return reviewBmanDtos;	//return reviewdeletes;
	}
	
	@Override
	public Reviewdelete				deleteRequestedReview(Long reviewdeleteId) {
		//요청된 리뷰 삭제,
		//리뷰딜리트 삭제
		int				queryStatus = 0;
//		Reviewdelete	reviewdelete = reviewdeleteRepository.selectReviewdeleteByReviewId(reviewId);
		Reviewdelete	reviewdelete = reviewdeleteRepository.selectReviewdeleteById(reviewdeleteId);
		
		queryStatus = reviewdeleteRepository.deleteReviewdeleteByReviewId(reviewdelete.getRevwId());
		if (!(queryStatus > 0)) {
			throw new QueryFailedException("Failed to delete the Reviewdelete.");
		}
		queryStatus = reviewRepository.deleteReviewById(reviewdelete.getRevwId());
		if (!(queryStatus > 0)) {
			throw new QueryFailedException("Failed to delete the Review.");
		}
		
		return reviewdelete;
	}

}
