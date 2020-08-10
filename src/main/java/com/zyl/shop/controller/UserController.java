package com.zyl.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.zyl.shop.entity.User;
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
		mav.setViewName("/html/user.html");
		return mav;
	}

	@RequestMapping("/addresses")
	@ResponseBody
	public ResponseJson userAddresses(HttpSession session){
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
		mav.setViewName("/html/userInfo.html");
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
		mav.setViewName("/html/userAddress.html");
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
	@RequestMapping(value="/updateUserInfo",method=RequestMethod.POST)
	public ResponseJson updateUserInfo(HttpSession session,@RequestParam("tel")String tel,
									   @RequestParam("sex")String sex,@RequestParam("realName")String realName,
									   @RequestParam("email")String email) {
		Integer userId = (Integer) session.getAttribute("userID");
		Integer row = userService.updateUserInfo(userId,realName,tel,email,sex);
		if(row!=null&&row>0){
			return new ResponseJson(true,"更新成功");
		}
		return new ResponseJson(true,"更新失败");
	}
	@RequestMapping(value="/updateUserAddress",method=RequestMethod.POST)
	public ResponseJson updateUserAddress(HttpSession session,@RequestParam("aid")Integer aid) {
		Integer userId = (Integer) session.getAttribute("userID");
		Integer row = userService.updateUserAddress(userId,aid);
		if(row!=null&&row>0){
			return new ResponseJson(true,"更新成功");
		}
		return new ResponseJson(false,"更新失败");
	}

	@RequestMapping(value="/addUserAddress",method=RequestMethod.POST)
	public ResponseJson addUserAddress(HttpSession session,@RequestParam("address")String address) {
		Integer userId = (Integer) session.getAttribute("userID");
		Integer row = userService.addUserAddress(userId,address);
		if(row!=null&&row>0){
			return new ResponseJson(true,"添加成功");
		}
		return new ResponseJson(false,"添加失败");
	}

	@RequestMapping(value="/updateUserAddressDefault",method=RequestMethod.POST)
	public ResponseJson updateUserAddressDefault(HttpSession session,@RequestParam("aid")Integer aid) {
		Integer userId = (Integer) session.getAttribute("userID");
		Integer row = userService.updateUserAddressDefault(userId,aid);
		System.out.println(row);
		if(row!=null&&row>0){
			return new ResponseJson(true,"更新成功");
		}
		return new ResponseJson(false,"更新失败");
	}

}
