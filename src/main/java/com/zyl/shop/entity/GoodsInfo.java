package com.zyl.shop.entity;

public class GoodsInfo {
	private int gid;
	private String goodsname;
	private double goodsprice;
	private int store;
	private String status;
	private String goodsdescription;
	private int goodstype;
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public double getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(double goodsprice) {
		this.goodsprice = goodsprice;
	}
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGoodsdescription() {
		return goodsdescription;
	}
	public void setGoodsdescription(String goodsdescription) {
		this.goodsdescription = goodsdescription;
	}
	public int getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(int goodstype) {
		this.goodstype = goodstype;
	}
	@Override
	public String toString() {
		return "GoodsInfo [gid=" + gid + ", goodsname=" + goodsname + ", goodsprice=" + goodsprice + ", store=" + store
				+ ", status=" + status + ", goodsdescription=" + goodsdescription + ", goodstype=" + goodstype + "]";
	}
}