package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.CartItem;

public interface CartService {
	 /**
	  * 获取用户购物车内所有商品编号
	  * @param userId 用户编号
	  * @return 商品编号列表
	  */
	 List<CartItem> getGoods(int userId);
	 /**
	  * 添加商品到用户的购物车
	  * @param userId 用户编号
	  * @param cartItem 购物车前端类
	  * @return 添加成功返回true
	  */
	 boolean addGood(Integer userId, CartItem cartItem);
	 /**
	  * 删除用户购物车中的商品
	  * @param userId 用户编号
	  * @param cartItem 购物车前端类
	  * @return
	  */
	boolean deleteGood(Integer userId, CartItem cartItem);

	/**
	 * 是不是在购物车之中
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	boolean existGoods(Integer userId,Integer goodsId);
}
