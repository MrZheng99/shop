package com.zyl.shop.entity;

public class GoodsImage {
	private int giid;
	private String url;
	private int gid;
	private String status;
	public int getGiid() {
		return giid;
	}
	public void setGiid(int giid) {
		this.giid = giid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "GoodsImage [giid=" + giid + ", url=" + url + ", gid=" + gid + ", status=" + status + "]";
	}
}
