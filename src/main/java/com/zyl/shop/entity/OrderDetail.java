package com.zyl.shop.entity;

public class OrderDetail {
	private int odid;
	private double price;
	private int number;
	private int gid;
	private int oid;
	private String status;
	public int getOdid() {
		return odid;
	}
	public void setOdid(int odid) {
		this.odid = odid;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "OrderDetail [odid=" + odid + ", price=" + price + ", number=" + number + ", gid=" + gid + ", oid=" + oid
				+ ", status=" + status + "]";
	}
}
