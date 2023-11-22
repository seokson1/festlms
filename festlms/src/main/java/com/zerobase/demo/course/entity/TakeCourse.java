package com.zerobase.demo.course.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeCourse implements TakeCourseCode {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	long courseId;
	String userId;
	
	long payPrice; // 결제금액
	String status; // 상테(수강신청, 결제완료, 수강취소)
	
	LocalDateTime regDt; // 신청일
	
	
}
