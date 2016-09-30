package com.FangBianMian.webservice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.FangBianMian.response.JsonResWrapper;
import com.FangBianMian.service.IUserService;

@RestController
@RequestMapping(value = "/ws/upload")
public class WSUpload {

	@Autowired
	private IUserService userService;
	
	/**
	 * 上传图片到正式目录
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/uploadHeadImage", method = RequestMethod.POST)
	public @ResponseBody JsonResWrapper headImage(HttpServletRequest request, Model model, @RequestParam(required=false) Integer userId) {
		JsonResWrapper response = new JsonResWrapper();
		return response;
	}
}
