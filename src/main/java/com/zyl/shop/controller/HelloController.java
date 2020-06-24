package com.zyl.shop.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.service.impl.UserServiceImpl;
import com.zyl.shop.util.SendEmailUtil;

@RestController
public class HelloController {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	JavaMailSender jms;

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView hello() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/index.html");
		return mav;
	}


	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Map<String,Object> login(@RequestParam String account,@RequestParam String password,HttpSession session) {
		Integer userID= userService.login(account, password);
		System.out.println(userID);
		Map<String,Object> map = new HashMap<String,Object>();
		if(userID!=null) {
			session.setAttribute("userID", userID);
			map.put("success", true);
			map.put("data", userID);
			return map;
		}	
		map.put("success", false);
		map.put("data", 0);
		return map;
	}
	@RequestMapping(value="/home/*",method=RequestMethod.GET)
	public ModelAndView home(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//别想直接访问 你不登陆
		if(session.getAttribute("userID")!=null) {
			mav.setViewName("/html/front/zylshop.html");
			return mav;
		}
		mav.setViewName("redirect:../index");
		return mav;
	}
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/home.html");
		return mav;
	}
	//返回注册页面
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/register.html");
		return mav;
	}
	//提交注册
	@RequestMapping(value="/register",method=RequestMethod.POST)
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
	@RequestMapping(value="/register/step2",method=RequestMethod.GET)
	public ModelAndView registerStepTwo() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/registerStep2.html");
		return mav;
	}
	private String VERIFY_CODE;
	private Integer VERIFY_TIME;

	@RequestMapping(value="/register/getCode",method=RequestMethod.POST)
	public Map<String,Object> getCode(@RequestParam("email") String email,HttpSession session) {
		session.setAttribute("email", email);
		Map<String,Object> map = new HashMap<String,Object>();
		SendEmailUtil emailUtil = new SendEmailUtil();
		VERIFY_CODE = emailUtil.send(jms, email);
		VERIFY_TIME = 5;
		map.put("success", true);
		map.put("data", "CODE_SENDED");
		return map;
	}
	@RequestMapping(value="/register/verifyCode",method=RequestMethod.POST)
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
	@RequestMapping(value="/register/success",method=RequestMethod.GET)
	public ModelAndView registerStepThree() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/registerStep3.html");
		return mav;
	}
}
