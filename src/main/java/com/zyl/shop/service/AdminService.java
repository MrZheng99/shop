package com.zyl.shop.service;


import com.zyl.shop.entity.Admin;
import com.zyl.shop.entity.ResponseJson;

public interface AdminService {
    Admin login(String account, String Password);

    /**
     * 查询所有管理员信息，除当前管理员以外
     * @return
     */
    ResponseJson getAllAdminInfo(Integer mid);

    /**
     * 查询所有用户信息
     * @return
     */
    ResponseJson getAllUserInfo();

    /**
     * 添加管理员
     * @return
     * @param name
     * @param password
     * @param tel
     */
    ResponseJson addAmin(String name, String password, String tel);

    /**
     * 冻结或者解冻管理员
     * @param aid
     * @param status
     * @return
     */
    ResponseJson updateAdmin(String aid, String status);
    
    /**
     * 修改密码
     * @param aid
     * @param oldPassword
     * @param newPassword
     */
    ResponseJson changePassword(int aid, String oldPassword, String newPassword);

    /**
     * 获取管理员信息
     * @param aid
     * @return
     */
	Admin getInfo(int aid);

    /**
     * 管理员修改自己的电话号码
     * @param aid
     * @param tel
     * @return
     */
    ResponseJson updateAdminTel(String aid, String tel);
}
