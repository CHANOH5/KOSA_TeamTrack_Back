package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;

public class LoginController extends CustomerController {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// CORS 문제 해결
		res.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
//		res.setHeader("Access-Control-Allow-Origin", "http://192.168.1.105:5500");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		res.setContentType("application/json; charset=utf-8");
		
		// 응답 출력스트림 얻기
		PrintWriter out = res.getWriter();
		
		// 요청 전달데이터 얻기
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		
		// jackson 라이브러리에서 제공하는 ObjectMapper 클래스 사용하기
		ObjectMapper mapper = new ObjectMapper(); // JSON 문자열 만드는 API
		
		Map<String, Object> map = new HashMap<>();
		
		HttpSession session = req.getSession();
		// attribute가 있으면 제거함
		session.removeAttribute("loginedId");
		
		try {
			service.login(id, pwd);
			
			map.put("status", 1);
			map.put("msg", "로그인 되었습니다");
			
			// session에 loginedId 설정
			session.setAttribute("loginedId", id);
			
			System.out.println(session);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 1);
			map.put("msg", "다시 로그인해주세요.");
		}
		
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);
		
		return null;
		
	} // execute

} // end class