package com.metanet.amatmu.reviewdelete.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.metanet.amatmu.review.model.Review;
import com.metanet.amatmu.review.service.IReviewService;
import com.metanet.amatmu.reviewdelete.model.Reviewdelete;
import com.metanet.amatmu.reviewdelete.model.ReviewdeleteDto;
import com.metanet.amatmu.reviewdelete.service.IReviewdeleteService;

@RestController
public class ReviewdeleteController {
	private	IReviewdeleteService	reviewdeleteService;
	
	@Autowired
	public ReviewdeleteController(IReviewdeleteService reviewdeleteService) {
		this.reviewdeleteService = reviewdeleteService;
	}
	
	//사업자의 리뷰 삭제 요청
	@PostMapping("/bm/review/delete/{reviewId}")
	public ResponseEntity<Reviewdelete>	requestDeleteReview(@PathVariable Long reviewId, @RequestBody ReviewdeleteDto reviewdeleteDto) {
		Reviewdelete reviewdelete = reviewdeleteService.insertReviewdelete(reviewId, reviewdeleteDto);
		
		return new ResponseEntity<>(reviewdelete, HttpStatus.CREATED);
	}
	
	//관리자의 리뷰 삭제 요청 조회
	@GetMapping("/admin/review/delete/list")
	public ResponseEntity<List<Reviewdelete>>	getDeleteReviewRequest() {
		List<Reviewdelete>	reviewdeletes = reviewdeleteService.getDeleteReviewRequest(); 
		
		return new ResponseEntity<>(reviewdeletes, HttpStatus.OK);
	}

	//관리자의 리뷰 삭제 관리 
	@DeleteMapping("/admin/review/delete/{reviewdeleteId}")
	public ResponseEntity<Reviewdelete>	deleteRequestedReview(@PathVariable Long reviewdeleteId) {
		Reviewdelete	reviewdelete = reviewdeleteService.deleteRequestedReview(reviewdeleteId);
		
		return new ResponseEntity<>(reviewdelete, HttpStatus.OK);	
	}

}
