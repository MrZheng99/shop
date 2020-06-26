package com.zyl.shop.entity;

public class Address {
	private int aid;
	private String address;
	private int uid;
	private String status;
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Address [aid=" + aid + ", address=" + address + ", uid=" + uid + ", status=" + status + "]";
	}
}
