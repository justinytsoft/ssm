package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.TestUpload;
import com.FangBianMian.utils.EasyUiTree;

public interface IUserService {

	List<EasyUiTree> getMenus(Map<String, Object> param);

	void saveTestUpload(TestUpload tu);

}
