package com.FangBianMian.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.constant.Common;
import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.City;
import com.FangBianMian.domain.PaymentType;
import com.FangBianMian.domain.Position;
import com.FangBianMian.domain.ProductCategory;
import com.FangBianMian.domain.Province;
import com.FangBianMian.domain.SecurityUser;
import com.FangBianMian.domain.SysRoles;
import com.FangBianMian.utils.Captcha;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.DataValidation;

@Controller
public class BaseController {

	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 获取支付方式
	 * @param request
	 * @param response
	 */
	@RequestMapping("/payment_type")
	@ResponseBody
	public List<PaymentType> payment_type(){
		List<PaymentType> pts = DataUtil.isEmpty(baseDao.queryPaymentType());
		return pts;
	}
	
	/**
	 * 获取省份
	 * @param request
	 * @param response
	 */
	@RequestMapping("/province")
	@ResponseBody
	public List<Province> province(@RequestParam(required=false) String extend){
		List<Province> ps = DataUtil.isEmpty(baseDao.queryProvince());
		//给返回的数据添加一个查询的逻辑元素
		if(!StringUtils.isBlank(extend)){
			Province p = new Province();
			p.setId(-1);
			p.setName("不限");
			ps.add(0, p);
		}
		return ps;
	}
	
	/**
	 * 获取城市
	 * @param request
	 * @param response
	 */
	@RequestMapping("/city")
	@ResponseBody
	public List<City> city(@RequestParam(required=false) Integer pid,@RequestParam(required=false) String extend){
		List<City> cs = DataUtil.isEmpty(baseDao.queryCityByPid(pid));
		//给返回的数据添加一个查询的逻辑元素
		if(!StringUtils.isBlank(extend)){
			City c = new City();
			c.setId(-1);
			c.setName("不限");
			cs.add(0, c);
		}
		return cs;
	}
	
	/**
	 * 获取区县
	 * @param request
	 * @param response
	 */
	@RequestMapping("/position")
	@ResponseBody
	public List<Position> position(@RequestParam(required=false) Integer cid,@RequestParam(required=false) String extend){
		List<Position> ps = DataUtil.isEmpty(baseDao.queryPositionByCid(cid));
		//给返回的数据添加一个查询的逻辑元素
		if(!StringUtils.isBlank(extend)){
			Position p = new Position();
			p.setId(-1);
			p.setName("不限");
			ps.add(0, p);
		}
		return ps;
	}
	
	/**
	 * 获取商品一级分类
	 * @param request
	 * @param response
	 */
	@RequestMapping("/product_category")
	@ResponseBody
	public List<ProductCategory> product_category(@RequestParam(required=false) String extend){
		List<ProductCategory> pcs = DataUtil.isEmpty(baseDao.queryProductCategory());
		//给返回的数据添加一个查询的逻辑元素
		if(!StringUtils.isBlank(extend)){
			ProductCategory pc = new ProductCategory();
			pc.setId(-1);
			pc.setName("不限");
			pcs.add(0, pc);
		}
		return pcs;
	}
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response){
		Captcha captcha = new Captcha();
		try {
			captcha.crimg(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录页面
	 * @param request
	 * @param model
	 * @param msg
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model, 
						@RequestParam(required=false) String msg) {
		
		if(msg == null){
			msg = (String) request.getAttribute("msg");
		}
		
		if(!StringUtils.isBlank(msg)){
			if(Common.LOGIN_FAILD.equals(msg)){
				model.addAttribute("msg", "用户名或密码错误");
			}else if(Common.SESSION_INVALID.equals(msg)){
				model.addAttribute("msg", "session过期,请重新登录");
			}else if(Common.SESSION_MULTI.equals(msg)){
				model.addAttribute("msg", "该帐号已从其他地方登录");
			}else if(Common.CAPTCHA_FAILD.equals(msg)){
				model.addAttribute("msg", "验证码错误");
			}else if(Common.CAPTCHA_GENERATED_FAILD.equals(msg)){
				model.addAttribute("msg", "验证码创建失败");
			}
		}
		return "pages/frame/login";
	}
	
	/**
	 * session异常类
	 * @param model
	 * @param msg
	 * @return
	 */
	@RequestMapping("/sessionException")
	public String sessionException(Model model, @RequestParam(required=false) String msg){
		model.addAttribute("msg", msg);
		return "pages/frame/sessionException";
	}
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user == null){
			return "redirect: sessionException?msg=SEESION.INVALID";
		}
		model.addAttribute("user", user);
		return "pages/frame/index";
	}
	
	/**
	 * 上
	 * @param model
	 * @return
	 */
	@RequestMapping("/top")
	public String top(Model model){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user == null){
			return "redirect: sessionException?msg=SEESION.INVALID";
		}
		model.addAttribute("user", user);
		return "pages/frame/top";
	}

	/**
	 * 下
	 * @param model
	 * @return
	 */
	@RequestMapping("/bottom")
	public String bottom(){
		if(!DataValidation.isLogin()){
			return "redirect: sessionException?msg=SEESION.INVALID";
		}
		return "pages/frame/bottom";
	}

	/**
	 * 左
	 * @param model
	 * @return
	 */
	@RequestMapping("/left")
	public String left(Model model){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user == null || user.getId()==null){
			return "redirect: sessionException?msg=SEESION.INVALID";
		}
		List<SysRoles> srs = DataUtil.isEmpty(user.getRoles());
		SysRoles sr = srs.size()==0?new SysRoles():srs.get(0);
		model.addAttribute("menus", DataUtil.isEmpty(sr.getMenus()));
		return "pages/frame/left";
	}

	/**
	 * 右
	 * @param model
	 * @return
	 */
	@RequestMapping("/right")
	public String right(){
		if(!DataValidation.isLogin()){
			return "redirect: sessionException?msg=SEESION.INVALID";
		}
		return "pages/frame/right";
	}
	
	/**
	 * 403
	 * @return
	 */
	@RequestMapping("/denied")
	public String denied(){
		return "error/403";
	}
	
	/**
	 * 404
	 * @return
	 */
	@RequestMapping("/notFound")
	public String notFound(){
		return "error/404";
	}
	
	/**
	 * 500
	 * @return
	 */
	@RequestMapping("/error")
	public String error(){
		return "error/500";
	}
}
