package com.zerobase.demo.member.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.zerobase.demo.admin.dto.MemberDto;
import com.zerobase.demo.admin.model.MemberParam;
import com.zerobase.demo.course.model.ServiceResult;
import com.zerobase.demo.member.model.MemberInput;
import com.zerobase.demo.member.model.ResetPasswordInput;

public interface MemberService extends UserDetailsService{
	
	boolean register(MemberInput parameter);
	
	/**
	 * uuid 해당하는 계정을 활성화 합니다.
	 * @param uuid
	 * @return
	 */
	boolean emailAuth(String uuid);
	
	/**
	 * 입력한 이메일로 비밀번호 초기화 정보를 전송하는 메소드
	 */
	boolean sendResetPassword(ResetPasswordInput parameter);

	/**
	 * 입력받은 uuid에 대해서 password 초기화 함.
	 */
	boolean resetPassword(String id, String password);
	
	/**
	 *입력받은 uuid 값이 유효한지 확인
	 */
	boolean checkResetPassoword(String uuid);
	
	/**
	 *회원의 목록 리턴(관리자에서만 사용) 
	 */
	List<MemberDto> list(MemberParam parameter);
	
	//회원 상세정보
	MemberDto detail(String userId);
	
	//회원 상태 변경
	boolean updateStatus(String userId, String userStatus);

	//회원 비밀번호 초기화
	boolean updatePassword(String userId, String password);
	
	//회원 정보 페이지내 비밀번호 변경 기능
	ServiceResult updateMemberPassword(MemberInput parameter);
	
	//회원정보 수정
	ServiceResult updateMember(MemberInput parameter);
}
