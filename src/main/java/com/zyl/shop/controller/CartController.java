package com.zyl.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.CartItem;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService cartService;
	
	@RequestMapping(value="/cart",method=RequestMethod.GET)
	public ModelAndView home(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Integer userIdSession = (Integer) session.getAttribute("userID");
		if(userIdSession==null||userIdSession<=100) {
			mav.setViewName("redirect:/index");
			return mav;
		}
		mav.setViewName("/html/front/cart.html");
		return mav;
	}

	@RequestMapping("/cart/goods")
	public List<CartItem> getCartGoodsGid(@SessionAttribute("userID")int userId){
		return cartService.getGoods(userId);
	}
	
	@RequestMapping(value="/cart/good", method=RequestMethod.PUT)
	public ResponseJson addGood(@SessionAttribute(name="userID", required=false)Integer userId, @RequestBody CartItem cartItem) {
		ResponseJson responseJson = new ResponseJson();
		if(userId == null) {
			return responseJson;
		}
		responseJson.setSuccess(cartService.addGood(userId, cartItem));
		return responseJson;
	}
	
	@RequestMapping(value="/cart/good", method=RequestMethod.DELETE)
	public ResponseJson deleteGood(@SessionAttribute(name="userID", required=false)Integer userId, @RequestBody CartItem cartItem) {
		ResponseJson responseJson = new ResponseJson();
		if(userId == null) {
			return responseJson;
		}
		responseJson.setSuccess(cartService.deleteGood(userId, cartItem));
		return responseJson;
	}
}
