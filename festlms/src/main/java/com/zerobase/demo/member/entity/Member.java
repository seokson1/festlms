package com.zerobase.demo.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements MemberCode{

	@Id
	private String userId;
	
	private String userName;
	
	private String phone;
	
	private String password;
	
	private LocalDateTime regDt; // 회원 가입일
	private LocalDateTime udtDt; // 회원정보 수정일
	
	private boolean emailAuthYn; //메인 인증 했는지 안했는지 확인 여부
	private String emailAuthKey;
	private LocalDateTime emailAuthDt;
	
	private String resetPasswordKey;
	private LocalDateTime resetPasswordLimitDt;
	
	//관리자여부를 지정할건지
	//회원에 따른 ROLE 지정할꺼냐?
	//준회원/정회원/특별회원/관리자
	//ROLE_SEMI_USER, ROLE_USER, SPECIAL_USER
	// 준회원/정회원/특별회원
	// 관리자? 별개로 체크 하는 방법
	
	private boolean adminYn;
	
	private String userStatus; // 이용 가능, 정지 상태 2가지
	
	private String zipcode;
	private String addr;
	private String addrDetail;
	
}

