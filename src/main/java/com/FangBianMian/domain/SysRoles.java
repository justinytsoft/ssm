/**
 * 
 */
package com.FangBianMian.domain;

/**
 * @author randy
 * 
 */
public class SysRoles {
	
	/**
	 * 超级管理员
	 */
	public static final Integer ADMIN = 1;
	/**
	 * 技术干部
	 */
	public static final Integer USER = 2;
	
	private Integer id;
	private String name;
	private String code;

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

}
