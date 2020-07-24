package com.zyl.shop.service.impl;


import java.util.ArrayList;
import java.util.*;

import com.zyl.shop.entity.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyl.shop.dao.UserDao;

import com.zyl.shop.entity.Address;
import com.zyl.shop.entity.AddressItem;
import com.zyl.shop.entity.User;
import com.zyl.shop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String account, String password) {

        User user = userDao.login(account, password);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public Boolean register(String account, String password, String email) {
        Integer row = userDao.register(account, password, email);
        System.out.println(row);
        if (row == null || row <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 查找账户通过名字
     *
     * @see #existAccount(String, String)
     */
    @Override
    public Boolean existAccount(String account) {
        Integer userID = userDao.queryByName(account);
        if (userID != null && userID > 100) {
            return true;
        }
        return false;
    }

    /**
     * 查找账户通过名字和邮箱
     *
     * @see #existAccount(String)
     */
    @Override
    public Boolean existAccount(String account, String email) {
        Integer userID = userDao.queryByNameEmail(account, email);
        if (userID != null && userID > 100) {
            return true;
        }
        return false;

    }

    /**
     * 更新密码
     */
    public Boolean updatePassword(String account, String password, String email) {
        Integer row = userDao.update(account, email, password);
        System.out.println(row);
        if (row == null || row <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<AddressItem> getAddresses(int userId) {
        List<Address> addresses = userDao.getUserAddresses(userId);
        List<AddressItem> addressItems = new ArrayList<AddressItem>();
        for (Address addr : addresses) {
            AddressItem ai = new AddressItem();
            ai.setAid(String.valueOf(addr.getAid()));
            ai.setAddress(addr.getAddress());
            addressItems.add(ai);
        }
        return addressItems;
    }

    @Override
    public String getUserNameById(Integer userId) {
        User user = userDao.getUserById(userId);
        return user.getName();
    }

    @Override
    public ResponseJson updateUser(Integer userId, String data, String type) {
        ResponseJson responseJson = new ResponseJson();
        if ("updatePassword".equals(type)) {
            Integer row = userDao.updatePassword(userId, data);
            if (row != null && row > 0) {
                responseJson.setSuccess(true);
                responseJson.setData(row);
                return responseJson;
            }
            responseJson.setSuccess(false);
            return responseJson;
        }
        if ("updateStatus".equals(type)) {
            Integer row = userDao.updateStatus(userId, data);
            if (row != null && row > 0) {
                responseJson.setSuccess(true);
                responseJson.setData(row);
                return responseJson;
            }
            responseJson.setSuccess(false);
            return responseJson;
        }
        responseJson.setSuccess(false);
        return responseJson;
    }

    @Override
    public ResponseJson getUserByNameEmail(String userName, String email) {
        ResponseJson responseJson = new ResponseJson();
        List<User> listUser = userDao.queryAllUserByNameEmail(userName, email);
        responseJson.setSuccess(true);
        responseJson.setData(listUser);
        return responseJson;
    }

    @Override
    public User getUserById(Integer userId) {
       return userDao.queryAllUserById(userId);
    }

    @Override
    public Integer updateUserInfo(Integer userId, String realName, String tel, String email, String sex) {
        User user = new User();
        user.setId(userId);
        user.setSex(sex);
        user.setRealName(realName);
        user.setEmail(email);
        user.setTel(tel);
System.out.println(user);
        return userDao.updateUserInfo(user);
    }



}
