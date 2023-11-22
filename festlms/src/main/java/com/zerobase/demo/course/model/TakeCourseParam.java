package com.zerobase.demo.course.model;

import com.zerobase.demo.admin.model.CommonParam;

import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {
	
	long id;
	String status;
	String userId;
}
