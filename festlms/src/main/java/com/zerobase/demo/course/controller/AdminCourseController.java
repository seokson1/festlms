package com.zerobase.demo.course.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.zerobase.demo.admin.service.CategoryService;
import com.zerobase.demo.course.dto.CourseDto;
import com.zerobase.demo.course.model.CourseInput;
import com.zerobase.demo.course.model.CourseParam;
import com.zerobase.demo.course.service.CourseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminCourseController extends BaseController{
	
	private final CourseService courseService;
	private final CategoryService categoryService;
	
	@GetMapping("/admin/course/list.do")
	public String list(Model model, CourseParam parameter) {
		
		parameter.init();	
		
		List<CourseDto> courseList = courseService.list(parameter);
		
		long totalCount = 0;
		if(CollectionUtils.isEmpty(courseList)) {
			totalCount = courseList.get(0).getTotalCount();
		}
		
		String queryString = parameter.getQueryString();
		
		String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
		
		model.addAttribute("list",courseList);
		model.addAttribute("pager", pagerHtml);
		model.addAttribute("totalCount", totalCount);
		
		return "admin/course/list";
	}
	@GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
	public String add(Model model, HttpServletRequest request, CourseInput parameter) {
		
		//카테고리 정보를 내려줘야함.
		model.addAttribute("category",categoryService.list());
		
		
		boolean editMode = request.getRequestURI().contains("/edit.do");
		CourseDto detail = new CourseDto();
		
		if(editMode) {
			long id = parameter.getId();
			CourseDto existCourse = courseService.getById(id);
			
			if(existCourse == null) {
				// error처리 해야됨.
				model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
				return "common/error";
			}
			detail = existCourse;
			
		}
		
		model.addAttribute("editMode", editMode);
		model.addAttribute("detail",detail);
		return "admin/course/add";
	}
	@PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
	public String addSubmit(Model model, CourseInput parameter, HttpServletRequest request) {
		
		boolean editMode = request.getRequestURI().contains("/edit.do");
		if(editMode) {
			long id = parameter.getId();
			CourseDto existCourse = courseService.getById(id);
			
			if(existCourse == null) {
				// error처리 해야됨.
				model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
				return "common/error";
			}
			boolean result = courseService.set(parameter);
		} else {
			boolean result = courseService.add(parameter);			
		}
		
		
		return "redirect:/admin/course/list.do";
	}
	@PostMapping("/admin/course/delete.do")
	public String del(Model model, HttpServletRequest request, CourseInput parameter) {
		
		boolean result = courseService.del(parameter.getIdList());
		
		return "redirect:/admin/course/list.do";
	}
}
