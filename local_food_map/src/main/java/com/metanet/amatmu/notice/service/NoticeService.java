package com.metanet.amatmu.notice.service;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.amatmu.notice.dao.INoticeRepository;




@Service
public class NoticeService implements INoticeService{
	private INoticeRepository	noticeRepository;
	
	@Autowired
	public NoticeService(INoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}
	
	@Override
	public Notice	insertNotice(NoticeDto noticeDto) {
		//dto로 받은것 엔티티로 만들어서 repository에 저장해주고, 엔티티 반환 
		//변환시에 id, date 등등 추가적인 서비스 로직 적용 필요
		
		Notice	notice = new Notice(
				noticeRepository.selectMaxNoticeId() + 1,
				noticeDto.getNotiTitle(),
				noticeDto.getNotiContent(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				0,
				noticeDto.getMembId()
				);
		noticeRepository.insertNotice(notice);
		return notice;
	}
	
	@Override
	public List<Notice>	selectAllNotice() {
		List<Notice> list = noticeRepository.selectAllNotice();
		return list;
	}
	
	@Override
	public Notice	selectNoticeById(Long notiId) {
		Notice	notice = noticeRepository.selectNoticeById(notiId);
		
		if (notice == null) {
			throw new QueryFailedException("There is no Notice " + notiId + ".");
		}
		notice.setNotiViews(notice.getNotiViews() + 1);
		int	n = noticeRepository.updateNoticeViews(notice);
		if (n > 0) {
			return notice;
		} else {
			throw new QueryFailedException("Failed to update notice views");
		}
	}
	
	
	@Override
	public Notice	updateNotice(Notice notice) {
		int	n = 0;
		
		notice.setNotiUpdateDate(LocalDateTime.now());
		n = noticeRepository.updateNotice(notice);
		if (n > 0) {
			return notice;
		} else {
			throw new UpdateFailedException("Fail to update the Notice");
		}
	}
	
	@Override
	public Notice	deleteNotice(Long noticeId) {
		int	n = 0;
		
		Notice deletedNotice = noticeRepository.selectNoticeById(noticeId);
		
		n = noticeRepository.deleteNoticeById(noticeId);
		if (n > 0) {
			return deletedNotice;
		} else {
			throw new QueryFailedException("Fail to delete the Notice.");
		}
	}
	
}
