package com.FangBianMian.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.service.IMemberService;
import com.FangBianMian.utils.DataValidation;

@RequestMapping("/web/user")
@Controller
public class WEB_UserController {

	@Autowired
	private IMemberService memberService;
	
	/**
	 * 用户登录
	 * @param model
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping("/signin")
	public String signin(Model model,
						@RequestParam(required=false) String phone,
						@RequestParam(required=false) String code){
		if(StringUtils.isBlank(code) || !DataValidation.isMobile(phone)){
			model.addAttribute("phone", phone);
			model.addAttribute("code", code);
			model.addAttribute("msg", "登录失败");
			return "web/login";
		}
	
		return "redirect: ../index/main";
	}
}
