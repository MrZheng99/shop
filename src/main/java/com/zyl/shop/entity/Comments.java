package com.zyl.shop.entity;

import java.util.Date;

public class Comments {
	//评论人
private String person;
	//细节
private String details;
//时间
private Date date;
public String getPerson() {
	return person;
}
public void setPerson(String person) {
	this.person = person;
}
public String getDetails() {
	return details;
}
public void setDetails(String details) {
	this.details = details;
}
public Date getDateTime() {
	return date;
}
public void setDateTime(Date date) {
	this.date= date;
}
}
