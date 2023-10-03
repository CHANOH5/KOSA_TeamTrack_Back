package com.my.customer.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.my.customer.dto.CustomerDTO;
import com.my.exception.AddException;
import com.my.exception.FindException;

public class CustomerDAOImpl implements CustomerDAO {
	
	// Mybatis에서 db와 연결하고 sql문을 실행 할 SqlSessionFactory 인터페이스 선언
	private SqlSessionFactory sqlSessionFactory;

	public CustomerDAOImpl() {
		
		// Mybatis 설정파일 로드
		String resource = "com/my/sql/mybatis-config.xml";
		InputStream inputStream;
		
		try {
			
			// 리소스 경로에 파일 읽어들이는 클래스(Resources)
			inputStream = Resources.getResourceAsStream(resource);
			
			// sqlSessionFactory를 멤버변수로 만듦
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		} // try-catch

	} // constructor
	
	
	// 회원가입 메서드
	@Override
	public void create(CustomerDTO customerDTO) throws AddException {
		
		// db와 상호작용하기 위한 객체 생성
		SqlSession session = null;
		
		try {
			
			// session객체로 sql문 수행
			session = sqlSessionFactory.openSession();
			session.insert("com.my.customer.CustomerMapper.create", customerDTO);
			
			session.commit();
			
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			
			if(session != null) {
				session.close();
			} // if
			
		} // try-catch-finally
		
	} // insert

	// 회원조회 메서드
	@Override
	public CustomerDTO selectById(String id) throws FindException {
		
		SqlSession session = null;
		
		try {
			
			session = sqlSessionFactory.openSession();
			CustomerDTO customer = (CustomerDTO)session.selectOne("com.my.customer.CustomerMapper.selectById", id);
			
			if( customer != null ) {
				return customer;
			} else {
				throw new FindException("고객없음");
			} // if-else
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
		
	} // selectById

	
	
// ======================== db와 연결 테스트 ============================
	
	public static void main(String[] args) {
		CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
		
		// ================ create 메서드 테스트 =========================
//		CustomerDTO dto = new CustomerDTO();
//		
//		dto.setId("test01");
//		dto.setPwd("test01");
//		dto.setNickname("test01");
//		dto.setName("test01");
//		dto.setBirthday("1993-05-04");
//		dto.setPhone("01011112222");
//		dto.setEmail("dd@naver.com");
////		dto.setStatus(1);
//		
//		try {
//			customerDAOImpl.create(dto);
//			
//			System.out.println("회원가입 완료 ^ㅡ^b");
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
		// ==================================================================
		
		// ================= selectById 메서드 테서트 =======================

		String id = "test01";
		
		
		try {
			CustomerDTO dto = customerDAOImpl.selectById(id);
			// 출력
			System.out.println("id : " +  dto.getId());
			System.out.println("nickname : " + dto.getName());			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(" 해당 회원이 없습니다 ");
		}
	} // main(test)
	
	
} // end class
