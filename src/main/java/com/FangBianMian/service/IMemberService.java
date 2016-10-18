package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.Member;

public interface IMemberService {

	/**
	 * 查询所有用户
	 * @param param
	 * @return
	 */
	List<Member> queryMembersByParam(Map<String, Object> param);

	/**
	 * 查询所有用户总数
	 * @param param
	 * @return
	 */
	int queryMembersByParamTotal(Map<String, Object> param);

	/**
	 * 通过用户ID查询用户
	 * @param phone
	 * @return
	 */
	Member queryMemberByUsername(String phone);

	/**
	 * 更新用户
	 * @param m
	 */
	void updateMember(Member m);

	/**
	 * 保存用户
	 * @param m
	 */
	void insertMember(Member m);

}
