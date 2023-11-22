package com.zerobase.demo.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult {

	boolean result;
	String message;
	
	public ServiceResult(boolean result) {
		this.result = result;
	}
	
}
