package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.GoodsTypeItem;

public interface GoodsTypeService {

	/**
	 * 获取所有商品类型
	 * @return 商品类型前端实体类列表
	 */
	List<GoodsTypeItem> getTypes();

	/**
	 * 修改商品类型状态
	 * @param tid 商品类型id
	 * @param status 状态
	 */
	void updateTypeStatus(int tid, String status);

	/**
	 * 添加商品类型
	 * @param tname 商品类型名
	 */
	void addType(String tname);
	
}
