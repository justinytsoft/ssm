package com.FangBianMian.wechant.req;
/** 
 * 图片消息 
 */  
public class ImageMessage extends BaseMessage {  
    // 图片链接  
    private String picUrl;

	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}  
}  