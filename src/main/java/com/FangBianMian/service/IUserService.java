package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.User;
import com.FangBianMian.utils.EasyUiTree;

public interface IUserService {

	/**
	 * 查询用户菜单
	 * @param param
	 * @return
	 */
	List<EasyUiTree> getMenus(Map<String, Object> param);

	/**
	 * 通过id查询用户
	 * @param id
	 * @return
	 */
	User queryUserById(Integer id);

	/**
	 * 更新用户
	 * @param u
	 */
	void updateUser(User u);
}
