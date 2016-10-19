package com.FangBianMian.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.constant.Common;
import com.FangBianMian.domain.Member;
import com.FangBianMian.service.IMemberService;
import com.FangBianMian.utils.DataValidation;
import com.FangBianMian.utils.DateFormatter;

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
	@ResponseBody
	public Map<String, Object> signin(HttpServletRequest request,
						 @RequestParam(required=false) String phone,
						 @RequestParam(required=false) String code){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isBlank(code) || !DataValidation.isMobile(phone)){
			map.put("flag",false);
			return map;
		}
		
		Member m = memberService.queryMemberByUsername(phone);
		if(m==null){
			m = new Member();
			m.setBalance(0f);
			m.setLogin_num(1);
			m.setStatus(0);
			m.setUsername(phone);
			m.setPassword(code);
			m.setCreate_time(new Date());
			m.setMessage(DateFormatter.formatDateTime(new Date()));
			memberService.insertMember(m);
		}else{
			m.setStatus(0);
			m.setPassword(code);
			m.setLogin_num(m.getLogin_num().intValue()+1);
			m.setMessage(m.getMessage() + "," + DateFormatter.formatDateTime(new Date()));
			memberService.updateMember(m);
		}
	
		request.getSession().setAttribute(Common.MEMBER_SESSION_WAIT, m);
		request.getSession().setAttribute(Common.WEBSOCKET_USERNAME, m.getUsername());
		
		map.put("flag", true);
		return map;
	}
}
