package com.zyl.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyl.shop.dao.OrderDao;
import com.zyl.shop.entity.Order;
import com.zyl.shop.entity.OrderDetail;
import com.zyl.shop.entity.OrderDetailItem;
import com.zyl.shop.entity.OrderItem;
import com.zyl.shop.service.OrderService;

@Service("OrderService")
@Transactional
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderDao orderDao;

	@Override
	public List<OrderDetailItem> getOrderGoods(String oid) {
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
	public String getOrderProgress(String oid) {
		Order order = orderDao.getOrderById(oid);
		
		return order.getOrderprogress();
	}

	@Override
	public double getOrderAmount(String oid) {
		Order order = orderDao.getOrderById(oid);
		
		return order.getAmount();
	}

	@Override
	public boolean pay(String oid) {
		String progress = this.getOrderProgress(oid);
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
	public List<OrderItem> getUserOrders(String uid) {
		List<Order> orders = orderDao.getOrderByUserId(uid);
		List<OrderItem> orderItem = new ArrayList<OrderItem>();
		for(Order o : orders) {
			OrderItem item = new OrderItem();
			item.setOid(o.getOid());
			item.setAmount(o.getAmount());
			item.setDate(o.getDate());
			item.setProgress(o.getOrderprogress());
			orderItem.add(item);
		}
		return orderItem;
	}
}


