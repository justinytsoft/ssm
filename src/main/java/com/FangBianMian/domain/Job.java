package com.FangBianMian.domain;

import java.util.Date;

public class Job {
	private Integer id;
	private Integer company_id;
	private String person;
	private String jobName;
	private String phone;
	private String experience;
	private String education;
	private String address;
	private String age;
	private String language;
	private String salary;
	private String description;
	private Integer number;
	private String duty;
	private Date create_time;
	private String posts_time;
	private String require;
	private String delete;
	private String work_type;
	private Integer province;
	private Integer city;
	private Integer jobType;
	private Integer is_new;
	private String logo;
	private Integer status; //职位状�??: 0 招聘�?, 1 招聘结束
	private Date refresh_time; //刷新时间

	private Boolean isComm;// 是否佣金
	private String commission;

	private String company_name;
	private String cityName; 
	private String provinceName;
	private String detailUrl;
	private String head_img;
	private Boolean istop;
	private Integer top_status;
	private Date top_time;
	private Integer receiveResumeNum; //接收到的�?历数�?

	private Boolean is_recruiter;
	private Boolean has_resume;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getRequire() {
		return require;
	}

	public void setRequire(String require) {
		this.require = require;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getWork_type() {
		return work_type;
	}

	public void setWork_type(String work_type) {
		this.work_type = work_type;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public Boolean getIstop() {
		return istop;
	}

	public void setIstop(Boolean istop) {
		this.istop = istop;
	}

	public Date getTop_time() {
		return top_time;
	}

	public void setTop_time(Date top_time) {
		this.top_time = top_time;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Boolean getIs_recruiter() {
		return is_recruiter;
	}

	public void setIs_recruiter(Boolean is_recruiter) {
		this.is_recruiter = is_recruiter;
	}

	public Boolean getHas_resume() {
		return has_resume;
	}

	public void setHas_resume(Boolean has_resume) {
		this.has_resume = has_resume;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Boolean getIsComm() {
		return isComm;
	}

	public void setIsComm(Boolean isComm) {
		this.isComm = isComm;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public Integer getJobType() {
		return jobType;
	}

	public void setJobType(Integer jobType) {
		this.jobType = jobType;
	}

	

	public Integer getIs_new() {
		return is_new;
	}

	public void setIs_new(Integer is_new) {
		this.is_new = is_new;
	}

	public String getPosts_time() {
		return posts_time;
	}

	public void setPosts_time(String posts_time) {
		this.posts_time = posts_time;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getReceiveResumeNum() {
		return receiveResumeNum;
	}

	public void setReceiveResumeNum(Integer receiveResumeNum) {
		this.receiveResumeNum = receiveResumeNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getRefresh_time() {
		return refresh_time;
	}

	public void setRefresh_time(Date refresh_time) {
		this.refresh_time = refresh_time;
	}

	public Integer getTop_status() {
		return top_status;
	}

	public void setTop_status(Integer top_status) {
		this.top_status = top_status;
	}

}
