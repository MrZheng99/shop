package com.zyl.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyl.shop.dao.CartDao;
import com.zyl.shop.entity.Cart;
import com.zyl.shop.entity.CartItem;
import com.zyl.shop.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartDao cartDao;

	@Override
	public List<CartItem> getGoods(int userId) {
		List<Cart> carts = cartDao.getUserGoodsGid(userId);
		List<CartItem> list = new ArrayList<>();
		for(Cart cart : carts) {
			CartItem cartItem = new CartItem();
			cartItem.setScid(cart.getScid());
			cartItem.setGid(cart.getGid());
			cartItem.setNumber(cart.getNumber());
			list.add(cartItem);
		}
		return list;
	}

	@Override
	public boolean addGood(Integer userId, CartItem cartItem) {
		Cart cart = new Cart();
		cart.setScid(0);
		cart.setGid(cartItem.getGid());
		cart.setNumber(cartItem.getNumber());
		cart.setUid(userId);
		cart.setStatus("1");
		cartDao.addGoodToUserCart(cart);
		return true;
	}

	@Override
	public boolean deleteGood(Integer userId, CartItem cartItem) {
		cartDao.deleteUserGood(userId, cartItem.getGid());
		return true;
	}

	@Override
	public boolean existGoods(Integer userId, Integer goodsId) {
		Integer scid = cartDao.queryGoodsByUserId(userId,goodsId);
		System.out.println(scid);
		if(scid!=null&&scid>=100){
			return true;
		}
		return false;
	}
}
