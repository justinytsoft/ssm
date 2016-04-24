package com.FangBianMian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custom")
public class CustomController {

	@RequestMapping("/phone")
	public String bs() {
		return "pages/phone/index";
	}

	@RequestMapping("/calender")
	public String custom() {
		return "pages/custom/calender";
	}
	
	@RequestMapping("/editor")
	public String kindEditor() {
		return "pages/editor/index";
	}
	
	@RequestMapping("/euEditor")
	public String euEditor() {
		return "pages/editor/ribbon";
	}
	
	@RequestMapping("/uploadify")
	public String uploadify() {
		return "pages/uploadify/index";
	}
}
