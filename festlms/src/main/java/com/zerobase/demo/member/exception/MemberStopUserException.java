package com.zerobase.demo.member.exception;

public class MemberStopUserException extends RuntimeException {
	public MemberStopUserException(String error) {
		super(error);
	}
}
