package com.zyl.shop.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zyl.shop.entity.Cart;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CartDaoTest {
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Cart cart;
	
	@Before
	public void getInsertedOrder() {
		final int goodId = 2316118;
		final int goodtypeId = 2316118;
		final String account = "testUserAccount";
		userDao.register(account, "password", "12345678910");
		final int userId = userDao.queryByName(account);
		
		jdbcTemplate.execute("insert into tb_goodstype(gtid, typename, status) values("+goodtypeId+", 'testGoodType', 0)");
		jdbcTemplate.execute("insert into tb_goodsinfo(gid, goodsprice, store, goodsname, goodsdescription, status, goodstype) values('"+goodId+"', '34.1', '589', 'testGood', 'testGoodDescr', 0, "+goodtypeId+");");
		
		cart = new Cart();
		cart.setGid(goodId);
		cart.setUid(userId);
		cart.setScid(0);
		cart.setStatus("1");
		cart.setNumber(100);
		cartDao.addGoodToUserCart(cart);
	}
	
	@Test
	@Rollback
	public void testAddGood() {
		Integer queryedId = jdbcTemplate.queryForObject("select scid from tb_shoppingcart where uid='"+cart.getUid()+"'", Integer.class);
		assertEquals(cart.getScid(), queryedId);
	}
}
