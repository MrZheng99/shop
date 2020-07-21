package com.zyl.shop.controller;

import com.zyl.shop.entity.Goods;
import com.zyl.shop.entity.ResponseJson;
import com.zyl.shop.service.impl.GoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@Scope("singleton")
public class SearchController {
    @Autowired
    GoodsServiceImpl goodsService;
    @GetMapping(value= {"","{pageNum}"})
    public ResponseJson queryGoodsByName(@RequestParam("goodsName") String goodsName, @PathVariable(value = "pageNum",required = false) Integer pageNum) {
        List<Goods> listGoods=null;
        goodsName = goodsName.trim();
        if(pageNum==null) {
            listGoods=goodsService.queryGoodsByName(goodsName,0,8);
        }else {
            listGoods=goodsService.queryGoodsByName(goodsName,pageNum*8,8);
        }
        if(listGoods==null) {
            return new ResponseJson(false,"数据获取失败",null);

        }
        return new ResponseJson(true,"数据获取成功",listGoods);

    }
}
