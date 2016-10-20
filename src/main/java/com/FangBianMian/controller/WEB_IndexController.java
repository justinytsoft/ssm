package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.constant.Common;
import com.FangBianMian.constant.LoginStatus;
import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.Bulletin;
import com.FangBianMian.domain.Member;
import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.service.IBulletinService;
import com.FangBianMian.service.IMemberService;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataValidation;

/**
 * 商城首页
 * @author Administrator
 *
 */
@RequestMapping("/web/index")
@Controller
public class WEB_IndexController {

	@Autowired
	private IProductService productService;
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IBulletinService bulletinService;
	
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		return "web/login";
	}
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String main(){
		return "web/main";
	}
	
	/**
	 * 上
	 * @param model
	 * @return
	 */
	@RequestMapping("/top")
	public String top(Model model, HttpServletRequest request){
		//获取登录用户
		Member user = (Member)request.getSession().getAttribute(Common.MEMBER_SESSION);
		if(user!=null){
			user = memberService.queryMemberByUsername(user.getUsername());
			//如果session中的用户状态不是登录成功，则设为null
			if(user.getStatus()!=LoginStatus.SUCCESS){
				user = null;
			}else{
				request.getSession().setAttribute(Common.MEMBER_SESSION, user);
			}
		}
		
		//获取商品分类列表
		List<ProductCategory> pcs = DataValidation.isEmpty(baseDao.queryProductCategory());
		
		model.addAttribute("pcs", pcs);
		model.addAttribute("user", user);
		return "web/header";
	}

	/**
	 * 中
	 * @param model
	 * @return
	 */
	@RequestMapping("/center")
	public String center(Model model, 
						 @RequestParam(required=false) Integer category_id,
						 @RequestParam(required=false) String name){
		//公告查询条件
		Map<String, Object> bulletinParam = new HashMap<String, Object>();
		bulletinParam.put("page", 0);
		bulletinParam.put("rows", 15);
		
		//查询公告
		List<Bulletin> bs = bulletinService.queryBulletins(bulletinParam);
		
		//商品查询条件
		Map<String, Object> productParam = new HashMap<String, Object>();
		productParam.put("page", 0);
		productParam.put("rows", 5);
		productParam.put("status", true);
		productParam.put("hot", true);
		
		//查询大图热门商品
		List<Product> big_hot_ps = productService.queryProductsByParam(productParam);
		
		if(!StringUtils.isBlank(name)){
			productParam.put("name", name);
		}
		if(category_id!=null){
			productParam.put("category_id", category_id);
		}
		
		//查询热门商品
		productParam.put("rows", 15);
		List<Product> hot_ps = productService.queryProductsByParam(productParam);
		
		//查询普通商品
		productParam.put("rows", 12);
		productParam.put("hot", false);
		List<Product> ps = productService.queryProductsByParam(productParam);
		
		model.addAttribute("bs", bs);
		model.addAttribute("ps", ps);
		model.addAttribute("hot_ps", hot_ps);
		model.addAttribute("big_hot_ps", big_hot_ps);
		return "web/list";
	}

	/**
	 * 下
	 * @param model
	 * @return
	 */
	@RequestMapping("/bottom")
	public String left(){
		return "web/footer";
	}

	/**
	 * 判断用户是否登录
	 * @return
	 */
	@RequestMapping("/logged")
	@ResponseBody
	public Map<String,Object> logged(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Member user = (Member) request.getSession().getAttribute(Common.MEMBER_SESSION);
		map.put("flag", user!=null);
		return map;
	}
}
