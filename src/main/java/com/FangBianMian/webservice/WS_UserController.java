package com.FangBianMian.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FangBianMian.service.IUserService;

@RestController
@RequestMapping("/ws/user")
public class WS_UserController {

	@Autowired
	private IUserService userService;
}
