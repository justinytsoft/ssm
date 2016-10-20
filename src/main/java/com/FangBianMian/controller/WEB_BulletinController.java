package com.FangBianMian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FangBianMian.domain.Bulletin;
import com.FangBianMian.service.IBulletinService;

/**
 * 公告
 * @author Administrator
 *
 */
@RequestMapping("/web/bulletin")
@Controller
public class WEB_BulletinController {

	@Autowired
	public IBulletinService bulletinService;
	
	/**
	 * 公告详情
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(Model model, @RequestParam Integer id){
		Bulletin b = bulletinService.queryBulletinById(id);
		model.addAttribute("b", b);
		return "web/bulletin";
	}
}
