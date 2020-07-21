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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	@RequestMapping(value="/queryName",method=RequestMethod.GET)
	public ResponseJson queryName(HttpSession session) {
		String userName = (String) session.getAttribute("userName");
		if(userName==null||userName=="") {
			return  new ResponseJson(false,"未登录",null);
		}
		return  new ResponseJson(true,"登录成功",userName);
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
	@RequestMapping(value="/queryGoodsNumber/{categroy}",method=RequestMethod.GET)
	public ResponseJson queryGoodsNumber(@PathVariable("categroy") String categroy) {
		return new ResponseJson(true,null,goodsService.queryRowsNumber(categroy));
	}
	@RequestMapping(value= {"/queryGoods/{categroy}","/queryGoods/{categroy}/{pageNum}"})
	public ResponseJson queryGoods(@PathVariable("categroy") String categroy,@PathVariable(value = "pageNum",required = false) Integer pageNum) {
		List<Goods> listGoods=null;
		if(pageNum==null) {
			listGoods=goodsService.queryGoods(categroy,0,8);
		}else {
			listGoods=goodsService.queryGoods(categroy,pageNum*8,8);
		}
		if(listGoods==null) {
			return new ResponseJson(false,"数据获取失败",null);
		}
		return new ResponseJson(true,"数据获取成功",listGoods);
	}
}
