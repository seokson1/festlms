package com.zerobase.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {

	@GetMapping("/")
	public String index() {
		return "HELLO";
	}
}
