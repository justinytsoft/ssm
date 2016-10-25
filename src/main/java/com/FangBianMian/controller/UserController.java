package com.FangBianMian.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.SecurityUser;
import com.FangBianMian.domain.User;
import com.FangBianMian.response.JsonResWrapper;
import com.FangBianMian.service.IUserService;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.EasyUiTree;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("/update_pwd")
	public String update_pwd(){
		return "pages/user/update_pwd";
	}
	
	/**
	 * 保存修改密码
	 * @return
	 */
	@RequestMapping("/save_new_pwd")
	@ResponseBody
	public JsonResWrapper save_new_pwd(Model model, HttpServletRequest request, 
							   @RequestParam String oldPwd,
							   @RequestParam String newPwd){
		JsonResWrapper jrw = new JsonResWrapper();
		User u = DataUtil.getSession(request);
		if(u==null || u.getId()==null){
			jrw.setFlag(false);
			jrw.setMessage("修改失败,请重新登录");
		}else{
			u = userService.queryUserById(u.getId());
			PasswordEncoder spe = new BCryptPasswordEncoder();
			if(spe.matches(oldPwd, u.getPassword())){
				u.setPassword(spe.encode(newPwd));
				userService.updateUser(u);
				jrw.setFlag(true);
				jrw.setMessage("修改成功");
			}else{
				jrw.setFlag(false);
				jrw.setMessage("修改失败,原密码错误");
			}
		}
		return jrw;
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
