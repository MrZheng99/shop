package com.zyl.shop.controller;

import com.zyl.shop.entity.Admin;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.entity.User;
import com.zyl.shop.service.impl.AdminServiceImpl;
import com.zyl.shop.service.impl.UserServiceImpl;
import com.zyl.shop.util.SessionKey;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    UserServiceImpl userService;
    @RequestMapping(value="/name",method= RequestMethod.GET)
    public ResponseJson queryName(HttpSession session) {
        String admName = (String) session.getAttribute(SessionKey.CURRENT_ADMIN_NAME);
        System.out.println(admName);
        if(admName==null||admName=="") {
            return  new ResponseJson(false,"未登录",null);
        }
        return  new ResponseJson(true,"登录成功",admName);
    }
    @RequestMapping(value="/{adminId}",method= RequestMethod.GET)
    public ModelAndView home(@PathVariable("adminId")Integer adminId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Integer userIdSession = (Integer) session.getAttribute(SessionKey.CURRENT_ADMIN_ID);
        if(userIdSession==null||adminId<=100||userIdSession<=100||adminId!=userIdSession) {
            mav.setViewName("redirect:/back/index");
            return mav;
        }
        mav.setViewName("/back/manager/index.html");
        return mav;
    }

    @RequestMapping(value="",method=RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/back/index");
        return mav;
    }
    @RequestMapping(value="/getAllAdminInfo",method=RequestMethod.GET)
    public ResponseJson getAdminsInfo(@SessionAttribute(SessionKey.CURRENT_ADMIN_ID)Integer mid) {
        ResponseJson responseJson = adminService.getAllAdminInfo(mid);
        System.out.println(responseJson);
        return responseJson;
    }
    @RequestMapping(value="/getAllUserInfo",method=RequestMethod.GET)
    public ResponseJson getAllUserInfo() {
        ResponseJson responseJson = adminService.getAllUserInfo();
        System.out.println(responseJson);
        return responseJson;
    }
    @RequestMapping("/getAdminInfo")
    public ResponseJson getInfo(@SessionAttribute(SessionKey.CURRENT_ADMIN_ID) int aid) {
        return new ResponseJson(true, "", adminService.getInfo(aid));
    }

    @RequestMapping(value="/updateUser",method=RequestMethod.POST)
    public ResponseJson updateUserStatus(@RequestParam("userId")Integer userId,@RequestParam("data")String data,@RequestParam("type")String type) {
        ResponseJson responseJson =userService.updateUser(userId,data,type);
        System.out.println(responseJson);
        return responseJson;
    }
    @RequestMapping(value="/queryUser",method=RequestMethod.POST)
    public ResponseJson queryUser(@RequestParam("userName")String userName,@RequestParam("email")String email) {
        ResponseJson responseJson =userService.getUserByNameEmail(userName,email);

        System.out.println(responseJson);
        return responseJson;
    }
    @PostMapping("/changePassword")
    public ResponseJson changePassword(@SessionAttribute(SessionKey.CURRENT_ADMIN_ID) int aid, @RequestParam String password_older, @RequestParam String password_new) {
        return adminService.changePassword(aid, password_older, password_new);
    }
    @RequestMapping(value="/changeTel",method=RequestMethod.POST)
    public ResponseJson updateAdminTel(@RequestParam("id")String aid,@RequestParam("tel")String tel) {
        ResponseJson responseJson = adminService.updateAdminTel(aid,tel);
        System.out.println(responseJson);
        return responseJson;
    }
/**
 * 超级管理员
 */
    @RequestMapping(value="/supper/{adminId}",method= RequestMethod.GET)
    public ModelAndView supperHome(@PathVariable("adminId")Integer adminId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Integer admIdSession = (Integer) session.getAttribute(SessionKey.CURRENT_ADMIN_ID);
        if(admIdSession==null||adminId<=100||admIdSession<=100||adminId!=admIdSession) {
            mav.setViewName("redirect:/back/index");
            return mav;
        }
        System.out.println("超级管理员登录:"+admIdSession);

        mav.setViewName("/back/supper/index.html");
        return mav;
    }
    @RequestMapping(value="/addAdmin",method=RequestMethod.POST)
    public ResponseJson addAdmin(@RequestParam("name")String name,@RequestParam("password")String password,@RequestParam("tel")String tel) {
        System.out.println(name+":"+password+":"+tel);
        ResponseJson responseJson = adminService.addAmin(name,password,tel);
        System.out.println(responseJson);
        return responseJson;
    }
    @RequestMapping(value="/updateAdmin",method=RequestMethod.POST)
    public ResponseJson updateAdmin(@RequestParam("aid")String aid,@RequestParam("status")String status) {
        ResponseJson responseJson = adminService.updateAdmin(aid,status);
        System.out.println(responseJson);
        return responseJson;

    }
}
