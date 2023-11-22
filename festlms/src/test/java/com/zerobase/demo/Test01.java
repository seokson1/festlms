package com.zerobase.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;


public class Test01 {

	@Test
	void TEST_01() {
		
		String value = "2023-06-08";
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate.parse(value, formatter);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		
	}
	
}
