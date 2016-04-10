package com.FangBianMian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custom")
public class CustomController {

	@RequestMapping("/calender")
	public String custom() {
		return "pages/custom/calender";
	}
	
	@RequestMapping("/editor")
	public String kindEditor() {
		return "pages/editor/index";
	}
}
