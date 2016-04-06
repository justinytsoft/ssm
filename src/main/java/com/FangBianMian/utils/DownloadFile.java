package com.FangBianMian.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class DownloadFile {
	
	public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
	
	/**
	 * 下载远程服务器上的文件
	 * @param filePath
	 * @throws Exception
	 */
	public void downloadNet(HttpServletResponse res, String filePath) throws Exception{
		//new一个URL对象  
		URL url = new URL(filePath);  
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");  
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);  
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();  
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        byte[] data = readInputStream(inStream);
     	//创建输出流  
        ServletOutputStream outStream = res.getOutputStream();  
        //写入数据  
        outStream.write(data);  
        //关闭输出流  
        outStream.close(); 
	}
	
	private byte[] readInputStream(InputStream inStream) throws Exception{  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
		//创建一个Buffer字符串  
		byte[] buffer = new byte[1024];  
		//每次读取的字符串长度，如果为-1，代表全部读取完毕  
		int len = 0;  
		//使用一个输入流从buffer里把数据读取出来  
		while( (len=inStream.read(buffer)) != -1 ){  
		    //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
		    outStream.write(buffer, 0, len);  
		}  
		//关闭输入流  
		inStream.close();  
		//把outStream里的数据写入内存  
		return outStream.toByteArray();  
	}  

	/**
	 * 下载本地服务器文件
	 * @param filePath
	 * @param response
	 * @param isOnLine
	 * @throws Exception
	 */
	public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
		
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); 
        if (isOnLine) { // 在线打开方式
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }
}
