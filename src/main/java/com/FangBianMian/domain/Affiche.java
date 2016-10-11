package com.FangBianMian.domain;

import java.util.Date;

/**
 * 公告类
 * @author Administrator
 *
 */
public class Affiche {

	private Integer id;
	private String title;
	private String content;
	private Date create_time;
	
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
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}
