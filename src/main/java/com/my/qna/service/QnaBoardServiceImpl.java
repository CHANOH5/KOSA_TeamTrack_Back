package com.my.qna.service;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dto.NoticeDTO;
import com.my.qna.dao.QnaBoardDAO;
import com.my.qna.dao.QnaBoardDAOImpl;
import com.my.qna.dto.QnaBoardDTO;
import com.my.task.dto.TaskDTO;
import com.my.util.PageGroup;

public class QnaBoardServiceImpl implements QnaBoardService {

	private QnaBoardDAO qnaBoardDAO;
	
	private static QnaBoardServiceImpl  service = new QnaBoardServiceImpl();
	
	private QnaBoardServiceImpl() {
		qnaBoardDAO = new QnaBoardDAOImpl();
	}
	
	public static QnaBoardService getInstance() {
		return service;
	}

	@Override
	public void create(QnaBoardDTO qnaBoardDTO) throws AddException {
		
		qnaBoardDAO.create(qnaBoardDTO);

	} // create

	@Override
	public PageGroup<QnaBoardDTO> selectAll(Integer teamNo, int currentPage) throws FindException {
		
		if(currentPage <1) {
			currentPage = 1;
		}
		
		int cntPerPage = 10; //한페이지당 보여줄 목록 수
		
		int startRow = 0;
		int endRow = 0;
		
		startRow = (currentPage -1)*cntPerPage +1;
		endRow = currentPage*cntPerPage;
		
		System.out.println("service teamNO ============> " + teamNo);
		
		List<QnaBoardDTO> list = qnaBoardDAO.selectAll(teamNo, startRow, endRow);
		
		int totalCnt = qnaBoardDAO.selectAllCount(teamNo);
		
		PageGroup<QnaBoardDTO> pg = new PageGroup<>(list, currentPage, totalCnt); 
		return pg;

	} // selectAll4
	
	

	@Override
	public QnaBoardDTO selectByQnaNo(Integer teamNo, Integer QnaNo) throws FindException {
		
		
		
		return null;
		
	} // selectByQnaNo

	@Override
	public Integer update(QnaBoardDTO qnaBoardDTO) throws ModifyException {

		qnaBoardDAO.update(qnaBoardDTO);
		
		return null;
	} // update

	@Override
	public Integer delete(Integer qna_no) throws RemoveException {
		
		qnaBoardDAO.delete(qna_no);

		return null;
	} // delete
	
	// ------------ 테스트 -------------
	
	 public static void main(String[] args) {
		 
	        QnaBoardServiceImpl service = new QnaBoardServiceImpl();

	        try {
	            Integer teamNo = 9999; // 팀 번호를 지정하세요.
	            int currentPage = 1; // 현재 페이지를 지정하세요.

	            PageGroup<QnaBoardDTO> result = service.selectAll(teamNo, currentPage);

	            List<QnaBoardDTO> list = result.getList();
	            for (QnaBoardDTO dto : list) {
	            	
	                System.out.println(dto);
	                
	            }

	            // 페이지 정보 출력
	            System.out.println("Total Pages: " + result.getTotalPage());
	            System.out.println("Current Page: " + result.getCurrentPage());
	            System.out.println("Total Count: " + result.getTotalCnt());

	        } catch (FindException e) {
	            e.printStackTrace();
	        }
	    }

} // end class