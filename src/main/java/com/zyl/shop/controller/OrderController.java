package com.zyl.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.OrderDetailItem;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/{orderId}")
	public ModelAndView checkoutPage(@PathVariable String orderId) {
		if(orderId == null) {
			return null;
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/order.html");
		return mav;
	}
	
	@RequestMapping("/order/{orderId}/items")
	@ResponseBody
	public List<OrderDetailItem> orderDetailItems(@PathVariable String orderId) {
		return orderService.getOrderGoods(orderId);
	}
	
	@RequestMapping("/order/{orderId}/status")
	@ResponseBody
	public ResponseJson orderStatus(@PathVariable String orderId) {
		ResponseJson responseJson = new ResponseJson();
		responseJson.setData(orderService.getOrderProgress(orderId));
		responseJson.setSuccess(true);
		return responseJson;
	}
	
	@RequestMapping("/order/{orderId}/amount")
	@ResponseBody
	public ResponseJson orderAmount(@PathVariable String orderId) {
		ResponseJson responseJson = new ResponseJson();
		responseJson.setData(orderService.getOrderAmount(orderId));
		responseJson.setSuccess(true);
		return responseJson;
	}
	
	@RequestMapping("/orders")
	public String ordersPage() {
		return "/orders.html";
	}
	
	@RequestMapping("/orders/orders")
	@ResponseBody
	public ResponseJson userOrders() {
		ResponseJson responseJson = new ResponseJson();
		responseJson.setData(orderService.getUserOrders("111"));
		responseJson.setSuccess(true);
		return responseJson;
	}
	
}
