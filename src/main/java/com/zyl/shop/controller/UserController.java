package com.zyl.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zyl.shop.entity.AddressItem;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.UserService;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@GetMapping()
	public ModelAndView user(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Integer userIdSession = (Integer) session.getAttribute("userID");
		if(userIdSession==null||userIdSession<=100) {
			mav.setStatus(HttpStatus.FORBIDDEN);
			mav.setViewName("redirect:index");
			return mav;
		}
		mav.setViewName("/html/front/user.html");
		return mav;
	}
	@RequestMapping("/addresses")
	@ResponseBody
	public List<AddressItem> userAddresses(HttpSession session){
		int userId = (int) session.getAttribute("userID");
		return userService.getAddresses(userId);
	}

	@RequestMapping(value="/name",method= RequestMethod.GET)
	public ResponseJson queryName(HttpSession session) {
		String userName = (String) session.getAttribute("userName");
		if(userName==null||userName=="") {
			return  new ResponseJson(false,"未登录",null);
		}
		return  new ResponseJson(true,"登录成功",userName);
	}
	@GetMapping("/getUserInfoPage")
	public ModelAndView getUserInfoPage(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Integer userIdSession = (Integer) session.getAttribute("userID");
		if(userIdSession==null||userIdSession<=100) {
			mav.setStatus(HttpStatus.FORBIDDEN);
			return mav;
		}
		mav.setViewName("/html/front/userInfo.html");
		return mav;
	}
	@GetMapping("/getUserAddressPage")
	public ModelAndView getUserAddressPage(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Integer userIdSession = (Integer) session.getAttribute("userID");
		if(userIdSession==null||userIdSession<=100) {
			mav.setStatus(HttpStatus.FORBIDDEN);
			return mav;
		}
		mav.setViewName("/html/front/userAddress.html");
		return mav;
	}
	@RequestMapping(value="/getUserInfo",method=RequestMethod.GET)
	public ResponseJson getUserInfo(HttpSession session) {
		Integer userIdSession = (Integer) session.getAttribute("userID");
		if(userIdSession==null||userIdSession<=100) {
			return new ResponseJson(false,"请登录");
		}
		return new ResponseJson(true,"获取成功",userService.getUserById(userIdSession));
	}
}
