package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.Bulletin;
import com.FangBianMian.response.JsonResWrapper;
import com.FangBianMian.service.IBulletinService;
import com.FangBianMian.utils.EasyuiDatagrid;

@Controller
@RequestMapping("/admin/bulletin")
public class BulletinController {

	@Autowired
	private IBulletinService bulletinService;
	
	/**
	 * 删除公告
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public JsonResWrapper del(@RequestParam(required=false) Integer id){
		
		JsonResWrapper jrw = new JsonResWrapper();
		if(id==null){
			jrw.setFlag(false);
			jrw.setMessage("删除失败，参数缺失");
		}else{
			bulletinService.deleteBulletinById(id);
			jrw.setMessage("删除成功");
		}
		return jrw;
	}
	
	/**
	 * 公告添加、编辑页面
	 * @param model
	 * @param id
	 * @param curr 列表页面编辑时的 当前页
	 * @param size 列表页面编辑时的 当前页大小
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model, 
					  @RequestParam(required=false) Integer id,
					  @RequestParam(required=false) Integer curr,
					  @RequestParam(required=false) Integer size){
		
		if(id!=null){
			Bulletin b = bulletinService.queryBulletinById(id);
			model.addAttribute("b", b);
		}
		
		if(curr!=null && size!=null){
			model.addAttribute("curr", curr);
			model.addAttribute("size", size);
		}
		
		return "pages/bulletin/add";
	}
	
	/**
	 * 公告添加、编辑保存
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JsonResWrapper save(Model model, 
							   @RequestParam(required=false) Integer id,
							   @RequestParam(required=false) String title,
							   @RequestParam(required=false) String content,
							   @RequestParam(required=false) Integer top,
							   @RequestParam(required=false) Integer type){
		
		JsonResWrapper jrw = new JsonResWrapper();
		if(StringUtils.isBlank(title) || StringUtils.isBlank(content)){
			jrw.setFlag(false);
			jrw.setMessage("保存失败，参数缺失");
		}else{
			Bulletin b = new Bulletin();
			b.setId(id);
			b.setTitle(title);
			b.setContent(content);
			b.setTop(top);
			b.setType(type);
			bulletinService.saveBulletin(b);
			
			jrw.setMessage("保存成功");
		}
		return jrw;
	}
	
	/**
	 * 公告管理页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model,
						@RequestParam(required=false) Integer curr,
						@RequestParam(required=false) Integer size){
		if(curr!=null && size!=null){
			model.addAttribute("curr", curr);
			model.addAttribute("size", size);
		}
		return "pages/bulletin/list";
	}
	
	/**
	 * 公告列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyuiDatagrid<Bulletin> list(@RequestParam(required=false) Integer page,
										 @RequestParam(required=false) Integer rows,
										 @RequestParam(required=false) Integer type){
		EasyuiDatagrid<Bulletin> ed = new EasyuiDatagrid<Bulletin>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(page!=null && rows!=null){
			param.put("rows", rows);
			param.put("page", ((page-1)*rows));
		}
		if(type!=null && type.intValue()!=-1){
			param.put("type", type);
		}
		
		List<Bulletin> bs = bulletinService.queryBulletins(param);
		int total = bulletinService.queryBulletinsTotal(param);
		
		ed.setRows(bs);
		ed.setTotal(total);
		return ed;
	}
}
