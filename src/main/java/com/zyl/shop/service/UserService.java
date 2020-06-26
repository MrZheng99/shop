package com.zyl.shop.service;

import java.util.List;

import com.zyl.shop.entity.User;

public interface UserService {
	 User login(String account,String Password);
	 Boolean register(String account, String password, String email);
	
	 Boolean existAccount(String account);

	 Boolean existAccount(String account, String email);
	 Boolean updatePassword(String account, String password, String email);

}
