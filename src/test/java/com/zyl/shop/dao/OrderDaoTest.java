package com.zyl.shop.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zyl.shop.entity.Order;
import com.zyl.shop.entity.OrderDetail;
import com.zyl.shop.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderDaoTest {
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private Order order;
	
	@Before
	public void getInsertedOrder() {
		final String account = "testUserAccount";
		userDao.register(account, "password", "12345678910");
		int userId = userDao.queryByName(account);
		Order insertedOrder = new Order();
		insertedOrder.setUid(userId);
		insertedOrder.setOrderprogress(OrderService.OrderProgress.unpaid);
		insertedOrder.setAmount(new Random().nextDouble()*100);
		insertedOrder.setDate(new Date());
		insertedOrder.setStatus("1");
		
		orderDao.addOrder(insertedOrder);
		order = insertedOrder;
	}
	
	@Test
	@Rollback
	public void testAddOrder() {
		Order queryedOrder = orderDao.getOrderById(String.valueOf(order.getOid()));
		assertEquals(order.getAmount(), queryedOrder.getAmount());
	}
	
	@Test
	@Rollback
	public void testAddOrderDetail() throws Exception {
		final int goodId = 2316118;
		final int goodtypeId = 2316118;

		jdbcTemplate.execute("insert into tb_goodstype(gtid, typename, status) values("+goodtypeId+", 'testGoodType', 0)");
		jdbcTemplate.execute("insert into tb_goodsinfo(gid, goodsprice, store, goodsname, goodsdescription, status, goodstype) values('"+goodId+"', '34.1', '589', 'testGood', 'testGoodDescr', 0, "+goodtypeId+");");
			
		OrderDetail detail = new OrderDetail();
		detail.setOdid(0);
		detail.setOid(order.getOid());
		detail.setGid(goodId);
		detail.setNumber(5);
		detail.setPrice(1100);
		detail.setStatus("1");
		orderDao.addOrderDetail(detail);
		OrderDetail queryedOrderDetail = orderDao.getOrderDetails(String.valueOf(order.getOid())).get(0);
		assertEquals(detail.toString(), queryedOrderDetail.toString());
	}
}
