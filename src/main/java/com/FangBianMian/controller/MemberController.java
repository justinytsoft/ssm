package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.constant.Common;
import com.FangBianMian.constant.LoginStatus;
import com.FangBianMian.domain.Member;
import com.FangBianMian.response.JsonResWrapper;
import com.FangBianMian.service.IMemberService;
import com.FangBianMian.utils.DataValidation;
import com.FangBianMian.utils.EasyuiDatagrid;

@RequestMapping("/admin/member")
@Controller
public class MemberController {

	@Autowired
	private IMemberService memberService;
	
	/**
	 * 充值
	 * @return
	 */
	@RequestMapping("/charge")
	@ResponseBody
	public JsonResWrapper charge(@RequestParam(required=false) String username,
								 @RequestParam(required=false) Float price){
		JsonResWrapper jrw = new JsonResWrapper();
		if(StringUtils.isBlank(username) || price==null){
			jrw.setFlag(false);
			jrw.setMessage("充值失败，请求参数缺失");
		}else{
			Member m = memberService.queryMemberByUsername(username);
			if(m==null){
				jrw.setFlag(false);
				jrw.setMessage("充值失败，未查询到用户");
			}else{
				m.setUsername(username);
				m.setBalance(m.getBalance()+price);
				memberService.updateMember(m);
			}
		}
		return jrw;
	}
	
	/**
	 * 确认登录
	 * @return
	 */
	@RequestMapping("/confirmLogin")
	@ResponseBody
	public JsonResWrapper confirmLogin(HttpServletRequest request,
									   @RequestParam(required=false) String username,
									   @RequestParam(required=false) Integer flag){
		JsonResWrapper jrw = new JsonResWrapper();
		if(flag==null || StringUtils.isBlank(username)){
			jrw.setFlag(false);
			jrw.setMessage("操作失败，请求参数缺失");
		}else{
			Member m = memberService.queryMemberByUsername(username);
			if(m!=null){
				//更新用户的登录状态
				m.setStatus(flag);
				memberService.updateMember(m);
				
				//如果状态为登录失败，则吧等待确认的session清空
				if(flag==LoginStatus.FIALD){ 
					request.getSession().setAttribute(Common.MEMBER_SESSION_WAIT, null);
				}else{//否则 吧用户信息放入 正式session中
					request.getSession().setAttribute(Common.MEMBER_SESSION, m);
				}
			}else{
				jrw.setFlag(false);
				jrw.setMessage("操作失败，未查询到用户");
			}
		}
		
		//查询是否有等待登录的用户
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("status", LoginStatus.WAIT);
		List<Member> ms = memberService.queryMembersByParam(param);
		jrw.setData(ms);
		
		return jrw;
	}
	
	/**
	 * 用户管理
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model){
		//查询是否有等待登录的用户
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("status", LoginStatus.WAIT);
		List<Member> ms = memberService.queryMembersByParam(param);
		model.addAttribute("ms", ms);
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
											@RequestParam(required=false) String name,
											@RequestParam(required=false) Integer status){
		EasyuiDatagrid<Member> ed = new EasyuiDatagrid<Member>();
		Map<String,Object> param = new HashMap<String,Object>();

		if(page!=null && rows!=null){
			param.put("rows", rows);
			param.put("page", ((page-1)*rows));
		}
		if(!StringUtils.isBlank(name)){
			param.put("name", name);
		}
		if(status!=null && status!=-1){
			param.put("status", status);
		}
		
		List<Member> ms = DataValidation.isEmpty(memberService.queryMembersByParam(param));
		int total = memberService.queryMembersByParamTotal(param);
		
		ed.setRows(ms);
		ed.setTotal(total);
		return ed;
	}
}
