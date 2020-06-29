package com.zyl.shop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zyl.shop.entity.GoodsInfo;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.GoodsService;

@RestController
public class GoodController {
	@Autowired
	private GoodsService goodService;

	@RequestMapping(value="/good", method=RequestMethod.POST)
	public int addGood(@RequestParam("pic") MultipartFile[] file, @RequestParam int tid,
			@RequestParam String gname, @RequestParam double price, @RequestParam int balance,
			@RequestParam String intro, @RequestParam String descr) {
		
		System.out.println(file[0].getOriginalFilename());

		try {
			goodService.addGood(file, tid, gname, price, balance, intro, descr);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@RequestMapping("/goods")
	public List<GoodsInfo> searchGoodsByTypeAndName(@RequestParam Integer tid, @RequestParam String gname){
		return goodService.searchGoodByTypeAndName(tid, gname);
	}
	
	@RequestMapping(value="/good/{goodId}", method=RequestMethod.POST)
	public ResponseJson updateGoodStatus(@PathVariable("goodId")int goodId, @RequestParam String status) {
		goodService.updateGoodStatus(goodId, status);
		return new ResponseJson(true);
	}
}
