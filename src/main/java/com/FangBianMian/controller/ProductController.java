package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductImg;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.DataValidation;
import com.FangBianMian.utils.EasyuiDatagrid;

@RequestMapping("/admin/product")
@Controller
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	/**
	 * 替换ueditor编辑器里的图片路径为正式路径
	 * @param content
	 * @return
	 */
	public static String replaceImgSrc(String content){
		String imagePatternStr = "<img[\\w\\W]*?src=[\"|\']?([\\w\\W]*?)(jpg|png)[\\w\\W]*?/>";
		Pattern imagePattern = Pattern.compile(imagePatternStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = imagePattern.matcher(content);
		
		while (matcher.find()) {
			// img src中图片的url前缀
			String imageFragmentURL = matcher.group(1);
			// img src中图片的url后缀
			String imageFragmentSuffix = matcher.group(2);
		}
		
		return content;
	}
	
	public static void main(String[] args) {
		String content = "<p>黑人牙膏，谁用谁知道<img src=\"http://localhost:8099/ssm/temp/1476339719819_S.png\" title=\"\" alt=\"\"/></p>";
		System.out.println(replaceImgSrc(content));
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> del(@RequestParam(required=false) Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(id==null){
			map.put("flag",false);
			map.put("msg","删除失败");
			return map;
		}
		
		productService.deleteProductById(id);
		
		map.put("flag", true);
		map.put("msg", "删除成功");
		return map;
	}
	
	/**
	 * 商品列表页面
	 * @param model
	 * @param curr 被编辑商品的所在页数
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, 
					   @RequestParam(required=false) Integer curr,
					   @RequestParam(required=false) Integer size){
		if (curr!=null) {
			model.addAttribute("curr", curr);
			model.addAttribute("size", size);
		}
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
	public EasyuiDatagrid<Product> listData(@RequestParam(required=false) Integer page,
											@RequestParam(required=false) Integer rows,
											@RequestParam(required=false) String name,
			   								@RequestParam(required=false) Integer status,
			   								@RequestParam(required=false) Integer hot,
			   								@RequestParam(required=false) Integer category){
		EasyuiDatagrid<Product> ed = new EasyuiDatagrid<Product>();
		Map<String,Object> param = new HashMap<String,Object>();
		
		if(page!=null && rows!=null){
			param.put("rows", rows);
			param.put("page", ((page-1)*rows));
		}
		if(!StringUtils.isBlank(name)){
			param.put("name", name);
		}
		if(status!=null && status.intValue()!=-1){
			param.put("status", status);
		}
		if(category!=null && category.intValue()!=-1){
			param.put("category_id", category);
		}
		if(hot!=null && hot.intValue()!=-1){
			param.put("hot", hot);
		}
		
		List<Product> ps = DataValidation.isEmpty(productService.queryProductsByParam(param));
		int psTotal = productService.queryProductsByParamTotal(param);
		
		ed.setRows(ps);
		ed.setTotal(psTotal);
		return ed;
	}
	
	/**
	 * 商品添加和修改页面
	 * @param model
	 * @param id 商品id
	 * @param page 在列表页面修改商品时传入
	 * @param size 在列表页面修改商品时传入
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model, 
					  @RequestParam(required=false) Integer page,
					  @RequestParam(required=false) Integer size,
					  @RequestParam(required=false) Integer id){
		
		if(page!=null && size!=null){
			model.addAttribute("page", page);
			model.addAttribute("size", size);
		}
		
		if(id!=null){
			Product p = productService.queryProductById(id,null);
			if(p==null){
				model.addAttribute("msg", "未查询到商品记录");
			}else{
				//凑够5张图给thymeleaf遍历，让新增和编辑页面的图片保持一致
				List<ProductImg> pis = DataValidation.isEmpty(p.getImgs());
				int max = 5 - pis.size();
				for(int i = 0; i < max; i++){
					ProductImg pi = new ProductImg();
					pis.add(pi);
				}
				model.addAttribute("product", p);
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
	@ResponseBody
	public Map<String, Object> save(@RequestParam(required=false) Integer id,
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(name) || StringUtils.isBlank(detail) || quantity==null || price==null || category==null
		   || payment_type==null || status==null || hot==null || productImgs==null){
			map.put("flag", false);
			map.put("msg", "保存失败");
			return map;
		}
		
		Product p = new Product();
		p.setId(id);
		p.setName(name);
		p.setQuantity(quantity);
		p.setPrice(price);
		p.setDiscount_price(discount_price);
		p.setFreight_price(freight_price==null?0:freight_price);
		p.setCategory_id(category);
		p.setPayment_type(payment_type);
		p.setStatus(status);
		p.setHot(hot);
		p.setDetail(detail);
		
		productService.saveProduct(p, productImgs);
		
		map.put("flag", true);
		map.put("msg", "保存成功");
		return map;
	}
}
