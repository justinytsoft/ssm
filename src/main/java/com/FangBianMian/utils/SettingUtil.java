package com.FangBianMian.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 加载配置文件，并提供读取的方法
 */
public class SettingUtil {

	public static final String SEPARATOR = File.separator;
	public static String FILE_COMMON = "/common.properties";
	private static Map<String, Properties> pools = new HashMap<String, Properties>();
	
	/**
	 * 加载系统级的配置文件
	 * 
	 * @param uri
	 * @return
	 */
	public static Properties loadSetting(String uri) {
		Properties prop = pools.get(uri);
		if (prop == null) {
			// 没有加载则加载
			InputStream is = SettingUtil.class.getResourceAsStream(uri);
			if (is == null) {
				throw new IllegalArgumentException("Resource [" + uri + "] not found");
			}
			prop = new Properties();
			try {
				prop.load(is);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
			pools.put(uri, prop);
		}
		return prop;
	}

	/**
	 * 读取common.properties文件中的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getCommonSetting(String key) {
		Properties prop = loadSetting(FILE_COMMON);
		return prop.getProperty(key);
	}
}
