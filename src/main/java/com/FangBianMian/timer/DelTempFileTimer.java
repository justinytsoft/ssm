package com.FangBianMian.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DelTempFileTimer implements Job{

	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		System.out.println("DelTempFileTimer");
	}
}
