package com.zyl.shop.service;

import java.util.List;

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
	 List<AddressItem> getAddresses(int userId);
	 /**
	  * 根据用户编号获取用户名
	  * @param userId 用户编号
	  * @return 用户名
	  */
	String getUserNameById(Integer userId);
}
