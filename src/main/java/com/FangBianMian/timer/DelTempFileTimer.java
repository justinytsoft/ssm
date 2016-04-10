package com.FangBianMian.timer;

import java.io.File;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.FangBianMian.utils.DateUtil;
import com.FangBianMian.utils.SettingUtil;

public class DelTempFileTimer implements Job{
	
	private static Logger logger = LoggerFactory.getLogger(DelTempFileTimer.class);

	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		String tempPath = SettingUtil.getCommonSetting("upload.file.temp.path");
		File tempDir = new File(tempPath);
		if(tempDir.exists()){
			File[] files = tempDir.listFiles();
			logger.info("{}执行了一次DelTempFileTimer;共删除{}文件",DateUtil.formatDateTime(new Date()),files.length);
			for(File file : files){
				file.delete();
			}
		}
	}
}
