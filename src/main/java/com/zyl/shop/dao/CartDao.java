package com.zyl.shop.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zyl.shop.entity.Cart;
import com.zyl.shop.entity.Goods;

@Mapper
public interface CartDao {
	@Results(id = "cartResult", value = {
			  @Result(property = "id", column = "gid", id = true)
			})
	@Select("select gid  from tb_userinfo u,tb_shoppingcart sc where u.uid=sc.uid and sc.status=1 and sc.uid=#{uid} ;")
	Cart search(@Param("uid") Integer uid);
	@Select("select  from tb_shoppingcart sc,tb_goodsimages img,tb_goodsinfo gi where sc.gid=gi.gid and gi.gid=img.gid and sc.status=1 and sc.gid=#{gid} ;")
    Goods querry(@Param("gid") Integer gid);
}
