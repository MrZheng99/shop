package com.zyl.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zyl.shop.conf.AliPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.OrderService;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		return "/html/pay.html";
	}
	@RequestMapping(value="/pay/{orderId}", method=RequestMethod.POST)
	@ResponseBody
	public ResponseJson pay(HttpSession session, @PathVariable String orderId) {
		int userId = (int)session.getAttribute("userID");
		System.out.println("支付，更新数据库");
		ResponseJson responseJson = new ResponseJson(orderService.pay(userId, orderId));
		if(!responseJson.isSuccess()) {
			responseJson.setMsg("请不要重复支付");
		}
		return responseJson;
	}
	@ResponseBody
	@RequestMapping(value="/aliPay/{orderId}", method=RequestMethod.GET)
	public String aliPay(@PathVariable String orderId) throws AlipayApiException {
		System.out.println("获取支付页面");
		return orderService.alipay(orderId);
	}

	/**
	 * @Description: 支付宝回调接口
	 * @param out_trade_no 商户订单号
	 * @param trade_no     支付宝交易凭证号
	 * @param trade_status 交易状态
	 * @return String
	 * @throws AlipayApiException
	 */
	@PostMapping("/aliPay/notify")
	private ResponseJson aliPayNotify(HttpServletRequest request, String out_trade_no, String trade_no, String trade_status)
			throws AlipayApiException {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			map.put(name, valueStr);
		}
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(map, AliPayConfig.alipayPublicKey, AliPayConfig.charset,
					AliPayConfig.signType);
		} catch (com.alipay.api.AlipayApiException e) {
			// 验签发生异常,则直接返回失败
			return new ResponseJson(false,"验签发生异常");
		}
		if (signVerified) {
			try {
				return pay(request.getSession(),out_trade_no);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return new ResponseJson(false,"验签失败");

	}
	/**
	 * @Description: 支付宝回调接口
	 * @return String
	 * @throws AlipayApiException
	 */
	@GetMapping("/aliPay/return")
	private String aliPayReturn(HttpServletRequest request) {
		System.out.println("接收到支付宝的同步通知请求");
		Map<String, String[]> maps = request.getParameterMap();
		String orderId = maps.get("out_trade_no")[0];//获取订单号
		pay(request.getSession(),orderId);
		return "redirect:/home";
	}
}
