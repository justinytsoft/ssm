package com.FangBianMian.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.SecurityUser;
import com.FangBianMian.service.IUserService;
import com.FangBianMian.utils.EasyUiTree;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	/**
	 * 获取用户菜单
	 * @param pid
	 * @return
	 */
	@RequestMapping("/getMenus")
	@ResponseBody
	public List<EasyUiTree> getMenus(@RequestParam(required=false) Integer pid){
		SecurityUser su = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(su == null){
			return new ArrayList<EasyUiTree>();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", su.getId());
		//param.put("pid", (pid==null?0:pid));
		List<EasyUiTree> datas = userService.getMenus(param);
		return datas;
	}
}
