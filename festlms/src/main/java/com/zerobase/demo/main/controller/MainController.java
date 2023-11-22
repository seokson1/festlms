package com.zerobase.demo.main.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zerobase.demo.components.MailComponents;

import lombok.RequiredArgsConstructor;

// MainPage 클래스 만든 목적은 매핑하기 위해서
// 주소와(논리적인주소 인터넷주소) 물리적인 파일(프로그래밍을 해야 하니깐) 매핑.

// 어디서 매핑? 누가 매핑?
// 후보군? 클래스, 속성, 메소드(0) - 주소 매핑

@RequiredArgsConstructor
@Controller
public class MainController {
	
	@Autowired
	private final MailComponents mailComponents;
	
	@RequestMapping("/") //- 이 방법도 있음
	//@GetMapping("/")
	public String index() {
		
//		String email = "seokson1@naver.com";
//		String subject = "안녕하세요. 손석연 입니다";
//		String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";
//		
//		mailComponents.sendMail(email, subject, text);
		
		return "index"; // 기본  설정이 되어 있음.
	}
	//request -> web -> server
	//response -> server -> web
	
	//스프링 -> MVC (View) 템플릿엔진 화면에 내용을 출력 (html)
	//.NET -> MVC (VIew -> 출력>
	//payhon django -> mtv
	//백엔드과정 -> VIew(화면에 보여지는 부분 많음 프론트 엔드 과정에서 많이 다룸.)
	// V- html 바인딩 되면 - 웹페이지
	// V - json -> API가 됨
	
	
	@RequestMapping("/hello")
	public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=UTF-8"); // 인코인 문제는 UTF-8 문제가 거의 많음.
		PrintWriter printWriter = response.getWriter();
		
		
		String msg = "<html>"
				+ "<head>"
				+ "<meta charset='UTF-8'>"
				+ "</head>"
				+ "<body>"
				+ "<p>hello</p> <p>\r\n festlms website!!!</p>"
				+ "<p>안녕하세요!!!===></p>"
				+ "</body>"
				+ "</html>"; // 줄바꾸기 했지만 보이는건 다 보임. html 언어가 아니기 때문에 p태그 써야됨. 화면에 출력이 됨.
		// 이런부분이 어렵기 때문에 프레임워크 사용 vue엔지 타임리프 사용 이유.
		printWriter.write(msg);
		printWriter.close();
	}
	
	@RequestMapping("/error/denied")
	public String errorDenied() {
		
		
		return "error/denied";
	}
	
}
