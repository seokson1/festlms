package com.zerobase.demo.components;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailComponents { 

	@Autowired
	private final JavaMailSender javaMailSender;
	
//	public void sendMailTest() {
//		
//		SimpleMailMessage msg= new SimpleMailMessage();
//		msg.setTo("seokson1@naver.com");
//		msg.setSubject("안녕하세요. 테스트 중입니다.");
//		msg.setText("안녕하세요. 메세지 테스트 중입니다.");
//		
//		javaMailSender.send(msg);
//	}
	
	public boolean sendMail(String mail, String subject, String text) {
		
		boolean result = false;
		
		MimeMessagePreparator msg = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo(mail);
				mimeMessageHelper.setSubject(subject);
				mimeMessageHelper.setText(text, true);
				
			}
		};
		try {
			javaMailSender.send(msg);	
			result = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
}
