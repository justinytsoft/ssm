package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.Bulletin;
import com.FangBianMian.service.IBulletinService;
import com.FangBianMian.utils.EasyuiDatagrid;

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
	 * 公告首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(){
		return "web/bulletin";
	}
	
	/**
	 * 获取公告列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyuiDatagrid<Bulletin> list(@RequestParam(required=false) Integer page,
										 @RequestParam(required=false) Integer rows){
		EasyuiDatagrid<Bulletin> ed = new  EasyuiDatagrid<Bulletin>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(page!=null && rows!=null){
			param.put("page", ((page-1)*rows));
			param.put("rows", rows);
		}
		
		List<Bulletin> bs = bulletinService.queryBulletins(param);
		int total = bulletinService.queryBulletinsTotal(param);
		
		ed.setRows(bs);
		ed.setTotal(total);
		return ed;
	}
}
