package com.FangBianMian.response;

/**
 * 返回响应状态类
 * @author justi
 *
 */
public class ResponseStatus {
	/**
	 * 请求成功
	 */
	public static final String OK = "200";
	
	/**
	 * 请求失败
	 */
	public static final String FAILED = "201";
	
	/**
	 * 参数为空
	 */
	public static final String FAILED_PARAM_LOST = "202";
	
	/**
	 * 账号已存在
	 */
	public static final String FAILED_REG_EXISTS = "203";
	
	/**
	 * 后台异常
	 */
	public static final String BACK_EXCEPTION = "400";
}
