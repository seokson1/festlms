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
import com.zerobase.demo.course.dto.TakeCourseDto;
import com.zerobase.demo.course.model.CourseInput;
import com.zerobase.demo.course.model.CourseParam;
import com.zerobase.demo.course.model.ServiceResult;
import com.zerobase.demo.course.model.TakeCourseParam;
import com.zerobase.demo.course.service.CourseService;
import com.zerobase.demo.course.service.TakeCourseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminTakeCourseController extends BaseController{
	
	private final TakeCourseService takeCourseService;
	
	@GetMapping("/admin/takecourse/list.do")
	public String list(Model model, TakeCourseParam parameter) {
		
		parameter.init();	
		
		List<TakeCourseDto> courseList = takeCourseService.list(parameter);
		
		long totalCount = 0;
		if(CollectionUtils.isEmpty(courseList)) {
			totalCount = courseList.get(0).getTotalCount();
		}
		
		String queryString = parameter.getQueryString();
		
		String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
		
		model.addAttribute("list",courseList);
		model.addAttribute("pager", pagerHtml);
		model.addAttribute("totalCount", totalCount);
		
		return "admin/takecourse/list";
	}
	@PostMapping("/admin/takecourse/status.do")
	public String status(Model model, TakeCourseParam parameter) {
		
		parameter.init();
		ServiceResult result = takeCourseService.updateStatus(parameter.getId(), parameter.getStatus());
		if(!result.isResult()) {
			model.addAttribute("message", result.getMessage());
			return "common/error";
		}
		
		return "redirect:/admin/takecourse/list.do";
	}
}
