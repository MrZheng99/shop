package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.Goods;

public interface GoodsService {
	 List<String> queryCategroy(); 
	 Integer queryRowsNumber(String categroy);
	List<Goods> queryGoods(String categroy, Integer startNumber, Integer number);
	List<Goods> queryGoodsByName(String goodsName, Integer startNumber, Integer number);
}
