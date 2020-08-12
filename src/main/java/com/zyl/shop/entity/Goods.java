package com.zyl.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Goods {
	private Integer id;
	private Float price;
	private String name;
	private String description;
	private String images;
	private Integer store;
	private String type;
	private List<Comments> comments;
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images= images;
	}
	@Override
	public String toString() {
		return "Goods [id=" + id + ", price=" + price + ", description=" + description + ", images=" + images
				+ ", store=" + store + ", type=" + type + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}


}
