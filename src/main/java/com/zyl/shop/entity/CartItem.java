package com.zyl.shop.entity;

public class CartItem {
	private int scid;
	private int gid;
	private int number;
	private String status;
	@Override
	public String toString() {
		return "CartItem [scid=" + scid + ", gid=" + gid + ", number=" + number + ", status=" + status + "]";
	}
	public int getScid() {
		return scid;
	}
	public void setScid(int scid) {
		this.scid = scid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
