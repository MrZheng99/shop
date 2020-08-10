package com.zyl.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.zyl.shop.entity.Address;
import com.zyl.shop.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Transactional
public interface UserDao {
	@Results(id = "userResult", value = {
			  @Result(property = "id", column = "uid", id = true),
			@Result(property = "realName",column = "realname")
			})
	@Select("select uid,name from tb_userinfo where name = #{account} and password=#{password} and status=1;")
	User login(@Param("account")String account, @Param("password")String password);
	@Select("select uid from tb_userinfo where name = #{account} and status=1;")
	Integer queryByName(@Param("account")String account);
	@Select("select uid from tb_userinfo where name = #{account} and email = #{email} and status=1;")
	Integer queryByNameEmail(@Param("account")String account, @Param("email") String email);

	@Insert("insert into `tb_userinfo` (`name`, `email`, `password`, `status`) values (#{account}, #{email}, #{password}, '1')")
	Integer register(@Param("account")String account, @Param("password")String password, @Param("email") String email);

	/**
	 * 用户忘记密码设置
	 * @param account
	 * @param email
	 * @param password
	 * @return
	 */
	@Update("update tb_userinfo set `password`=#{password} where name = #{account} and email = #{email} and status=1;")
	Integer update(@Param("account")String account, @Param("email") String email, @Param("password")String password);

	/**
	 * 重置用户密码
	 * @param userId
	 * @param password
	 * @return
	 */
	@Update("update tb_userinfo set `password`=#{password} where uid = #{userId};")
	Integer updatePassword(@Param("userId")Integer userId,@Param("password")String password);

	/**
	 * 更改用户状态
	 * @param userId
	 * @param status
	 * @return
	 */
	@Update("update tb_userinfo set `status`=#{status} where uid = #{userId};")
	Integer updateStatus(@Param("userId")Integer userId,  @Param("status")String status);

	@Select("select aid, address, uid, status,flag from tb_address where uid=#{uid} and status=1")
	List<Address> getUserAddresses(@Param("uid")int uid);
	
	@Select("select uid,name from tb_userinfo where uid=#{uid} and status=1;")
	User getUserById(@Param("uid")int uid);
	@ResultMap("userResult")
	@Select("select uid,name,realname,tel,email,status from tb_userinfo;")
	List<User> queryAllUser();
	@ResultMap("userResult")
	@Select("select uid,name,tel,email,status from tb_userinfo where email=#{email} and name=#{account};")
	List<User> queryAllUserByNameEmail(@Param("account")String account, @Param("email") String email);
	@ResultMap("userResult")
	@Select("select uid,name,tel,email,realname,sex,status from tb_userinfo where uid=#{userId};")
    User queryAllUserById(Integer userId);
	@Update("update tb_userinfo set `email`=#{user.email},`sex`=#{user.sex},`tel`=#{user.tel},`realname`=#{user.realName} where uid = #{user.id};")
    Integer updateUserInfo(@Param("user")User user);
    @Update("update `tb_address` set `status`=#{address.status} where uid=#{address.uid} and aid = #{address.aid};")
    Integer updateUserAddress(@Param("address")Address address);
    @Insert("insert into `tb_address` (`address`,`uid`,`status`,`flag`) values (#{address.address}, #{address.uid}, '1','0')")
    Integer addUserAddress(@Param("address")Address address);

	@Update("update `tb_address` set `flag`=( case when `aid`=#{address.aid} then 1 else 0 end) where uid=#{address.uid} and status=1;")
	//@Options(useGeneratedKeys = true)
	Integer updateUserAddressDefault(@Param("address")Address address);
}
