package com.metanet.amatmu.notice.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.metanet.amatmu.notice.model.Notice;
import com.metanet.amatmu.notice.model.NoticeDto;



public interface INoticeService {
	Notice			insertNotice(NoticeDto noticeDto);
	List<Notice>	selectAllNotice();
	Notice			selectNoticeById(Long notiId);
	Notice			updateNotice(Notice notice);
	Notice			deleteNotice(Long noticeId);
}
