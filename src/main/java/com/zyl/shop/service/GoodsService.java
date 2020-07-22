package com.zyl.shop.service;

import java.io.IOException;
import java.util.List;

import com.zyl.shop.entity.ResponseJson;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.zyl.shop.entity.Goods;
import com.zyl.shop.entity.GoodsInfo;

import javax.servlet.http.HttpSession;

public interface GoodsService {
	List<String> queryCategroy(); 
	Integer queryRowsNumber(String type,String option);
	ResponseJson queryGoodsByType(String type, Integer pageNum);
	ResponseJson queryGoodsByName(String goodsName, Integer pageNum) ;
	Goods queryGoodsById(Integer goodsId);

	/**
	 * 将商品添加到购物车
	 * @param userID
	 * @param goodsId
	 * @return
	 */
	Integer insert2ShoppingCart(Integer userID,Integer goodsId);
	
	/**
	 * 添加商品
	 * @param file
	 * @param tid
	 * @param gname
	 * @param price
	 * @param balance
	 * @param intro
	 * @param descr
	 * @return
	 */
	void addGood(MultipartFile[] file, int tid, String gname, double price, int balance,String intro, String descr) throws IOException;
	
	/**
	 * 模糊搜索指定类别下的商品
	 * @param tid 类别ID, 为null时返回所有类别
	 * @param gname 商品名，为null时返回所有商品
	 * @return
	 */
	List<GoodsInfo> searchGoodByTypeAndName(Integer tid, String gname);
	/**
	 * 修改商品状态，上架下架
	 * @param goodId 商品编号
	 * @param status 1上架, 0下架
	 */
	void updateGoodStatus(int goodId, String status);
}
