package com.FangBianMian.domain;

import java.util.Date;

/**
 * 配送时间类
 * @author justi
 *
 */
public class DeliveryTime {

	private Integer id;
	private Date time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
