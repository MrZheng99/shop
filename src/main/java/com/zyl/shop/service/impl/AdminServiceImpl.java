package com.zyl.shop.service.impl;

import com.zyl.shop.dao.AdminDao;
import com.zyl.shop.entity.Admin;
import com.zyl.shop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Override
    public Admin login(String account, String password) {
        Admin admin = adminDao.login(account, password);
        if(admin!=null) {
            return admin;
        }
        return  null;
    }
}
