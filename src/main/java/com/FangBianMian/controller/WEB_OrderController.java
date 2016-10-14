package com.FangBianMian.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.constant.Common;
import com.FangBianMian.domain.User;
import com.FangBianMian.service.IOrderService;

@RequestMapping("/web/order")
public class WEB_OrderController {

	@Autowired
	private IOrderService orderService;
	
	/**
	 * 订单首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model,
						@RequestParam(required=false) Integer flag){
		if(!logged(request)){
			return "redirect: ../index";
		}
		
		if(flag!=null){
			if(flag.intValue()==0){
				model.addAttribute("msg", "订单保存失败");
			}
		}
		return "kaquan/index";
	}
	
	@RequestMapping("/save")
	public String save(@RequestParam(required=false) Integer pid,
					   @RequestParam(required=false) Integer num){
		
		if(pid==null || num==null){
			
		}
		
		
		
		return "redirect: index";
	}
	
	/**
	 * 判断用户是否登录
	 * @param request
	 * @return
	 */
	private boolean logged(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(Common.USER_SESSION);
		return user!=null;
	}
}
