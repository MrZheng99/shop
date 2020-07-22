package com.zyl.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import com.zyl.shop.entity.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.Goods;
import com.zyl.shop.service.impl.GoodsServiceImpl;

@RestController
@RequestMapping("/home")
@Scope("singleton")
public class HomeController {
	@Autowired
	GoodsServiceImpl goodsService;
	//存储种类名称到全局变量避免重复查询
	private List<String> listCategory=null;
	@PostConstruct
	private void init(){
		listCategory =goodsService.queryCategroy();
	}
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public ModelAndView home(@PathVariable("userId")Integer userId,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Integer userIdSession = (Integer) session.getAttribute("userID");
//记录一下!userId.equals(userIdSession)和userId！=userIdSession不一样
		if(userIdSession==null||userId<=100||userIdSession<=100||!userId.equals(userIdSession)) {
			mav.setViewName("redirect:../index");
			return mav;
		}
		mav.setViewName("/html/front/home.html");
		return mav;
	}
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/home.html");
		return mav;
	}
	@RequestMapping(value="/queryCategroy",method=RequestMethod.GET)
	public ResponseJson queryCategroy() {
		ResponseJson responseJson =new ResponseJson();
		if(listCategory==null){
			init();
		}
		if(listCategory==null) {
			responseJson.setSuccess(false);
			responseJson.setMsg("类别获取失败");
			return responseJson;
		}
		responseJson.setSuccess(true);
		responseJson.setData(listCategory);
		return responseJson;
	}
    @RequestMapping("/saveKeyWord/{keyWord}")
    public ResponseJson saveKeyWords(@PathVariable("keyWord") String keyWord,HttpSession session) {
        try{
			session.setAttribute("keyWord",keyWord);
			return new ResponseJson(true,"关键字设置成功");
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ResponseJson(false,"关键字设置失败");
    }
}
