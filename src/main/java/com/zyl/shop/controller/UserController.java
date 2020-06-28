package com.zyl.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.zyl.shop.entity.AddressItem;
import com.zyl.shop.entity.ResponseJson;
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
	
	@RequestMapping("/user/name")
	@ResponseBody
	public ResponseJson getUserName(@SessionAttribute(name="userID", required=false) Integer userId) {
		ResponseJson responseJson = new ResponseJson();
		if(userId == null) {
			responseJson.setMsg("未登录");
			return responseJson;
		}
		responseJson.setData(userService.getUserNameById(userId));
		responseJson.setSuccess(true);
		return responseJson;
	}
}
