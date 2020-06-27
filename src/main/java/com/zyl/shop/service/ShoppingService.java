package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.Goods;

public interface ShoppingService {
	List<Goods> queryGoodsById(Integer gid);

}
