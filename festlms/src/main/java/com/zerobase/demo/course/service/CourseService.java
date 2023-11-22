package com.zerobase.demo.course.service;

import java.util.List;

import com.zerobase.demo.course.dto.CourseDto;
import com.zerobase.demo.course.model.CourseInput;
import com.zerobase.demo.course.model.CourseParam;
import com.zerobase.demo.course.model.ServiceResult;
import com.zerobase.demo.course.model.TakeCourseInput;

public interface CourseService {

	//강좌등록
	boolean add(CourseInput parameter);
	
	//강좌목록
	List<CourseDto> list(CourseParam parameter);

	//강좌 상세정보
	CourseDto getById(long id);

	//강좌 수정
	boolean set(CourseInput parameter);
	
	//강좌 삭제
	boolean del(String idList);
	
	//프론트에 필요한 내용만 보여주기 위해서 강좌값
	List<CourseDto> frontList(CourseParam paramter);

	//프론트에서 사용자 페이지 강좌 상세정보 가져옴.
	CourseDto frontDetail(long id);

	//수강신청
	ServiceResult req(TakeCourseInput parameter);
}
