package com.zyl.shop.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zyl.shop.entity.User;
@Mapper
public interface UserDao {
	@Results(id = "userResult", value = {
			  @Result(property = "id", column = "uid", id = true),
			  @Result(property = "name", column = "name")
			})
	@Select("select uid,name from tb_userinfo where name = #{account} and password=#{password} and status=1;")
	User login(@Param("account")String account, @Param("password")String password);
	@Select("select uid from tb_userinfo where name = #{account} and status=1;")
	Integer queryByName(@Param("account")String account);
	@Select("select uid from tb_userinfo where name = #{account} and email = #{email} and status=1;")
	Integer queryByNameEmail(@Param("account")String account, @Param("email") String email);

	@Insert("insert into `tb_userinfo` (`name`, `email`, `password`, `status`) values (#{account}, #{email}, #{password}, '1')")
	Integer register(@Param("account")String account, @Param("password")String password, @Param("email") String email);
	
	@Update("update tb_userinfo set `password`=#{password} where name = #{account} and email = #{email} and status=1;")
	Integer update(@Param("account")String account, @Param("email") String email, @Param("password")String password);
}
