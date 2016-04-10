package com.FangBianMian.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

public class LoginPageEntryPoint implements AuthenticationEntryPoint,
    InitializingBean {
	
    private LoginPageStrategy loginPageStrategy;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(loginPageStrategy, "loginPageStrategy must be specified");
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    																			throws IOException, ServletException {
    	loginPageStrategy.process((HttpServletRequest) request, (HttpServletResponse) response);
    }

    public void setLoginPageStrategy(LoginPageStrategy loginPageStrategy) {
        this.loginPageStrategy = loginPageStrategy;
    }
}