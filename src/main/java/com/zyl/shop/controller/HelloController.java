package com.zyl.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {
	@RequestMapping(value="/主页", method=RequestMethod.GET)
	public String hello() {
		return "index.html";
	}
}
