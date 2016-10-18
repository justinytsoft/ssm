package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.Member;

public interface IMemberDao {

	List<Member> queryMembersByParam(@Param("param") Map<String, Object> param);

	int queryMembersByParamTotal(@Param("param") Map<String, Object> param);

	Member queryMemberByUsername(@Param("username") String phone);

	void insertMember(Member m);

	void updateMember(Member m);

}
