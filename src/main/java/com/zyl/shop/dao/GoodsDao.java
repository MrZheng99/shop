package com.zyl.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.zyl.shop.entity.Comments;
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
	@Results( value = {
			  @Result(property = "id", column = "gid", id = true),
			  @Result(property = "name", column = "goodsname"),
			  @Result(property = "price", column = "goodsprice"),
			  @Result(property = "description", column = "goodsdescription"),
			  @Result(property = "store", column = "store")
			})
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,g.store from tb_goodsinfo g where g.gid=#{goodsId} and g.status=1")
	Goods queryGoodsById(@Param("goodsId")Integer goodsId);
	@Results(value= {
			@Result(property = "person", column = "username"),
			@Result(property = "details", column = "assessiondetails"),
			@Result(property = "date", column = "date")
	})
	@Select("select u.name username,a.assessiondetails,a.date from tb_goodsassession a,tb_userinfo u where u.uid=a.uid and a.gid=#{goodsId} and a.status=1 and u.status=1")
	List<Comments> queryGoodsComments(@Param("goodsId")Integer goodsId);

	/**
	 * 查询是否在用户的购物车里
	 * @param userID
	 * @param goodsId
	 * @return
	 */
	@Select("SELECT number from tb_shoppingcart WHERE gid=#{goodsId} and uid= #{userID} and status=1")
	Integer queryGoodsShoppingCart(@Param("userID")Integer userID,@Param("goodsId")Integer goodsId);
	@Insert("insert into `tb_shoppingcart` (`uid`, `gid`, `number`, `status`) values (#{userID}, #{goodsId}, '1', '1')")
    Integer insert2ShoppingCart(@Param("userID")Integer userID,@Param("goodsId")Integer goodsId);

	/**
	 * 查询商品图片
	 * @param goodsId
	 * @return
	 */
	@Select("select t.url from tb_goodsimages t where t.gid=#{goodsId} and t.status=1")
	List<String> queryGoodsImgs(Integer goodsId);
}
