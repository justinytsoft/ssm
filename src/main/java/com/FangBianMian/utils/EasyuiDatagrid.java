package com.FangBianMian.utils;

import java.util.List;

/**
 * easyui的datagrid使用
 * @author dreamtec
 *
 */
public class EasyuiDatagrid<T> {

	private Integer total; //总共多少条记录
	private List<T> rows; //返回的数据集合
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
