package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.team.dto.TeamDTO;

public class TeamManageController extends TeamController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gubun = request.getParameter("gubun");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(gubun.equals("create")) {
		
			HashMap<String, Object> param = new HashMap<>();
			param.put("I_TEAM_NAME", request.getParameter("teamName"));
			param.put("I_LEADER_ID", request.getParameter("leaderId"));
			param.put("I_STUDY_TYPE", request.getParameter("studyType"));
			param.put("I_ONOFFLINE", request.getParameter("onOffLine"));
			param.put("I_MAX_MEMBER", request.getParameter("maxMember"));
			param.put("I_STARTDATE", request.getParameter("startDate"));
			param.put("I_ENDDATE", request.getParameter("endDate"));
			param.put("I_BRIEF_INFO", request.getParameter("briefInfo"));
			param.put("I_TEAM_INFO", request.getParameter("teamInfo"));
			param.put("I_HASHTAG_NAME1", request.getParameter("hashtag1"));
			param.put("I_HASHTAG_NAME2", request.getParameter("hashtag2"));
			param.put("I_HASHTAG_NAME3", request.getParameter("hashtag3"));
			param.put("I_HASHTAG_NAME4", request.getParameter("hashtag4"));
			param.put("I_HASHTAG_NAME5", request.getParameter("hashtag5"));
			
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = new HashMap<>();

			try {
				service.createTeam(param);
				map.put("status", 1);
				map.put("msg", "팀생성 성공");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", "팀생성 실패");			
			}
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
			return null;
		}else if(gubun.equals("update")) {
			System.out.println("1");
//			HttpSession session = request.getSession();
			int teamNo = Integer.parseInt(request.getParameter("teamNo"));
			
			String teamName = request.getParameter("teamName");
			String studyType = request.getParameter("studyType");
			String onOffLine = request.getParameter("onOffLine");
			int maxMember = Integer.parseInt(request.getParameter("maxMember"));
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String briefInfo = request.getParameter("briefInfo");
			String teamInfo = request.getParameter("teamInfo");
			String hashtag1 = request.getParameter("hashtag1");
			String hashtag2 = request.getParameter("hashtag2");
			String hashtag3 = request.getParameter("hashtag3");
			String hashtag4 = request.getParameter("hashtag4");
			String hashtag5 = request.getParameter("hashtag5");
			
			TeamDTO t = new TeamDTO();
			
			t.setTeamNo(teamNo);
			t.setTeamName(teamName);
			t.setStudyType(studyType);
			t.setOnOffLine(onOffLine);
			t.setMaxMember(maxMember);
			t.setStartDate(startDate);
			t.setEndDate(endDate);
			t.setBriefInfo(briefInfo);
			t.setTeamInfo(teamInfo);
			System.out.println(t);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = new HashMap<>();

			try {
				List<Map> list = new ArrayList<Map>();
				if(hashtag1 != null) {
					Map<String, Object> teamHashtag1 = new HashMap<String, Object>();	
					teamHashtag1.put("teamNo", teamNo);
					teamHashtag1.put("hashtag" , hashtag1);
					list.add(teamHashtag1);
				}
				if(hashtag2 != null) {
					Map<String, Object> teamHashtag2 = new HashMap<String, Object>();	
					teamHashtag2.put("teamNo", teamNo);
					teamHashtag2.put("hashtag" , hashtag2);
					list.add(teamHashtag2);
				}
				if(hashtag3 != null) {
					Map<String, Object> teamHashtag3 = new HashMap<String, Object>();	
					teamHashtag3.put("teamNo", teamNo);
					teamHashtag3.put("hashtag" , hashtag3);
					list.add(teamHashtag3);
				}
				if(hashtag4 != null) {
					Map<String, Object> teamHashtag4 = new HashMap<String, Object>();	
					teamHashtag4.put("teamNo", teamNo);
					teamHashtag4.put("hashtag" , hashtag4);
					list.add(teamHashtag4);
				}
				if(hashtag5 != null) {
					Map<String, Object> teamHashtag5 = new HashMap<String, Object>();	
					teamHashtag5.put("teamNo", teamNo);
					teamHashtag5.put("hashtag" , hashtag5);
					list.add(teamHashtag5);
				}
				HashMap<String, Object> params = new HashMap<>();
				params.put("list", list);
				System.out.println(params);
				service.updateTeam(t);
				if(!(hashtag1 == null & hashtag2 == null & hashtag3 == null
						& hashtag4 == null & hashtag5 == null)) {
					service.deleteHashtag(teamNo);
					service.updateHashtag(params);
				}
				map.put("status", 1);
				map.put("msg", "팀수정 성공");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", "팀수정 실패");			
			}
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
			return null;
		
		}else if(gubun.equals("delete")) {
			ObjectMapper mapper = new ObjectMapper();
			int teamNo = Integer.parseInt(request.getParameter("teamNo"));
			
			Map<String, Integer> map = new HashMap<>();
			try {
				service.deleteTeam(teamNo);
				//성공적으로 삭제한 경우
				map.put("status", 0);
			} catch (RemoveException e) {
				//삭제하지 못한 경우
				e.printStackTrace();
				map.put("status", 1);
			}
			System.out.println(map);
			out.print(mapper.writeValueAsString(map));
			return null;
			
		}else if(gubun.equals("select")) {
			ObjectMapper mapper = new ObjectMapper();

			int teamNo = Integer.parseInt(request.getParameter("teamNo"));
			Map<String, Integer> map = new HashMap<>();
			try {
				service.selectByTeamNo(teamNo);
				System.out.println(service.selectByTeamNo(teamNo));
				//팀이 있는 경우
				map.put("status", 0);
			} catch (FindException e) {
				//팀이 없는 경우
				e.printStackTrace();
				map.put("status", 1);
			}
			System.out.println(map);
			out.print(mapper.writeValueAsString(map));
			return null;
		}
		
		return null;
	}

}