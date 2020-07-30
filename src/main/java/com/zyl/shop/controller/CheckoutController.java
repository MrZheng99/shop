package com.zyl.shop.controller;

import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.OrderService;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckoutController {

	@Autowired
	private OrderService orderService;

	@RequestMapping("/checkout/{orderId}")
	public String checkoutPage(@SessionAttribute(name="userID", required=false) Integer userId,@PathVariable String orderId) {
		if(userId == null) {
			return "redirect:index";
		}
		if(orderId == null) {
			return "error.html";
		}
		return "/html/checkout.html";
	}

	@RequestMapping(value="/pay/{orderId}", method=RequestMethod.GET)
	public String payPage(@PathVariable String orderId) throws AlipayApiException {
		//System.out.println("获取支付页面");
		return "/html/pay.html";
		//return orderService.alipay(orderId);
	}

	@RequestMapping(value="/pay/{orderId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseJson pay(HttpSession session, @PathVariable String orderId) {
		int userId = (int)session.getAttribute("userID");
		ResponseJson responseJson = new ResponseJson(orderService.pay(userId, orderId));
		if(!responseJson.isSuccess()) {
			responseJson.setMsg("请不要重复支付");
		}
		return responseJson;
	}
}
