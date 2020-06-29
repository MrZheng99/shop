package com.zyl.shop.entity;

public class GoodsTypeItem {
	private int gtid;
	private String typename;
	private String status;
	public int getGtid() {
		return gtid;
	}
	public void setGtid(int gtid) {
		this.gtid = gtid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "GoodsTypeItem [gtid=" + gtid + ", typename=" + typename + ", status=" + status + "]";
	}
}
