package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.AddressItem;

public interface UserService {
	 Integer login(String account,String Password);
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
}
