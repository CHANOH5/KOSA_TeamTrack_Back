package com.my.team.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dao.NoticeDAO;
import com.my.notice.dao.NoticeDAOImpl;
import com.my.notice.dto.NoticeDTO;
import com.my.qna.dao.QnaBoardDAO;
import com.my.qna.dao.QnaBoardDAOImpl;
import com.my.task.dao.TaskDAO;
import com.my.task.dao.TaskDAOImpl;
import com.my.task.dto.TaskDTO;
import com.my.team.dao.TeamDAO;
import com.my.team.dao.TeamDAOImpl;
import com.my.team.dto.AttendanceDTO;
import com.my.team.dto.SignupTeamDTO;
import com.my.team.dto.TeamDTO;
import com.my.team.dto.TeamMemberDTO;
import com.my.team.dto.TeamHashtagDTO;
import com.my.util.MainPageGroup;
import com.my.util.PageGroup;

public class TeamServiceImpl implements TeamService {

	private TeamDAO teamDAO;
	private NoticeDAO noticeDAO;
	private TaskDAO taskDAO;
	private QnaBoardDAO qnaDAO;
	
	private static TeamServiceImpl service = new TeamServiceImpl();

	private TeamServiceImpl() {
		teamDAO = new TeamDAOImpl();
		noticeDAO = new NoticeDAOImpl();
		taskDAO = new TaskDAOImpl();
		qnaDAO = new QnaBoardDAOImpl();
	}

	public static TeamServiceImpl getInstance() {
		return service;
	}

	// ------------------------------------------------------------------------

	// 서현 웅니
	public MainPageGroup<TeamDTO> findAll(int currentPage, String column) throws FindException{
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		int cntPerPage = 9; //한페이지당 보여줄 목록 수 
		                    
		//currentPage        //1  2  3  4
		int startRow;        //1  4  7  10
		int endRow;          //3  6  9  12
		//TODO
		endRow = currentPage * cntPerPage;
		startRow = ( currentPage -1 ) *cntPerPage + 1;
		//return repository.selectAll(startRow, endRow);
		
		List<TeamDTO> list = teamDAO.selectByCondition(column, startRow, endRow);
		int totalCnt = teamDAO.selectCount();		
		MainPageGroup<TeamDTO> pg = new MainPageGroup<>(list, currentPage, totalCnt);
		return pg;
	}
	
	@Override
	public MainPageGroup<TeamDTO> selectByData(int currentPage, String table, String column, String data) throws FindException{
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		int cntPerPage = 9; //한페이지당 보여줄 목록 수 
		                    
		//currentPage        //1  2  3  4
		int startRow;        //1  4  7  10
		int endRow;          //3  6  9  12 
		//TODO
		endRow = currentPage * cntPerPage;
		startRow = ( currentPage -1 ) *cntPerPage + 1;
		//return repository.selectAll(startRow, endRow);
		
		List<TeamDTO> list = teamDAO.selectByData(table, column, data, startRow, endRow);
		int totalCnt = teamDAO.selectCountOfSelectData(table, column, data);
		MainPageGroup<TeamDTO> pg = new MainPageGroup<>(list, currentPage, totalCnt);
		return pg;
	}
	
	@Override
	public MainPageGroup<TeamDTO> selectByDate(int currentPage, String column, String startDate, String endDate) throws FindException{
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		int cntPerPage = 9; //한페이지당 보여줄 목록 수 
		                    
		//currentPage        //1  2  3  4
		int startRow;        //1  4  7  10
		int endRow;          //3  6  9  12 
		//TODO
		endRow = currentPage * cntPerPage;
		startRow = ( currentPage -1 ) *cntPerPage + 1;
		//return repository.selectAll(startRow, endRow);
		
		List<TeamDTO> list = teamDAO.selectByDate(column, startDate, endDate, startRow, endRow);
		int totalCnt = teamDAO.selectCountOfSelectDate(column, startDate, endDate);
		MainPageGroup<TeamDTO> pg = new MainPageGroup<>(list, currentPage, totalCnt);
		return pg;
	}
	
	@Override
	public MainPageGroup<TeamDTO> selectHashtag(int currentPage, String hashtag) throws FindException{
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		int cntPerPage = 9; //한페이지당 보여줄 목록 수 
		                    
		//currentPage        //1  2  3  4
		int startRow;        //1  4  7  10
		int endRow;          //3  6  9  12 
		//TODO
		endRow = currentPage * cntPerPage;
		startRow = ( currentPage -1 ) *cntPerPage + 1;
		//return repository.selectAll(startRow, endRow);
		
		List<TeamDTO> list = teamDAO.selectHashtag(hashtag, startRow, endRow);
		int totalCnt = teamDAO.selectCountOfSelectHashtag(hashtag);
		System.out.println("총개수"+totalCnt);
		MainPageGroup<TeamDTO> pg = new MainPageGroup<>(list, currentPage, totalCnt);
		return pg;
	}

	@Override
	public TeamDTO selectByTeamNo(int teamNo) throws FindException {
		return teamDAO.selectByTeamNo(teamNo);
	}

	@Override
	public void createTeam(HashMap<String, Object> params) throws AddException {
		teamDAO.createTeam(params);
	}
	@Override
	public int teamNameDupChk(String teamName) throws FindException {
		return teamDAO.selectByTeamName(teamName);
	}

	@Override
	public void updateTeam(TeamDTO t) throws ModifyException {
		teamDAO.updateTeam(t);
	}

	@Override
	public List<TeamHashtagDTO> selectTeamHashtag(int teamNo) throws FindException {
		return teamDAO.selectTeamHashtag(teamNo);
	}
	
	@Override
	public void updateHashtag(HashMap<String, Object> params) throws ModifyException{
		teamDAO.updateHashtag(params);
	}

	@Override
	public void deleteHashtag(int teamNo) throws RemoveException{
		teamDAO.deleteHashtag(teamNo);
	}

	@Override
	public void deleteTeam(int teamNo) throws RemoveException {
		try {
			int noticeCnt = noticeDAO.selectNoticeCount(teamNo);
			int taskCnt = taskDAO.selectAllTaskCount(teamNo);
			int qnaCnt = qnaDAO.selectAllCount(teamNo);
			if(noticeCnt == 0 & taskCnt == 0 & qnaCnt == 0) {
				teamDAO.deleteTeam(teamNo);
			}else {
				throw new RemoveException("팀을 삭제할 수 없습니다.");
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<TeamDTO> selectTopThreeTeams() throws FindException {
		return teamDAO.selectByCondition("viewcnt", 1, 9);
	}

	@Override
	public List<TeamDTO> selectByCondition(String column) throws FindException {
		return teamDAO.selectByCondition(column, 1, 9);
	}



	// ------------------------------------------------------------------------
	
	//워니 침입
	
	@Override
	public Integer leaderChk(String id, Integer teamNo) throws FindException{
		String leaderId = teamDAO.selectLeaderId(teamNo);
		Integer memStatus = 0;
		if(id.equals(leaderId)) {
			memStatus = 1;
		}
		return memStatus;
	}
	
	@Override
	public PageGroup<SignupTeamDTO> findMyTeam(int currentPage, String id, int menuStatus) throws FindException{
		if(currentPage <1) {
			currentPage = 1;
		}

		int cntPerPage = 10; //한페이지당 보여줄 목록 수

		int startRow =0;
		int endRow =0;

		startRow = (currentPage -1)*cntPerPage +1;
		endRow = currentPage*cntPerPage;
		
		List<SignupTeamDTO> teamList = new ArrayList<>();
		int totalCnt=0;
		
		if(menuStatus==1) {
			teamList = teamDAO.selectMyTeam(startRow, endRow, id);
			totalCnt = teamDAO.selectMyTeamCount(id);
		}else if(menuStatus==2) {
			teamList = teamDAO.selectEndTeam(startRow, endRow, id);
			totalCnt = teamDAO.selectEndTeamCount(id);
		}else if(menuStatus==3) {
			int status = 0;
			teamList = teamDAO.selectWaitingTeam(startRow, endRow, id, status);
			totalCnt = teamDAO.selectWaitingTeamCount(id, status);
		}

		System.out.println("리스트 개수: " +totalCnt);

		PageGroup<SignupTeamDTO> pg = new PageGroup<>(teamList, currentPage, totalCnt);
		return pg;
	}
	
	@Override
	public PageGroup<SignupTeamDTO> findRejectedTeam(int currentPage, String id) throws FindException{
		if(currentPage <1) {
			currentPage = 1;
		}

		int cntPerPage = 10; //한페이지당 보여줄 목록 수

		int startRow =0;
		int endRow =0;

		startRow = (currentPage -1)*cntPerPage +1;
		endRow = currentPage*cntPerPage;
		
		int status = 2;
		
		List<SignupTeamDTO> teamList = teamDAO.selectWaitingTeam(startRow, endRow, id, status);
		int totalCnt= teamDAO.selectWaitingTeamCount(id, status);

		System.out.println("리스트 개수: " +totalCnt);

		PageGroup<SignupTeamDTO> pg = new PageGroup<>(teamList, currentPage, totalCnt);
		return pg;
	}
	
	@Override
	public void cancelWaiting(String id, Integer teamNo) throws RemoveException{
		teamDAO.deleteSignupTeam(id, teamNo);
	}
	
	@Override
	public void rejectCheck(String id, Integer teamNo) throws RemoveException{
		teamDAO.deleteSignupTeam(id, teamNo);
	}
	
	// ------------------------------------------------------------------------

	// 셍나

	@Override
	public Integer selectTeamMemberStatus(String id, Integer teamNo) throws FindException {
		return teamDAO.selectTeamMemberStatus(id, teamNo);
	}
	
	@Override
	public String selectTeamInfoByTeamNo(int teamNo) throws FindException {
		return teamDAO.selectTeamInfoByTeamNo(teamNo);
	}
	
	@Override
	public List<TeamDTO> selectAllTeamInfo(int teamNo) throws FindException {
		return teamDAO.selectAllTeamInfo(teamNo);
	}

	@Override
	public List<NoticeDTO> selectNoticeListByTeamNo(int teamNo) throws FindException {
		return teamDAO.selectNoticeListByTeamNo(teamNo);
	}

	@Override
	public void insertSignUpTeam(SignupTeamDTO signupTeamDTO) throws AddException {
		teamDAO.insertSignUpTeam(signupTeamDTO);
	}

	@Override
	public void updateTeamMemberStatusResign(Integer teamNo, String id) throws ModifyException {
	    teamDAO.updateTeamMemberStatusResign(teamNo, id);
	}

	@Override
	public void deleteSignupTeam(String id) throws RemoveException {
		teamDAO.deleteSignupTeam(id);
	}

	@Override
	public void leaveTeam(Integer teamNo, String id) throws Exception {
		teamDAO.leaveTeam(teamNo, id);
	}

	@Override
	public List<String> selectNicknameByTeamNo(int teamNo) throws FindException {
		return teamDAO.selectNicknameByTeamNo(teamNo);
	}

	@Override
	public void updateViewCnt(int teamNo) throws ModifyException {
		teamDAO.updateViewCnt(teamNo);
	}
	
	@Override
	public int selectViewCnt(int teamNo) throws FindException {
		return teamDAO.selectViewCnt(teamNo);
	}

//	-------------------
	
	@Override
	public String selectAttendanceDate(Map<String, Object> map) throws FindException {
		return teamDAO.selectAttendanceDate(map);
	}
	
	@Override
	public void insertAttendanceById(Integer teamNo, String id) throws AddException {
		teamDAO.insertAttendanceById(teamNo, id);
	}

	@Override
	public List<AttendanceDTO> selectAttendanceById(Integer teamNo, String id) throws FindException {
		return teamDAO.selectAttendanceById(teamNo, id);
	}
	
//	-------------------

	@Override
	public List<Map<String, Object>> selectMemberInfo(Integer teamNo) throws FindException {
		return teamDAO.selectMemberInfo(teamNo);
	}

	@Override
	public void updateTeamMemberStatusDismiss(Map<String, Object> map) throws ModifyException {
		teamDAO.updateTeamMemberStatusDismiss(map);
	}

	@Override
	public List<Map<String, Object>> selectRequestInfo(Integer teamNo) throws FindException {
		return teamDAO.selectRequestInfo(teamNo);
	}

	@Override
	public void updateRequestInfoApprove(Map<String, Object> map) throws ModifyException {
		teamDAO.updateRequestInfoApprove(map);
	}
	
	@Override
	public void insertRequestInfoApprove(Map<String, Object> map) throws AddException {
		teamDAO.insertRequestInfoApprove(map);
	}
	
    @Override
    public void approveRequest(Map<String, Object> map) throws Exception {
    	teamDAO.approveRequest(map);
    }

	@Override
	public void updateRequestInfoReject(Map<String, Object> map) throws ModifyException {
		teamDAO.updateRequestInfoReject(map);
	}

	@Override
	public void insertExaminer(TaskDTO taskDTO, Integer teamNo) throws ModifyException {
		teamDAO.insertExaminer(taskDTO, teamNo);
	}

} // end class