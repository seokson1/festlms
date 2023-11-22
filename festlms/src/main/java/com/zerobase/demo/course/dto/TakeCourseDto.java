package com.zerobase.demo.course.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.zerobase.demo.course.entity.TakeCourse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TakeCourseDto {
	
	
	Long id;
	
	long courseId;
	String userId;
	
	long payPrice; // 결제금액
	String status; // 상테(수강신청, 결제완료, 수강취소)
	
	LocalDateTime regDt; // 신청일
	
	String userName;
	String phone;
	String subject;
	
	long totalCount;
	long seq;
	
	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
		return regDt != null ? regDt.format(formatter) : "";
		
	}

	public static TakeCourseDto of(TakeCourse takeCourse) {
		
		return TakeCourseDto.builder()
				.id(takeCourse.getId())
				.courseId(takeCourse.getCourseId())
				.userId(takeCourse.getUserId())
				.payPrice(takeCourse.getPayPrice())
				.status(takeCourse.getStatus())
				.regDt(takeCourse.getRegDt())
				.build();
	}
	
}
