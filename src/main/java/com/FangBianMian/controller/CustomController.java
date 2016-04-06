package com.FangBianMian.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		for(int i = 0; i < 10; i++){
			EasyUiDataList eudl = new EasyUiDataList();
			eudl.setValue(i+"value");
			eudl.setText(i+"text");
			eudl.setGroup(i+"group");
			datas.add(eudl);
		}
		return datas;
	}
}
