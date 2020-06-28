package com.zyl.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.Goods;
import com.zyl.shop.service.impl.GoodsServiceImpl;

@RestController
@RequestMapping("/home")
public class HomeController {
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
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/html/front/home.html");
		return mav;
	}
	@RequestMapping(value="/queryName",method=RequestMethod.GET)
	public Map<String,Object> queryName(HttpSession session) {
		Map<String,Object> map = new HashMap<String, Object>();
		String userName = (String) session.getAttribute("userName");
		if(userName==null||userName=="") {
			map.put("success", false);
			map.put("data", "未登录");
			return map;
		}
		map.put("success", true);
		map.put("data", userName);
		return map;
	}
	@RequestMapping(value="/queryCategroy",method=RequestMethod.GET)
	public Map<String,Object> queryCategroy() {
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> names =goodsService.queryCategroy();
		if(names==null) {
			map.put("success", false);
			map.put("data", "类别获取失败");
			return map;
		}
		map.put("success", true);
		map.put("data", names);
		return map;
	}
	@RequestMapping(value="/queryGoodsNumber/{categroy}",method=RequestMethod.GET)
	public Map<String,Object> queryGoodsNumber(@PathVariable("categroy") String categroy) {
		Map<String,Object> map = new HashMap<String, Object>();
		Integer rows = goodsService.queryRowsNumber(categroy);
		map.put("success", true);
		map.put("data", rows);
		return map;
	}
	@RequestMapping(value= {"/queryGoods/{categroy}","/queryGoods/{categroy}/{pageNum}"})
	public Map<String,Object> queryGoods(@PathVariable("categroy") String categroy,@PathVariable(value = "pageNum",required = false) Integer pageNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Goods> listGoods= new ArrayList<Goods>();
		if(pageNum==null) {
			listGoods=goodsService.queryGoods(categroy,0,8);
		}else {
			listGoods=goodsService.queryGoods(categroy,pageNum*8,8);
		}
		if(listGoods==null) {
			map.put("success", false);
			map.put("data", "数据获取失败");
			return map;
		}
		map.put("success", true);
		map.put("data", listGoods);
		return map;
	}
	@PostMapping(value= {"/queryGoods","/queryGoods/{pageNum}"})
	public Map<String,Object> queryGoodsByName(@RequestParam("goodsName") String goodsName,@PathVariable(value = "pageNum",required = false) Integer pageNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		List<Goods> listGoods= new ArrayList<Goods>();
		goodsName = goodsName.trim();
		if(pageNum==null) {
			listGoods=goodsService.queryGoodsByName(goodsName,0,8);
		}else {
			listGoods=goodsService.queryGoodsByName(goodsName,pageNum*8,8);
		}
		if(listGoods==null) {
			map.put("success", false);
			map.put("data", "数据获取失败");
			return map;
		}
		map.put("success", true);
		map.put("data", listGoods);
		return map;
	}
}
