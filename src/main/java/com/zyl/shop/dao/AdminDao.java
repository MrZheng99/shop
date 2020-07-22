package com.zyl.shop.dao;

import com.zyl.shop.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;



@Mapper
public interface AdminDao {
    @Results(id = "adminResult", value = {
            @Result(property = "id", column = "aid", id = true)
    })
    @Select("select aid,name from tb_admininfo where name = #{account} and password=#{password} and status=1;")
    Admin login(@Param("account")String account,@Param("password")String password);

    @ResultMap("adminResult")
    @Select("select aid,name,tel,status from tb_admininfo where aid!=#{mid};")
    List<Admin> queryAllAdmin(Integer mid);

    /**
     * 增加管理员
     * @param name
     * @param password
     * @param tel
     * @return
     */
    @Insert("insert into `tb_admininfo` (`name`, `tel`, `password`, `status`) values (#{name}, #{tel}, #{password}, '1')")
    Integer addAdmin(@Param("name")String name,@Param("password") String password,@Param("tel") String tel);

    /**
     * 解冻或者冻结管理员
     * @param aid
     * @param status
     * @return
     */
    @Update("update tb_admininfo set `status`=#{status} where aid = #{aid};")
    Integer update(@Param("aid")String aid,@Param("status")String status);

    @Update("update tb_admininfo set password=#{npwd} where aid = #{aid} and password=#{opwd} and status=1;")
    void changePassword(@Param("aid")int aid, @Param("opwd")String oldPassword, @Param("npwd")String newPassword);
    
    @Select("select * from tb_admininfo where aid=#{aid}")
    @ResultMap("adminResult")
    Admin getAdminById(@Param("aid")int aid);
}