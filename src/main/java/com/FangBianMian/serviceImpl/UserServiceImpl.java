package com.FangBianMian.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FangBianMian.dao.IUserDao;
import com.FangBianMian.domain.TestUpload;
import com.FangBianMian.service.IUserService;
import com.FangBianMian.utils.EasyUiTree;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	public List<EasyUiTree> getMenus(Map<String, Object> param) {
		return userDao.getMenus(param);
	}

	public void saveTestUpload(TestUpload tu) {
		userDao.saveTestUpload(tu);
	}

}
