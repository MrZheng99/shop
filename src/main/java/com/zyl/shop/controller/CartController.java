package com.zyl.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.service.impl.GoodsServiceImpl;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	GoodsServiceImpl goodsService;
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public ModelAndView home(@PathVariable("userId")Integer userId,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Integer userIdSession = (Integer) session.getAttribute("userID");
		if(userIdSession==null||userId<=100||userIdSession<=100||userId!=userIdSession) {
			mav.setViewName("redirect:../index");
			return mav;
		}
		mav.setViewName("/html/front/home.html");
		return mav;
	}

}
