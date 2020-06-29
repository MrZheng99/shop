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

import com.zyl.shop.entity.User;
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
		User user= userService.login(account, password);
		Map<String,Object> map = new HashMap<String,Object>();
		if(user!=null) {
			session.setAttribute("userID", user.getId());
			session.setAttribute("userName", user.getName());
			session.setMaxInactiveInterval(30*60);
			map.put("success", true);
			map.put("data", user.getId());
			return map;
		}	
		map.put("success", false);
		map.put("data", -1);
		return map;
	}


	
}
