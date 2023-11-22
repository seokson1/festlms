package com.zerobase.demo.course.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zerobase.demo.admin.dto.CategoryDto;
import com.zerobase.demo.admin.service.CategoryService;
import com.zerobase.demo.course.dto.CourseDto;
import com.zerobase.demo.course.model.CourseInput;
import com.zerobase.demo.course.model.CourseParam;
import com.zerobase.demo.course.service.CourseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CourseController extends BaseController{
	
	private final CourseService courseService;
	private final CategoryService categoryService;
	
	@GetMapping("/course")
	public String list(Model model, CourseParam parameter) {
		
		
		
		List<CourseDto> list = courseService.frontList(parameter);
		model.addAttribute("list",list);
		
		List<CategoryDto> categoryList = categoryService.frontList(CategoryDto.builder().build());
		int courseToalCount = 0;
		if(categoryList != null) {
			for(CategoryDto x : categoryList) {
				courseToalCount += x.getCourseCount();
			}
		}
		
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("courseToalCount",courseToalCount);
		return "course/index";
	}
	@GetMapping("/course/{id}")
	public String courseDetail(Model model, CourseParam parameter) {
		
		CourseDto detail = courseService.frontDetail(parameter.getId());
		model.addAttribute("detail",detail);
		return "course/detail";
	}
	
}
