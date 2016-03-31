package com.FangBianMian.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.domain.Job;
import com.FangBianMian.service.IJobService;
import com.FangBianMian.utils.EasyUiDataGrid;

@Controller
@RequestMapping("/job")
public class JobController {

	@Autowired
	private IJobService jobService;
	
	@RequestMapping("/index")
	public String index(){
		return "pages/job/index";
	}
	
	/**
	 * 返回职位列表
	 * @param page easyui自动传入 当前页
	 * @param rows easyui自动传入 一页的大小
	 * @param sort easyui自动传入 需要排序的字段
	 * @param order easyui自动传入 排序方式
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUiDataGrid<Job> list(@RequestParam(required=false) Integer page,
							   @RequestParam(required=false) Integer rows,
							   @RequestParam(required=false) String sort,
							   @RequestParam(required=false) String order){
		
		EasyUiDataGrid<Job> eudg = new EasyUiDataGrid<Job>();
		
		if(page==null || rows==null){
			return eudg;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("page", ((page-1)*rows));
		param.put("rows", rows);
		param.put("sort", sort);
		param.put("order", order);
		
		List<Job> jobs = jobService.getAllJobs(param);
		int total = jobService.getJobTotal(param);
		
		eudg.setRows(jobs);
		eudg.setTotal(total);

		return eudg;
	}
}







