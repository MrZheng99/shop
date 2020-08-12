package com.zyl.shop.service.impl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyl.shop.entity.*;
import com.zyl.shop.util.LucenceUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zyl.shop.dao.GoodsDao;
import com.zyl.shop.service.GoodsService;

import javax.imageio.ImageIO;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 查询所有类别
	 */
	@Override
	public List<String> queryCategroy() {
		List<String> listCategroy = goodsDao.queryCategroy();
		if (listCategroy != null && !listCategroy.isEmpty()) {
			return listCategroy;
		}
		return null;
	}

	/*
	 * 查询指定数目的条数，如果为hot则查询热卖，否则查询指定类别的数据
	 */
	@Override
	public ResponseJson queryGoodsByType(String categroy, Integer pageNum) {
		List<Goods> listGoods = null;
		int startNumber=0;
		int number=8;
		if(pageNum!=null) {
			startNumber = pageNum*8;
		}
		if ("hot".equals(categroy)) {
			listGoods = goodsDao.queryHotGoods(startNumber, number);}
		else{
			listGoods = goodsDao.queryGoodsByCategroy(categroy, startNumber, number);
		}
		if (listGoods == null || listGoods.isEmpty()) {
			return new ResponseJson(false,"数据获取失败",null);
		}
		return new ResponseJson(true,"数据获取成功",listGoods);
	}
	@Override
	public Integer queryRowsNumber(String categroy,String option) {
		Integer row=null;
		if("name".equals(option)){
			row = goodsDao.queryGoodsNumberByName(categroy);
		}else if("type".equals(option)){
			if ("hot".equals(categroy)) {
				row = goodsDao.queryHotGoodsNum();
			} else{
				row = goodsDao.queryGoodsNumberByCategroy(categroy);
			}
		}

		if (row != null && row > 0) {
			return row;
		}
		return 0;
	}

	@Override
	public ResponseJson queryGoodsByName(String goodsName, Integer pageNum) {
		List<Goods> listGoods = null;
		if(pageNum==null) {
			listGoods=goodsDao.queryGoodsByName(goodsName,0,8);
		}else {
			listGoods=goodsDao.queryGoodsByName(goodsName,pageNum*8,8);
		}
		if(listGoods==null) {
			return new ResponseJson(false,"暂无结果",null);
		}
		return new ResponseJson(true,"数据获取成功",listGoods);
	}

	@Override
	public Goods queryGoodsById(Integer goodsId) {
		Goods goods = goodsDao.queryGoodsById(goodsId);
		goods.setComments(goodsDao.queryGoodsComments(goodsId));
		return goods;
	}

	/**
	 * 存在的话就返回0，不存在就添加，添加成功就返回行数，否则返回-1；
	 * @param userID
	 * @param goodsId
	 * @return
	 */
	@Override
	public Integer insert2ShoppingCart(Integer userID, Integer goodsId) {
		//System.out.println(userID+":"+goodsId);
		//Integer number = goodsDao.queryGoodsShoppingCart(userID, goodsId);
		//if(number==null||number<=0){
		Integer row = goodsDao.insert2ShoppingCart(userID, goodsId);
		if(row>0){
			return row;
		}
		return -1;
		//}
		//return 0;

	}

	@Override
	public void addGood(MultipartFile[] files, int tid, String gname, double price, int balance, String descr) throws IOException {
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setGid(0);
		goodsInfo.setGoodsname(gname);
		goodsInfo.setGoodsprice(price);
		goodsInfo.setGoodstype(tid);
		goodsInfo.setStore(balance);
		goodsInfo.setGoodsdescription(descr);
		goodsInfo.setStatus("1");
		List<String> listImage = new ArrayList<String>();
		if(files.length>0){
			for(MultipartFile file : files) {
				listImage.add(this.addImage(file));
			}
			goodsInfo.setImages(String.join(",",listImage));

		}

		goodsDao.addGood(goodsInfo);

	}

	@Override
	public void updateGood(MultipartFile[] files,int gid, int tid, String gname, double price, int balance, String descr) throws IOException {
		GoodsInfo goodsInfo = new GoodsInfo();
		goodsInfo.setGid(gid);
		goodsInfo.setGoodsname(gname);
		goodsInfo.setGoodsprice(price);
		goodsInfo.setGoodstype(tid);
		goodsInfo.setStore(balance);
		goodsInfo.setGoodsdescription(descr);
		goodsInfo.setStatus("1");
		List<String> listImage = new ArrayList<String>();

		for(MultipartFile file : files) {
			System.out.println(file.getContentType()+"==========");
			if(file.getContentType().indexOf("image")>=0)
				listImage.add(this.addImage(file));
		}
		goodsInfo.setImages(String.join(",",listImage));
		goodsDao.updateGood(goodsInfo);
	}

	@Override
	public Map<String, Object> uploadImage(MultipartFile file) throws IOException {


		Map<String, Object> rs = new HashMap<String, Object>();
		String fileName = addWaterLogo(file);
		rs.put("fileName", fileName);
		rs.put("upload", "images\\goods\\" + fileName);
		return 	rs;
	}

	private String addImage(MultipartFile file) throws IOException {

		//file.transferTo(dest);
		return 	"images/goods/"+addWaterLogo(file);

	}

	private String addWaterLogo(MultipartFile file) throws IOException {
		String fileName = "_"+System.currentTimeMillis()+"."+file.getOriginalFilename().split("\\.", 2)[1];
		File dest = new File(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/images/goods"),
				fileName);
		Image srcImg = ImageIO.read( file.getInputStream());
		int srcImgWidth = srcImg.getWidth(null);
		int srcImgHeight = srcImg.getHeight(null);
		// 加水印
		BufferedImage bufImg = new BufferedImage(srcImgWidth,
				srcImgHeight,
				BufferedImage.TYPE_INT_RGB);
		//获取 Graphics2D 对象
		Graphics2D g = bufImg.createGraphics();
		//设置绘图区域
		g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
		//设置字体
		Font font = new Font("宋体", Font.PLAIN, 20);
		// 根据图片的背景设置水印颜色
		g.setColor(Color.green);
		g.setFont(font);
		//获取文字长度
		int len = g.getFontMetrics(
				g.getFont()).charsWidth("图片來源:ZJ购物商城".toCharArray(),
				0,
				"图片來源:ZJ购物商城".length());
		int x = srcImgWidth - len - 10;
		int y = srcImgHeight - 20;
		g.drawString("图片來源:ZJ购物商城", x, y);
		g.dispose();
		// 输出图片
		FileOutputStream outImgStream = new FileOutputStream(dest);
		ImageIO.write(bufImg, fileName.substring(fileName.lastIndexOf(".")+1), outImgStream);
		outImgStream.flush();
		outImgStream.close();
		return fileName;
	}

	@Override
	public List<GoodsInfo> searchGoodByTypeAndName(Integer tid, String gname,Boolean hot) {
		if(gname == null) {
			gname = "";
		}
		if(hot){
			return goodsDao.searchHotGoodByTypeAndName(tid, "%"+gname+"%");
		}
		return goodsDao.searchGoodByTypeAndName(tid, "%"+gname+"%");
	}
	@Override
	public List<Map<String, Object>> searchGoodNameAndId() {
		return goodsDao.searchGoodNameAndId();
	}


    @Override
	public ResponseJson updateGoodStatus(int goodId, String status) {
		Integer row = goodsDao.updateGoodStatus(goodId, status);
		if(row!=null&&row>0){
			return new ResponseJson(true,"更新成功");
		}
		return new ResponseJson(false,"更新失败");

	}
	@Override
	public ResponseJson updateGoodHotStatus(int goodId, String hot) {
		Integer row = goodsDao.updateGoodHotStatus(goodId, hot);
		if(row!=null&&row>0){
			return new ResponseJson(true,"更新成功",goodsDao.updateGoodHotStatus(goodId, hot));
		}
		return new ResponseJson(false,"更新失败",-1);

	}
	@Override
	public ResponseJson getComments(Integer oid, Integer userId) {
		String comments = goodsDao.queryGoodsCommentsByOId(oid,userId);
		if(comments!=null&&comments!=""&&comments!="null"){
			return new ResponseJson(true,"获取成功",comments);
		}
		return new ResponseJson(false);
	}

	@Override
	public ResponseJson addComments(AddCommentsItem commentsItem) {
		try{
			if (commentsItem.getExist()) {
				for (Integer gid : commentsItem.getGoodsIds()) {
					goodsDao.updateGoodsComments(gid,commentsItem);
				}
			} else {
				for (Integer gid : commentsItem.getGoodsIds()) {
					goodsDao.insertGoodsComments(gid, commentsItem);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return new ResponseJson(false);

		}
		return new ResponseJson(true);
	}


	@Override
	public ResponseJson report(String op, Sale sale) {
		if(sale==null){
			if(op==null){
				return new ResponseJson(true,null,goodsDao.queryGoodsSalesTypes());
			}
		}
		return new ResponseJson(true);

	}
	@Override
	public ResponseJson report(SaleRequsetItem item) {
		if(item.getAssignType()){
			if(item.getAssignDate()){
				if(item.getGtid()==0){
					//查找指定时间全部类别
					return new ResponseJson(true,null,goodsDao.queryGoodsSalesTypesDate(item));
				}
				//查找指定时间指定类别
				return new ResponseJson(true,null,goodsDao.queryGoodsSalesByTypeDate(item));
			}
			if(item.getGtid()==0){
				//查找不指定时间全部类别
				return new ResponseJson(true,null,goodsDao.queryGoodsSalesTypes());
			}
			//查找不指定时间指定类别
			return new ResponseJson(true,null,goodsDao.queryGoodsSalesByType(item));
		}
		if(item.getAssignDate()){
			if(item.getGid()==0){
				//查找指定时间全部商品
				return new ResponseJson(true,null,goodsDao.queryGoodsSalesGoodsDate(item));
			}
			//查找指定时间指定商品
			return new ResponseJson(true,null,goodsDao.queryGoodsSalesByGidDate(item));
		}
		if(item.getGid()==0){
			//查找不指定时间全部商品
			return new ResponseJson(true,null,goodsDao.queryGoodsSalesGoods(item));
		}
		//查找不指定时间指定商品
		return new ResponseJson(true,null,goodsDao.queryGoodsSalesByGid(item));
	}

	/**
	 * lucence搜索
	 */
	public static LucenceUtil lu=null;

	@Override
	public ResponseJson myLucence(String goodsName, Integer pageNum) {
		initLu();
		List<Document> docs;
		if(pageNum==null) {
			docs = lu.search(new String[] {"id", "name","description","price","images"}, goodsName,8,0, 100);
		}else {
			docs = lu.search(new String[] {"id", "name","description","price","images"}, goodsName,8,pageNum, 100);
		}
		if(docs.size()<=0) {
			return new ResponseJson(false,"暂无结果",null);
		}
		// 取值
		Goods goods = null;
		List<Goods> listGoods = new ArrayList<>();
		for(Document doc : docs) {
			goods = new Goods();
			goods.setId(Integer.valueOf( doc.get("id")));
			goods.setName(doc.get("name"));
			goods.setPrice(Float.valueOf( doc.get("price")));
			goods.setImages(doc.get("images"));
			listGoods.add(goods);
			//System.out.println(doc.get("id") + "\t" + doc.get("name") + "\t" + doc.get("images")+ "\t" + doc.get("price")+"\t"+doc.get("description"));
		}
		return new ResponseJson(true,"数据获取成功",listGoods);
	}

    @Override
    public ResponseJson myLucenceNumber(String name) {
		initLu();
        return new ResponseJson(true,null,lu.getNumByName(new String[]{"id", "name","description","price","images"},name,100));
    }

	/**
	 * 	先查询字典是否初始化 如果没有则初始化
	 */
	private synchronized void initLu(){
		if(lu==null){
			lu = new LucenceUtil("Goods", "id", new String[] {"name","description","price","images"});
			//查询数据库所有商品
			List<Goods> rs = goodsDao.queryAllGoods();
			// 清空字典
			lu.deleteAll();
			// 添加
			lu.add(Goods.class,rs);
			System.out.println("初始化库");
		}
	}
}
