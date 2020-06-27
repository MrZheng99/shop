package com.zyl.shop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.zyl.shop.entity.OrderDetailItem;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/{orderId}")
	public String checkoutPage(@SessionAttribute(name="userID", required=false) Integer userId, @PathVariable String orderId) {
		if(userId == null) {
			return "redirect:/index";
		}
		if(orderId == null) {
			return "redirect:/orders.html";
		}
		return "/order.html";
	}
	
	@RequestMapping(value="/order", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseJson addOrder(@SessionAttribute(name="userID", required=false) Integer userId, @RequestBody List<OrderDetailItem> items) {
		ResponseJson responseJson = new ResponseJson();
		if(userId == null) {
			responseJson.setMsg("未登录");
			return responseJson;
		}
		responseJson.setSuccess(orderService.addOrder(userId, items));
		return responseJson;
	}
	
	@RequestMapping(value="/order/{orderId}/address", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseJson setOrderAddress(HttpSession session, @PathVariable String orderId, @RequestParam String aid) {
		int userId = (int)session.getAttribute("userID");
		return new ResponseJson(orderService.setAddress(userId, orderId, aid));
	}
	
	@RequestMapping("/order/{orderId}/items")
	@ResponseBody
	public List<OrderDetailItem> orderDetailItems(HttpSession session, @PathVariable String orderId) {
		int userId = (int)session.getAttribute("userID");
		return orderService.getOrderGoods(userId, orderId);
	}
	
	@RequestMapping("/order/{orderId}/status")
	@ResponseBody
	public ResponseJson orderStatus(HttpSession session, @PathVariable String orderId) {
		int userId = (int)session.getAttribute("userID");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setData(orderService.getOrderProgress(userId, orderId));
		responseJson.setSuccess(true);
		return responseJson;
	}
	
	@RequestMapping("/order/{orderId}/amount")
	@ResponseBody
	public ResponseJson orderAmount(HttpSession session, @PathVariable String orderId) {
		int userId = (int)session.getAttribute("userID");
		ResponseJson responseJson = new ResponseJson();
		responseJson.setData(orderService.getOrderAmount(userId, orderId));
		responseJson.setSuccess(true);
		return responseJson;
	}
	
	@RequestMapping("/order/{orderId}/hasten")
	@ResponseBody
	public ResponseJson orderHasten(HttpSession session, @PathVariable String orderId) {
		Integer userId = (Integer)session.getAttribute("userID");
		if(userId == null) {
			return new ResponseJson(false);
		}
		// 催货就直接发货，反正还没有后台
		ResponseJson responseJson = new ResponseJson(orderService.ship(orderId));
		return responseJson;
	}

	@RequestMapping("/order/{orderId}/receipt")
	@ResponseBody
	public ResponseJson orderReceipt(HttpSession session, @PathVariable String orderId) {
		int userId = (int)session.getAttribute("userID");
		
		ResponseJson responseJson = new ResponseJson(orderService.receipt(userId, orderId));
		return responseJson;
	}
	
	@RequestMapping("/orders")
	public String ordersPage(@SessionAttribute(name="userID", required=false) Integer userId) {
		if(userId == null) {
			return "redirect:/index";
		}
		return "/orders.html";
	}
	
	@RequestMapping("/orders/orders")
	@ResponseBody
	public ResponseJson userOrders(HttpSession session) {
		int userId = (int)session.getAttribute("userID");
		
		ResponseJson responseJson = new ResponseJson();
		responseJson.setData(orderService.getUserOrders(userId));
		responseJson.setSuccess(true);
		return responseJson;
	}
}
