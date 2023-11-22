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
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	long categoryId;
	
	String imagePath;
	String keyword;
	String subject;
	
	@Column(length = 1000)
	String summary;
	
	@Lob
	String contents;
	long price;
	long salePrice;
	LocalDate saleEndDt;
	
	LocalDateTime regDt; //등록일
	LocalDateTime udtDt; // 수정일
	
}
