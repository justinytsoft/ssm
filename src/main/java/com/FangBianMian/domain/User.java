package com.FangBianMian.domain;

import java.util.Date;

public class User {

	private Integer id;
	private String username;
	private String name;
	private String password;
	private boolean enabled;
	private Date create_time;
	private Date last_login_time;
	private Integer praise;
	private Integer posts_num;
	private Integer sex;
	private Integer share_count;
	private Integer shareCount;
	private Integer level_count;
	private String verify_code;

	private String head;
	private String code;
	private String higher_code;
	private String nick_name;
	private String level_name;
	private Integer type;
	private Integer level;
	private boolean overdue;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}
	public Integer getPraise() {
		return praise;
	}
	public void setPraise(Integer praise) {
		this.praise = praise;
	}
	public Integer getPosts_num() {
		return posts_num;
	}
	public void setPosts_num(Integer posts_num) {
		this.posts_num = posts_num;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getShare_count() {
		return share_count;
	}
	public void setShare_count(Integer share_count) {
		this.share_count = share_count;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Integer getLevel_count() {
		return level_count;
	}
	public void setLevel_count(Integer level_count) {
		this.level_count = level_count;
	}
	public String getVerify_code() {
		return verify_code;
	}
	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getHigher_code() {
		return higher_code;
	}
	public void setHigher_code(String higher_code) {
		this.higher_code = higher_code;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public boolean isOverdue() {
		return overdue;
	}
	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
	}
	
	/**
	 *  阻止同一个用户第二次登录时需要使用
	 */
	@Override
	public boolean equals(Object obj) {
	if (obj instanceof User) {  
	       if (this.id.equals(((User) obj).getId()))  
	           return true;  
	   }  
	   return false;  
	}

	@Override
	public int hashCode() {
		return this.id.hashCode(); 
	}
}
