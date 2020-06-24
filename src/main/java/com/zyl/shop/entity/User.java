package com.zyl.shop.entity;
/**
 * 用户信息实体类
 * @author ZJ
 *
 */
public class User {
	private Integer id;
	private String name;
	private String telNumber;
	private String sex;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", telNumber=" + telNumber + ", sex=" + sex + "]";
	}
	
}
