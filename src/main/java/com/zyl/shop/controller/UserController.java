package com.zyl.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyl.shop.entity.AddressItem;
import com.zyl.shop.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/addresses")
	@ResponseBody
	public List<AddressItem> userAddresses(HttpSession session){
		int userId = (int) session.getAttribute("userID");
		return userService.getAddresses(userId);
	}
}
