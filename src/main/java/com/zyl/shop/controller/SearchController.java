package com.zyl.shop.controller;

import com.zyl.shop.entity.Goods;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.impl.GoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/search")
@Scope("singleton")
public class SearchController {
    @Autowired
    GoodsServiceImpl goodsService;
    @GetMapping("")
    public ModelAndView jump2Search() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/html/front/search.html");
        return mav;
    }
    @RequestMapping("/getKeyWord")
    public ResponseJson saveKeyWords(HttpSession session) {
        try{
            String keyWord = (String) session.getAttribute("keyWord");
            if(keyWord!=null){
                return new ResponseJson(true,"获取关键字成功",keyWord);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseJson(false,"获取关键字失败");
    }
}
