package com.FangBianMian.security;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.thymeleaf.util.StringUtils;

import com.FangBianMian.domain.SecurityUser;

/**
 * 登录验证成功处理器
 * @author Luxh
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
    
    private static Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);
    
    //登录验证成功后需要跳转的url
    private String defaultTargetUrl;
    //是转发到目的地还是重定向到目的地, true 转发
    private boolean forwardToDestination = false;
    
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,ServletException {
       
    	SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	request.getSession().setAttribute("user", user);
    	
    	if (StringUtils.isEmpty(defaultTargetUrl)){
    		log.error("have no configure defaultTargetUrl");
    		throw new BeanInitializationException("You must configure defaultTargetUrl");
    	}

    	log.info("登录验证成功：{}", request.getContextPath()+defaultTargetUrl);
    	
    	if(forwardToDestination){
    		log.info("Login success,Forwarding to " + this.defaultTargetUrl);
    		request.getRequestDispatcher(defaultTargetUrl).forward(request, response);
    	}else{
    		log.info("Login success,Redirecting to " + this.defaultTargetUrl);
    		response.sendRedirect(request.getContextPath()+defaultTargetUrl);
    	}
    }
    
	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
    
    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }
    
    public void setForwardToDestination(boolean forwardToDestination){
    	this.forwardToDestination = forwardToDestination;
    }

}