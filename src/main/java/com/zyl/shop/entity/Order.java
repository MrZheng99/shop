package com.zyl.shop.entity;

import java.util.Date;

public class Order {
	private int oid;
	private int uid;
	private Date date;
	private String orderprogress;
	private double amount;
	private String status;
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getOrderprogress() {
		return orderprogress;
	}
	public void setOrderprogress(String orderprogress) {
		this.orderprogress = orderprogress;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", uid=" + uid + ", date=" + date + ", orderprogress=" + orderprogress
				+ ", amount=" + amount + ", status=" + status + "]";
	}
}
