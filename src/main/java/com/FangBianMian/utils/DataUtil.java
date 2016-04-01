/**
 * 
 */
package com.FangBianMian.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Administrator
 * 
 */
public class DataUtil {

	/**
	 * 每位允许的字符
	 */
	private static final String POSSIBLE_CHARS = "0123456789";

	/**
	 * 生产一个指定长度的随机字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS.length())));
		}
		return sb.toString();
	}

	public static String formatData(int number, int width) {
		if (number <= 0)
			return null;
		String sNum = number + "";
		if (sNum.length() < width) {
			int w = width - sNum.length();
			for (int i = 0; i < w; i++) {
				sNum = "0" + sNum;
			}
		}
		return sNum;
	}

	public static String encodeStr(String str) {
		if (str == null)
			return null;
		try {
			return new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encodeStrUTF8(String str) {
		if (StringUtils.isBlank(str))
			return null;
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };

	public static String _10_to_62(long number) {
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest != 0) {
			stack.add(array[new Long((rest - (rest / 62) * 62)).intValue()]);
			rest = rest / 62;
		}
		for (; !stack.isEmpty();) {
			result.append(stack.pop());
		}
		return result.toString();
	}

	public static long _62_to_10(String sixty_str) {
		int multiple = 1;
		long result = 0;
		Character c;
		for (int i = 0; i < sixty_str.length(); i++) {
			c = sixty_str.charAt(sixty_str.length() - i - 1);
			result += _62_value(c) * multiple;
			multiple = multiple * 62;
		}
		return result;
	}

	private static int _62_value(Character c) {
		for (int i = 0; i < array.length; i++) {
			if (c == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static void deleteByUploadImg(String fileName) {
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtil.getCommonSetting("upload.image.path");// 存放文件文件夹名称

		String filePath = fileDir + sep + fileName;
		FileUtils.deleteFile(filePath);

		if (!StringUtils.isBlank(fileName)) {
			String[] parsedName = new FileUtils().getFullFileNameAndExtension(filePath);
			String thumbPath = parsedName[0] + parsedName[1] + "_S" + parsedName[2];
			FileUtils.deleteFile(thumbPath);
		}
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param name
	 * @param createThumb
	 * @return
	 * @throws Exception
	 */
	public static List<String> uploadImg(HttpServletRequest req, String name, boolean createThumb,Integer W , Integer H) throws Exception {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtil.getCommonSetting("upload.image.path");// 存放文件文件夹名称

		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(array[random.nextInt(array.length)]);
			sb.append(array[random.nextInt(array.length)]);

			subDir.append(sb.toString());
		}

		File dirPath = new File(fileDir + sep + subDir.toString());
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		List<MultipartFile> mfs = multiRequest.getFiles(name);
		List<String> files = new ArrayList<String>();
		for (MultipartFile mft : mfs) {
			CommonsMultipartFile mf = (CommonsMultipartFile) mft;
			byte[] bytes = mf.getBytes();
			StringBuffer newFileName = new StringBuffer();
			if (bytes.length != 0) {
				String fileTrueName = mf.getOriginalFilename();
				String ext = fileTrueName.substring(fileTrueName.lastIndexOf("."));
				if (!".jpg/.jpeg/.gif/.bmp/.png".contains(ext.toLowerCase())) {
					throw new Exception("格式错误！");
				}
				newFileName.append(System.currentTimeMillis());
				newFileName.append(ext);
				String fileName = fileDir + sep + subDir.toString() + sep + newFileName.toString();

				File uploadedFile = new File(fileName);
				try {
					FileCopyUtils.copy(bytes, uploadedFile);
					if (createThumb) {
						String heightS = SettingUtil.getCommonSetting("thumbnailator.height");
						String widthS = SettingUtil.getCommonSetting("thumbnailator.width");

						Integer height = !StringUtils.isBlank(heightS) ? Integer.valueOf(heightS) : 0;
						Integer width = !StringUtils.isBlank(widthS) ? Integer.valueOf(widthS) : 0;
						
						if(W != 0 && H != 0){
							ImageResizer.resizeImage(fileName, 200, 200, "_S");
							/*String[] fileNames = fileName.split("\\.");
							ImageResizer.cutCenterImage(fileNames[0]+"_Z"+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], W, H);
							FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);*/
						} else {
							ImageResizer.resizeImage(fileName, 200, 200, "_S");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				files.add(subDir.toString() + sep + newFileName.toString());
			} else {
				files.add("");
			}

		}

		return files;
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static List<String> uploadImg(HttpServletRequest req, String name) throws Exception {
		return uploadImg(req, name, false,0,0);
	}
	
	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static List<String> uploadImg(HttpServletRequest req, String name,boolean createThumb) throws Exception {
		return uploadImg(req, name, createThumb,0,0);
	}
	
	/**
	 * 小文件和传
	 * 
	 * @param req
	 * @param name
	 * @param createThumb
	 * @return
	 * @throws Exception
	 */
	public static List<String> uploadFile(HttpServletRequest req, String name, boolean createThumb) throws Exception {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtil.getCommonSetting("upload.file.path");// 存放文件文件夹名称

		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(array[random.nextInt(array.length)]);
			sb.append(array[random.nextInt(array.length)]);

			subDir.append(sb.toString());
		}

		File dirPath = new File(fileDir + sep + subDir.toString());
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		List<MultipartFile> mfs = multiRequest.getFiles(name);
		List<String> files = new ArrayList<String>();
		for (MultipartFile mft : mfs) {
			CommonsMultipartFile mf = (CommonsMultipartFile) mft;
			byte[] bytes = mf.getBytes();
			StringBuffer newFileName = new StringBuffer();
			if (bytes.length != 0) {
				String fileTrueName = mf.getOriginalFilename();
				String ext = fileTrueName.substring(fileTrueName.lastIndexOf("."));
				newFileName.append(System.currentTimeMillis());
				newFileName.append(ext);
				String fileName = fileDir + sep + subDir.toString() + sep + newFileName.toString();

				File uploadedFile = new File(fileName);
				try {
					FileCopyUtils.copy(bytes, uploadedFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				files.add(subDir.toString() + sep + newFileName.toString());
			}

		}

		return files;
	}

	/**
	 * 图片上传临时文件夹
	 * @param req
	 * @return
	 */
	public static String uploadImgdFile(HttpServletRequest req){
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");// 存放文件文件夹名称

		File dirPath = new File(fileDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		MultipartFile mf = multiRequest.getFile("file2");
		try {
			byte[] src = mf.getBytes();
			String name = DataUtil.generateRandomString(8)+"_"+ mf.getOriginalFilename();
			if (src != null && src.length != 0) {
				File dst = new File(fileDir + sep + name.toLowerCase());
				FileUtils.copyByteToFile(src, dst);
				return name.toLowerCase();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 用于处理上传分片的文件
	 * 
	 * @param req
	 * @param name
	 */
	public static void uploadSegmentedFile(HttpServletRequest req, String name) {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");// 存放文件文件夹名称

		File dirPath = new File(fileDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		MultipartFile mf = multiRequest.getFile("file");
		try {
			byte[] src = mf.getBytes();
			if (src != null && src.length != 0) {
				File dst = new File(fileDir + sep + name);
				FileUtils.copyByteToFile(src, dst);
				//String fileName = fileDir + sep + name;
				//ImageResizer.resizeImage(fileDir + sep + name, 640, 320, "_Z");
				//String[] fileNames = fileName.split("\\.");
				//ImageResizer.cutCenterImage(fileNames[0]+"_Z"+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], 640, 320);
				//FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);
				//System.out.println("resize-------------");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mergeFile(String fileName, String ext, String toFileName, int size) {
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");// 存放文件文件夹名称
		
		OutputStream writer = null;
		InputStream reader = null;
		try {
			writer = new FileOutputStream(fileDir + sep + toFileName);
			int len;
			byte[] cbuf = new byte[1024 * 250];
			File file;
			for (int i = 0; i < size; i++) {
				file = new File(fileDir + sep + fileName + i + ext);
				if (!file.exists()) {
					break;
				}
				reader = new FileInputStream(file);
				while ((len = reader.read(cbuf, 0, cbuf.length)) != -1) {
					writer.write(cbuf, 0, len);
					writer.flush();
				}
				
				reader.close();
				
				file.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}

			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static String moveToDir(String filename, boolean createThumb) throws IOException {
		String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");
		String sep = System.getProperty("file.separator");
		String imgFileDir = SettingUtil.getCommonSetting("upload.image.path");

		File srcFile = new File(fileDir + sep + filename);
		try {
			String filenames = fileDir + sep + filename;
			String[] fileNamesi = filenames.split("\\.");
			FileUtils.deleteFile(fileNamesi[0]+"_S"+"."+fileNamesi[1]);
		} catch (Exception e) {
			System.out.println("图片名称错误。。。。");
		}
		
		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);

			subDir.append(sb.toString());
		}

		File dstDir = new File(imgFileDir + sep + subDir);
		if (!dstDir.exists()) {
			dstDir.mkdirs();
		}

		org.apache.commons.io.FileUtils.moveFileToDirectory(srcFile, dstDir, true);

		if (createThumb) {
			//String heightS = SettingUtil.getCommonSetting("thumbnailator.height");
			//String widthS = SettingUtil.getCommonSetting("thumbnailator.width");

			//Integer height = !StringUtils.isBlank(heightS) ? Integer.valueOf(heightS) : 0;
			//Integer width = !StringUtils.isBlank(widthS) ? Integer.valueOf(widthS) : 0;

			//ImageResizer.resizeImage(imgFileDir + sep + subDir + sep + filename, width, height, "_S");
//			String fileName = imgFileDir + sep + subDir + sep + filename;
//			ImageResizer.resizeImage(fileName, 640, 320, "_Z");
//			String[] fileNames = fileName.split("\\.");
//			ImageResizer.cutCenterImage(fileNames[0]+"_Z"+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], 640, 320);
//			FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);
			
			
			String  StandardHeight= SettingUtil.getCommonSetting("commoditythumb.height");
			String  StandardWidth = SettingUtil.getCommonSetting("commoditythumb.width");
			
			Integer height = !StringUtils.isBlank(StandardHeight) ? Integer.valueOf(StandardHeight) : 400;
			Integer width = !StringUtils.isBlank(StandardWidth) ? Integer.valueOf(StandardWidth) : 500;
		
			String fileName = imgFileDir + sep + subDir + sep + filename;
			ImageResizer.resizeImage(fileName, 200, 200, "_S");
			//String[] fileNames = fileName.split("\\.");
			//ImageUtil.ScaleAndCutCommodityImage(fileNames[0]+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], width, height);
		}

		return subDir + sep + filename;
	}
	
	
	
	/**
	 *  将商品图片图片从临时文件夹移动到正式文件里面，
	 * @param filename
	 * @param createThumb   如果需要创建说略图，则会将图片进行缩放，缩放规则如下：
	 *                       标准尺寸为：宽500*高400.
	 *                       如果高度比例低于5：4的，不进行裁剪
	 *                       如果高度比例等于5：4的，按照宽度比例进行缩放
	 *                       如果高度比例打印5：4的，按照宽带比例进行缩放，同时长度超过比例的部分，采取留中间部分的策略
	 * 
	 * @return
	 * @throws IOException
	 */
	
	
	public static String movePicForCommodity(String filename, boolean createThumb) throws IOException {
		String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");
		String sep = System.getProperty("file.separator");
		String imgFileDir = SettingUtil.getCommonSetting("upload.image.path");

		System.out.println("use this method to cut pic......");
		
		File srcFile = new File(fileDir + sep + filename);
		try {
			String filenames = fileDir + sep + filename;
			String[] fileNamesi = filenames.split("\\.");
			FileUtils.deleteFile(fileNamesi[0]+"_S"+"."+fileNamesi[1]);
		} catch (Exception e) {
			System.out.println("图片名称错误。。。。");
		}
		
		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);

			subDir.append(sb.toString());
		}

		File dstDir = new File(imgFileDir + sep + subDir);
		if (!dstDir.exists()) {
			dstDir.mkdirs();
		}

		org.apache.commons.io.FileUtils.moveFileToDirectory(srcFile, dstDir, true);

		if (createThumb) {
			//String heightS = SettingUtil.getCommonSetting("thumbnailator.height");
			//String widthS = SettingUtil.getCommonSetting("thumbnailator.width");

			//Integer height = !StringUtils.isBlank(heightS) ? Integer.valueOf(heightS) : 0;
			//Integer width = !StringUtils.isBlank(widthS) ? Integer.valueOf(widthS) : 0;

			//ImageResizer.resizeImage(imgFileDir + sep + subDir + sep + filename, width, height, "_S");
			
			String  StandardHeight= SettingUtil.getCommonSetting("commoditythumb.height");
			String  StandardWidth = SettingUtil.getCommonSetting("commoditythumb.width");
			
			Integer height = !StringUtils.isBlank(StandardHeight) ? Integer.valueOf(StandardHeight) : 400;
			Integer width = !StringUtils.isBlank(StandardWidth) ? Integer.valueOf(StandardWidth) : 500;
		
			String fileName = imgFileDir + sep + subDir + sep + filename;
			String[] fileNames = fileName.split("\\.");
			ImageUtil.ScaleAndCutCommodityImage(fileNames[0]+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], width, height);
//			FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);
		}

		return subDir + sep + filename;
	}
	
	
	
	
	
	
	/**
	 * 图片裁剪
	 * @param filename 图片名
	 * @param cx 裁剪宽度
	 * @param cy 裁剪高度
	 * @param width 原图发大或缩小后的宽度
	 * @param left 截图开始的左上角坐标x
	 * @param top  截图开始的左上角坐标y
	 * @return
	 * @throws IOException
	 */
	public static String moveImgToDir(String filename,int cx,int cy, int width,int left,int top) throws IOException {
		String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");
		String sep = System.getProperty("file.separator");
		String imgFileDir = SettingUtil.getCommonSetting("upload.image.path");

		File srcFile = new File(fileDir + sep + filename);
		//String filenames = fileDir + sep + filename;
		
		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);

			subDir.append(sb.toString());
		}

		File dstDir = new File(imgFileDir + sep + subDir);
		if (!dstDir.exists()) {
			dstDir.mkdirs();
		}

		org.apache.commons.io.FileUtils.moveFileToDirectory(srcFile, dstDir, true);
		String fileName = imgFileDir + sep + subDir + sep + filename;
		String subfilename = ImageResizer.resizeCutImage(fileName, "_S", width);
		ImageResizer.saveResizedCutImage(subfilename, fileName, cx, cy, left, top);
			/*String fileName = imgFileDir + sep + subDir + sep + filename;
			ImageResizer.resizeImage(fileName, width, 0, "_S");
			String[] fileNames = fileName.split("\\.");
			ImageResizer.cutCenterImage(fileNames[0]+"_S"+"."+fileNames[1], fileName, 640, 320);*/
			//FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);

		return subDir + sep + URLEncoder.encode(filename);
	}
	

	/**
	 * 上传图片至临时目录
	 * 
	 * @param req
	 * @param name
	 * @param createThumb
	 * @return
	 * @throws Exception
	 */
	public static String uploadImgToTempDir(HttpServletRequest req, String name,boolean thumb) throws Exception {
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
		String sep = System.getProperty("file.separator");
		String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");// 存放文件文件夹名称

		File dirPath = new File(fileDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		MultipartFile mft = multiRequest.getFile(name);
		CommonsMultipartFile mf = (CommonsMultipartFile) mft;
		byte[] bytes = mf.getBytes();
		StringBuffer newFileName = new StringBuffer();
		if (bytes.length != 0) {
			String fileTrueName = mf.getOriginalFilename();
			String ext = fileTrueName.substring(fileTrueName.lastIndexOf("."));
			if (!".jpg/.jpeg/.gif/.bmp/.png".contains(ext.toLowerCase())) {
				throw new Exception("格式错误！");
			}
			newFileName.append(System.currentTimeMillis());
			newFileName.append(ext);
			String fileName = fileDir + sep + newFileName.toString();

			File uploadedFile = new File(fileName);

			try {
				FileCopyUtils.copy(bytes, uploadedFile);
				if(thumb){
					int w=200;
					int h=200;
					ImageResizer.resizeImage(fileName, w, h, "_S");
					/*String[] fileNames = fileName.split("\\.");
					ImageResizer.cutCenterImage(fileNames[0]+"_Z"+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], w, h);
					FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);*/
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return newFileName.toString();
	}

	
	public static String uploadImageToTmp(String filename, String image){
		return DataUtil.uploadImageToTmp(filename, image, 0, 0);
	}
	/**
	 * 存放终端上传的图片到临时目录
	 * 
	 * @param filename
	 * @param image
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String uploadImageToTmp(String filename, String image,int w,int h) {
		FileOutputStream fos = null;
		try {
			String fileDir = SettingUtil.getCommonSetting("upload.file.temp.path");// 存放文件文件夹名称

			sun.misc.BASE64Decoder a = new sun.misc.BASE64Decoder();
			byte[] buffer = a.decodeBuffer(image); // 对android传过来的图片字符串进行解码
			String type = getImageType(buffer);
			filename = filename + "." +type;
			/*if (!StringUtils.equalsIgnoreCase(type, "jpg") && !StringUtils.equalsIgnoreCase(type, "jpeg") && !StringUtils.equalsIgnoreCase(type, "png")) {
				return "-1";
			}*/
			File destDir = new File(fileDir);
			if (!destDir.exists())
				destDir.mkdirs();
			fos = new FileOutputStream(new File(destDir, filename)); // 保存图片
			fos.write(buffer);
			fos.flush();
			fos.close();
			String sep = System.getProperty("file.separator");
			String filenames = fileDir+sep+filename;
			if(w==0 || h==0){
				w=200;
				h=200;
			}
			ImageResizer.resizeImage(filenames, w, h, "_S");
			/*String[] fileNames = filenames.split("\\.");
			ImageResizer.cutCenterImage(fileNames[0]+"_Z"+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], w, h);
			FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);*/
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String uploadImage(String filename, String image){
		return uploadImage(filename,image,false);
	}
	
	/**
	 * 存放终端上传的图片
	 * 
	 * @param filename
	 * @param image
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String uploadImage(String filename, String image,boolean createThumb) {
		FileOutputStream fos = null;
		try {
			String sep = System.getProperty("file.separator");
			String dirName = buildDirName();
			String toDir = SettingUtil.getCommonSetting("upload.image.path"); // 存储路径
			toDir += (sep + dirName);
			sun.misc.BASE64Decoder a = new sun.misc.BASE64Decoder();
			byte[] buffer = a.decodeBuffer(image); // 对android传过来的图片字符串进行解码
			String type = getImageType(buffer);
			filename = filename + "." +type;
			/*if (!StringUtils.equalsIgnoreCase(type, "jpg") && !StringUtils.equalsIgnoreCase(type, "jpeg") && !StringUtils.equalsIgnoreCase(type, "png")) {
				return "-1";
			}*/
			File destDir = new File(toDir);
			if (!destDir.exists())
				destDir.mkdirs();
			fos = new FileOutputStream(new File(destDir, filename)); // 保存图片
			fos.write(buffer);
			fos.flush();
			fos.close();
			if (createThumb) {
				String fileName = toDir + sep + filename;
				ImageResizer.resizeImage(fileName, 200, 200, "_S");
				/*String[] fileNames = fileName.split("\\.");
				ImageResizer.cutCenterImage(fileNames[0]+"_Z"+"."+fileNames[1], fileNames[0]+"_S"+"."+fileNames[1], 640, 320);
				FileUtils.deleteFile(fileNames[0]+"_Z"+"."+fileNames[1]);*/
			}
			return dirName + sep + filename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取图片格式
	 * 
	 * @param img
	 * @return
	 * @throws IOException
	 */
	public static String getImageType(byte[] img) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(img);
		MemoryCacheImageInputStream is = new MemoryCacheImageInputStream(bais);
		Iterator<ImageReader> it = ImageIO.getImageReaders(is);
		ImageReader r = null;
		while (it.hasNext()) {
			r = it.next();
			break;
		}
		if (r == null) {
			return null;
		}
		return r.getFormatName().toLowerCase();
	}

	public static String buildDirName() {
		String sep = System.getProperty("file.separator");
		StringBuffer subDir = new StringBuffer();
		for (int i = 0; i < 2; i++) {
			if (i != 0) {
				subDir.append(sep);
			}
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);
			sb.append(DataUtil.array[random.nextInt(DataUtil.array.length)]);

			subDir.append(sb.toString());
		}

		return subDir.toString();
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double distance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	/**
	 * 判断是否含有中文字符
	 * @param str
	 * @return
	 */
	public static boolean isChineseChar(String str){
	       boolean temp = false;
	       Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
	       Matcher m=p.matcher(str); 
	       if(m.find()){ 
	           temp =  true;
	       }
	       return temp;
	}
	
	public static List<String> toUrlEncode(String str) {
		
		return null;
	}
}
