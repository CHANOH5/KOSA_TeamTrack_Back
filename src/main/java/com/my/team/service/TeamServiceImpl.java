package com.my.team.service;

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
import com.my.task.dao.TaskDAO;
import com.my.task.dao.TaskDAOImpl;
import com.my.task.dto.TaskDTO;
import com.my.team.dao.TeamDAO;
import com.my.team.dao.TeamDAOImpl;
import com.my.team.dto.AttendanceDTO;
import com.my.team.dto.SignupTeamDTO;
import com.my.team.dto.TeamDTO;
import com.my.util.PageGroup;

public class TeamServiceImpl implements TeamService {

	private TeamDAO teamDAO;
	private NoticeDAO noticeDAO;
	private TaskDAO taskDAO;

	private static TeamServiceImpl service = new TeamServiceImpl();

	private TeamServiceImpl() {
		teamDAO = new TeamDAOImpl();
		noticeDAO = new NoticeDAOImpl();
		taskDAO = new TaskDAOImpl();
	}

	public static TeamServiceImpl getInstance() {
		return service;
	}

	// ------------------------------------------------------------------------

	// 서현 웅니
	@Override
	public PageGroup<TeamDTO> findAll(int currentPage) throws FindException{
		if(currentPage < 1) {
			currentPage = 1;
		}

		int cntPerPage = 10; //한페이지당 보여줄 목록 수

		//currentPage        //1  2  3  4
		int startRow;        //1  4  7  10
		int endRow;          //3  6  9  12
		//TODO
		endRow = currentPage * cntPerPage;
		startRow = ( currentPage -1 ) *cntPerPage + 1;
		//return repository.selectAll(startRow, endRow);

		List<TeamDTO> list = teamDAO.selectByCondition("createdate", startRow, endRow);
		int totalCnt = teamDAO.selectCount();
		PageGroup<TeamDTO> pg = new PageGroup<>(list, currentPage, totalCnt);
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
	public void teamNameDupChk(String teamName) throws FindException {
		teamDAO.selectByTeamName(teamName);
	}

	@Override
	public void updateTeam(TeamDTO t) throws ModifyException {
		teamDAO.updateTeam(t);
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
			if(noticeCnt == 0 & taskCnt ==0) {
				teamDAO.deleteTeam(teamNo);
			}
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<TeamDTO> selectTopThreeTeams() throws FindException {
		return teamDAO.selectByCondition("view_cnt", 1, 3);
	}

	@Override
	public List<TeamDTO> selectByCondition(String column) throws FindException {
		return teamDAO.selectByCondition(column, 1, 10);
	}

	@Override
	public TeamDTO selectByTeamName(String teamName) throws FindException {
		return teamDAO.selectByTeamName(teamName);
	}

	@Override
	public List<TeamDTO> selectByHashtag(String hashtag) throws FindException {
		return teamDAO.selectByHashtag(hashtag, 1, 10);
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
	
	// ------------------------------------------------------------------------

	// 셍나

	@Override
	public String selectTeamInfoByTeamNo(int teamNo) throws FindException {
		return teamDAO.selectTeamInfoByTeamNo(teamNo);
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
	public void updateTeamMemberStatusResign(String id) throws ModifyException {
		teamDAO.updateTeamMemberStatusResign(id);
	}

	@Override
	public void deleteSignupTeam(String id) throws RemoveException {
		teamDAO.deleteSignupTeam(id);
	}

	@Override
	public void leaveTeam(String id) throws Exception {
		teamDAO.leaveTeam(id);
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

	@Override
	public void insertAttendanceById(Integer teamNo, String id) throws AddException {
		teamDAO.insertAttendanceById(teamNo, id);
	}

	@Override
	public List<AttendanceDTO> selectAttendanceById(Integer teamNo, String id) throws FindException {
		return teamDAO.selectAttendanceById(teamNo, id);
	}

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
	public void updateRequestInfoReject(Map<String, Object> map) throws ModifyException {
		teamDAO.updateRequestInfoReject(map);
	}

	@Override
	public void insertExaminer(TaskDTO taskDTO) throws ModifyException {
		teamDAO.insertExaminer(taskDTO);
	}

} // end class