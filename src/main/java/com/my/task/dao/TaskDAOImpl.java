package com.my.task.dao;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.my.exception.FindException;
import com.my.task.dto.MemberTaskDTO;
import com.my.task.dto.TaskDTO;

public class TaskDAOImpl implements TaskDAO {
	private SqlSessionFactory sqlSessionFactory;

	public TaskDAOImpl() {
		String resource="com/my/sql/mybatis-config.xml";
		InputStream inputStream;

		try {
			inputStream=Resources.getResourceAsStream(resource);
			sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<TaskDTO> selectMainTaskList(Integer teamNo, boolean desc) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("desc", desc);
			List<TaskDTO> list=session.selectList("com.my.task.TaskMapper.selectMainTaskList", map);
			return list;
		} catch(Exception e) {
			throw new FindException("메인 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public List<TaskDTO> selectAllTaskList(Integer teamNo, int startRow, int endRow, boolean desc) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("desc", desc);
			map.put("start", startRow);
			map.put("end", endRow);
			List<TaskDTO> list=session.selectList("com.my.task.TaskMapper.selectAllTaskList", map);
			return list;
		} catch(Exception e) {
			throw new FindException("전체 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public int selectAllTaskCount(Integer teamNo) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			String tableName="task_"+teamNo;
			int count=session.selectOne("com.my.task.TaskMapper.selectAllTaskCount", tableName);
			return count;
		} catch(Exception e) {
			throw new FindException("전체 과제 개수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public List<MemberTaskDTO> selectCompleteTaskList(Integer teamNo, String id, int startRow, int endRow, boolean desc) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName1", "memberscore_"+teamNo);
			map.put("tableName2", "task_"+teamNo);
			map.put("id", id);
			map.put("desc", desc);
			map.put("start", startRow);
			map.put("end", endRow);
			List<MemberTaskDTO> list=session.selectList("com.my.task.TaskMapper.selectCompleteTaskList", map);
			return list;
		} catch(Exception e) {
			throw new FindException("완료한 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public int selectCompleteTaskCount(Integer teamNo, String id) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "memberscore_"+teamNo);
			map.put("id", id);
			int count=session.selectOne("com.my.task.TaskMapper.selectCompleteTaskCount", map);
			return count;
		} catch(Exception e) {
			throw new FindException("완료한 과제 개수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}
	
	public List<TaskDTO> selectMyTaskList(Integer teamNo, String id, int startRow, int endRow, boolean desc) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("id", id);
			map.put("desc", desc);
			map.put("start", startRow);
			map.put("end", endRow);
			List<TaskDTO> list=session.selectList("com.my.task.TaskMapper.selectMyTaskList", map);
			return list;
		} catch(Exception e) {
			throw new FindException("출제한 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}
	
	public int selectMyTaskCount(Integer teamNo, String id) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("id", id);
			int count=session.selectOne("com.my.task.TaskMapper.selectMyTaskCount", map);
			return count;
		} catch(Exception e) {
			throw new FindException("출제한 과제 개수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}
	
	public TaskDTO selectByTaskNo(Integer teamNo, String id, Integer taskNo) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "quizanswer_"+teamNo);
			map.put("id", id);
			map.put("taskNo", taskNo);
			TaskDTO task=session.selectOne("com.my.task.TaskMapper.selectByTaskNo", map);
			return task;
		} catch(Exception e) {
			throw new FindException("과제 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}


	// main test
	public static void main(String[] args) {
		TaskDAOImpl t=new TaskDAOImpl();
		try {
			System.out.println("======================\n메인과제리스트");
			List<TaskDTO> list4=t.selectMainTaskList(9999, true);
			for(int i=0;i<list4.size();i++) {
				System.out.print("과제 타이틀 : "+list4.get(i).getTitle()+" | ");
				System.out.print("출제자 : "+list4.get(i).getId()+" | ");
				System.out.print("과제 생성일 : "+list4.get(i).getRegDate()+" | ");
				System.out.println("과제 마감일 : "+list4.get(i).getEndDate());
			}
			System.out.print("======================\n전체과제리스트 - ");
			int cnt=t.selectAllTaskCount(9999);
			System.out.println(cnt);
			List<TaskDTO> list=t.selectAllTaskList(9999, 1, 4, true);
			for(int i=0;i<list.size();i++) {
				System.out.print("과제 타이틀 : "+list.get(i).getTitle()+" | ");
				System.out.print("출제자 : "+list.get(i).getId()+" | ");
				System.out.println("과제 생성일 : "+list.get(i).getRegDate());
			}
			System.out.print("======================\n완료과제리스트 - ");
			int cnt2=t.selectCompleteTaskCount(9999, "nwh2023");
			System.out.println(cnt2);
			List<MemberTaskDTO> list2=t.selectCompleteTaskList(9999, "nwh2023", 1, 2, true);
			for(int i=0;i<list2.size();i++) {
				System.out.print("과제 타이틀 : "+list2.get(i).getTitle()+" | ");
				System.out.print("출제자 : "+list2.get(i).getId()+" | ");
				System.out.println("과제 제출일 : "+list2.get(i).getSubmitDate());
			}
			System.out.print("======================\n출제과제리스트 - ");
			int cnt3=t.selectMyTaskCount(9999, "khb2023");
			System.out.println(cnt3);
			List<TaskDTO> list3=t.selectMyTaskList(9999, "khb2023", 1, 2, true);
			for(int i=0;i<list3.size();i++) {
				System.out.print("과제 타이틀 : "+list3.get(i).getTitle()+" | ");
				System.out.println("평점 : "+list3.get(i).getAvgReviewscore());
			}
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
