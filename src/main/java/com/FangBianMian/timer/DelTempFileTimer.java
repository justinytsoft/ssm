package com.FangBianMian.timer;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.FangBianMian.utils.DateUtil;

public class DelTempFileTimer implements Job{
	
	private static Logger logger = LoggerFactory.getLogger(DelTempFileTimer.class);

	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		logger.info("{}执行了一次{}",DateUtil.formatDateTime(new Date()),"DelTempFileTimer");
		/*String tempPath = SettingUtil.getCommonSetting("upload.file.temp.path");
		File tempDir = new File(tempPath);
		if(tempDir.exists()){
			File[] files = tempDir.listFiles();
			for(File file : files){
				System.out.println(file.getName());
			}
		}*/
	}
}
