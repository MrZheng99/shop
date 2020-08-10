package com.zyl.shop.controller;

import javax.servlet.http.HttpSession;

import com.zyl.shop.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.Admin;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.impl.AdminServiceImpl;

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
    public ResponseJson backLogin(@RequestParam String account, @RequestParam String password,@RequestParam String role, HttpSession session) {
        Admin admin = adminService.login(account, password,role);
        ResponseJson responseJson = new ResponseJson();
        if(admin!=null) {
            session.setAttribute(SessionKey.CURRENT_ADMIN_ID, admin.getId());
            session.setAttribute(SessionKey.CURRENT_ADMIN_NAME, admin.getName());
            session.setMaxInactiveInterval(30*60);
            responseJson.setSuccess(true);
            responseJson.setData(admin);

            return responseJson;
        }
        responseJson.setSuccess(false);
        responseJson.setData(-1);
        return responseJson;
    }
    


}
