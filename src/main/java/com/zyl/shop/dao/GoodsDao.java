package com.zyl.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zyl.shop.entity.Goods;

@Mapper
public interface GoodsDao {
	
	@Select("select typename from tb_goodstype where status=1")
	List<String> queryCategroy();
	@Results(id = "goodsResult", value = {
			  @Result(property = "id", column = "gid", id = true),
			  @Result(property = "name", column = "goodsname"),
			  @Result(property = "price", column = "goodsprice"),
			  @Result(property = "description", column = "goodsdescription"),
			  @Result(property = "imgUrl", column = "url")
			})
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,t.url from tb_goodsinfo g LEFT JOIN tb_goodsimages t on t.giid=g.gid and t.status=1 and g.status=1 order by RAND() LIMIT #{startNumber},#{number}")
	List<Goods> queryLimit(@Param("startNumber")Integer startNumber,@Param("number") Integer number);
	@ResultMap("goodsResult")
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,t.url from tb_goodsinfo g,tb_goodstype s,tb_goodsimages t where s.typename=#{categroy} and s.gtid=g.goodstype and t.gid=g.gid and t.status=1 and g.status=1 and s.status=1 LIMIT #{startNumber},#{number}")
	List<Goods> queryGoodsByCategroy(@Param("categroy")String categroy,@Param("startNumber") Integer startNumber,@Param("number") Integer number);
	@Select("select count(*) from tb_goodsinfo g,tb_goodstype s where s.typename=#{categroy} and s.gtid=g.goodstype and g.status=1 and s.status=1")
	Integer queryGoodsNumberByCategroy(String categroy);
	@ResultMap("goodsResult")
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,t.url from tb_goodsinfo g,tb_goodsimages t where g.goodsname=#{goodsName} and t.gid=g.gid and t.status=1 and g.status=1 LIMIT #{startNumber},#{number}")
	List<Goods> queryGoodsByName(@Param("goodsName")String goodsName, @Param("startNumber")Integer startNumber, @Param("number")Integer number);
	
}
