package com.zyl.shop.entity;

public class LoginResponse {
	private Boolean success;
	private Integer userID;
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	@Override
	public String toString() {
		return "LoginResponse [success=" + success + ", userID=" + userID + "]";
	}

	public LoginResponse(Boolean success, Integer userID) {
		super();
		this.success = success;
		this.userID = userID;
	}
}
