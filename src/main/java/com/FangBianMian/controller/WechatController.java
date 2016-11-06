package com.FangBianMian.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.FangBianMian.wechant.utils.ReqVerifyUtil;

@RequestMapping("/wechat")
@Controller
public class WechatController {

	/** 
     * 确认请求来自微信服务器 
     */ 
	@RequestMapping("/ver")
	public void ver(HttpServletRequest request, HttpServletResponse response){
		try {
			// 微信加密签名  
	        String signature = request.getParameter("signature");  
	        // 时间戳  
	        String timestamp = request.getParameter("timestamp");  
	        // 随机数  
	        String nonce = request.getParameter("nonce");  
	        // 随机字符串  
	        String echostr = request.getParameter("echostr");  
	  
	        PrintWriter out = response.getWriter();
	        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
	        if (ReqVerifyUtil.checkSignature(signature, timestamp, nonce)) {  
	            out.print(echostr);  
	        }  
	        out.close();  
	        out = null; 
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
