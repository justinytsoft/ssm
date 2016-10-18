package com.FangBianMian.constant;

public class Common {

	/**
	 * WEBSOCKET获取客户端的名称，以便可以定向的向某个客户端推送消息
	 */
	public static final String WEBSOCKET_USERNAME = "WEBSOCKET_USERNAME";
	
	/**
	 * 验证码
	 */
	public static final String SESSION_GENERATED_CAPTCHA_KEY = "CAPTCHA_TOKEN";
	
	/**
	 * 后台用户session
	 */
	public static final String USER_SESSION = "USER_SESSION";

	/**
	 * 普通用户session
	 */
	public static final String MEMBER_SESSION = "MEMBER_SESSION";
	
	/**
	 * 用户名和密码校验失败
	 */
	public static final String LOGIN_FAILD = "LOGIN.FAILD";
	
	/**
	 * 验证码校验失败
	 */
	public static final String CAPTCHA_FAILD = "CAPTCHA.FAILD";
	
	/**
	 * 验证码创建失败
	 */
	public static final String CAPTCHA_GENERATED_FAILD = "CAPTCHA.GENERATED.FAILD";
	
	/**
	 * session失效
	 */
	public static final String SESSION_INVALID = "SESSION.INVALID";
	
	/**
	 * 一个账户多次登陆
	 */
	public static final String SESSION_MULTI = "SESSION.MULTI";
}
