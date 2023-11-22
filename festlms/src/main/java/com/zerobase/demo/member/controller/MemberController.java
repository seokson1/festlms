package com.zerobase.demo.member.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zerobase.demo.admin.dto.MemberDto;
import com.zerobase.demo.course.dto.TakeCourseDto;
import com.zerobase.demo.course.model.ServiceResult;
import com.zerobase.demo.course.service.TakeCourseService;
import com.zerobase.demo.member.model.MemberInput;
import com.zerobase.demo.member.model.ResetPasswordInput;
import com.zerobase.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	
	private final MemberService memberService;
	private final TakeCourseService takeCourseService;
	
	//	@RequestMapping(value = "/member/register", method = {RequestMethod.GET, RequestMethod.POST}) //두개 같이 받는 방법
	@GetMapping("/member/register")
	public String register() {
		
		
		return "member/register";
	}
	
	
	//request web -> server
	//response server -> web
	@PostMapping("/member/register")
	public String registerSubmit(Model model,HttpServletRequest request, HttpServletResponse response,
			MemberInput parameter) {
		
		boolean result = memberService.register(parameter);
		model.addAttribute("result", result);
		
		return "member/register_complete";
	}
	// 기본적 웹의 주소는 http://www.naver.com 대표 도메인 /news  ?뒤에는 파라미터
	// https 등 앞에 오는 건 프로토콜
	// 다음은 도메인(IP)/news    도메인 밑으로 상세 주소로 매핑 페이지 이동
	// http://www.naver.com/news/list.do?id=123&key=1234     ?뒤부터 시작 id 매개변수명 뒤에는 값
	
	@GetMapping("/member/email-auth")
	public String emailAuth(Model model,HttpServletRequest request) {
		
		String uuid = request.getParameter("id"); // 일반적으로 받는 방법 클라이언트에서 보내주기 때문에
		System.out.println(uuid);
		
		boolean result = memberService.emailAuth(uuid);
		System.out.println(result);
		model.addAttribute("result", result);
		
		return "member/email_auth";
		
	}
	@GetMapping("/member/info")
	public String memberInfo(Model model,Principal principal) {
		
		String userId = principal.getName();
		MemberDto detail = memberService.detail(userId);
		model.addAttribute("detail",detail);
		
		return "member/info";
	}
	
	@PostMapping("/member/info")
	public String memberInfoSubmit(Model model
			,MemberInput parameter
			,Principal principal) {
		String userId = principal.getName();
		parameter.setUserId(userId);
		
		ServiceResult result = memberService.updateMember(parameter);
		if(!result.isResult()) {
			model.addAttribute("message",result.getMessage());
			return "common/error";
		}
		
		return "redirect:/member/info";
	}
	
	@GetMapping("/member/password")
	public String memberPassword(Model model,Principal principal) {
		
		String userId = principal.getName();
		MemberDto detail = memberService.detail(userId);
		model.addAttribute("detail",detail);
		
		return "member/password";
	}
	
	@PostMapping("/member/password")
	public String memberPasswordSubmit(Model model,Principal principal, MemberInput parameter) {
		
		String userId = principal.getName();
		parameter.setUserId(userId);
		
		ServiceResult result = memberService.updateMemberPassword(parameter);
		if(!result.isResult()) {
			model.addAttribute("message",result.getMessage());
			return "common/error";
		}
		model.addAttribute("result",result);
		
		return "redirect:/member/info";
	}
	
	@GetMapping("/member/takecourse")
	public String memberTakeCourse(Model model,Principal principal) {
		
		String userId = principal.getName();		
		List<TakeCourseDto> list = takeCourseService.myCourse(userId);
		
		
		model.addAttribute("list",list);
		
		return "member/takecourse";
	}
	
	@RequestMapping("/member/login")
	public String login() {
		
		
		return "member/login";
	}
	
	@GetMapping("/member/find/password")
	public String findPassword() {
		
		return "member/find_password";
	}
	@PostMapping("/member/find/password")
	public String findPasswordSubmit(Model model,ResetPasswordInput parameter) {
		boolean result = false;
		try {
			result = memberService.sendResetPassword(parameter);
		} catch (Exception e) {
		}
		model.addAttribute("result", result);			
		
		return "member/find_password_result";
	}
	
	@GetMapping("/member/reset/password")
	public String resetPassword(Model model, HttpServletRequest request) {
		
		String uuid = request.getParameter("id");
		boolean result = memberService.checkResetPassoword(uuid);
		
		model.addAttribute("result",result);
		
		return "member/reset_password";
	}
	
	@PostMapping("/member/reset/password")
	public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {
		boolean result = false;
		try {
			result = memberService.resetPassword(parameter.getId(), parameter.getPassword());			
		} catch (Exception e) {
		}
		
		model.addAttribute("result",result);
		
		return "member/reset_password_result";
	}
}
