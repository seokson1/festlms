package com.zerobase.demo.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.demo.course.dto.TakeCourseDto;
import com.zerobase.demo.course.model.TakeCourseParam;

@Mapper
public interface TakeCourseMapper {

	List<TakeCourseDto> selectList(TakeCourseParam parameter);
	long selectListCount(TakeCourseParam  parameter);
	
	List<TakeCourseDto> selectListMyCourse(TakeCourseParam parameter);
}
