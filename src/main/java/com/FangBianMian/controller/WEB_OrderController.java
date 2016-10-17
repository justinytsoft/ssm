package com.FangBianMian.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.domain.Product;
import com.FangBianMian.service.IOrderService;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataUtil;

@RequestMapping("/web/order")
@Controller
public class WEB_OrderController {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IProductService productService;
	
	/**
	 * 订单首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model,
						@RequestParam(required=false) Integer flag){
		if(DataUtil.getSession(request)!=null){
			return "redirect: ../index";
		}
		
		if(flag!=null){
			if(flag.intValue()==0){
				model.addAttribute("msg", "订单保存失败");
			}
		}
		return "web/index";
	}
	
	/**
	 * 订单确认
	 * @param pid
	 * @param num
	 * @return
	 */
	@RequestMapping("/confirm")
	public String confirm(Model model,
						  @RequestParam(required=false) Integer pid,
						  @RequestParam(required=false) Integer number){
		Product p = productService.queryProductById(pid);
		model.addAttribute("p", p);		
		model.addAttribute("pid", pid);		
		model.addAttribute("number", number);		
		return "web/buy";
	}
	
	/**
	 * 保存订单
	 * @param pid
	 * @param num
	 * @return
	 */
	@RequestMapping("/save")
	public String save(@RequestParam(required=false) Integer pid,
					   @RequestParam(required=false) Integer number){
		
		if(pid==null || number==null){
			
		}
		
		
		
		return "redirect: index";
	}
}
