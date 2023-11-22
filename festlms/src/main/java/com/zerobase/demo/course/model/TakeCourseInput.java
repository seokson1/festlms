package com.zerobase.demo.course.model;

import com.zerobase.demo.admin.model.CommonParam;

import lombok.Data;

@Data
public class TakeCourseInput extends CommonParam {

	long courseId;
	String userId;
	
	long takeCourseId; // take_course_id
}
