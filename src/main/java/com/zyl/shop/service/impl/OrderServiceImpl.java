package com.zyl.shop.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zyl.shop.conf.AliPayConfig;
import com.zyl.shop.dao.CartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyl.shop.dao.GoodsDao;
import com.zyl.shop.dao.OrderDao;
import com.zyl.shop.entity.Goods;
import com.zyl.shop.entity.Order;
import com.zyl.shop.entity.OrderDetail;
import com.zyl.shop.entity.OrderDetailItem;
import com.zyl.shop.entity.OrderItem;
import com.zyl.shop.service.OrderService;
import com.alibaba.fastjson.JSON;
@Service("OrderService")
@Transactional
public class OrderServiceImpl implements OrderService{
	static private SimpleDateFormat sdf = new SimpleDateFormat();
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private CartDao cartDao;

	@Override
	public String alipay(String orderId) throws AlipayApiException {
		Order order = orderDao.getOrderById(orderId);
		DecimalFormat df = new DecimalFormat("#.00");//一定要两位精度的金额
		//构建客户端
		DefaultAlipayClient alipayRsa2Client = new DefaultAlipayClient(
				AliPayConfig.gatewayUrl,
				AliPayConfig.appId,
				AliPayConfig.merchantPrivateKey,
				"json",
				AliPayConfig.charset,
				AliPayConfig.alipayPublicKey,
				AliPayConfig.signType);
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest(); // 网页支付
		request.setNotifyUrl(AliPayConfig.notifyUrl);
		request.setReturnUrl(AliPayConfig.returnUrl);
		request.setBizContent("{\"out_trade_no\":\""+ orderId +"\","
				+ "\"total_amount\":\""+ df.format(order.getAmount()) +"\","
				+ "\"subject\":\"ZJ商城支付\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		return alipayRsa2Client.pageExecute(request).getBody();
	}


	@Override
	public List<OrderDetailItem> getOrderGoods(int userId, String oid) {
		if(this.orderOfUser(userId, oid) == null) {
			return null;
		}
		
		List<OrderDetail> orderDetails = orderDao.getOrderDetails(oid);
		List<OrderDetailItem> detailItems = new ArrayList<OrderDetailItem>();
		for(OrderDetail od : orderDetails) {
			OrderDetailItem item = new OrderDetailItem();
			item.setGid(String.valueOf(od.getGid()));
			item.setNumber(od.getNumber());
			item.setPrice(od.getPrice());
			detailItems.add(item);
		}
		return detailItems;
	}

	@Override
	public String getOrderProgress(int userId, String oid) {
		Order order = this.orderOfUser(userId, oid);
		
		return order.getOrderprogress();
	}

	@Override
	public double getOrderAmount(int userId, String oid) {
		Order order = this.orderOfUser(userId, oid);
		
		return order.getAmount();
	}

	@Override
	public boolean pay(int userId, String oid) {
		String progress = this.getOrderProgress(userId, oid);
		if(!OrderService.OrderProgress.unpaid.equals(progress)) {
			return false;
		}
		List<OrderDetail> orderDetails = orderDao.getOrderDetails(oid);
		for(OrderDetail od : orderDetails) {
			int gid = od.getGid();
			int number = od.getNumber();
			orderDao.decreaseStore(gid, number);
		}
		orderDao.updateOrderProgress(oid, OrderService.OrderProgress.paid);
		return true;
	}

	@Override
	public List<OrderItem> getUserOrders(int uid) {
		List<Order> orders = orderDao.getOrderByUserId(uid);
		List<OrderItem> orderItem = new ArrayList<OrderItem>();
		for(Order o : orders) {
			OrderItem item = new OrderItem();
			item.setOid(o.getOid());
			item.setAmount(o.getAmount());
			item.setDate(sdf.format(o.getDate()));
			item.setProgress(o.getOrderprogress());
			orderItem.add(item);
		}
		return orderItem;
	}

	@Override
	public boolean ship(String orderId) {
		orderDao.updateOrderProgress(orderId, OrderService.OrderProgress.unshiped);
		return true;
	}
	
	@Override
	public boolean receipt(int userId, String orderId) {
		if(this.orderOfUser(userId, orderId) == null) {
			return false;
		}
		orderDao.updateOrderProgress(orderId, OrderService.OrderProgress.shiped);
		return true;
	}
	

	@Override
	public boolean setAddress(int userId, String orderId, String aid) {
		if(this.orderOfUser(userId, orderId) == null) {
			return false;
		}
		orderDao.setAddress(orderId, aid);
		return true;
	}
	
	private Order orderOfUser(int userId, String oid) {
		Order order = orderDao.getOrderById(oid);
		if(order != null && order.getUid() == userId){
			return order;
		} else {
			return null;
		}
	}

	@Override
	public boolean addOrder(int userId, List<OrderDetailItem> items,Double amount,Integer oid) {
		Order order = new Order();
		order.setOid(oid);
		order.setUid(userId);
		order.setDate(new Date());
		order.setOrderprogress(OrderService.OrderProgress.unpaid);
		order.setAmount(amount);
		orderDao.addOrder(order);
        for(OrderDetailItem item : items) {
			OrderDetail detail = new OrderDetail();
			detail.setOdid(0);
			detail.setOid(order.getOid());
			detail.setNumber(item.getNumber());
			detail.setGid(Integer.parseInt(item.getGid()));
			detail.setPrice(item.getPrice());
			orderDao.addOrderDetail(detail);
			cartDao.deleteUserGood(userId,Integer.valueOf(item.getGid()));
		}
		
		return true;
	}

}


