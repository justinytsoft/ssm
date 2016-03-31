package com.FangBianMian.service;

import java.util.List;
import java.util.Map;

import com.FangBianMian.domain.Job;

public interface IJobService {

	List<Job> getAllJobs(Map<String, Object> param);

	int getJobTotal(Map<String, Object> param);

}
