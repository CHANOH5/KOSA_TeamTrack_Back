package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;


public class TeamSearchController extends TeamController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gubun = request.getParameter("gubun");
		System.out.println(gubun);
		if(gubun.equals("hashtag")) {
			System.out.println("2");
			response.setContentType("application/json;charset=utf-8");

			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			//		HttpSession session = request.getSession();
			String hashtag = request.getParameter("hashtag");
			System.out.println(hashtag);
			Map<String, Integer> map = new HashMap<>();
			try {
				service.selectByHashtag(hashtag);
				System.out.println(service.selectByHashtag(hashtag));
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
		}else if(gubun.equals("teamName")) {
			System.out.println("ok");
			response.setContentType("application/json;charset=utf-8");
			
			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
//			HttpSession session = request.getSession();
			String teamName = request.getParameter("teamName");
			Map<String, Integer> map = new HashMap<>();
			try {
				service.selectByTeamName(teamName);
				System.out.println(service.selectByTeamName(teamName));
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
		System.out.println("여기");
		return null;
		
	}

}