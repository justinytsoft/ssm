package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.response.JsonResWrapper;
import com.FangBianMian.response.ResponseStatus;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.SettingUtil;

@RequestMapping("/upload")
@Controller
public class UploadController {
	/**
	 * 上传图片到临时文件
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody 
	public JsonResWrapper uploadImage(Model model, HttpServletRequest request, 
										@RequestParam(required=false) boolean isTemp) {
		JsonResWrapper response = new JsonResWrapper();
		try {
			List<String> paths = DataUtil.uploadImg(request, isTemp);
			Map<String ,Object> param = new HashMap<String,Object>();
			param.put("prefix", isTemp ? SettingUtil.getCommonSetting("base.temp.url") : SettingUtil.getCommonSetting("base.image.url"));
			param.put("paths", paths);
			response.setData(param);
			return response;
		} catch (Exception e) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	/**
	 * 上传普通文件到临时文件
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody 
	public JsonResWrapper uploadFile(Model model, HttpServletRequest request) {
		JsonResWrapper response = new JsonResWrapper();
		try {
			List<String> paths = DataUtil.uploadFile(request, true);
			Map<String ,Object> param = new HashMap<String,Object>();
			param.put("paths", paths);
			response.setData(param);
			return response;
		} catch (Exception e) {
			response.setStatus(ResponseStatus.FAILED);
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
