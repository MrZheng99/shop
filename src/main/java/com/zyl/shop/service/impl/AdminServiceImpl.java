package com.zyl.shop.service.impl;

import com.zyl.shop.dao.AdminDao;
import com.zyl.shop.dao.UserDao;
import com.zyl.shop.entity.Admin;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.entity.User;
import com.zyl.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Autowired
    UserDao userDao;

    @Override
    public Admin login( String account, String password,String role) {
        Admin admin = adminDao.login(account, password,role);
        if(admin!=null) {
            return admin;
        }
        return  null;
    }

    @Override
    public ResponseJson getAllAdminInfo(Integer mid) {
        List<Admin> listAdmin = adminDao.queryAllAdmin(mid);
        ResponseJson responseJson = new ResponseJson();
        responseJson.setSuccess(true);
        responseJson.setData(listAdmin);
        return  responseJson;
    }

    @Override
    public ResponseJson getAllUserInfo() {
        List<User> listUser = userDao.queryAllUser();
        ResponseJson responseJson = new ResponseJson();
        responseJson.setSuccess(true);
        responseJson.setData(listUser);
        return  responseJson;
    }

    @Override
    public ResponseJson addAmin(String name, String password, String tel) {
        Integer row = adminDao.addAdmin(name,password,tel);
        ResponseJson responseJson = new ResponseJson();
        if(row!=null && row>0){
            responseJson.setSuccess(true);
            responseJson.setData(row);
            return  responseJson;
        }
        responseJson.setSuccess(false);
        return  responseJson;
    }

    @Override
    public ResponseJson updateAdmin(String aid, String status) {
        Integer row = adminDao.update(aid,status);
        ResponseJson responseJson = new ResponseJson();
        if(row!=null && row>0){
            responseJson.setSuccess(true);
            responseJson.setData(row);
            return  responseJson;
        }
        responseJson.setSuccess(false);
        return  responseJson;
    }
    @Override
    public ResponseJson updateAdminTel(String aid, String tel) {
        Integer row = adminDao.changeTel(aid,tel);
        ResponseJson responseJson = new ResponseJson();
        if(row!=null && row>0){
            responseJson.setSuccess(true);
            responseJson.setData(row);
            return  responseJson;
        }
        responseJson.setSuccess(false);
        return  responseJson;
    }

    @Override
	public ResponseJson changePassword(int aid, String oldPassword, String newPassword) {
        Integer row = adminDao.changePassword(aid, oldPassword, newPassword);
        ResponseJson responseJson = new ResponseJson();
        if(row!=null && row>0){
            responseJson.setSuccess(true);
            responseJson.setData(row);
            return  responseJson;
        }
        responseJson.setSuccess(false);
        return  responseJson;
	}

	
	@Override
	public Admin getInfo(int aid) {
		return adminDao.getAdminById(aid);
	}
}
