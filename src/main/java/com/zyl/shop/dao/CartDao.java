package com.zyl.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.zyl.shop.entity.Cart;

@Mapper
public interface CartDao {
	@Select("select * from tb_shoppingcart sc where sc.status=1 and sc.uid=#{uid}")
	List<Cart> getUserGoodsGid(@Param("uid") Integer uid);

	@Insert("insert into tb_shoppingcart(scid, uid, gid, number, status) values(#{scid}, #{uid}, #{gid}, #{number}, #{status})")
	@Options(useGeneratedKeys=true, keyProperty="scid")
	void addGoodToUserCart(Cart cart);
	
	@Delete("delete from tb_shoppingcart where uid=#{uid} and gid=#{gid}")
	void deleteUserGood(@Param("uid")int userId, @Param("gid")int gid);
}
