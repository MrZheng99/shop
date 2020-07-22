package com.zyl.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zyl.shop.entity.AddressItem;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
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
}
