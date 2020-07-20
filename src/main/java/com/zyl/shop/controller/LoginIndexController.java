package com.zyl.shop.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zyl.shop.entity.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.User;
import com.zyl.shop.service.impl.UserServiceImpl;

@RestController
public class LoginIndexController {
	@Autowired
	UserServiceImpl userService;

	/**
	 * 跳转到首页
	 * @return
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/index.html");
		return mav;
	}

	/**
	 * 登录，如果成功则将账号密码写到session中，并返回用户id。如果失败返回-1
	 * @param account
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseJson login(@RequestParam String account,@RequestParam String password,HttpSession session) {
		User user= userService.login(account, password);
		ResponseJson responseJson = new ResponseJson();
		if(user!=null) {
			session.setAttribute("userID", user.getId());
			System.out.println(user.getId());
			session.setAttribute("userName", user.getName());
			session.setMaxInactiveInterval(30 * 60);
			responseJson.setSuccess(true);
			responseJson.setData(user.getId());
			return responseJson;
		}
		responseJson.setSuccess(false);
		responseJson.setData(-1);
		return responseJson;
	}

}
