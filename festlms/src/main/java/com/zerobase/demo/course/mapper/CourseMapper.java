package com.zerobase.demo.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.demo.course.dto.CourseDto;
import com.zerobase.demo.course.model.CourseParam;

@Mapper
public interface CourseMapper {

	List<CourseDto> selectList(CourseParam parameter);
	long selectListCount(CourseParam  parameter);
	
}
