package com.my.team.board.task.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TaskInfo {

	Integer taskNo;
	Integer teamMemberNo;
	Date dueDate1;
	Date dueDate2;
	String title;
	String type;
	Date regDate;
	String endDate;
	Double avgReviewScore;
	
}
