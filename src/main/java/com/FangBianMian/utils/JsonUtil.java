package com.FangBianMian.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	
	 /**
     * Ω‚ŒˆJSON
     * @param jsonStr
     * @return
     */
    public static String parseJSON(String jsonStr){
    	
    	StringBuffer parseStr = new StringBuffer();
    	
    	try {
            JSONArray results = new JSONArray(jsonStr);
            for (int i = 0; i < results.length(); i++) {
            	JSONObject result = results.getJSONObject(i);
            	String sTime = result.getString("start_time") + " - ";
            	String eTime = result.getString("end_time") + ":" + (char)11; //(char)11 ªª––
            	String content = "    " + result.getString("content") + (char)11 + (char)11;
            	parseStr.append(sTime).append(eTime).append(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return parseStr.toString();
    }
}
