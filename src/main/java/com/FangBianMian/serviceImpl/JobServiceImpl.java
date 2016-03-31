package com.FangBianMian.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FangBianMian.dao.IJobDao;
import com.FangBianMian.domain.Job;
import com.FangBianMian.service.IJobService;

@Service
public class JobServiceImpl implements IJobService{

	@Autowired
	private IJobDao jobDao;
	
	public List<Job> getAllJobs(Map<String, Object> param) {
		return jobDao.getAllJobs(param);
	}

	public int getJobTotal(Map<String, Object> param) {
		return jobDao.getJobTotal(param);
	}

}
