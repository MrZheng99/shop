package com.zyl.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyl.shop.dao.GoodsDao;
import com.zyl.shop.entity.Goods;
import com.zyl.shop.service.GoodsService;
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;
	/**
	 * 查询所有类别
	 */
	@Override
	public List<String> queryCategroy() {
		List<String> listCategroy = goodsDao.queryCategroy();
		if(listCategroy!=null && !listCategroy.isEmpty()) {
			return listCategroy;
		}
		return null;
	}
	/*
	 * 查询指定数目的条数，如果为all则查询随机数据，否则查询指定类别的数据
	 */
	@Override
	public List<Goods> queryGoods(String categroy,Integer startNumber,Integer number) {
		List<Goods> listgoods = new ArrayList<Goods>();
		
		if("all".equals(categroy)) {
			listgoods =goodsDao.queryLimit(startNumber,number);
		}else {
			listgoods = goodsDao.queryGoodsByCategroy(categroy,startNumber,number);
		}
		if(listgoods!=null&&!listgoods.isEmpty()) {
			return listgoods;
		}
		return null;
	}
	public Integer queryRowsNumber(String categroy) {
		Integer row = goodsDao.queryGoodsNumberByCategroy(categroy);
		if(row!=null&&row>0) {
			return row;
		}
		return 0;
	}

	@Override
	public List<Goods> queryGoodsByName(String goodsName,Integer startNumber,Integer number) {
		List<Goods> listgoods =  goodsDao.queryGoodsByName(goodsName,startNumber,number);
		if(listgoods!=null&&!listgoods.isEmpty()) {
			return listgoods;
		}
		return null;
	}

}
