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

import com.FangBianMian.domain.Product;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.EasyuiDatagrid;

@RequestMapping("/admin/product")
@Controller
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	/**
	 * 商品列表页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/list")
	public String list(){
		return "pages/product/list";
	}
	
	/**
	 * 商品列表数据
	 * @param name
	 * @param status
	 * @param category
	 * @return
	 */
	@RequestMapping("/listData")
	@ResponseBody
	public EasyuiDatagrid<Product> listData(@RequestParam(required=false) String name,
			   								@RequestParam(required=false) Boolean status,
			   								@RequestParam(required=false) Integer category){
		EasyuiDatagrid<Product> ed = new EasyuiDatagrid<Product>();
		Map<String,Object> param = new HashMap<String,Object>();
		
		List<Product> ps = DataUtil.isEmpty(productService.queryProductsByParam(param));
		int psTotal = productService.queryProductsByParamTotal(param);
		
		ed.setRows(ps);
		ed.setTotal(psTotal);
		return ed;
	}
	
	/**
	 * 商品添加和修改页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model, @RequestParam(required=false) Integer id){
		
		if(id!=null){
			Product p = productService.queryProductById(id);
			if(p==null){
				model.addAttribute("msg", "未查询到商品记录");
			}else{
				model.addAttribute("product", p);
			}
		}
		
		return "pages/product/add";
	}
}
