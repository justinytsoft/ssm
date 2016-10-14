package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.constant.Common;
import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.domain.User;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataUtil;

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
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,
						@RequestParam(required=false) Boolean hot,
						@RequestParam(required=false) Integer category_id){
		Map<String, Object> hot_param = new HashMap<String, Object>();
		hot_param.put("page", 0);
		hot_param.put("rows", 5);
		hot_param.put("hot", true); //是否是热门商品
		hot_param.put("status", true); //是否上架
		//查询热门商品
		List<Product> hot_ps = productService.queryProductsByParam(hot_param);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", 0);
		param.put("rows", 12);
		param.put("status", true);
		if(hot!=null){
			param.put("hot", hot);
			model.addAttribute("hot", hot);
		}
		if(category_id!=null){
			param.put("category_id", category_id);
			model.addAttribute("category_id", category_id);
		}
		//查询所有商品
		List<Product> ps = productService.queryProductsByParam(param);
		
		//获取商品分类列表
		List<ProductCategory> pcs = DataUtil.isEmpty(baseDao.queryProductCategory());
		
		model.addAttribute("ps", ps);
		model.addAttribute("hot_ps", hot_ps);
		model.addAttribute("pcs", pcs);
		return "kaquan/index";
	}
	
	/**
	 * 判断用户是否登录
	 * @return
	 */
	@RequestMapping("/logged")
	@ResponseBody
	public Map<String,Object> logged(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute(Common.USER_SESSION);
		map.put("flag", user==null);
		return map;
	}
}
