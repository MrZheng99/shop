package com.zyl.shop.entity;

/**
 * 订单详情前端实体类
 * 由order.html, 订单详情页面使用
 *
 */
public class OrderDetailItem{
	private String gid;
	private Integer number;
	private Double price;
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OrderDetailItem{" +
				"gid='" + gid + '\'' +
				", number=" + number +
				", price=" + price +
				'}';
	}
}