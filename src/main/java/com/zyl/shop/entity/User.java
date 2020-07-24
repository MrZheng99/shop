package com.zyl.shop.entity;
/**
 * 用户信息实体类
 * @author ZJ
 *
 */
public class User {
	private Integer id;
	private String name;
	private String realName;

	private String tel;
	private String sex;
	private String email;
	private String status;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel= tel;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", tel='" + tel + '\'' +
				", sex='" + sex + '\'' +
				", email='" + email + '\'' +
				", status='" + status + '\'' +
				'}';
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
