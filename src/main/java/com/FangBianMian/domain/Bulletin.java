package com.FangBianMian.domain;

import java.util.Date;

/**
 * 公告类
 * @author Administrator
 *
 */
public class Bulletin {

	private Integer id;
	private String title;
	private String content;
	private Integer top;
	private Integer invisible;
	private Integer type;
	private Date create_time;
	
	private String type_name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getInvisible() {
		return invisible;
	}
	public void setInvisible(Integer invisible) {
		this.invisible = invisible;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
}
