package com.zerobase.demo.course.service;

import java.util.List;

import com.zerobase.demo.course.dto.TakeCourseDto;
import com.zerobase.demo.course.model.ServiceResult;
import com.zerobase.demo.course.model.TakeCourseParam;

public interface TakeCourseService {

	//수강목록
	List<TakeCourseDto> list(TakeCourseParam parameter);
	
	//수강내용 상태 변경
	ServiceResult updateStatus(long id, String status);

	//내 수강 내역
	List<TakeCourseDto> myCourse(String userId);

	//수강 상세정보
	TakeCourseDto detail(long id);
	
	//수강신청 취소
	ServiceResult cancel(long id);
}
