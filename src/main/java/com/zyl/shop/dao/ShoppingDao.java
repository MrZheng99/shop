package com.zyl.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zyl.shop.entity.Goods;
@Mapper
public interface ShoppingDao {
	
	@Results(id = "goodsResult", value = {
			  @Result(property = "id", column = "gid", id = true),
			  @Result(property = "name", column = "goodsname"),
			  @Result(property = "price", column = "goodsprice"),
			  @Result(property = "description", column = "goodsdescription"),
			  @Result(property = "imgUrl", column = "url"),
			  @Result(property = "store", column = "store")
			})
	@Select("select i.gid,i.goodsname,i.goodsprice,i.store,i.goodsdescription,img.url from tb_goodsinfo i,tb_goodsimages img where gid=#{gid} and i.status=1 and i.tid=img.tid;")
	List<Goods> queryGoodsById(@Param("gid")Integer gid);
	@Insert("insert into `tb_shoppingcart` (`uid`, `gid`) values (#{uid}, #{gid},'1')")
    Goods cart(@Param("uid")Integer uid,@Param("gid")Integer gid);
}
