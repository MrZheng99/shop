package com.zyl.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zyl.shop.entity.Order;
import com.zyl.shop.entity.OrderDetail;


@Mapper
public interface OrderDao {
	@Select("SELECT * FROM tb_orderinfo WHERE oid=#{oid}")
	Order getOrderById(@Param("oid")String oid);

	@Select("SELECT * FROM tb_orderdetails WHERE oid=#{oid}")
	List<OrderDetail> getOrderDetails(@Param("oid")String oid);

	@Update("UPDATE tb_orderinfo SET orderprogress=#{progress} WHERE oid=#{oid}")
	void updateOrderProgress(@Param("oid")String oid, @Param("progress")String progress);
	
	@Update("UPDATE tb_goodsinfo SET store=store-#{number} where gid=#{gid}")
	void decreaseStore(@Param("gid")int gid, @Param("number")int number);

	@Select("SELECT * FROM tb_orderinfo where uid=#{uid} order by date desc")
	List<Order> getOrderByUserId(@Param("uid")int uid);

	@Update("UPDATE tb_orderinfo SET aid=#{aid} WHERE oid=#{oid}")
	void setAddress(@Param("oid")String orderId, @Param("aid")String aid);
	
	@Insert("INSERT INTO tb_orderinfo(oid,uid, date, orderprogress, amount, status) VALUES(#{oid},#{uid}, #{date}, #{orderprogress}, #{amount}, 1)")
	@Options(useGeneratedKeys = true, keyProperty = "oid")
	void addOrder(Order order);

	@Insert("INSERT INTO tb_orderdetails(price, number, gid, oid, status) VALUES(#{price}, #{number}, #{gid}, #{oid}, 1)")
	@Options(useGeneratedKeys = true, keyProperty = "odid")
	void addOrderDetail(OrderDetail detail);
}
