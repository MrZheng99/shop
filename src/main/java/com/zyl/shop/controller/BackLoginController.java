package com.zyl.shop.controller;

import com.zyl.shop.entity.Admin;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.entity.User;
import com.zyl.shop.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RequestMapping("/back")
@RestController
public class BackLoginController {
    @Autowired
    AdminServiceImpl adminService;

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public ModelAndView backIndex() {
        ModelAndView mav = new ModelAndView();
        System.out.println("登录");
        mav.setViewName("/back/index.html");
        return mav;
    }
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseJson backLogin(@RequestParam String account, @RequestParam String password, HttpSession session) {
        Admin admin = adminService.login(account, password);
        ResponseJson responseJson = new ResponseJson();
        if(admin!=null) {
            session.setAttribute("adminID", admin.getId());
            session.setAttribute("adminName", admin.getName());
            session.setMaxInactiveInterval(30*60);
            responseJson.setSuccess(true);
            responseJson.setData(admin.getId());

            return responseJson;
        }
        responseJson.setSuccess(false);
        responseJson.setData(-1);
        return responseJson;
    }

}
