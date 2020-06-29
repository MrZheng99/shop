package com.zyl.shop.controller;

import com.zyl.shop.entity.Admin;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.impl.AdminServiceImpl;
import com.zyl.shop.service.impl.UserServiceImpl;
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
    @RequestMapping(value="/{adminId}",method= RequestMethod.GET)
    public ModelAndView home(@PathVariable("adminId")Integer adminId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Integer userIdSession = (Integer) session.getAttribute("adminID");
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
    public ResponseJson getAdminsInfo(@SessionAttribute("adminID")Integer mid) {
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
}
