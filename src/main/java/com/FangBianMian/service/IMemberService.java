package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.Member;

public interface IMemberService {

	List<Member> queryMembersByParam(Map<String, Object> param);

	int queryMembersByParamTotal(Map<String, Object> param);

}
