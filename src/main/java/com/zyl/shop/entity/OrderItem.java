package com.zyl.shop.entity;

/**
 * 订单前端实体类
 * 由orders.html，订单浏览界面使用
 */
public class OrderItem {
	private int oid;
	private String date;
	private String progress;
	private double amount;
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "OrderItem [oid=" + oid + ", date=" + date + ", progress=" + progress + ", amount=" + amount + "]";
	}
}
