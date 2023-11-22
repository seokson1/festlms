package com.zerobase.demo.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface MemberCode {

		//현재 회원가입 요청중
		String MEMBER_STATUS_REQ = "REQ";
		
		//현재 이용중인 상태
		String MEMBER_STATUS_ING = "ING";
		
		//현재 정지된 상태
		String MEMBER_STATUS_STOP = "STOP";
}

