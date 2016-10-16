package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.Member;
import com.FangBianMian.service.IMemberService;
import com.FangBianMian.utils.DataUtil;
import com.FangBianMian.utils.EasyuiDatagrid;

@RequestMapping("/admin/member")
@Controller
public class MemberController {

	@Autowired
	private IMemberService memerService;
	
	/**
	 * 用户管理
	 * @return
	 */
	@RequestMapping("/list")
	public String list(){
		return "pages/member/list";
	}
	
	/**
	 * 用户列表数据
	 * @return
	 */
	@RequestMapping("/listData")
	@ResponseBody
	public EasyuiDatagrid<Member> listData(@RequestParam(required=false) Integer page,
											@RequestParam(required=false) Integer rows,
											@RequestParam(required=false) String name){
		EasyuiDatagrid<Member> ed = new EasyuiDatagrid<Member>();
		Map<String,Object> param = new HashMap<String,Object>();

		if(page!=null && rows!=null){
			param.put("rows", rows);
			param.put("page", ((page-1)*rows));
		}
		if(!StringUtils.isBlank(name)){
			param.put("name", name);
		}
		
		List<Member> ms = DataUtil.isEmpty(memerService.queryMembersByParam(param));
		int total = memerService.queryMembersByParamTotal(param);
		
		ed.setRows(ms);
		ed.setTotal(total);
		return ed;
	}
}
