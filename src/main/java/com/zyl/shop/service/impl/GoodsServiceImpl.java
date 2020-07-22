package com.zyl.shop.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zyl.shop.entity.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.zyl.shop.dao.GoodsDao;
import com.zyl.shop.entity.Goods;
import com.zyl.shop.entity.GoodsImage;
import com.zyl.shop.entity.GoodsInfo;
import com.zyl.shop.service.GoodsService;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 查询所有类别
	 */
	@Override
	public List<String> queryCategroy() {
		List<String> listCategroy = goodsDao.queryCategroy();
		if (listCategroy != null && !listCategroy.isEmpty()) {
			return listCategroy;
		}
		return null;
	}

	/*
	 * 查询指定数目的条数，如果为hot则查询热卖，否则查询指定类别的数据
	 */
	@Override
	public ResponseJson queryGoodsByType(String categroy, Integer pageNum) {
		List<Goods> listGoods = null;
		int startNumber=0;
		int number=8;
		if(pageNum!=null) {
			startNumber = pageNum*8;
		}
		if ("hot".equals(categroy)) {
			listGoods = goodsDao.queryHotGoods(startNumber, number);}
		else{
			listGoods = goodsDao.queryGoodsByCategroy(categroy, startNumber, number);
		}
		if (listGoods == null || listGoods.isEmpty()) {
			return new ResponseJson(false,"数据获取失败",null);
		}
		return new ResponseJson(true,"数据获取成功",listGoods);
	}
	@Override
	public Integer queryRowsNumber(String categroy,String option) {
		Integer row=null;
		if("name".equals(option)){
			row = goodsDao.queryGoodsNumberByName(categroy);
		}else if("type".equals(option)){
			if ("hot".equals(categroy)) {
				row = goodsDao.queryHotGoodsNum();
			} else{
				row = goodsDao.queryGoodsNumberByCategroy(categroy);
			}
		}

		if (row != null && row > 0) {
			return row;
		}
		return 0;
	}

	@Override
	public ResponseJson queryGoodsByName(String goodsName, Integer pageNum) {
		List<Goods> listGoods = null;
		if(pageNum==null) {
			listGoods=goodsDao.queryGoodsByName(goodsName,0,8);
		}else {
			listGoods=goodsDao.queryGoodsByName(goodsName,pageNum*8,8);
		}
		if(listGoods==null) {
			return new ResponseJson(false,"暂无结果",null);
		}
		return new ResponseJson(true,"数据获取成功",listGoods);
	}

	@Override
	public Goods queryGoodsById(Integer goodsId) {
		Goods goods = goodsDao.queryGoodsById(goodsId);
		goods.setComments(goodsDao.queryGoodsComments(goodsId));
		return goods;
	}

	/**
	 * 存在的话就返回0，不存在就添加，添加成功就返回行数，否则返回-1；
	 * @param userID
	 * @param goodsId
	 * @return
	 */
	@Override
	public Integer insert2ShoppingCart(Integer userID, Integer goodsId) {
		System.out.println(userID+":"+goodsId);
		Integer number = goodsDao.queryGoodsShoppingCart(userID, goodsId);
		if(number==null||number<=0){
			Integer row = goodsDao.insert2ShoppingCart(userID, goodsId);
			if(row>0){
				return row;
			}
			return -1;
		}
		return 0;

	}

	@Override
	public void addGood(MultipartFile[] files, int tid, String gname, double price, int balance,String intro, String descr) throws IOException {
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setGid(0);
		goodsInfo.setGoodsname(gname);
		goodsInfo.setGoodsprice(price);
		goodsInfo.setGoodstype(tid);
		goodsInfo.setStore(balance);
		goodsInfo.setGoodsdescription(descr);
		goodsInfo.setStatus("1");
		goodsDao.addGood(goodsInfo);
		for(MultipartFile file : files) {
			this.addImage(file, goodsInfo.getGid());
		}
	}

	private void addImage(MultipartFile file, int gid) throws IOException{
		GoodsImage goodsImage = new GoodsImage();
		goodsImage.setGiid(0);
		goodsImage.setGid(gid);
		String fileName = ""+gid+"_"+System.currentTimeMillis()+"."+file.getOriginalFilename().split("\\.", 2)[1];
		goodsImage.setUrl("/images/goods/"+fileName);
		goodsImage.setStatus("1");
		goodsDao.addGoodImage(goodsImage);

		File dest = new File(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/images/goods"),
				fileName);

		file.transferTo(dest);
	}

	@Override
	public List<GoodsInfo> searchGoodByTypeAndName(Integer tid, String gname) {
		if(gname == null) {
			gname = "";
		}
		return goodsDao.searchGoodByTypeAndName(tid, "%"+gname+"%");
	}

	@Override
	public void updateGoodStatus(int goodId, String status) {
		goodsDao.updateGoodStatus(goodId, status);

	}

}
