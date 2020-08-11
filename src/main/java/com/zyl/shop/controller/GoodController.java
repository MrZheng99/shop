package com.zyl.shop.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyl.shop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.zyl.shop.service.GoodsService;

import javax.servlet.http.HttpSession;

@RestController
public class GoodController {
	@Autowired
	private GoodsService goodsService;
	@PostMapping(value="/goods/uploadImage")
	public Map<String,Object> uploadImage(@RequestParam("upload") MultipartFile file) {
		try {
			Map<String, Object> rs = goodsService.uploadImage(file);

			Map<String, Object> map = new HashMap<String, Object>();
			if (rs != null) {
				map.put("fileName", rs.get("fileName"));
				map.put("url", "..\\..\\" + rs.getOrDefault("upload", ""));
				map.put("uploaded", 1);
				System.out.println(map.get("url"));
				return map;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<String,Object>();
	}

	@RequestMapping(value="/good", method=RequestMethod.POST)
	public int addGood(@RequestParam("pic") MultipartFile[] file, @RequestParam int tid,
					   @RequestParam String gname, @RequestParam double price, @RequestParam int balance,
					   @RequestParam String descr) {

		System.out.println(file[0].getOriginalFilename());
		System.out.println(descr);
		try {
			goodsService.addGood(file, tid, gname, price, balance, descr);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@RequestMapping(value="/good/update/{gid}", method=RequestMethod.POST)
	public int updateGood(@RequestParam("pic") MultipartFile[] file,@PathVariable("gid") int gid, @RequestParam int tid,
					   @RequestParam String gname, @RequestParam double price, @RequestParam int balance,
					   @RequestParam String descr) {

		System.out.println(file[0].getOriginalFilename());
		System.out.println(descr);
		try {
			goodsService.updateGood(file, gid,tid, gname, price, balance, descr);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	@RequestMapping("/goods")
	public List<GoodsInfo> searchGoodsByTypeAndName(@RequestParam Integer tid, @RequestParam String gname){
		return goodsService.searchGoodByTypeAndName(tid, gname,false);
	}
	@RequestMapping("/goods/name")
	public List<Map<String,Object>> searchGoodsByTypeAndName(){
		return goodsService.searchGoodNameAndId();
	}
	@RequestMapping("/goods/getComments")
	public ResponseJson getComments(@RequestParam Integer oid,HttpSession session){
		Integer userId = (Integer) session.getAttribute("userID");
		return goodsService.getComments(oid, userId);
	}
	@PostMapping("/goods/addComments")
	public ResponseJson addComments(@RequestBody AddCommentsItem comments,HttpSession session){
		Integer userId = (Integer) session.getAttribute("userID");
		comments.setUid(userId);
		System.out.println(comments);

		return goodsService.addComments(comments);
	}
	@RequestMapping("/goods/hot")
	public List<GoodsInfo> searchHotGoodsByTypeAndName(@RequestParam Integer tid, @RequestParam String gname){
		return goodsService.searchGoodByTypeAndName(tid, gname,true);
	}
	@RequestMapping("/goods/hot/update")
	public ResponseJson deleteHotGoodsById(@RequestParam Integer gid, @RequestParam String hot){
		return goodsService.updateGoodHotStatus(gid,hot);
	}
	@RequestMapping(value="/good/{goodId}", method=RequestMethod.POST)
	public ResponseJson updateGoodStatus(@PathVariable("goodId")int goodId, @RequestParam String status) {
		return goodsService.updateGoodStatus(goodId, status);
	}

	@RequestMapping(value="/goods/number/type/{type}",method=RequestMethod.GET)
	public ResponseJson queryGoodsNumberByType(@PathVariable("type") String type) {
		return new ResponseJson(true,null,goodsService.queryRowsNumber(type,"type"));
	}
	@RequestMapping(value="/goods/number/name/{name}",method=RequestMethod.GET)
	public ResponseJson queryGoodsNumberByName(@PathVariable("name") String name) {
		return new ResponseJson(true,null,goodsService.queryRowsNumber(name,"name"));
	}
	@RequestMapping(value= {"/goods/type/{type}","/goods/type/{type}/{pageNum}"})
	public ResponseJson queryGoodsByType(@PathVariable("type") String type,@PathVariable(value = "pageNum",required = false) Integer pageNum) {
		return goodsService.queryGoodsByType(type,pageNum);
	}
	@GetMapping(value= {"/goods/name/{name}","/goods/name/{name}/{pageNum}"})
	public ResponseJson queryGoodsByName(@PathVariable("name") String name, @PathVariable(value = "pageNum",required = false) Integer pageNum) {
		return goodsService.queryGoodsByName(name,pageNum);
	}
    @RequestMapping(value="/goods/report/default",method=RequestMethod.GET)
    public ResponseJson reportDefault() {
        return goodsService.report(null,null);
    }
	@RequestMapping(value="/goods/report",method=RequestMethod.POST)
	public ResponseJson report(@RequestBody SaleRequsetItem item) {
		System.out.println(item);
		return goodsService.report(item);
	}

}
