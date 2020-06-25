package com.zyl.shop.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.service.impl.UserServiceImpl;

@RestController
public class LoginIndexController {
	@Autowired
	UserServiceImpl userService;

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView index() {
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
	
	
}
