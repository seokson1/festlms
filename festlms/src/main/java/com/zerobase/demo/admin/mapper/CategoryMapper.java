package com.zerobase.demo.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zerobase.demo.admin.dto.CategoryDto;
import com.zerobase.demo.admin.dto.MemberDto;
import com.zerobase.demo.admin.model.MemberParam;

@Mapper
public interface CategoryMapper {
	
	List<CategoryDto> select(CategoryDto parameter);
	
}
