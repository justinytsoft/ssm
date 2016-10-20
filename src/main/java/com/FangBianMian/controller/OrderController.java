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

import com.FangBianMian.constant.OrderStatus;
import com.FangBianMian.domain.Orders;
import com.FangBianMian.domain.OrdersLog;
import com.FangBianMian.service.IOrderService;
import com.FangBianMian.utils.DataValidation;
import com.FangBianMian.utils.DateFormatter;
import com.FangBianMian.utils.EasyuiDatagrid;

/**
 * 订单类
 * @author Administrator
 *
 */
@RequestMapping("/admin/order")
@Controller
public class OrderController {

	@Autowired
	private IOrderService orderService;
	
	/**
	 * 更新订单状态为发货
	 * @param oid
	 * @param express
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(@RequestParam(required=false) Integer oid,
									  @RequestParam(required=false) String express){
		Map<String,Object> map = new HashMap<String,Object>();
		if(oid==null || StringUtils.isBlank(express)){
			map.put("flag", false);
			map.put("msg", "参数为空");
			return map;
		}
		
		//更新订单状态
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("oid", oid);
		param.put("status", OrderStatus.WAIT_RECEVIE);
		param.put("express", express);
		orderService.updateOrderStatus(param);
		
		//保存订单日志
		OrdersLog ol = new OrdersLog();
		ol.setOid(oid);
		ol.setStatus(OrderStatus.WAIT_RECEVIE);
		ol.setContent("订单已发货");
		orderService.insertOrderLog(ol);
		
		map.put("flag", true);
		map.put("msg", "发货成功");
		return map;
	}
	
	/**
	 * 订单详情页
	 * @param model
	 * @param id
	 * @param curr
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(Model model, @RequestParam(required=false) Integer id,
						 @RequestParam(required=false) Integer curr,
						 @RequestParam(required=false) Integer size){
		if(id==null){
			return "redirect: list";
		}
		if(curr!=null && size!=null){
			model.addAttribute("curr", curr);
			model.addAttribute("size", size);
		}
		Orders o = orderService.queryOrdersByOid(id);
		model.addAttribute("o", o);
		return "pages/order/detail";
	}
	
	/**
	 * 订单管理页面
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, 
					   @RequestParam(required=false) Integer curr,
					   @RequestParam(required=false) Integer size){
		if(curr!=null && size!=null){
			model.addAttribute("curr", curr);
			model.addAttribute("size", size);
		}
		return "pages/order/list";
	}
	
	/**
	 * 订单列表数据
	 * @param name
	 * @param status
	 * @return
	 */
	@RequestMapping("/listData")
	@ResponseBody
	public EasyuiDatagrid<Orders> listData(@RequestParam(required=false) Integer page,
											@RequestParam(required=false) Integer rows,
											@RequestParam(required=false) String sn,
											@RequestParam(required=false) String sdate,
											@RequestParam(required=false) String edate,
			   								@RequestParam(required=false) Integer status){
		EasyuiDatagrid<Orders> ed = new EasyuiDatagrid<Orders>();
		Map<String,Object> param = new HashMap<String,Object>();
		
		try{
			if(page!=null && rows!=null){
				param.put("rows", rows);
				param.put("page", ((page-1)*rows));
			}
			if(!StringUtils.isBlank(sn)){
				param.put("sn", sn);
			}
			if(status!=null && status.intValue()!=-1){
				param.put("status", status);
			}
			if(!StringUtils.isBlank(sdate)){
				sdate += " 00:00:00";
				param.put("sdate", DateFormatter.parseByStr(sdate, DateFormatter.DATE_TIME));
			}
			if(!StringUtils.isBlank(edate)){
				edate += " 23:59:59";
				param.put("edate", DateFormatter.parseByStr(edate, DateFormatter.DATE_TIME));
			}
		
			List<Orders> os = DataValidation.isEmpty(orderService.queryOrdersByParam(param));
			int total = orderService.queryOrdersByParamTotal(param);
			
			ed.setRows(os);
			ed.setTotal(total);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ed;
	}
}
