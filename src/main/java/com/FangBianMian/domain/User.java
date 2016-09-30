package com.FangBianMian.domain;

import java.util.Date;
import java.util.List;

public class User {

	private Integer id;
	private String username;
	private String password;
	private String head;
	private Integer type;
	private String name;
	private boolean enabled;
	private Date last_login_time;
	private String last_login_ip;
	private Date create_time;
	
	private List<SysRoles> roles;
	
	/**
	 *  阻止同一个用户第二次登录时需要使用
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {  
	       if (this.id.equals(((User) obj).getId()))  
	           return true;  
	   }  
	   return true;  
	}

	@Override
	public int hashCode() {
		return this.id.hashCode(); 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public String getLast_login_ip() {
		return last_login_ip;
	}

	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public List<SysRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRoles> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
