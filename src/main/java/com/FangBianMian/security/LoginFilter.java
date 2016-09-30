package com.FangBianMian.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.FangBianMian.exception.CaptchaException;
import com.FangBianMian.utils.Constant;

/**
 * 增加了验证码的验证
 * @author justi
 *
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter{
       
	public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";  
    public static final String SESSION_GENERATED_CAPTCHA_KEY = Constant.SESSION_GENERATED_CAPTCHA_KEY;  
      
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;  
      
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {  
          
    	//设置拦截表单提交方式, 默认(true)只能处理POST, false不限
    	//setPostOnly(false);
    	
    	//获取登录帐号的类型, 是否是后台
    	String type = (String) request.getParameter("type");
    	request.getSession().setAttribute("type", type);
    	
    	//验证码校验
        String genCode = this.obtainGeneratedCaptcha(request);  
        String inputCode = this.obtainCaptcha(request);  
        
        if(genCode == null){  
        	request.setAttribute("msg", "CAPTCHA.GENERATED.FAILD");
            throw new CaptchaException("LoginAuthentication.captchaInvalid");  
        }
        if(!genCode.equalsIgnoreCase(inputCode)){  
        	request.setAttribute("msg", "CAPTCHA.FAILD");
            throw new CaptchaException("LoginAuthentication.captchaNotEquals");  
        }  
        
        Authentication auth = null;
        try{
        	auth = super.attemptAuthentication(request, response);
        }catch(AuthenticationException e){
        	request.setAttribute("msg", "LOGIN.FAILD");
        	throw e;
        }
        
        return auth;
    }  
      
    protected String obtainCaptcha(HttpServletRequest request){  
        return request.getParameter(this.captchaParameter);  
    }  
      
    protected String obtainGeneratedCaptcha (HttpServletRequest request){  
        return (String)request.getSession().getAttribute(SESSION_GENERATED_CAPTCHA_KEY);  
    }  

}
