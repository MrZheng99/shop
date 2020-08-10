package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.entity.User;
import com.zyl.shop.entity.AddressItem;

public interface UserService {
	 User login(String account,String Password);
	 Boolean register(String account, String password, String email);
	
	 Boolean existAccount(String account);

	 Boolean existAccount(String account, String email);
	 Boolean updatePassword(String account, String password, String email);

	 /**
	  * 获取用户的地址列表
	  * @param userId 用户编号
	  * @return 用户地址前端实体类列表
	  */
	 ResponseJson getAddresses(int userId);
	 /**
	  * 根据用户编号获取用户名
	  * @param userId 用户编号
	  * @return 用户名
	  */
	String getUserNameById(Integer userId);

	/**
	 * 冻结或者解冻用户或者重置密码
	 * @param userId
	 * @param data 密码或者状态值
	 * @param type 重置密码或者解冻冻结账户区分
	 * @return
	 */
    ResponseJson updateUser(Integer userId, String data,String type);

	/**
	 * 获取用户
	 * @param userName
	 * @param email
	 * @return
	 */
	ResponseJson getUserByNameEmail(String userName, String email);

	/**
	 * 获取用户byId
	 * @param userId
	 * @return
	 */
    User getUserById(Integer userId);

	/**
	 * 修改用户信息
	 * @param userId
	 * @param realName
	 * @param tel
	 * @param email
	 * @param sex
	 * @return
	 */
	Integer updateUserInfo(Integer userId,String realName,String tel,String email,String sex);

    /**
     * 删除用户地址
     * @param userId
     * @param aid
     * @return
     */
    Integer updateUserAddress(Integer userId, Integer aid);

	/**
	 * 添加用户地址
	 * @param userId
	 * @param address
	 * @return
	 */
	Integer addUserAddress(Integer userId, String address);

	Integer updateUserAddressDefault(Integer userId, Integer aid);
}
