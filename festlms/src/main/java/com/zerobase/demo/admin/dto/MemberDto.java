package com.zerobase.demo.admin.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.zerobase.demo.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

	private String userId;
	private String userName;
	private String phone;
	private String password;
	private LocalDateTime regDt; // 회원 가입일
	private LocalDateTime udtDt; // 회원 정보 수정일
	
	private boolean emailAuthYn; // 메인 인증 했는지 안했는지 확인 여부
	private String emailAuthKey;
	private LocalDateTime emailAuthDt;

	private String resetPasswordKey;
	private LocalDateTime resetPasswordLimitDt;

	private boolean adminYn;

	private String userStatus;
	private String zipcode;
	private String addr;
	private String addrDetail;

	// 추가 컬럼
	long totalCount;
	long seq;

	public static MemberDto of(Member member) {

		return MemberDto.builder().userId(member.getUserId()).password(member.getPassword()).phone(member.getPhone())
				.regDt(member.getRegDt()).userName(member.getUserName()).emailAuthYn(member.isEmailAuthYn())
				.emailAuthKey(member.getEmailAuthKey()).emailAuthDt(member.getEmailAuthDt())
				.resetPasswordKey(member.getResetPasswordKey()).resetPasswordLimitDt(member.getResetPasswordLimitDt())
				.adminYn(member.isAdminYn()).userStatus(member.getUserStatus()).udtDt(member.getUdtDt()).zipcode(member.getZipcode())
				.addr(member.getAddr()).addrDetail(member.getAddrDetail()).build();

	}

	public String getRegDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return regDt != null ? regDt.format(formatter) : "";
	}
	
	public String getUdtDtText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		return udtDt != null ? udtDt.format(formatter) : "";
	}
}