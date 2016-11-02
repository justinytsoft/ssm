package com.FangBianMian.qq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qq.connect.oauth.Oauth;

@RequestMapping("/qq")
@Controller
public class LoginController {

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response){
		try {
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
