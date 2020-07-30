package com.zyl.shop.service;

import java.util.List;

import com.alipay.api.AlipayApiException;
import com.zyl.shop.entity.OrderDetailItem;
import com.zyl.shop.entity.OrderItem;

public interface OrderService {
    String alipay(String orderId) throws AlipayApiException;

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
	 * @param userId 用户编号 
	 * @param orderId 订单编号
	 * @return 对应订单编号的订单详情列表
	 */
	List<OrderDetailItem> getOrderGoods(int userId, String orderId);
	/**
	 * 获取订单的进度
	 * @param userId 用户编号
	 * @param oid 订单编号
	 * @return 订单进度
	 */
	String getOrderProgress(int userId, String oid);
	/**
	 * 获取订单金额
	 * @param userId 用户编号
	 * @param oid 订单编号
	 * @return 订单金额
	 * 没有精度要求吧？
	 */
	double getOrderAmount(int userId, String oid);
	/**
	 *  付款业务
	 * 扣除相应库存，将订单进度设置为已付款
	 * 
	 * @param userId 用户编号
	 * @param oid 订单编号
	 * @return 如果订单状态不是未付款，直接返回false, 
	 * 			支付成功返回true
	 */
	boolean pay(int userId, String oid);
	/**
	 * 获取用户的所有订单
	 * @param userId 用户编号
	 * @param uid 用户编号
	 * @return 订单的前端实体类列表
	 */
	List<OrderItem> getUserOrders(int uid);
	/**
	 * 订单发货
	 * @param orderId 订单编号
	 * @return 发货成功返回true
	 */
	boolean ship(String orderId);
	/**
	 * 收货
	 * @param userId 用户编号
	 * @param orderId 订单编号
	 * @return 收货成功返回true, 未登录或用户不拥有该订单返回false
	 */
	boolean receipt(int userId, String orderId);
	/**
	 * 设置订单的收货地址
	 * @param userId 用户编号
	 * @param orderId 订单编号
	 * @param aid 地址编号
	 * @return 成功返回true
	 */
	boolean setAddress(int userId, String orderId, String aid);

	/**
	 * 给用户添加订单
	 * @param userId 用户编号
	 * @param items 商品列表
	 * @param amount 商品总额
	 * @param oid 订单编号
	 * @return 添加成功返回true
	 */
	boolean addOrder(int userId, List<OrderDetailItem> items,Double amount,Integer oid);
}
