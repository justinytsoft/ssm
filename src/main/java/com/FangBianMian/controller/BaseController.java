package com.FangBianMian.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.utils.encoder.EncoderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.dao.IBaseDao;
import com.FangBianMian.domain.SecurityUser;
import com.FangBianMian.utils.Captcha;
import com.FangBianMian.utils.Constant;

@Controller
public class BaseController {

	@Autowired
	private IBaseDao baseDao;
	
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

	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model, @RequestParam(required=false) String msg) {
		
		if(msg == null){
			msg = (String) request.getAttribute("msg");
		}
		
		if(!StringUtils.isBlank(msg)){
			if(Constant.LOGIN_FAILD.equals(msg)){
				model.addAttribute("msg", "用户名或密码错误");
			}else if(Constant.SESSION_INVALID.equals(msg)){
				model.addAttribute("msg", "session过期,请重新登录");
			}else if(Constant.SESSION_MULTI.equals(msg)){
				model.addAttribute("msg", "该帐号已从其他地方登录");
			}else if(Constant.CAPTCHA_FAILD.equals(msg)){
				model.addAttribute("msg", "验证码错误");
			}else if(Constant.CAPTCHA_GENERATED_FAILD.equals(msg)){
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
	
	@RequestMapping("/index")
	public String index(Model model){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user == null){
			return "redirect: sessionException?msg=SEESION.INVALID";
		}
		model.addAttribute("user", user);
		return "pages/frame/index";
	}
	
	@RequestMapping("/top")
	public String top(){
		return "pages/frame/top";
	}

	@RequestMapping("/bottom")
	public String bottom(){
		return "pages/frame/bottom";
	}
	
	@RequestMapping("/left")
	public String left(){
		return "pages/frame/left";
	}
	
	@RequestMapping("/right")
	public String right(){
		return "pages/frame/right";
	}
}
