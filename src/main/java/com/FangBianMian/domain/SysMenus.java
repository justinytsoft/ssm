/**
 * 
 */
package com.FangBianMian.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author randy
 * 
 */
public class SysMenus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String url;
	private Integer parent_id;
	private String icon;

	private List<SysMenus> subMenus = new ArrayList<SysMenus>();

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public List<SysMenus> getSubMenus() {
		return subMenus;
	}
	
	public void addSubMenus(SysMenus sm) {
		subMenus.add(sm);
	}
	
	public void setSubMenus(List<SysMenus> subMenus) {
		this.subMenus = subMenus;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
