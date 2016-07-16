/**
 * 
 */
package com.FangBianMian.domain;

import java.util.List;

/**
 * @author randy
 * 
 */
public class SysRoles {
	
	private Integer id;
	private String name;
	private String code;
	
	private List<SysMenus> menus;

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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	public List<SysMenus> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenus> menus) {
		this.menus = menus;
	}
}
