package com.zyl.shop.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
		mav.setViewName("/html/index.html");
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
	public ResponseJson login(@RequestParam String account,@RequestParam String password,@RequestParam String code,HttpSession session) {
		String scode = (String) session.getAttribute(SessionKey.VALIDATE_CODE);
		ResponseJson responseJson = new ResponseJson();
		System.out.println(code+":"+scode);
		if(!scode.equals(code)){
			responseJson.setSuccess(false);
			responseJson.setMsg("验证码不正确");
			responseJson.setData(500);
			return responseJson;
		}
		User user= userService.login(account, password);
		if(user!=null) {
			session.setAttribute("userID", user.getId());
			System.out.println(user.getId());
			session.setAttribute("userName", user.getName());
			session.setMaxInactiveInterval(30 * 60);
			responseJson.setSuccess(true);
			responseJson.setMsg("登录成功");
			responseJson.setData(user.getId());
			return responseJson;
		}
		responseJson.setSuccess(false);
		responseJson.setMsg("账户密码不正确");
		responseJson.setData(404);
		return responseJson;
	}

	/**
	 * 退出账户
	 * @param session
	 * @return
	 */
	@GetMapping("/loginOut")
	public ModelAndView loginOut(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Integer userIdSession = (Integer) session.getAttribute("userID");
		if(userIdSession!=null && userIdSession>100) {
			session.removeAttribute("userID");
			session.removeAttribute("userName");
			mav.setViewName("redirect:index");
			return mav;
		}
		mav.setStatus(HttpStatus.FORBIDDEN);
		mav.setViewName("redirect:index");
		return mav;
	}
}
