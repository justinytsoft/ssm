package com.FangBianMian.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.FangBianMian.utils.DateUtil;
import com.FangBianMian.utils.EasyUiDataList;

@Controller
@RequestMapping("/custom")
public class CustomController {

	@RequestMapping("/calender")
	public String custom() {
		return "pages/custom/calender";
	}
	
	@RequestMapping("/dataList")
	@ResponseBody
	public List<EasyUiDataList> dataList(){
		List<EasyUiDataList> datas = new ArrayList<EasyUiDataList>();
		Date date = new Date();
		for(int i = 0; i < 10; i++){
			EasyUiDataList eudl = new EasyUiDataList();
			eudl.setValue(i+"value");
			eudl.setText(DateUtil.formatDate(DateUtil.getLaterDay(date, i*30)));
			eudl.setGroup(i+"group");
			datas.add(eudl);
		}
		return datas;
	}
}
