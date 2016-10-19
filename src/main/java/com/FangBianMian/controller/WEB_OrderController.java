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

import com.FangBianMian.constant.OrderStatus;
import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.Member;
import com.FangBianMian.domain.Orders;
import com.FangBianMian.domain.OrdersItem;
import com.FangBianMian.domain.OrdersLog;
import com.FangBianMian.domain.Product;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.domain.ProductComment;
import com.FangBianMian.service.IMemberService;
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
	@Autowired
	private IMemberService memberService;
	
	/**
	 * 更新订单状态
	 * @param oid
	 * @param express
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request,
									  @RequestParam(required=false) Integer oid,
									  @RequestParam(required=false) Integer status,
									  @RequestParam(required=false) Integer score,
									  @RequestParam(required=false) String comment){
		
		Map<String,Object> map = new HashMap<String,Object>();
		Member m = DataUtil.getMemberSession(request);

		if(m==null){
			map.put("flag", false);
			return map;
		}
		
		if(oid==null || status==null){
			map.put("flag", false);
			return map;
		}
		
		if(status==6){ //删除订单
			orderService.deleteOrderById(oid);
		}else{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("oid", oid);
			param.put("status", status);
			//更新订单状态
			orderService.updateOrderStatus(param);
		}
		
		//评论商品
		if(!StringUtils.isBlank(comment)){
			Orders o = orderService.queryOrdersByOid(oid);
			OrdersItem oi = o.getItems().get(0);
			
			ProductComment pc = new ProductComment();
			pc.setMid(m.getId());
			pc.setPid(oi.getPid());
			pc.setComment(comment);
			pc.setScore(score==null?0:score);
			pc.setName(m.getUsername());
			productService.insertProductComment(pc);
		}
		
		OrdersLog ol = new OrdersLog();
		ol.setOid(oid);
		ol.setStatus(status);
		//保存订单日志
		if(status==OrderStatus.WAIT_COMMENT){
			ol.setContent("用户已确认收货");
		}else if(status==OrderStatus.DONE){
			ol.setContent("用户对商品进行了评价；  评分：" + score + " 评论：" + comment);
		}
		orderService.insertOrderLog(ol);
		
		map.put("flag", true);
		return map;
	}
	
	/**
	 * 订单首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model,
						@RequestParam(required=false) Integer page,
						@RequestParam(required=false) Integer rows,
						@RequestParam(required=false) Integer status){
		Member m = DataUtil.getMemberSession(request);
		if(m==null){
			return "redirect: ../index/login";
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mid", m.getId());
		param.put("invisible", 0);
		if(page!=null && rows!=null){
			param.put("page", ((page-1)*rows));
			param.put("rows", rows);
			model.addAttribute("page", page);
			model.addAttribute("rows", rows);
		}
		if(status!=null){
			param.put("status",status);
			model.addAttribute("status", status);
		}
		
		List<Orders> os = orderService.queryOrdersByParam(param);
		int total = orderService.queryOrdersByParamTotal(param);
		
		model.addAttribute("os", os);
		model.addAttribute("total", total);
		model.addAttribute("orderStatus", new OrderStatus());
		return "web/order";
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
						  @RequestParam(required=false) Integer number,
						  @RequestParam(required=false) Integer flag){
		Product p = productService.queryProductById(pid, true);
		if(pid==null || number==null || p==null){
			return "redirect: ../index/center";
		}
		
		if(flag!=null){
			if(flag==1){
				model.addAttribute("msg", "商品库存不足");
			}else if(flag==2){
				model.addAttribute("msg", "您的余额不足");
			}
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
		
		Member m = DataUtil.getMemberSession(request);

		if(m==null){
			return "redirect: ../index/center";
		}
		
		if(pid==null || number==null || province==null || city==null || position==null || address==null || receiver==null
				|| !(DataValidation.isMobile(phone) || DataValidation.isPhone(phone))){
			return "redirect: ../index/center";
		}
		
		Product p = productService.queryProductById(pid, true);
		
		if(p==null){
			return "redirect: ../index/center";
		}
		
		//判断库存是否足够
		if(p.getQuantity().intValue() < number){
			return "redirect: confirm?pid="+pid+"&number="+number+"&flag=1";
		}
		
		m = memberService.queryMemberByUsername(m.getUsername());
		
		//判断用户余额是否足够
		if(m.getBalance().floatValue() < (p.getPrice().floatValue() * number + p.getFreight_price().floatValue())){
			return "redirect: confirm?pid="+pid+"&number="+number+"&flag=2";
		}
		
		//拼接收货地址
		String provinceName = baseDao.queryProvinceNameById(province);
		String cityName = baseDao.queryCityNameById(city);
		String positionName = baseDao.queryPositionNameById(position);
		String fullAddress = provinceName + cityName + positionName + address;
		
		//商品总价
		float total = p.getPrice().floatValue() * number + p.getFreight_price().floatValue();
		
		//保存订单
		Orders o = new Orders();
		o.setMid(m.getId());
		o.setSn(DataUtil.createOrderNO());
		o.setAmount(total);
		o.setAmount_paid(total);
		o.setReceiver(receiver);
		o.setPhone(phone);
		o.setAddress(fullAddress);
		o.setStatus(OrderStatus.WAIT_SEND);
		o.setFreight(p.getFreight_price());
		o.setMessage(message);
		o.setPayment_type("余额支付");
		o.setDiscount(null);
		o.setPostcode(null);
		orderService.insertOrder(o);
		
		//保存订单商品条目
		OrdersItem oi = new OrdersItem();
		oi.setOid(o.getId());
		oi.setPid(p.getId());
		oi.setImage(p.getDefault_img());
		oi.setName(p.getName());
		oi.setPrice(p.getPrice());
		oi.setQuantity(number);
		ProductCategory pc = productService.queryProductCategoryById(p.getCategory_id());
		oi.setCategory(pc.getName());
		orderService.insertOrderItem(oi);
		
		//保存订单日志
		OrdersLog ol = new OrdersLog();
		ol.setOid(o.getId());
		ol.setStatus(o.getStatus());
		ol.setContent("用户完成下单");
		orderService.insertOrderLog(ol);
		
		//更新商品库存
		Product product = new Product();
		product.setId(p.getId());
		product.setQuantity(p.getQuantity().intValue() - number);
		productService.updateProduct(product);
		
		//更新用户余额
		Member member = new Member();
		member.setUsername(m.getUsername());
		member.setBalance(m.getBalance().floatValue() - total);
		memberService.updateMember(member);
		
		return "redirect: index";
	}
}
