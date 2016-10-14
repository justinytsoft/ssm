package com.FangBianMian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataUtil;

/**
 * 商城商品类
 * @author Administrator
 *
 */
@RequestMapping("/web/product")
@Controller
public class WEB_ProductController {

	@Autowired
	private IProductService productService;
	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 商品详情页
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(Model model, @RequestParam(required=false) Integer id){
		//获取商品
		Product p = productService.queryProductById(id);
		//获取商品分类列表
		List<ProductCategory> pcs = DataUtil.isEmpty(baseDao.queryProductCategory());
		
		model.addAttribute("p", p);
		model.addAttribute("pcs", pcs);
		return "kaquan/detail";
	}
}
