package com.FangBianMian.wechant.req;
/** 
 * 文本消息 
 */  
public class TextMessage extends BaseMessage {  
    // 消息内容  
    private String content;

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}  
}  