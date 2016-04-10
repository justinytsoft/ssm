package com.FangBianMian.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.SecurityUser;
import com.FangBianMian.domain.TestUpload;
import com.FangBianMian.response.JsonResWrapper;
import com.FangBianMian.response.ResponseStatus;
import com.FangBianMian.service.IUserService;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.EasyUiTree;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping("/list")
	public String list(){
		
		return "pages/user/list";
	}
	
	@RequestMapping("/save_signup")
	@ResponseBody
	public JsonResWrapper save_signup(@RequestParam(required=false) String name, 
							  @RequestParam(required=false) String headTempName
							 ){
		JsonResWrapper jwr = new JsonResWrapper();
		if(StringUtils.isBlank(name) || StringUtils.isBlank(headTempName)){
			jwr.setStatus(ResponseStatus.FAILED_PARAM_LOST);
			jwr.setMessage("提交参数为空");
			return jwr;
		}
		try {
			String path = DataUtil.moveToDir(headTempName, true);
			TestUpload tu = new TestUpload();
			tu.setName(name);
			tu.setHead(path);
			userService.saveTestUpload(tu);
			jwr.setMessage("保存成功");
		} catch (IOException e) {
			e.printStackTrace();
			jwr.setStatus(ResponseStatus.BACK_EXCEPTION);
			jwr.setMessage(e.getMessage());
		}
		return jwr;
	}
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("/signup")
	public String signup(){
		return "pages/user/signup";
	}
	
	/**
	 * 获取用户菜单
	 * @param pid
	 * @return
	 */
	@RequestMapping("/getMenus")
	@ResponseBody
	public List<EasyUiTree> getMenus(HttpServletResponse response, @RequestParam(required=false) Integer pid){
		SecurityUser su = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(su == null){
			return new ArrayList<EasyUiTree>();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", su.getId());
		List<EasyUiTree> datas = userService.getMenus(param);
		return datas;
	}
}
