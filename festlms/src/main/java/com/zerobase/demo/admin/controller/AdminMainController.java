package com.zerobase.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zerobase.demo.member.service.MemberService;

@Controller
public class AdminMainController {
	
		
	//fianl 이기 때문에 초기화해야됨
	//	public AdminMainConroller(MemberService memberService) {
	//		this.memberService = memberService;  생성자 주입이 됨.  == RquiredArgsConstructor 이원리임.
	//	}
		
		@GetMapping("/admin/main.do")
		public String main() {
		
			return "admin/main";
		}
}
