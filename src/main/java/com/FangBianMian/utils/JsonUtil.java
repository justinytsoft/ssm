package com.FangBianMian.utils;

import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static GsonBuilder instance;

	private static synchronized GsonBuilder build() {
		if (instance == null) {
			instance = new GsonBuilder();
		}
		return instance;
	}
	
	private static GsonBuilder getInstance(){
		return build();
	}
	
	public static String toJson(Object obj){
		return getInstance().disableHtmlEscaping().create().toJson(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> clazz){
		return getInstance().create().fromJson(json, clazz);
	}
}
