package com.FangBianMian.utils;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AlidayuSMS {
	
	public static void main(String[] args) {
		
		//官网的URL
		String url = "http://gw.api.taobao.com/router/rest";
		//成为开发者，创建应用后系统自动生成
		String appkey = "23655967";
		String secret = "17ad96cdbaa53099f1a5f44200da07b9";
		//短信模板的内容
		String json = JsonUtil.toJson(new AlidayuSMSBean());
		
		TaobaoClient client=new DefaultTaobaoClient(url,appkey,secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456"); //可选， 用于标识用户
		req.setSmsType("normal"); //默认使用 normal
		req.setSmsFreeSignName("个人测试"); //短信签名
		req.setSmsParam(json); //短信内容
		req.setSmsTemplateCode("SMS_50145002"); //短信模板ID
		req.setRecNum("18380426135");//手机号码，如果是多个手机号码可以用逗号隔开
		
		try{
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
		}catch(Exception e){
			System.out.println("连接失败");
		}
	}
}

