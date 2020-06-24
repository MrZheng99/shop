package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.OrderDetailItem;
import com.zyl.shop.entity.OrderItem;

public interface OrderService {
	/**
	 * 常量类
	 * 存放订单进度的常量
	 */
	class OrderProgress{
		public static final String unpaid = "未付款";
		public static final String paid = "已付款";
		public static final String unshiped = "已发货";
		public static final String shiped = "已收货";
	}
	/**
	 * 根据订单编号获取对应的订单详情
	 * @param oid 订单编号
	 * @return 对应订单编号的订单详情列表
	 */
	List<OrderDetailItem> getOrderGoods(String oid);
	/**
	 * 获取订单的进度
	 * @param oid 订单编号
	 * @return 订单进度
	 */
	String getOrderProgress(String oid);
	/**
	 * 获取订单金额
	 * @param oid 订单编号
	 * @return 订单金额
	 * 没有精度要求吧？
	 */
	double getOrderAmount(String oid);
	/**
	 *  付款业务
	 * 扣除相应库存，将订单进度设置为已付款
	 * 
	 * @param oid 订单编号
	 * @return 如果订单状态不是未付款，直接返回false, 
	 * 			支付成功返回true
	 */
	boolean pay(String oid);
	/**
	 * 获取用户的所有订单
	 * @param uid 用户编号
	 * @return 订单的前端实体类列表
	 */
	List<OrderItem> getUserOrders(String uid);
}
