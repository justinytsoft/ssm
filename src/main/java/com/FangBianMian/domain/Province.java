package com.FangBianMian.domain;

import java.util.List;

public class Province {

	private Integer id;
	private String name;

	private List<City> detailList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<City> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<City> detailList) {
		this.detailList = detailList;
	}

}
