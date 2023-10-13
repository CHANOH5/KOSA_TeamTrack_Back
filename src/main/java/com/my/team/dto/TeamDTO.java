package com.my.team.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // 일단 보류
@Getter @Setter @ToString
public class TeamDTO {

	// 팀 테이블
	private int teamNo;
	private String teamName;
	private String leaderId;
	private String studyType;
	private String onOffLine;
	private Integer joinMember;
	private int maxMember;
	private Date createDate;
	private String startDate;
	private String endDate;
	private String briefInfo;
	private String teamInfo;
	private Integer viewCnt;
	
	// 팀 해시태그 테이블
	private String hashtagName1;
	private String hashtagName2;
	private String hashtagName3;
	private String hashtagName4;
	private String hashtagName5;
	
} // end class
