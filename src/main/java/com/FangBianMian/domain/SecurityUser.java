package com.FangBianMian.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser extends User implements UserDetails{

	private static final long serialVersionUID = 1L;
	private Collection<GrantedAuthority> authorities;
	
	public SecurityUser() {
		super();
	}

	public SecurityUser(String username, String password, Collection<GrantedAuthority> authorities) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.authorities = authorities;
	}
	
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}
}
