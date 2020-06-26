package com.zyl.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.service.impl.UserServiceImpl;
import com.zyl.shop.util.SendEmailUtil;
@RestController
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	JavaMailSender jms;
	@Autowired
	SendEmailUtil emailUtil;
	//返回注册页面
		@GetMapping(value="")
		public ModelAndView register() {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/html/front/register.html");
			return mav;
		}
		//提交注册
		@PostMapping(value="/step1")
		public 	Map<String,Object> register(@RequestParam String account,@RequestParam String password,HttpSession session) {
			
			System.out.println(account+password);
			Boolean rs = userService.existAccount(account);
			Map<String,Object> map = new HashMap<String,Object>();
			if(!rs) {
				session.setAttribute("account", account);
				session.setAttribute("password", password);
				map.put("success", true);
				return map;
			}	
			map.put("success", false);
			map.put("data", "ACCOUNT_REPEAT");
			return map;
		}
		//
		@GetMapping(value="/step2")
		public ModelAndView registerStepTwo() {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("/html/front/registerStep2.html");
			return mav;
		}
		private String VERIFY_CODE;
		private Integer VERIFY_TIME;

		@PostMapping(value="/getCode")
		public Map<String,Object> getCode(@RequestParam("email") String email,HttpSession session) {
			session.setAttribute("email", email);
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				SendEmailUtil emailUtil = new SendEmailUtil();
				VERIFY_CODE = emailUtil.send(jms, email,6);
				VERIFY_TIME = 5;
				map.put("success", true);
				map.put("data", "CODE_SENDED");
				

				return map;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			map.put("success", false);
			map.put("data", "CODE_SENDED_FAIL");
			return map;
		}
		@PostMapping(value="/verifyCode")
		public Map<String,Object> verifyEmail(@RequestParam String verifyCode,HttpSession session) {
			Map<String,Object> map = new HashMap<String,Object>();
			System.out.println(VERIFY_CODE+":"+verifyCode);
			VERIFY_TIME--;
			if(verifyCode.equals(VERIFY_CODE)&&VERIFY_TIME>=0) {
				//开始存到数据库
				String account = (String) session.getAttribute("account");
				String password = (String) session.getAttribute("password");
				String email = (String) session.getAttribute("email");

				System.out.println(account+password+email);

				Boolean rs = userService.register(account, password,email);
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
		@GetMapping(value="/success")
		public ModelAndView registerStepThree(HttpSession session) {
			ModelAndView mav = new ModelAndView();
			//成功之后清除session
			session.removeAttribute("account");
			session.removeAttribute("password");
			mav.setViewName("/html/front/registerStep3.html");
			return mav;
		}
}