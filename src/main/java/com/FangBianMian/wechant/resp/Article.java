package com.FangBianMian.wechant.resp;
/** 
 * 图文model 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class Article {  
    // 图文消息名称  
    private String title;  
    // 图文消息描述  
    private String description;  
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致  
    private String picUrl;  
    // 点击图文消息跳转链接  
    private String url;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}  