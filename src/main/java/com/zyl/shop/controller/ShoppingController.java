package com.zyl.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.Goods;
import com.zyl.shop.service.GoodsService;
import com.zyl.shop.service.impl.GoodsServiceImpl;
import com.zyl.shop.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
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
		mav.setViewName("/html/front/shopping.html");
		return mav;
	}
	@PostMapping(value= "/queryGoodsById")
	public Map<String,Object> queryGoodsByName(@RequestParam("gid") Integer gid) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Goods> listGoods= new ArrayList<Goods>();
		map.put("success", true);
		map.put("data", listGoods);
		return map;
	}
}