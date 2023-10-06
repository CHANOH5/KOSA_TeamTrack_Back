package com.my.notice.service;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dao.NoticeDAO;
import com.my.notice.dao.NoticeDAOImpl;
import com.my.notice.dto.NoticeDTO;
import com.my.util.PageGroup;

public class NoticeServiceImpl implements NoticeService {
	private NoticeDAO noticeDAO;
	private static NoticeServiceImpl service = new NoticeServiceImpl();
	public NoticeServiceImpl() {
		noticeDAO = new NoticeDAOImpl();
	}
	public static NoticeServiceImpl getInstance() {
		return service;
	}
	
	@Override
	public PageGroup<NoticeDTO> findNoticeAll(int currentPage, Integer teamNo) throws FindException{
		if(currentPage <1) {
			currentPage = 1;
		}
		
		int cntPerPage = 10; //한페이지당 보여줄 목록 수
		
		int startRow = 0;
		int endRow = 0;
		
		startRow = (currentPage -1)*cntPerPage +1;
		endRow = currentPage*cntPerPage;
		
		List<NoticeDTO> noticeList = noticeDAO.selectNoticeAll(startRow, endRow, teamNo);
		
		int totalCnt = noticeDAO.selectNoticeCount(teamNo);
		
		PageGroup<NoticeDTO> pg = new PageGroup<>(noticeList, currentPage, totalCnt); 
		return pg;
	}
	
	@Override
	public NoticeDTO findByNoticeNo(Integer teamNo, Integer noticeNo) throws FindException{
		return noticeDAO.selectByNoticeNo(teamNo, noticeNo);
	}
	
	@Override
	public void writeNotice(Integer teamNo, NoticeDTO notice) throws AddException{
		noticeDAO.insertNotice(teamNo, notice);
	}
	
	@Override
	public void removeNotice(Integer teamNo, Integer noticeNo) throws RemoveException{
		noticeDAO.deleteNotice(teamNo, noticeNo);
	}
	
	@Override
	public void modifyNotice(Integer teamNo, NoticeDTO notice) throws ModifyException{
		noticeDAO.updateNotice(teamNo, notice);
	}
}
