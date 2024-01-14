package com.metanet.amatmu.notice.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metanet.amatmu.notice.model.Notice;
import com.metanet.amatmu.notice.model.NoticeDto;
import com.metanet.amatmu.notice.model.NoticeResponseDto;
import com.metanet.amatmu.notice.service.INoticeService;

import ch.qos.logback.core.model.Model;



@RestController
@RequestMapping("/")
public class NoticeController {
	private INoticeService noticeService;
	
	@Autowired
	public NoticeController(INoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@PostMapping("/admin/notice")
	public ResponseEntity<Notice> createNotice(@AuthenticationPrincipal User member, @RequestBody NoticeDto noticeDto) {
		Notice notice = noticeService.insertNotice(member, noticeDto);
		return new ResponseEntity<>(notice, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/notice")
	public ResponseEntity<List<Notice>> readAllNotice() {
		List<Notice> list = noticeService.selectAllNotice();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
//	@GetMapping("/notice/{noticeId}")
//	public ResponseEntity<Notice> readNoticEntityById(@PathVariable Long noticeId) {
//		Notice	notice = noticeService.selectNoticeById(noticeId);
//		return new ResponseEntity<>(notice, HttpStatus.OK);
//	}
	@GetMapping("/notice/{noticeId}")
	public ResponseEntity<NoticeResponseDto> readNoticEntityById(@PathVariable Long noticeId) {
		NoticeResponseDto	notice = noticeService.selectNoticeById(noticeId);
		return new ResponseEntity<>(notice, HttpStatus.OK);
	}
	
	@PutMapping("/admin/notice/{noticeId}")
	public ResponseEntity<Notice>	updateNotice(@RequestBody Notice notice) {
		Notice	updatedNotice = noticeService.updateNotice(notice);
		return new ResponseEntity<>(updatedNotice, HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/notice/{noticeId}")
	public ResponseEntity<Notice> deleteNotice(@PathVariable Long noticeId) {
		Notice deletedNotice = noticeService.deleteNotice(noticeId);
		return new ResponseEntity<>(deletedNotice, HttpStatus.NO_CONTENT);
	}

}
