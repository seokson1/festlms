package com.zerobase.demo.course.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseInput {
	
	long id;
	long categoryId;
	String subject;
	String keyword;
	String summary;
	String contents;
	long price;
	long salePrice;
	String saleEndDtText;
	LocalDateTime regDt; //등록일
	LocalDateTime udtDt; // 수정일
	
	//삭제를 위한 속성값
	String idList;
}
