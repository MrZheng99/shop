package com.zyl.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyl.shop.dao.GoodsTypeDao;
import com.zyl.shop.entity.GoodsType;
import com.zyl.shop.entity.GoodsTypeItem;
import com.zyl.shop.service.GoodsTypeService;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {

	
	@Autowired
	private GoodsTypeDao goodsTypeDao;
	@Override
	public List<GoodsTypeItem> getTypes() {
		List<GoodsType> allTypes = goodsTypeDao.getAllTypes();
		List<GoodsTypeItem> items = new ArrayList<>();
		for(GoodsType type : allTypes) {
			GoodsTypeItem item = new GoodsTypeItem();
			item.setGtid(type.getGtid());
			item.setTypename(type.getTypename());
			item.setStatus(type.getStatus());
			items.add(item);
		}
		return items;
	}
	

	@Override
	public void updateTypeStatus(int tid, String status) {
		goodsTypeDao.updateStatus(tid, status);
	}


	@Override
	public void addType(String tname) {
		goodsTypeDao.addType(tname);
	}
}
