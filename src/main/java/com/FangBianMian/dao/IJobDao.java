package com.FangBianMian.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.FangBianMian.domain.Job;

public interface IJobDao {

	List<Job> getAllJobs(@Param("param") Map<String, Object> param);

	int getJobTotal(@Param("param") Map<String, Object> param);


}
