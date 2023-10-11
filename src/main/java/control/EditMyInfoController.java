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
import com.my.customer.dto.CustomerDTO;
import com.my.notice.dto.NoticeDTO;
import com.my.util.Attach;

public class EditMyInfoController extends CustomerController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
//		HttpSession session = request.getSession();
//		String loginedId = (String)session.getAttribute("loginedId");
		
		String loginedId = "psh2023";
		String name = request.getParameter("name");
		String birthday =  request.getParameter("birthday");
		String phone =  request.getParameter("phone");
		String email = request.getParameter("email");
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<>();; 
		
		try {
			
			CustomerDTO customer = new CustomerDTO(name, birthday, phone, email);
			service.modifyMyInfo(loginedId, customer);
			map.put("status", 1);
			map.put("msg", "정보가 수정되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		
		out.print(mapper.writeValueAsString(map));

		return null;
	}
}