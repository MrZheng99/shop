package com.zyl.shop.service;


public interface UserService {
	 Integer login(String account,String Password);
	 Boolean register(String account, String password, String email);

}
