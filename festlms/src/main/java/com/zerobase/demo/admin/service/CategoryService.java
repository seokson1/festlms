package com.zerobase.demo.admin.service;

import java.util.List;

import com.zerobase.demo.admin.dto.CategoryDto;
import com.zerobase.demo.admin.model.CategoryInput;

public interface CategoryService {

	//카테고리 보여줌
	List<CategoryDto> list();
	
	//카테고리 신규추가
	boolean add(String categoryName);
	
	//카테고리 수정
	boolean update(CategoryInput parameter);
	
	//카테고리 삭제
	boolean del(long id);
	
	//프론트 카테고리 정보
	List<CategoryDto> frontList(CategoryDto parameter);
}
