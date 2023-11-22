package com.zerobase.demo.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zerobase.demo.admin.dto.CategoryDto;
import com.zerobase.demo.admin.entity.Category;
import com.zerobase.demo.admin.mapper.CategoryMapper;
import com.zerobase.demo.admin.model.CategoryInput;
import com.zerobase.demo.admin.repository.CategoryRepository;
import com.zerobase.demo.admin.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	private Sort getSortBySortValueDesc() {
		
		return Sort.by(Sort.Direction.DESC, "sortValue");
	}
	
	@Override
	public boolean add(String categoryName) {
		
		//카테고리명 중복 확인
		
		
		//추가
		Category category = Category.builder()
				.categoryName(categoryName)
				.usingYn(true)
				.sortValue(0)
				.build();
		categoryRepository.save(category);
		
		return true;
	}

	@Override
	public boolean update(CategoryInput parameter) {
		
		Optional<Category> optionalCaOptional = categoryRepository.findById(parameter.getId());
		if(optionalCaOptional.isPresent()) {
			
			Category category = optionalCaOptional.get();
			
			category.setCategoryName(parameter.getCategoryName());
			category.setSortValue(parameter.getSortValue());
			category.setUsingYn(parameter.isUsingYn());
			categoryRepository.save(category);
			
		}
		
		return true;
	}

	@Override
	public boolean del(long id) {
		
		categoryRepository.deleteById(id);	
		return true;
	}

	@Override
	public List<CategoryDto> list() {
		
		List<CategoryDto> categoryDtoList = new ArrayList<>();
		List<Category> categoryies = categoryRepository.findAll(getSortBySortValueDesc());
		return CategoryDto.of(categoryies);
		
	}

	@Override
	public List<CategoryDto> frontList(CategoryDto parameter) {
		
		return categoryMapper.select(parameter);
		
	}

}
