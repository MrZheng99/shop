package com.zyl.shop.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zyl.shop.entity.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GoodsDao {
	/**
	 * 查询商品类别
	 * @return
	 */
	@Select("select typename from tb_goodstype where status=1")
	List<String> queryCategroy();
	@Select("select gid,goodsname from tb_goodsinfo where status=1")
	List<Map<String, Object>> searchGoodNameAndId();

	/**
	 * 所有商品
	 * @return
	 */
	@ResultMap("goodsResult")
	@Select("select * from tb_goodsinfo g where g.status=1")
	List<Goods> queryAllGoods();

	/**
	 * 根据商品类别分页查询商品
	 * @param categroy
	 * @param startNumber
	 * @param number
	 * @return
	 */
	@Results(id = "goodsResult", value = {
			@Result(property = "id", column = "gid", id = true),
			@Result(property = "name", column = "goodsname"),
			@Result(property = "price", column = "goodsprice"),
			@Result(property = "description", column = "goodsdescription"),
			@Result(property = "images", column = "images")
	})
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,g.images from tb_goodsinfo g,tb_goodstype s where s.typename=#{categroy} and s.gtid=g.goodstype and g.status=1 and s.status=1 LIMIT #{startNumber},#{number}")
	//@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,t.url from tb_goodsinfo g,tb_goodstype s,tb_goodsimages t where s.typename=#{categroy} and s.gtid=g.goodstype and t.gid=g.gid and t.status=1 and g.status=1 and s.status=1 LIMIT #{startNumber},#{number}")
	List<Goods> queryGoodsByCategroy(@Param("categroy")String categroy,@Param("startNumber") Integer startNumber,@Param("number") Integer number);

	/**
	 * 查询热门商品
	 * @param startNumber
	 * @param number
	 * @return
	 */
	@ResultMap("goodsResult")
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,g.images from tb_goodsinfo g,tb_goodstype s where g.hot=1 and s.gtid=g.goodstype and g.status=1 and s.status=1 LIMIT #{startNumber},#{number}")
	List<Goods> queryHotGoods(@Param("startNumber") Integer startNumber,@Param("number") Integer number);

	/**
	 * 根据商品名称查询商品
	 * @param goodsName
	 * @param startNumber
	 * @param number
	 * @return
	 */
	@ResultMap("goodsResult")
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,g.images from tb_goodsinfo g where g.goodsname like concat('%',#{goodsName},'%') and g.status=1 LIMIT #{startNumber},#{number}")
	List<Goods> queryGoodsByName(@Param("goodsName")String goodsName, @Param("startNumber")Integer startNumber, @Param("number")Integer number);

	/**
	 * 根据商品id查询商品
	 * @param goodsId
	 * @return
	 */

	@Results( value = {
			@Result(property = "store", column = "store"),
	})
	@ResultMap("goodsResult")
	@Select("select g.gid,g.goodsname,g.goodsprice,g.goodsdescription,g.store,g.images from tb_goodsinfo g where g.gid=#{goodsId} and g.status=1")
	Goods queryGoodsById(@Param("goodsId")Integer goodsId);
	/**
	 * 查询某类别商品的数量
	 * @param categroy
	 * @return
	 */
	@Select("select count(g.gid) from tb_goodsinfo g,tb_goodstype s where s.typename=#{categroy} and s.gtid=g.goodstype and g.status=1 and s.status=1")
	Integer queryGoodsNumberByCategroy(String categroy);

	/**
	 * 模糊查询该名称的商品数量
	 * @param name
	 * @return
	 */
	@Select("select count(g.gid) from tb_goodsinfo g,tb_goodstype s where g.goodsname like concat('%',#{name},'%') and s.gtid=g.goodstype and g.status=1 and s.status=1")
	Integer queryGoodsNumberByName(String name);
	/**
	 * 查询热卖商品的数量
	 * @return
	 */
	@Select("select count(gid) from tb_goodsinfo where hot=1 and status=1")
	Integer queryHotGoodsNum();

	/**
	 * 查询商品评论
	 * @param goodsId
	 * @return
	 */
	@Results(value= {
			@Result(property = "person", column = "username"),
			@Result(property = "details", column = "assessiondetails"),
			@Result(property = "date", column = "date")
	})
	@Select("select u.name username,a.assessiondetails,a.date from tb_goodsassession a,tb_userinfo u where u.uid=a.uid and a.gid=#{goodsId} and a.status=1 and u.status=1")
	List<Comments> queryGoodsComments(@Param("goodsId")Integer goodsId);

	/**
	 * 查询订单的评论
	 * @param oid
	 * @param uid
	 * @return
	 */
	@Select("select assessiondetails from tb_goodsassession where oid=#{oid} and uid=#{uid} and status=1 limit 0,1")
	String queryGoodsCommentsByOId(@Param("oid")Integer oid,@Param("uid")Integer uid);
	/**
	 * 查询商品图片
	 * @param goodsId
	 * @return
	 */
	@Select("select t.url from tb_goodsimages t where t.gid=#{goodsId} and t.status=1")
	List<String> queryGoodsImgs(Integer goodsId);
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
	 * 添加商品
	 * @param goodsInfo
	 */
	@Insert("insert into `tb_goodsinfo`(`gid`, `goodsname`, `goodsprice`, `store`, `status`, `goodsdescription`, `images`,`goodstype`) values(#{gid}, #{goodsname}, #{goodsprice}, #{store}, #{status}, #{goodsdescription},#{images}, #{goodstype})")
	@Options(useGeneratedKeys=true, keyProperty="gid")
	void addGood(GoodsInfo goodsInfo);
	@Insert("UPDATE `tb_goodsinfo` SET `goodsname`=#{goodsname}, `goodsprice`=#{goodsprice}, `store`= #{store}, `goodsdescription`=#{goodsdescription},`images`=if(''=#{images},images,concat(images,',',#{images})), `goodstype`=#{goodstype} WHERE (`gid`=#{gid})")
	void updateGood(GoodsInfo goodsInfo);
	/**
	 * 添加商品图片
	 * @param goodsImage
	 */
	@Insert("insert into `tb_goodsimages`(giid, url, gid, status) values(#{giid}, #{url}, #{gid}, #{status})")
	@Options(useGeneratedKeys=true, keyProperty="giid")
	void addGoodImage(GoodsImage goodsImage);

	/**
	 * 搜索指定类别下的符合条件的商品
	 * @param gtid 类别ID
	 * @param gname like字符串
	 * @return
	 */
	@Select("select * from tb_goodsinfo where (isnull(#{gtid}) or goodstype=#{gtid}) and goodsname like #{gname} order by store")
	List<GoodsInfo> searchGoodByTypeAndName(@Param("gtid")Integer gtid, @Param("gname")String gname);
	@Select("select gid,goodsname from tb_goodsinfo where (isnull(#{gtid}) or goodstype=#{gtid}) and goodsname like #{gname} and hot=1 and status=1")
	List<GoodsInfo> searchHotGoodByTypeAndName(@Param("gtid")Integer gtid, @Param("gname")String gname);

	/**
	 * 上下架商品
	 * @param goodId
	 * @param status
	 */
	@Update("update tb_goodsinfo set status=#{status} where gid=#{gid}")
	Integer updateGoodStatus(@Param("gid")int goodId, @Param("status")String status);

	/**
	 * 上下架热卖
	 * @param goodId
	 * @param hot
	 */
	@Update("update tb_goodsinfo set hot=#{hot} where gid=#{gid}")
	Integer updateGoodHotStatus(@Param("gid")int goodId, @Param("hot")String hot);

	/**
	 * 添加评论
	 * @param gid
	 */
	@Insert("INSERT INTO `tb_goodsassession` (`uid`, `gid`,`oid`, `assessiondetails`, `date`, `status`) VALUES (#{commentsItem.uid}, #{gid},#{commentsItem.oid}, #{commentsItem.comments}, now(), '1')")
	void insertGoodsComments(@Param("gid")Integer gid,@Param("commentsItem")AddCommentsItem commentsItem);

	/**
	 * 更新评论
	 * @param gid
	 */
	@Update("UPDATE `tb_goodsassession` SET `assessiondetails`=#{commentsItem.comments}, `date`=now() WHERE `gid`=#{gid} and `uid`=#{commentsItem.uid} and `oid`=#{commentsItem.oid} and `status`=1")
	void updateGoodsComments(@Param("gid") Integer gid,@Param("commentsItem")AddCommentsItem commentsItem);


	/**
	 * 不指定时间和类别,默认按年按类
	 * @return
	 */
	@Results(
			id = "sale",
			value ={
					@Result(property = "type",column = "typename")}
	)
	@Select("select *from (select sum(od.number*g.goodsprice) as amount,t.typename,sum(od.number) as sales from `tb_goodsinfo` g ,`tb_goodstype` t, `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and g.goodstype=t.gtid and o.orderprogress!='未付款' and " +
			" YEAR(`date`) = YEAR(now()) GROUP BY typename) s order by amount desc;")
    List<Sale> queryGoodsSalesTypes();
	/**
	 * 指定时间，不指定类别
	 * @return
	 * @param item
	 */
	@ResultMap("sale")
	@Select("select *from (select sum(od.number*g.goodsprice) as amount,t.typename,sum(od.number) as sales from `tb_goodsinfo` g ,`tb_goodstype` t, `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and g.goodstype=t.gtid and o.orderprogress!='未付款' and " +
			"(ISNULL(#{item.d}) or DAY(`date`) = #{item.d}) and (ISNULL(#{item.m}) or MONTH(`date`) = #{item.m}) and YEAR(`date`) = #{item.y} GROUP BY typename) s order by amount desc;")
	List<Sale> queryGoodsSalesTypesDate(@Param("item")SaleRequsetItem item);
	/**
	 * 指定类别，不指定时间
	 * @return
	 * @param item
	 */
	@ResultMap("sale")
	@Select("select g.goodsname,t.typename ,od.number as sales,od.number*g.goodsprice as amount,YEAR(`date`) y,MONTH(`date`) m ,DAY(`date`) d from `tb_goodsinfo` g,`tb_goodstype` t , `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and t.gtid=g.goodstype and o.orderprogress!='未付款' and " +
			"g.goodstype = #{item.gtid} ORDER BY amount desc;")
	List<Sale> queryGoodsSalesByType(@Param("item")SaleRequsetItem item);
	/**
	 * 指定类别，指定时间
	 * @return
	 * @param item
	 */
	@ResultMap("sale")
	@Select("select g.goodsname,t.typename ,od.number as sales,od.number*g.goodsprice as amount,YEAR(`date`) y,MONTH(`date`) m ,DAY(`date`) d from `tb_goodsinfo` g,`tb_goodstype` t , `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and t.gtid=g.goodstype and o.orderprogress!='未付款' and " +
			"(ISNULL(#{item.d}) or DAY(`date`) = #{item.d}) and (ISNULL(#{item.m}) or MONTH(`date`) = #{item.m}) and YEAR(`date`) = #{item.y} and g.goodstype = #{item.gtid} ORDER BY amount desc;")
//	@Select("select *from (select sum(od.number*g.goodsprice) as amount,t.typename,sum(od.number) as sales from `tb_goodsinfo` g ,`tb_goodstype` t, `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od\n" +
//			"on o.oid=od.oid " +
//			"where g.gid = od.gid and g.goodstype=t.gtid and o.orderprogress!='未付款' and g.goodstype=#{item.gtid} and " +
//			"(ISNULL(#{item.d}) or DAY(`date`) = #{item.d}) and (ISNULL(#{item.m}) or MONTH(`date`) = #{item.m}) and YEAR(`date`) = #{item.y} GROUP BY typename) s order by amount desc;")
	List<Sale> queryGoodsSalesByTypeDate(@Param("item")SaleRequsetItem item);
	/**
	 * 不指定时间，不指定商品
	 * @return
	 * @param item
	 */
	@ResultMap("sale")
	@Select("select *from(select g.goodsname,sum(od.number) as sales,sum(od.number*g.goodsprice) as amount from `tb_goodsinfo` g ,`tb_goodstype` t, `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and g.goodstype=t.gtid and o.orderprogress!='未付款' " +
			"GROUP BY goodsname ) s order by amount desc;")
	List<Sale> queryGoodsSalesGoods(@Param("item")SaleRequsetItem item);
	/**
	 * 指定时间，不指定商品
	 * @return
	 * @param item
	 */
	@ResultMap("sale")
	@Select("select *from(select g.goodsname,sum(od.number) as sales,sum(od.number*g.goodsprice) as amount from `tb_goodsinfo` g ,`tb_goodstype` t, `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and g.goodstype=t.gtid and o.orderprogress!='未付款' and " +
			"(ISNULL(#{item.d}) or DAY(`date`) = #{item.d}) and (ISNULL(#{item.m}) or MONTH(`date`) = #{item.m}) and YEAR(`date`) = #{item.y} GROUP BY goodsname ) s order by amount desc;")
	List<Sale> queryGoodsSalesGoodsDate(@Param("item")SaleRequsetItem item);
	/**
	 * 指定商品，不指定时间
	 * @return
	 * @param item
	 */
	@ResultMap("sale")
	@Select("select g.goodsname,sum(od.number) as sales,sum(od.number*g.goodsprice) as amount,YEAR(`date`) y,MONTH(`date`) m ,DAY(`date`) d from `tb_goodsinfo` g ,`tb_goodstype` t, `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and g.goodstype=t.gtid and o.orderprogress!='未付款' and " +
			"g.gid=#{item.gid} GROUP BY goodsname,y,m,d ")
	List<Sale> queryGoodsSalesByGid(@Param("item")SaleRequsetItem item);
	/**
	 * 指定商品，指定时间
	 * @return
	 * @param item
	 */
	@ResultMap("sale")
	@Select("select g.goodsname,sum(od.number) as sales,sum(od.number*g.goodsprice) as amount,YEAR(`date`) y,MONTH(`date`) m ,DAY(`date`) d from `tb_goodsinfo` g ,`tb_goodstype` t, `tb_orderinfo` o LEFT JOIN `tb_orderdetails` od " +
			"on o.oid=od.oid " +
			"where g.gid = od.gid and g.goodstype=t.gtid and o.orderprogress!='未付款' and " +
			"g.gid=#{item.gid} and YEAR(`date`) = #{item.y} and (ISNULL(#{item.d}) or DAY(`date`) = #{item.d}) and (ISNULL(#{item.m}) or MONTH(`date`) = #{item.m}) " +
			"GROUP BY goodsname,y,m,d;")
	List<Sale> queryGoodsSalesByGidDate(@Param("item") SaleRequsetItem item);



}
