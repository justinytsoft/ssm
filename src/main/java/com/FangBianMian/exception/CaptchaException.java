package com.FangBianMian.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常类
 * @author justi
 *
 */
public class CaptchaException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public CaptchaException(String msg) {
		super(msg);
	}

}
