package com.FangBianMian.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.FangBianMian.utils.SettingUtil;

/**
 * 文件上传类
 * 
 * @author justi
 *
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private Log log = LogFactory.getLog(UploadController.class);

	/**
	 * 将图片上传到临时文件夹中
	 * 
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/temp/img")
	@ResponseBody
	public JsonResWrapper tempUpload(HttpServletRequest request) {
		JsonResWrapper jrw = new JsonResWrapper();
		try {
			String path = DataUtil.uploadImg(request, true, 0, 0).get(0);
			String[] str = FileUtils.getFileNameAndExtension(path);
			if (!StringUtils.isBlank(path)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("path", str[0]+"_S"+str[1]);
				map.put("tempName", path);
				jrw.setData(map);
			} else {
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

	/**
	 * 保存富文本编辑器上传的文件到临时目录
	 * 
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/temp/editorImg")
	@ResponseBody
	public void editorImgUpload(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");  
		try {
	        PrintWriter out = response.getWriter();  
	        // CKEditor提交的很重要的一个参数  
	        String callback = request.getParameter("CKEditorFuncNum");
			String path = DataUtil.uploadImg(request, true).get(0);
			if (StringUtils.isBlank(path)) {
				out.println("<script type=\"text/javascript\">");
				out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件上传失败');");
				out.println("</script>");
			}else{
				String fileDir = SettingUtil.getCommonSetting("base.temp.url");
				// 返回"图像"选项卡并显示图片  request.getContextPath()为web项目名   
		        out.println("<script type=\"text/javascript\">");  
		        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileDir + path + "','')");  
		        out.println("</script>"); 
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
