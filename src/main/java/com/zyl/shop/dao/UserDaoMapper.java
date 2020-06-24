package com.zyl.shop.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


import com.zyl.shop.entity.User;
@Mapper
public interface UserDaoMapper {
	@Results(id = "userResult", value = {
			  @Result(property = "id", column = "uid", id = true)
			})
	@Select("select uid from tb_userinfo where name = #{account} and password=#{password} and status=1;")
	User login(@Param("account")String account, @Param("password")String password);
	@Select("select uid from tb_userinfo where name = #{account} and status=1;")
	Integer queryByName(@Param("account")String account);
	@Insert("INSERT INTO `tb_userinfo` (`name`, `email`, `password`, `status`) VALUES (#{account}, #{email}, #{password}, '1')")
	Integer register(@Param("account")String account, @Param("password")String password, @Param("email") String email);
}
