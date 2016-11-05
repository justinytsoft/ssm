package com.FangBianMian.utils;

import java.io.File;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import org.w3c.dom.Element;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;

/**
 * 裁剪后无黑色背景
 * @author Administrator
 *
 */
public class ImagesTool {
	
	/**
	 * 压缩文件
	 * @param path 原图路径
	 * @param ext 压缩文件的扩展名
	 * @param w 宽
	 * @param h 高
	 * @return 压缩文件的名称
	 */
    public static String img_small(String path, String ext, int w, int h) { 
    	//压缩文件的路径
    	String new_path  =  path.substring(0,path.indexOf(".")) + ext + path.substring(path.indexOf(".")); 
    	//压缩文件
    	Tosmallerpic(path,new_path,w,h,(float)1); 
    	//获取扩展名
    	String[] parsedName = FileUtils.getFullFileNameAndExtension(new_path);
    	return parsedName[1] + parsedName[2];
    } 

    /** 
     * 压缩文件
     * @param old_path 原图片路径 
     * @param new_path 新图片路径 
     * @param w 目标宽 
     * @param h 目标高 
     * @param per 百分比 
     */ 
    private static void Tosmallerpic(String old_path, String new_path, int w, int h, float per){ 
    	Image src; 
        try { 
        	src  =  javax.imageio.ImageIO.read(new File(old_path)); //构造Image对象 
            
            int old_w = src.getWidth(null); //得到源图宽 
            int old_h = src.getHeight(null); 
            int new_w = 0; 
            int new_h = 0; //得到源图长 

            double w2 = (old_w*1.00)/(w*1.00); 
            double h2 = (old_h*1.00)/(h*1.00); 

            //图片跟据长宽留白，成一个正方形图。 
            BufferedImage oldpic; 
            if(old_w>old_h) { 
            	oldpic = new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB); 
        	}else{
        		if(old_w<old_h){ 
        			oldpic = new BufferedImage(old_h,old_h,BufferedImage.TYPE_INT_RGB); 
        		}else{ 
        			oldpic = new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB); 
        		} 
        	} 
            Graphics2D g  =  oldpic.createGraphics(); 
            g.setColor(Color.white); 
            if(old_w>old_h){ 
            	g.fillRect(0, 0, old_w, old_w); 
                g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null); 
            }else{ 
                if(old_w<old_h){ 
                	g.fillRect(0,0,old_h,old_h); 
                    g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null); 
                }else{ 
                    g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null); 
                } 
            }              
            g.dispose(); 
            src  =  oldpic; 
            
            //图片调整为方形结束 
            if(old_w>w) 
            	new_w = (int)Math.round(old_w/w2); 
            else 
                new_w = old_w; 
            if(old_h>h) 
        	    new_h = (int)Math.round(old_h/h2);//计算新图长宽 
            else 
                new_h = old_h; 

            BufferedImage image_to_save  =  new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB);        
            image_to_save.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null); 
            FileOutputStream fos = new FileOutputStream(new_path); //输出到文件流 

            saveAsJPEG(100, image_to_save, per, fos);

            fos.close(); 
        } catch (IOException ex) { 
        	System.out.println("压缩图片错误。。。。。。。。。。。。。。。。。。。");
        	ex.printStackTrace();
        } 
    } 


    /**
     * 以JPEG编码保存图片
     * @param dpi  分辨率
     * @param image_to_save  要处理的图像图片
     * @param JPEGcompression  压缩比
     * @param fos 文件输出流
     * @throws IOException
     */
    public static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos) throws IOException {

        JPEGImageWriter imageWriter  =  (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);


        if(dpi !=  null && !dpi.equals("")){

            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", Integer.toString(dpi) );
            jfif.setAttribute("Ydensity", Integer.toString(dpi));

        }


        if(JPEGcompression >= 0 && JPEGcompression <= 1f){

            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(JPEGcompression);

        }

        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
        ios.close();
        imageWriter.dispose();
    }

}