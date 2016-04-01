package com.FangBianMian.utils;

public class EasyUiTree {

	private Integer id; //当前节点的ID
	private Integer parentId; //父节点ID
	private String text; //显示文本
	private String state; //是否展开, open(默认值) 展开, closed 不展开
	private boolean checked; //指示节点是否被选中。
	private String attributes; //自定义属性
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
