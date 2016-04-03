package com.FangBianMian.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.response.JsonResWrapper;
import com.FangBianMian.response.ResponseStatus;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.FileUtils;

/**
 * 文件上传类
 * @author justi
 *
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private Log log = LogFactory.getLog(UploadController.class);

	/**
	 * 将图片上传到临时文件夹中
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/temp/img")  
	@ResponseBody
	public JsonResWrapper tempUpload(HttpServletRequest request){
		JsonResWrapper jrw = new JsonResWrapper();
		try {
			String path = DataUtil.uploadImg(request,true,0,0).get(0);
			if (!StringUtils.isBlank(path)) {
				String tempName = FileUtils.getFileName(path);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("path", path);
				map.put("tempName", tempName);
				jrw.setData(map);
			}else{
				jrw.setStatus(ResponseStatus.FAILED_PARAM_LOST);
				jrw.setMessage("请求参数为空");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			jrw.setStatus(ResponseStatus.BACK_EXCEPTION);
			jrw.setMessage("请求发生异常");
		}
		return jrw;
	}
}
