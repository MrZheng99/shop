package com.zyl.shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.shop.entity.Goods;
import com.zyl.shop.service.GoodsService;
import com.zyl.shop.service.impl.GoodsServiceImpl;
import com.zyl.shop.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {
    @Autowired
    GoodsServiceImpl goodsService;

    @RequestMapping(value = "/{goodsId}", method = RequestMethod.GET)
    public ModelAndView home(@PathVariable("goodsId") Integer goodsId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/html/front/shopping.html");
        return mav;
    }

    @RequestMapping(value = "/queryName", method = RequestMethod.GET)
    public Map<String, Object> queryName(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        String userName = (String) session.getAttribute("userName");
        if (userName == null || userName == "") {
            map.put("success", false);
            map.put("data", "未登录");
            return map;
        }
        map.put("success", true);
        map.put("data", userName);
        return map;
    }

    @GetMapping(value = "/getGoodsDetails/{goodsId}")
    public Map<String, Object> getGoodsDetails(@PathVariable("goodsId") Integer goodsId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Goods goods = goodsService.queryGoodsById(goodsId);
        map.put("success", true);
        map.put("data", goods);
        return map;
    }

    @GetMapping(value = "/addGoods2ShoppingCart/{goodsId}")
    public Map<String, Object> addGoods2ShoppingCart(@PathVariable("goodsId") Integer goodsId, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userID = (Integer) session.getAttribute("userID");
        if(userID==null){
			map.put("success", false);
			map.put("data", "未登录");
			return map;
		}
        Integer row = goodsService.insert2ShoppingCart(userID, goodsId);
        if (row > 0) {
            map.put("success", true);
            map.put("data", "成功加入购物车");
            return map;
        }
        if (row == 0) {
            map.put("success", false);
            map.put("data", "已在购物车之中");
            return map;

        }

        map.put("success", false);
        map.put("data", "加入购物车失败");
        return map;
    }
}
