package com.zyl.shop.entity;

public class AddressItem {
	private String aid;
	private String address;
	private String flag;

	@Override
	public String toString() {
		return "AddressItem{" +
				"aid='" + aid + '\'' +
				", address='" + address + '\'' +
				", flag='" + flag + '\'' +
				'}';
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
