package com.FangBianMian.timer;

import java.io.File;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.FangBianMian.utils.SettingUtil;

public class DelTempFileTimer implements Job{
	
	//private static Logger logger = LoggerFactory.getLogger(DelTempFileTimer.class);

	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		String tempPath = SettingUtil.getCommonSetting("upload.file.temp.path");
		File tempDir = new File(tempPath);
		if(tempDir.exists()){
			File[] files = tempDir.listFiles();
			for(File file : files){
				file.delete();
			}
		}
	}
}
