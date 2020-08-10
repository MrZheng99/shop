package com.zyl.shop.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyl.shop.entity.AddCommentsItem;
import com.zyl.shop.entity.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zyl.shop.dao.GoodsDao;
import com.zyl.shop.entity.Goods;
import com.zyl.shop.entity.GoodsInfo;
import com.zyl.shop.service.GoodsService;

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
		//System.out.println(userID+":"+goodsId);
		//Integer number = goodsDao.queryGoodsShoppingCart(userID, goodsId);
		//if(number==null||number<=0){
		Integer row = goodsDao.insert2ShoppingCart(userID, goodsId);
		if(row>0){
			return row;
		}
		return -1;
		//}
		//return 0;

	}

	@Override
	public void addGood(MultipartFile[] files, int tid, String gname, double price, int balance, String descr) throws IOException {
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setGid(0);
		goodsInfo.setGoodsname(gname);
		goodsInfo.setGoodsprice(price);
		goodsInfo.setGoodstype(tid);
		goodsInfo.setStore(balance);
		goodsInfo.setGoodsdescription(descr);
		goodsInfo.setStatus("1");
		List<String> listImage = new ArrayList<String>();
		for(MultipartFile file : files) {
			listImage.add(this.addImage(file));
		}
		goodsInfo.setImages(String.join(",",listImage));
		goodsDao.addGood(goodsInfo);

	}

	@Override
	public void updateGood(MultipartFile[] files,int gid, int tid, String gname, double price, int balance, String descr) throws IOException {
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setGid(gid);
		goodsInfo.setGoodsname(gname);
		goodsInfo.setGoodsprice(price);
		goodsInfo.setGoodstype(tid);
		goodsInfo.setStore(balance);
		goodsInfo.setGoodsdescription(descr);
		goodsInfo.setStatus("1");
		List<String> listImage = new ArrayList<String>();
		for(MultipartFile file : files) {
			listImage.add(this.addImage(file));
		}
		goodsInfo.setImages(String.join(",",listImage));
		goodsDao.updateGood(goodsInfo);

	}

	@Override
	public Map<String, Object> uploadImage(MultipartFile file) throws IOException {
		String fileName = "_"+System.currentTimeMillis()+"."+file.getOriginalFilename().split("\\.", 2)[1];

		File dest = new File(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/images/goods"),
				fileName);
		System.out.println(dest.getAbsolutePath());
		file.transferTo(dest);
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("fileName", fileName);
		rs.put("upload", "images\\goods\\" + fileName);
		return 	rs;
	}

	private String addImage(MultipartFile file) throws IOException {

		String fileName = "_"+System.currentTimeMillis()+"."+file.getOriginalFilename().split("\\.", 2)[1];

		File dest = new File(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/images/goods"),
				fileName);

		file.transferTo(dest);
		return 	"images/goods/"+fileName;

	}

	@Override
	public List<GoodsInfo> searchGoodByTypeAndName(Integer tid, String gname,Boolean hot) {
		if(gname == null) {
			gname = "";
		}
		if(hot){
			return goodsDao.searchHotGoodByTypeAndName(tid, "%"+gname+"%");
		}
		return goodsDao.searchGoodByTypeAndName(tid, "%"+gname+"%");
	}

	@Override
	public ResponseJson updateGoodStatus(int goodId, String status) {
		Integer row = goodsDao.updateGoodStatus(goodId, status);
		if(row!=null&&row>0){
			return new ResponseJson(true,"更新成功");
		}
		return new ResponseJson(false,"更新失败");

	}
	@Override
	public ResponseJson updateGoodHotStatus(int goodId, String hot) {
		Integer row = goodsDao.updateGoodHotStatus(goodId, hot);
		if(row!=null&&row>0){
			return new ResponseJson(true,"更新成功",goodsDao.updateGoodHotStatus(goodId, hot));
		}
		return new ResponseJson(false,"更新失败",-1);

	}
	@Override
	public ResponseJson getComments(Integer oid, Integer userId) {
		String comments = goodsDao.queryGoodsCommentsByOId(oid,userId);
		if(comments!=null&&comments!=""&&comments!="null"){
			return new ResponseJson(true,"获取成功",comments);
		}
		return new ResponseJson(false);
	}

	@Override
	public ResponseJson addComments(AddCommentsItem commentsItem) {
		try{
			if (commentsItem.getExist()) {
				for (Integer gid : commentsItem.getGoodsIds()) {
					goodsDao.updateGoodsComments(gid,commentsItem);
				}
			} else {
				for (Integer gid : commentsItem.getGoodsIds()) {
					goodsDao.insertGoodsComments(gid, commentsItem);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return new ResponseJson(false);

		}
		return new ResponseJson(true);
	}
}
