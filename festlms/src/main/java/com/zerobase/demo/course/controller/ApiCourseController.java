package com.zerobase.demo.course.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.demo.admin.service.CategoryService;
import com.zerobase.demo.common.model.ResponseResult;
import com.zerobase.demo.course.model.ServiceResult;
import com.zerobase.demo.course.model.TakeCourseInput;
import com.zerobase.demo.course.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiCourseController extends BaseController{
	
	private final CourseService courseService;
	private final CategoryService categoryService;
	
	@PostMapping("/api/course/req.api")  //spring security에 Principal은 로그인 값 주입해줌
	public ResponseEntity<?> courseReq(Model model
			,@RequestBody TakeCourseInput parameter, Principal principal) {
		
		parameter.setUserId(principal.getName());
		//principal.getName() // 로그인 아이디
		
		ServiceResult result = courseService.req(parameter);
		if(!result.isResult()) {
			
			ResponseResult responseResult = new ResponseResult(false, result.getMessage());			
			return ResponseEntity.ok().body(responseResult);			
		}
		ResponseResult responseResult = new ResponseResult(true, result.getMessage());	
		return ResponseEntity.ok().body(responseResult);
	}

	
}
