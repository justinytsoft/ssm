package com.FangBianMian.security;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.FangBianMian.constant.UserType;
import com.FangBianMian.dao.IUserDao;
import com.FangBianMian.domain.SecurityUser;

/**
 * 登录验证处理类
 * @author dreamtec
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService{
    
    private static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Autowired
    private IUserDao userDao;
    
    /**
     * @param username 登录帐号
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        //登录验证类, 该对象用于和前台传入的值进行比较
        SecurityUser userDetails = userDao.selectUserByUsername(username);
        
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        
        //设置登录用户的类型
        if(userDetails.getType().intValue() == UserType.ADMIN){
			auths.add(new SimpleGrantedAuthority(UserType.ADMIN_NAME));
		}else if(userDetails.getType().intValue() == UserType.ENTERPRISE){
			auths.add(new SimpleGrantedAuthority(UserType.ENTERPRISE_NAME));
		}
        
        userDetails.setAuthorities(auths);
        
        return userDetails;
    }
}