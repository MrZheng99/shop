package com.zyl.shop.entity;

/**
 * 订单详情前端实体类
 * 由order.html, 订单详情页面使用
 *
 */
public class OrderDetailItem{
	private String gid;
	private int number;
	private double price;
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}