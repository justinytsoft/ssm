package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.SecurityUser;
import com.FangBianMian.domain.User;
import com.FangBianMian.utils.EasyUiTree;

public interface IUserDao {

	SecurityUser selectUserByUsername(@Param("username") String username);

	List<EasyUiTree> getMenus(@Param("param") Map<String, Object> param);

	User queryUserById(@Param("id") Integer id);

	void updateUser(User u);
}
