package com.FangBianMian.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlMappingLoginPageStrategy implements LoginPageStrategy {
	
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       
    	String targetUrl = null;
        String uri = request.getRequestURI();

        if (uri.indexOf("/merchant") != -1) {
            targetUrl = "/merchant/login";
        } else {
            targetUrl = "/login";
        }

        targetUrl = request.getContextPath() + targetUrl;
        response.sendRedirect(targetUrl);
    }
}