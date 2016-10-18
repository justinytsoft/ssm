package com.FangBianMian.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import com.FangBianMian.constant.Common;
import com.FangBianMian.domain.SecurityUser;
import com.FangBianMian.domain.User;

/**
 * 校验传入数据
 *
 */
public class DataValidation {
	
	/**
	 * 检查后台账户是否登录
	 * @return 是返回true， 否则返回false
	 */
	public static boolean isLogin(){
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user == null || user.getId()==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断集合是否为空
	 * @param list
	 * @return 为空则创建一个新的ArrayList返回, 否则, 直接返回
	 */
	@SuppressWarnings("hiding")
	public static <T> List<T> isEmpty(List<T> list){
		return CollectionUtils.isEmpty(list) ? new ArrayList<T>() : list;
	}
	
	/** 
     * 检测邮箱地址是否合法 
     * @param email 
     * @return true合法 false不合法 
     */  
    public static boolean isEmail(String email){  
    	if (null==email || "".equals(email)){
    		return false;    
    	}
		Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);  
		return m.matches();  
    }  
    
	/**
	 * 手机号验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) { 
		if(str==null){
			return false;
		}
		Pattern p = null;
		Matcher m = null;
		boolean b = false; 
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches(); 
		return b;
	}
	/**
	 * 电话号码验证
	 * 
	 * @param  str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) { 
		if(str==null){
			return false;
		}
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;  
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(str.length() >9)
		{	m = p1.matcher(str);
 		    b = m.matches();  
		}else{
			m = p2.matcher(str);
 			b = m.matches(); 
		}  
		return b;
	}
}
