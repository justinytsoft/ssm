package com.FangBianMian.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/wechat")
@Controller
public class WechatController {

	@RequestMapping("/ver")
	public String ver(){
		
		return "";
	}
}
