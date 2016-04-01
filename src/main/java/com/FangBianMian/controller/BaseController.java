package com.FangBianMian.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.domain.SecurityUser;

@Controller
public class BaseController {

/*	@Autowired
	private BaseDao baseDao;*/
	
	@RequestMapping("/login")
	public String login(Model model, @RequestParam(required=false) String msg) {
		if(!StringUtils.isBlank(msg)){
			if("LOGIN.FAILD".equals(msg)){
				model.addAttribute("msg", "用户名或密码错误");
			}else if("SESSION.INVALID".equals(msg)){
				model.addAttribute("msg", "session过期,请重新登录");
			}else if("SESSION.MULTI".equals(msg)){
				model.addAttribute("msg", "该帐号已从其他地方登录");
			}
		}
		return "pages/frame/login";
	}
	
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
