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
        if (listCategroy != null && !listCategroy.isEmpty()) {
            return listCategroy;
        }
        return null;
    }

    /*
     * 查询指定数目的条数，如果为all则查询随机数据，否则查询指定类别的数据
     */
    @Override
    public List<Goods> queryGoods(String categroy, Integer startNumber, Integer number) {
        List<Goods> listGoods = new ArrayList<Goods>();

        if ("all".equals(categroy)) {
            listGoods = goodsDao.queryLimit(startNumber, number);
        } else {
            listGoods = goodsDao.queryGoodsByCategroy(categroy, startNumber, number);
        }
        if (listGoods != null && !listGoods.isEmpty()) {
            return listGoods;
        }
        return null;
    }

    public Integer queryRowsNumber(String categroy) {
        Integer row = goodsDao.queryGoodsNumberByCategroy(categroy);
        if (row != null && row > 0) {
            return row;
        }
        return 0;
    }

    @Override
    public List<Goods> queryGoodsByName(String goodsName, Integer startNumber, Integer number) {
        List<Goods> listGoods = goodsDao.queryGoodsByName(goodsName, startNumber, number);
        if (listGoods != null && !listGoods.isEmpty()) {
            return listGoods;
        }
        return null;
    }

    @Override
    public Goods queryGoodsById(Integer goodsId) {
        Goods goods = goodsDao.queryGoodsById(goodsId);
        goods.setComments(goodsDao.queryGoodsComments(goodsId));
        goods.setImgUrls(goodsDao.queryGoodsImgs(goodsId));
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

}
