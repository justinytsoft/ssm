package com.FangBianMian.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

	public static void ScaleImage(File originalFile, File resizedFile,
			int newWidth, float quality, boolean needScaleBig)
			throws IOException {

		if (quality > 1) {
			throw new IllegalArgumentException(
					"Quality has to be between 0 and 1");
		}

		ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
		Image i = ii.getImage();
		Image resizedImage = null;

		int iWidth = i.getWidth(null);
		int iHeight = i.getHeight(null);
		if (!needScaleBig) {
			if (iWidth < newWidth) {
				FileUtils.copyFile(originalFile.getAbsolutePath(),
						resizedFile.getAbsolutePath());
				// newWidth=iWidth;
				return;
			}
		}

		if (iWidth > iHeight) {
			resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
					/ iWidth, Image.SCALE_SMOOTH);
		} else {
			resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
					newWidth, Image.SCALE_SMOOTH);
		}

		// This code ensures that all the pixels in the image are loaded.
		Image temp = new ImageIcon(resizedImage).getImage();

		// Create the buffered image.
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

		// Copy image to buffered image.
		Graphics g = bufferedImage.createGraphics();

		// Clear background and paint the image.
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();

		// Soften.
		float softenFactor = 0.05f;
		float[] softenArray = { 0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);

		// Write the jpeg to a file.
		FileOutputStream out = new FileOutputStream(resizedFile);

		// Encodes image as a JPEG data stream
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

		JPEGEncodeParam param = encoder
				.getDefaultJPEGEncodeParam(bufferedImage);

		param.setQuality(quality, true);

		encoder.setJPEGEncodeParam(param);
		encoder.encode(bufferedImage);
	} // Example usage

	/***
	 * 裁剪图片
	 * 
	 * @param originalFileName
	 * @param file
	 * @param cx
	 *            目标图片宽度
	 * @param cy
	 *            目标图片高度
	 * @param left
	 *            裁剪区域左边位置
	 * @param top
	 *            裁剪区域上边位置
	 * @throws IOException
	 */

	public static void CutImage(String originalFileName, String file, int cx,
			int cy, int left, int top) throws IOException {
		Iterator<ImageReader> iterator = ImageIO
				.getImageReadersByFormatName("jpg");
		ImageReader reader = iterator.next();
		InputStream in = new FileInputStream(originalFileName);
		// InputStream in = new ByteArrayInputStream(bytes);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		if (reader.getWidth(0) < cx)
			cx = reader.getWidth(0);
		if (reader.getHeight(0) < cy)
			cy = reader.getHeight(0);
		Rectangle rect = new Rectangle(left < 0 ? 0 : left, top < 0 ? 0 : top,
				cx, cy);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(file));
		iis.close();
		in.close();
	}

	/***
	 * 裁剪图片
	 * 
	 * @param originalFileName
	 * @param file
	 * @param cx
	 *            目标图片宽度
	 * @param cy
	 *            目标图片高度
	 * @param left
	 *            裁剪区域左边位置
	 * @param top
	 *            裁剪区域上边位置
	 * @throws IOException
	 */

	public static void CutImage(String originalFileName, String file, int cx,
			int cy) throws IOException {
		Iterator<ImageReader> iterator = ImageIO
				.getImageReadersByFormatName("jpg");
		ImageReader reader = iterator.next();
		InputStream in = new FileInputStream(originalFileName);
		// InputStream in = new ByteArrayInputStream(bytes);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int soureImageWidth = reader.getWidth(0);
		int soureImageHeight = reader.getHeight(0);
		int orignalCx=cx;
		int orignalCy=cy;
		
		if (soureImageWidth < cx) {
			cx = soureImageWidth;
			// 再次修正调整后的目标图片的高度
			cy = cx * orignalCy / orignalCx;
		}
		
		if (soureImageHeight < cy) {
			cy = soureImageHeight;
		}
		
		//设置裁剪区域
		int left = 0;
		int top = 0;
		if(cy<soureImageHeight)
		{
			top= (soureImageHeight-cy)/2;
		}
		if(cx<soureImageWidth)
		{
			left=(soureImageWidth-cx)/2;
		}
		
		Rectangle rect = new Rectangle(left < 0 ? 0 : left, top < 0 ? 0 : top,
				cx, cy);
		
//		System.out.println(rect.toString());
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(file));
		iis.close();
		in.close();
	}

	/**
	 * 
	 * 
	 * @param createThumb
	 *            如果需要创建说略图，则会将图片进行缩放，缩放规则如下： 标准尺寸为：宽500*高400.
	 *            如果高度比例低于5：4的，不进行裁剪 如果高度比例等于5：4的，按照宽度比例进行缩放
	 *            如果高度比例打印5：4的，按照宽带比例进行缩放，同时长度超过比例的部分，采取留中间部分的策略
	 */
	public static void ScaleAndCutCommodityImage(String orinalFile,
			String targetFile, int standardWidth, int standardHeight) {
		File originalImage = new File(orinalFile);
		try {
			// 首先缩放图片
			ScaleImage(originalImage, new File(targetFile), standardWidth,
					1.0f, false);
			int cx, cy;
			cx = standardWidth;
			cy = standardHeight;
			// 接着裁剪图片
			CutImage(targetFile, targetFile, cx, cy);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	public static void main(String[] args) throws IOException {
//		// File originalImage = new File("C:\\11.jpg");
//		// resize(originalImage, new File("c:\\11-0.jpg"),150, 0.7f);
//		// resize(originalImage, new File("c:\\11-1.jpg"),150, 1f);
////		long beginTime = System.currentTimeMillis();
////		File originalImage = new File("C:\\1.jpg");
////		ScaleImage(originalImage, new File("c:\\2.jpg"), 800, 0.7f, false);
////		long firstTime = System.currentTimeMillis();
////		System.out.println(firstTime - beginTime);
////		ScaleImage(originalImage, new File("c:\\3.jpg"), 150, 1f, false);
////		long secondTime = System.currentTimeMillis();
////
////		System.out.println(secondTime - firstTime);
////		CutImage("c:\\2.jpg", "c:\\999.jpg", 500, 250, 0, 125);
////		System.out.println(System.currentTimeMillis() - firstTime);
//
////		ScaleAndCutCommodityImage("C:\\1\\5.png", "C:\\1\\777.jpg", 500, 400);
////		ScaleAndCutCommodityImage("C:\\1\\3.jpg", "C:\\1\\888.jpg", 500, 400);
////		ScaleAndCutCommodityImage("C:\\1\\6.jpg", "C:\\1\\999.jpg", 500, 400);
////		ScaleAndCutCommodityImage("C:\\1.jpg", "C:\\777.jpg", 500, 400);
//		
//		ScaleAndCutCommodityImage("C:\\1\\7.jpg", "C:\\1\\888.jpg", 500, 400);
//		
//	}
}
