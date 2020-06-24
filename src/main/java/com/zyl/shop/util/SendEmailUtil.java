package com.zyl.shop.util;

import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmailUtil {
	private final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R','S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public String send(JavaMailSender jms,String email){
		//建立邮件消息
		SimpleMailMessage mainMessage = new SimpleMailMessage();
		//发送者
		mainMessage.setFrom("zjqq520@vip.qq.com");
		//接收者
		mainMessage.setTo(email);
		//发送的标题
		mainMessage.setSubject("ZYL商城注册");
		//发送的内容,随机序列
		Random rand = new Random();
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<6;i++) {
			int index = rand.nextInt(36);
			buffer.append(codeSequence[index]);
		}
		String verifyCode = buffer.toString();
		mainMessage.setText(verifyCode);
		jms.send(mainMessage);
		return verifyCode;
	}

}
