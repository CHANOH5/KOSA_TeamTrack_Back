package com.my.team.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // 일단 보류
@Getter @Setter @ToString
public class AttendanceDTO {

	// 출석부 테이블
	private String attendanceDate; 		// 출석일자
	private String attendanceId; 		// 회원 아이디
	
	// rank 산정에 필요한 변수
	private Integer attendanceday;		//월별 출석일수
	private Integer monthday;			//월별 총 일수
	private Integer month;				//월
	
} // end class