package com.zyl.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.OrderService;

@Controller
public class CheckoutController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/checkout/{orderId}")
	public String checkoutPage(@PathVariable String orderId) {
		if(orderId == null) {
			return "error.html";
		}
		return "/checkout.html";
	}
	
	@RequestMapping(value="/pay/{orderId}", method=RequestMethod.GET)
	public String payPage(@PathVariable String orderId) {
		return "/pay.html";
	}
	
	@RequestMapping(value="/pay/{orderId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseJson pay(@PathVariable String orderId) {
		ResponseJson responseJson = new ResponseJson(orderService.pay(orderId));
		if(!responseJson.isSuccess()) {
			responseJson.setMsg("请不要重复支付");
		}
		return responseJson;
	}
}
