package com.FangBianMian.constant;

/**
 * 登录状态
 * @author Administrator
 *
 */
public class LoginStatus {
	
	/**
	 * 等待发送验证码
	 */
	public static final Integer WAIT_SEND_VERIFY_CODE = -2;

	/**
	 * 验证码已发送
	 */
	public static final Integer VERIFY_CODE_SENT = -1;

	/**
	 * 等待登录
	 */
	public static final Integer WAIT_LOGIN = 0;
	
	/**
	 * 登录成功
	 */
	public static final Integer SUCCESS = 1;
	
	/**
	 * 登录失败
	 */
	public static final Integer FIALD = 2;
}
