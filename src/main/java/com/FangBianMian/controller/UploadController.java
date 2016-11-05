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
	 * 上传图片， 默认 不压缩图片，存放在正式目录
	 * @param model
	 * @param request
	 * @param thumb 是否压缩图片 true 压缩 false 不压缩
	 * @param temp 存放位置 true 临时目录 false 正式目录
	 * @param fileSize 限制上传文件的大小，默认为配置文件中的限制
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody 
	public JsonResWrapper uploadImage(Model model, HttpServletRequest request, 
										@RequestParam(required=false) boolean thumb,
										@RequestParam(required=false) boolean temp,
										@RequestParam(required=false) Long fileSize) {
		JsonResWrapper response = new JsonResWrapper();
		try {
			List<String> paths = null;
			fileSize = fileSize==null?Long.parseLong(SettingUtil.getCommonSetting("upload.image.maxSize")):fileSize;
			if(thumb){
				paths = DataUtil.uploadImg(request, temp, 0, 0, fileSize);
			}else{
				paths = DataUtil.uploadImg(request, temp, fileSize);
			}
			Map<String ,Object> param = new HashMap<String,Object>();
			param.put("prefix", temp ? SettingUtil.getCommonSetting("base.temp.url") : SettingUtil.getCommonSetting("base.image.url"));
			param.put("paths", paths);
			response.setData(param);
			return response;
		} catch (Exception e) {
			response.setFlag(false);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	/**
	 * 上传普通文件到临时文件夹
	 * @param model
	 * @param request
	 * @param temp 存放位置 true 临时目录 false 正式目录
	 * @param fileSize 限制上传文件的大小，默认为配置文件中的限制
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody 
	public JsonResWrapper uploadFile(Model model, HttpServletRequest request,
									@RequestParam(required=false) boolean temp,
									@RequestParam(required=false) Long fileSize) {
		JsonResWrapper response = new JsonResWrapper();
		try {
			fileSize = fileSize==null?Long.parseLong(SettingUtil.getCommonSetting("upload.file.maxSize")):fileSize;
			List<String> paths = DataUtil.uploadFile(request, temp, fileSize);
			Map<String ,Object> param = new HashMap<String,Object>();
			param.put("paths", paths);
			response.setData(param);
			return response;
		} catch (Exception e) {
			response.setFlag(false);
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
