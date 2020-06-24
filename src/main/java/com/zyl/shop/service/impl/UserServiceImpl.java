package com.zyl.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyl.shop.dao.UserDaoMapper;
import com.zyl.shop.entity.User;
import com.zyl.shop.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDaoMapper userDao; 
	@Override
	public Integer login(String account, String password) {
		
		User user = userDao.login(account, password);
		if(user!=null) {
			return user.getId() ;
		}
		return  null;
	}
	@Override
	public Boolean register(String account, String password, String email) {
		Integer row = userDao.register(account, password,email);
		System.out.println(row);
		if(row==null&&row<=0) {
			return false;
		}
		return true;
	}
	public Boolean existAccount(String account) {
		Integer userID = userDao.queryByName(account);
		if(userID!=null && userID>100) {
			return true;
		}
		return false;
	}

}
