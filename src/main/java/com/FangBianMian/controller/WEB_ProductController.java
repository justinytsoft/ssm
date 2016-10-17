package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductComment;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.EasyuiDatagrid;

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
		model.addAttribute("p", p);
		return "web/detail";
	}
	
	/**
	 * 商品评论
	 * @param page
	 * @param rows
	 * @param id
	 * @return
	 */
	@RequestMapping("/commentList")
	@ResponseBody
	public EasyuiDatagrid<ProductComment> commentList(@RequestParam(required=false) Integer page,
													  @RequestParam(required=false) Integer rows,
													  @RequestParam(required=false) Integer id){
		EasyuiDatagrid<ProductComment> ed = new EasyuiDatagrid<ProductComment>();
		Map<String, Object> param = new HashMap<String, Object>();
		if(page!=null && rows!=null){
			param.put("rows", rows);
			param.put("page", ((page-1)*rows));
		}
		if(id!=null){
			param.put("id", id);
		}
		
		List<ProductComment> pcs = productService.queryProductComments(param);
		int total = productService.queryProductCommentsTotal(param);
		
		ed.setRows(pcs);
		ed.setTotal(total);
		return ed;
	}
	
	
	
	
	
}
