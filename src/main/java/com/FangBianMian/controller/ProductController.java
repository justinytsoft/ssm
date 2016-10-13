package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
	public String add(Model model, 
					  @RequestParam(required=false) Integer id,
					  @RequestParam(required=false) Integer flag){
		
		if(id!=null){
			Product p = productService.queryProductById(id);
			if(p==null){
				model.addAttribute("msg", "未查询到商品记录");
			}else{
				model.addAttribute("product", p);
			}
		}
		
		if(flag!=null){
			if(flag.intValue()==0){
				model.addAttribute("msg", "保存失败");
			}else{
				model.addAttribute("msg", "保存成功");
			}
		}
		
		return "pages/product/add";
	}
	
	/**
	 * 保存商品数据
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/save")
	public String save(@RequestParam(required=false) Integer id,
					   @RequestParam(required=false) String name,
					   @RequestParam(required=false) Integer quantity,
					   @RequestParam(required=false) Float price,
					   @RequestParam(required=false) Float discount_price,
					   @RequestParam(required=false) Float freight_price,
					   @RequestParam(required=false) Integer category,
					   @RequestParam(required=false) Integer payment_type,
					   @RequestParam(required=false) Boolean status,
					   @RequestParam(required=false) Boolean hot,
					   @RequestParam(required=false) String detail,
					   @RequestParam(required=false) String[] productImgs){
		
		if(StringUtils.isBlank(name) || StringUtils.isBlank(detail) || quantity==null || price==null || category==null
		   || payment_type==null || status==null || hot==null || productImgs==null){
			return "redirect: add?flag=0";
		}
		
		Product p = new Product();
		p.setId(id);
		p.setName(name);
		p.setQuantity(quantity);
		p.setPrice(price);
		p.setDiscount_price(discount_price);
		p.setFreight_price(freight_price);
		p.setCategory_id(category);
		p.setPayment_type(payment_type);
		p.setStatus(status);
		p.setHot(hot);
		p.setDetail(detail);
		
		productService.saveProduct(p, productImgs);
		
		return "redirect: add?flag=1";
	}
}
