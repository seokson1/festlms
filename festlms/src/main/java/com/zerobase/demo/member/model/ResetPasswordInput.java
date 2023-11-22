package com.zerobase.demo.member.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ResetPasswordInput {
	
	private String userId;
	private String userName;
	
	private String password;
	private String id;
		
}
