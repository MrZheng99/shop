package com.zyl.shop.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.zyl.shop.service.OrderService;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private OrderService orderService;
	
	private MockHttpSession session;
	
	private final int userId = 111;
	
	@Before
	public void setupHttpSession() {
		session = new MockHttpSession();
		session.setAttribute("userID", userId);
	}
	
	@Test
	public void testAddOrder() throws Exception{
		Mockito.when(orderService.addOrder(Mockito.eq(userId), Mockito.any())).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/order")
				.session(session)
				.contentType(MediaType.APPLICATION_JSON)
				.content("[{\"gid\":\"123\", \"number\":10, \"price\":12.3}]");
		
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("{\"success\":true,\"msg\":null,\"data\":null}"));
		
		Mockito.verify(orderService, Mockito.times(1)).addOrder(Mockito.eq(userId), Mockito.any());
	}
	
	@Test
	public void testAddOrderWithoutSession() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content("[{\"gid\":\"123\", \"number\":10, \"price\":12.3}]");
		
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("{\"success\":false,\"msg\":\"未登录\",\"data\":null}"));
	}

	@Test
	public void testOrderHasten() throws Exception{
		Mockito.when(orderService.ship("1")).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/1/hasten").session(session);
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("{\"success\":true,\"msg\":null,\"data\":null}"));
		
		Mockito.verify(orderService, Mockito.times(1)).ship("1");
	}

	@Test
	public void testOrderReceipt() throws Exception{
		Mockito.when(orderService.receipt(userId, "1")).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/1/receipt").session(session);
		mockMvc.perform(requestBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("{\"success\":true,\"msg\":null,\"data\":null}"));
		
		Mockito.verify(orderService, Mockito.times(1)).receipt(userId, "1");
	}
}
