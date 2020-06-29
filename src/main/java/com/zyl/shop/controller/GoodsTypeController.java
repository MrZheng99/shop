package com.zyl.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zyl.shop.entity.GoodsTypeItem;
import com.zyl.shop.service.GoodsTypeService;

@RestController
public class GoodsTypeController {
	
	@Autowired
	private GoodsTypeService goodsTypeService;

	@RequestMapping(value="/types", method=RequestMethod.GET)
	public List<GoodsTypeItem> getTypes(){
		return goodsTypeService.getTypes();
	}
	
	@RequestMapping(value="/type", method=RequestMethod.POST)
	public int updateTypeStatus(@RequestParam int tid, @RequestParam String status) {
		goodsTypeService.updateTypeStatus(tid, status);
		return 1;
	}
	
	@RequestMapping(value="/type", method=RequestMethod.PUT)
	public int addType(@RequestParam String tname) {
		goodsTypeService.addType(tname);
		return 1;
	}
}
