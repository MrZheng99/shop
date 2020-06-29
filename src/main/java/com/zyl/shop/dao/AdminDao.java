package com.zyl.shop.dao;

import com.zyl.shop.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminDao {
    @Results(id = "adminResult", value = {
            @Result(property = "id", column = "aid", id = true),
            @Result(property = "name", column = "name")
    })
    @Select("select aid,name from tb_admininfo where name = #{account} and password=#{password} and status=1;")
    Admin login(String account,String password);
}
