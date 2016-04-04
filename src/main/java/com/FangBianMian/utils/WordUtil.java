package com.FangBianMian.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template;


/**
 * 生成word文档
 * @author dreamtec
 *
 */
public class WordUtil {  
	
	private static Configuration configuration = null;  
    private static Map<String, Template> allTemplates = null;
    
    static { //初始化配置信息
        configuration = new Configuration();  
        configuration.setDefaultEncoding("utf-8");  
        configuration.setClassForTemplateLoading(WordUtil.class, "/word-template");  
        allTemplates = new HashMap<>();   // Java 7 钻石语法  
        try {  
            allTemplates.put("resume", configuration.getTemplate("resume.ftl")); //生成模版
        } catch (IOException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    private WordUtil() {  
        throw new AssertionError();  
    }  
  
    /**
     * 生成word
     * @param dataMap 需要填充的数据
     * @param type 使用那个模版
     * @return
     */
    public static File createDoc(Map<?, ?> dataMap, String type) {  
        String name = "temp" + (int) (Math.random() * 100000) + ".doc";  
        File f = new File(name);  
        Template t = allTemplates.get(type);  
        try {  
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
        	t.process(dataMap, w);  
            w.close();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            throw new RuntimeException(ex);  
        }  
        return f;  
    }  
}  














