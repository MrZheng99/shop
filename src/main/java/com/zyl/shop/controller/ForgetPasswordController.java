package com.zyl.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.service.impl.UserServiceImpl;
import com.zyl.shop.util.SendEmailUtil;
@RestController
@RequestMapping("/forgetPassword")
public class ForgetPasswordController {

	@Autowired
	UserServiceImpl userService;
	@Autowired
	JavaMailSender jms;
	@Autowired
	SendEmailUtil emailUtil;

	private String VERIFY_CODE;
	private Integer VERIFY_TIME;

	/**************忘记密码***************/
	@GetMapping("")
	public ModelAndView forgetPassword() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/forgetPassword.html");
		return mav;
	}
	@PostMapping("/getCode")
	public Map<String,Object> getCode(@RequestParam("email") String email,@RequestParam("account") String account,HttpSession session) {
		Map<String,Object> map = new HashMap<String,Object>();

		if(!userService.existAccount(account,email)) {
			map.put("success", false);
			map.put("data", "ACCOUNT_NOT_EXIST");
			return map;
		}
		try {
			SendEmailUtil emailUtil = new SendEmailUtil();
			VERIFY_CODE = emailUtil.send(jms, email,6);
			VERIFY_TIME = 5;
			map.put("success", true);
			map.put("data", "CODE_SENDED");
			session.setAttribute("email", email);
			session.setAttribute("account", account);

			return map;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		map.put("success", false);
		map.put("data", "CODE_SENDED_FAIL");
		return map;
	}
	@PostMapping("/verifyCode")
	public Map<String,Object> verifyEmail(@RequestParam String verifyCode,HttpSession session) {
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println(VERIFY_CODE+":"+verifyCode);
		VERIFY_TIME--;
		if(verifyCode.equals(VERIFY_CODE)&&VERIFY_TIME>=0) {
			String account = (String) session.getAttribute("account");
			String email = (String) session.getAttribute("email");
			String npwd =DigestUtils.md5DigestAsHex(emailUtil.send(jms, email,15).getBytes());

			Boolean rs = userService.updatePassword(account, npwd,email);
			if(rs) {
				map.put("success", true);
				return map;
			}
			map.put("success", false);
			map.put("data", "DATABASE_ERROR");
			return map;
		}
		if(VERIFY_TIME==0) {
			map.put("success", false);
			map.put("data", "VERIFY_TIME_MULTIPLE");
			return map;

		}
		map.put("success", false);
		map.put("data", "VERIFY_CODE_ERROR");
		return map;
	}

}
