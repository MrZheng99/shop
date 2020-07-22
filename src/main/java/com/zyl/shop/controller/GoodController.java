package com.zyl.shop.controller;

import java.io.IOException;
import java.util.List;

import com.zyl.shop.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.zyl.shop.entity.GoodsInfo;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.GoodsService;

import javax.servlet.http.HttpSession;

@RestController
public class GoodController {
	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value="/good", method=RequestMethod.POST)
	public int addGood(@RequestParam("pic") MultipartFile[] file, @RequestParam int tid,
			@RequestParam String gname, @RequestParam double price, @RequestParam int balance,
			@RequestParam String intro, @RequestParam String descr) {
		
		System.out.println(file[0].getOriginalFilename());

		try {
			goodsService.addGood(file, tid, gname, price, balance, intro, descr);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@RequestMapping("/goods")
	public List<GoodsInfo> searchGoodsByTypeAndName(@RequestParam Integer tid, @RequestParam String gname){
		return goodsService.searchGoodByTypeAndName(tid, gname);
	}
	
	@RequestMapping(value="/good/{goodId}", method=RequestMethod.POST)
	public ResponseJson updateGoodStatus(@PathVariable("goodId")int goodId, @RequestParam String status) {
		goodsService.updateGoodStatus(goodId, status);
		return new ResponseJson(true);
	}

	@RequestMapping(value="/goods/number/type/{type}",method=RequestMethod.GET)
	public ResponseJson queryGoodsNumberByType(@PathVariable("type") String type) {
		return new ResponseJson(true,null,goodsService.queryRowsNumber(type,"type"));
	}
	@RequestMapping(value="/goods/number/name/{name}",method=RequestMethod.GET)
	public ResponseJson queryGoodsNumberByName(@PathVariable("name") String name) {
		return new ResponseJson(true,null,goodsService.queryRowsNumber(name,"name"));
	}
	@RequestMapping(value= {"/goods/type/{type}","/goods/type/{type}/{pageNum}"})
	public ResponseJson queryGoodsByType(@PathVariable("type") String type,@PathVariable(value = "pageNum",required = false) Integer pageNum) {
		return goodsService.queryGoodsByType(type,pageNum);
	}
	@GetMapping(value= {"/goods/name/{name}","/goods/name/{name}/{pageNum}"})
	public ResponseJson queryGoodsByName(@PathVariable("name") String name, @PathVariable(value = "pageNum",required = false) Integer pageNum) {
		return goodsService.queryGoodsByName(name,pageNum);
	}
}
