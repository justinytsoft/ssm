package com.FangBianMian.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.Orders;
import com.FangBianMian.domain.Product;
import com.FangBianMian.service.IOrderService;
import com.FangBianMian.service.IProductService;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.DataValidation;

@RequestMapping("/web/order")
@Controller
public class WEB_OrderController {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 订单首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model,
						@RequestParam(required=false) Integer flag){
		if(DataUtil.getSession(request)!=null){
			return "redirect: ../../index/center";
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
		Product p = productService.queryProductById(pid, true);
		if(pid==null || number==null || p==null){
			return "redirect: ../../index/center";
		}
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
	public String save(HttpServletRequest request,
					   @RequestParam(required=false) Integer pid,
					   @RequestParam(required=false) Integer number,
					   @RequestParam(required=false) Integer province,
					   @RequestParam(required=false) Integer city,
					   @RequestParam(required=false) Integer position,
					   @RequestParam(required=false) String address,
					   @RequestParam(required=false) String receiver,
					   @RequestParam(required=false) String phone,
					   @RequestParam(required=false) String message){
		
		//Member m = DataUtil.getSession(request);
		
		if(pid==null || number==null || province==null || city==null || position==null || address==null || receiver==null
				|| !(DataValidation.isMobile(phone) || DataValidation.isPhone(phone))){
			return "redirect: ../../index/center";
		}
		
		Product p = productService.queryProductById(pid, true);
		
		if(p==null){
			return "redirect: ../../index/center";
		}
		
		String provinceName = baseDao.queryProvinceNameById(province);
		String cityName = baseDao.queryCityNameById(city);
		String positionName = baseDao.queryPositionNameById(position);
		String fullAddress = provinceName + cityName + positionName + address;
		
		Orders o = new Orders();
		o.setAddress(fullAddress);
		
		return "redirect: index";
	}
}
