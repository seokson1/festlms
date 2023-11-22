package com.zerobase.demo.course.model;

import com.zerobase.demo.admin.model.CommonParam;

import lombok.Data;

@Data
public class CourseParam extends CommonParam {

	long categoryId;
	long id; // course id
	
}
