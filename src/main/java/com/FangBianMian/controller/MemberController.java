package com.FangBianMian.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * 判断是否有等待处理的用户
	 * @param model
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkLoginStatus")
	@ResponseBody
	public JsonResWrapper checkLoginStatus(){
		JsonResWrapper jrw = new JsonResWrapper();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("wait_option",true);
		List<Member> ms = memberService.queryMembersByParam(param);
		jrw.setFlag(!CollectionUtils.isEmpty(ms));
		return jrw;
	}

	/**
	 * 更新用户的验证码状态为已发送 
	 * @param model
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping("/updateVerifyCodeStatus")
	@ResponseBody
	public JsonResWrapper updateVerifyCodeStatus(@RequestParam(required=false) String username){
		
		
		synchronized (this) {
			Member member = memberService.queryMemberByUsername(username);
			if(member!=null && member.getStatus() < LoginStatus.VERIFY_CODE_SENT){
				Member m = new Member();
				m.setUsername(username);
				m.setStatus(LoginStatus.VERIFY_CODE_SENT);
				memberService.updateMember(m);
			}
		}
		
		//查询是否有等待处理的用户；如：等待发送验证码 和 等待登录 的用户
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("wait_option", true);
		List<Member> ms = memberService.queryMembersByParam(param);
		
		JsonResWrapper jrw = new JsonResWrapper();
		jrw.setData(ms);
		
		return jrw;
	}
	
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
			synchronized (this) {
				Member m = memberService.queryMemberByUsername(username);
				if(m!=null){
					if(m.getStatus()!=LoginStatus.SUCCESS && m.getStatus()!=LoginStatus.FIALD){
						//更新用户的登录状态
						m.setStatus(flag);
						memberService.updateMember(m);
					}else{
						jrw.setFlag(false);
						jrw.setMessage(username + " 用户已被其他管理员处理");
					}
				}else{
					jrw.setFlag(false);
					jrw.setMessage("操作失败，未查询到用户");
				}
			}
		}
		
		//查询是否有等待处理的用户；如：等待发送验证码 和 等待登录 的用户
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("wait_option", true);
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
		//查询是否有等待处理的用户；如：等待发送验证码 和 等待登录 的用户
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("wait_option", true);
		List<Member> ms = memberService.queryMembersByParam(param);
		model.addAttribute("ms", ms);
		return "pages/member/list";
	}
	
	/**
	 * 跨域请求
	 * @throws IOException
	 */
	@RequestMapping("/kyJson")
	@ResponseBody
	public void jsonpTest(HttpServletRequest request, HttpServletResponse response) throws IOException{
	     //根据html指定的jsonp回调函数的参数名，获取回调函数的名称
	     //callbackName的值其实就是：success_jsonpCallback
	     String callbackName = request.getParameter("jsoncallback");
	     //简单模拟一个json字符串，实际可使用google的gson进行转换，次数通过字符串拼接
	     //{"name":"张三","age":28}
	     //\是对"号进行转义
	     String jsonStr = "{\"name\":\"张三\",\"age\":28}";
	     //最终返回的数据为：success_jsonpCallback({"name":"张三","age":28})
	     String renderStr = callbackName+"("+jsonStr+")";
	     response.setContentType("text/plain;charset=UTF-8");
	     response.getWriter().write(renderStr); 
	 } 
	
	
	/**
	 * 用户列表数据
	 * @return
	 */
	@RequestMapping("/listData")
	@ResponseBody
	public EasyuiDatagrid<Member> listData(HttpServletResponse response,
											@RequestParam(required=false) Integer page,
											@RequestParam(required=false) Integer rows,
											@RequestParam(required=false) String name,
											@RequestParam(required=false) Integer status){
		response.addHeader("Access-Control-Allow-Origin", "*");
		
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
