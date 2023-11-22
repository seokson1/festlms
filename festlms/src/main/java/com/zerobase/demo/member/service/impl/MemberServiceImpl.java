package com.zerobase.demo.member.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zerobase.demo.admin.dto.MemberDto;
import com.zerobase.demo.admin.mapper.MemberMapper;
import com.zerobase.demo.admin.model.MemberParam;
import com.zerobase.demo.components.MailComponents;
import com.zerobase.demo.course.model.ServiceResult;
import com.zerobase.demo.member.entity.Member;
import com.zerobase.demo.member.exception.MemberNotEmailAuthException;
import com.zerobase.demo.member.exception.MemberStopUserException;
import com.zerobase.demo.member.model.MemberInput;
import com.zerobase.demo.member.model.ResetPasswordInput;
import com.zerobase.demo.member.repository.MemberRepository;
import com.zerobase.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;
	private final MailComponents mailComponents;
	private final MemberMapper memberMapper;
	
	/**
	 * 회원가입
	 */
	@Override
	public boolean register(MemberInput parameter) {
		
		
		
		Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
		if(optionalMember.isPresent()) {
			//현재 uerId에 해당하는 데이터 존재
			return false;
		}
		
		
		
//		Member member1 = Member.builder()
//				.userId(parameter.getUserId())
//				.userName(parameter.getUserName())
//				.password(parameter.getPassword())
//				.phone(parameter.getPhone())
//				.regDt(LocalDateTime.now())
//				.emailAuthYn(false)
//				.emailAuthKey(uuid)
//				.build();
//		memberRepository.save(member1); 다른 방법
		String uuid = UUID.randomUUID().toString();
		String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
		
		Member member = new Member();
		member.setUserId(parameter.getUserId());
		member.setUserName(parameter.getUserName());
		member.setPhone(parameter.getPhone());
		member.setPassword(encPassword);
		member.setRegDt(LocalDateTime.now());	
		member.setEmailAuthYn(false);
		member.setEmailAuthKey(uuid);
		member.setUserStatus(Member.MEMBER_STATUS_REQ);
		memberRepository.save(member);
	
		String email = parameter.getUserId();
		String subject = "페스트 사이트 가입을 축하드립니다.";
		String text = "<p>페스트 사이트 가입을 축하드립니다</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
				+ "<div><a target='_blank' href='http://localhost:8081/member/email-auth?id=" + uuid + "'> 가입완료 </a></div>";
		mailComponents.sendMail(email, subject, text);
		
		
		return true;
	}

	@Override
	public boolean emailAuth(String uuid) {
		
		Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		
		if(member.isEmailAuthYn()) {
			return false;
		}
		member.setUserStatus(Member.MEMBER_STATUS_ING);
		member.setEmailAuthYn(true);
		member.setEmailAuthDt(LocalDateTime.now());
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> optionalMember = memberRepository.findById(username);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		Member member = optionalMember.get();
		
		if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())) {
			throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
		}
		
		if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
			throw new MemberStopUserException("정지된 회원입니다.");
		}
		
		List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
		grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		if(member.isAdminYn()) {
			grantedAuthority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		return new User(member.getUserId(), member.getPassword(),grantedAuthority);
	}

	@Override
	public boolean sendResetPassword(ResetPasswordInput parameter) {
		
		Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
		}
		Member member = optionalMember.get();
		
		String uuid = UUID.randomUUID().toString();
		member.setResetPasswordKey(uuid);
		member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
		memberRepository.save(member);
		
		String email = parameter.getUserId();
		String subject = "[페스트캠퍼스] 비밀번호 초기화 메일입니다.";
		String text = "<p>페스트캠퍼스 비밀번호 초기화 메일입니다.</p>"
				+ "<p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"
				+ "<div><a target='_blank' href='http://localhost:8081/member/reset/password?id=" + uuid + "'> 비밀번호 초기화 링크 </a></div>";
		mailComponents.sendMail(email, subject, text);
		
		return true;
	}

	@Override
	public boolean resetPassword(String uuid, String password) {
		
		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
		}
		
		
		Member member = optionalMember.get();
		
		//초기화 날짜가 유효한지 체크 필요함.
		if(member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		
		String encPassowrd = BCrypt.hashpw(password, BCrypt.gensalt());
		member.setPassword(encPassowrd);
		member.setResetPasswordKey("");
		member.setResetPasswordLimitDt(null);
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public boolean checkResetPassoword(String uuid) {

		Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
		if(!optionalMember.isPresent()) {
			return false;
		}
		
		Member member = optionalMember.get();
		
		if(member.getResetPasswordLimitDt() == null) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		
		if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("유효한 날짜가 아닙니다.");
		}
		
		return true;
	}

	@Override
	public List<MemberDto> list(MemberParam parameter) {
		
		long totalCount = memberMapper.selectListCount(parameter);	
		List<MemberDto> list = memberMapper.selectList(parameter);
		
		if(!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for(MemberDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount - parameter.getPageStart() - i);
				i++;
			}
		}
		
		return list;
		
		//return memberRepository.findAll();
		
	}

	@Override
	public MemberDto detail(String userId) {
		
		Optional<Member> OptionalMember = memberRepository.findById(userId);
		if(!OptionalMember.isPresent()) {
			return null;
		}
		Member member = OptionalMember.get();
		
		return MemberDto.of(member);
	}

	@Override
	public boolean updateStatus(String userId, String userStatus) {
		
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
		}
		
		Member member = optionalMember.get();
		member.setUserStatus(userStatus);
		memberRepository.save(member);
		
		return true;
	}

	@Override
	public boolean updatePassword(String userId, String password) {

		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
		}
		
		String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		Member member = optionalMember.get();
		member.setPassword(encPassword);
		memberRepository.save(member);
		
		return true;
		
	}

	@Override
	public ServiceResult updateMemberPassword(MemberInput parameter) {
		
		String userId = parameter.getUserId();
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
		}
		
		Member member = optionalMember.get();
		
		if(!BCrypt.checkpw(parameter.getPassword(), member.getPassword())) {
			return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
		}
		
		String encPassword = BCrypt.hashpw(parameter.getNewPassword(), BCrypt.gensalt());
		member.setPassword(encPassword);
		memberRepository.save(member);
		return new ServiceResult(true);
	}

	@Override
	public ServiceResult updateMember(MemberInput parameter) {
		
		String userId = parameter.getUserId();
		Optional<Member> optionalMember = memberRepository.findById(userId);
		if(!optionalMember.isPresent()) {
			return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
		}
		Member member = optionalMember.get();
		member.setPhone(parameter.getPhone());
		member.setZipcode(parameter.getZipcode());
		member.setAddr(parameter.getAddr());
		member.setAddrDetail(parameter.getAddrDetail());
		member.setUdtDt(LocalDateTime.now());
		memberRepository.save(member);
		
		return new ServiceResult(true);
	}

}
