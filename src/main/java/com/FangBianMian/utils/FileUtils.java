package com.FangBianMian.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;


/**
 * Class for handling media files in a filesystem.
 * 
 */
public class FileUtils {

	/**
	 * Retrieves the filename separated by name and extension.
	 * 
	 * @param file
	 *            File to be used.
	 * @return A pair consisting of name and extension of the passed file.
	 */
	public String[] getFileNameAndExtension(File file) {
		return getFileNameAndExtension(file.getName());
	}

	/**
	 * Retrieves the filename separated by name and extension.
	 * 
	 * @param filename
	 *            File to be used.
	 * @return A pair consisting of name and extension of the passed file.
	 */
	public String[] getFileNameAndExtension(String filename) {
		String name = filename.substring(0, filename.lastIndexOf("."));
		String extension = filename.substring(filename.lastIndexOf("."));
		return new String[] { name, extension };
	}

	/**
	 * Retrieves the filename separated by path, name and extension.
	 * 
	 * @param filename
	 *            File to be used.
	 * @return A set consisting of path, name and extension of the passed file.
	 */
	public String[] getFullFileNameAndExtension(String filename) {
		String path = new File(filename).getParentFile().getPath() + File.separator;
		String[] fullname = getFileNameAndExtension(new File(filename).getName());

		String name = fullname[0];
		String extension = fullname[1];

		return new String[] { path, name, extension };
	}

	/**
	 * Simple method for saving a file.
	 * 
	 * @param file
	 *            File to be saved.
	 * @param input
	 *            Data to be saved.
	 * @throws IOException
	 *             if any error arises.
	 */
	public void saveFile(File file, InputStream input) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		BufferedInputStream bis = new BufferedInputStream(input);
		int aByte;
		while ((aByte = bis.read()) != -1) {
			bos.write(aByte);
		}
		bos.flush();
		bos.close();
		bis.close();
		input.close();
	}

	/**
	 * Simple method for saving a file.
	 * 
	 * @param fileName
	 *            Name of the file to be saved.
	 * @param input
	 *            Data to be saved.
	 * @throws IOException
	 *             if any error arises.
	 */
	public void saveFile(String fileName, InputStream input) throws IOException {
		saveFile(new File(fileName), input);
	}

	/**
	 * Deletes a file
	 * 
	 * @param fileToDelete
	 *            File to be deleted
	 */
	public void deleteFile(File fileToDelete) {
		if (fileToDelete.exists()) {
			fileToDelete.delete();
		}
	}

	/**
	 * Creates a .lock file with the usedId written on it.
	 * 
	 * @param lockFileName
	 *            Name of the lock file.
	 * @param userId
	 *            User Id that is locking.
	 * @throws IOException
	 */
	public void createLock(String lockFileName, Long userId) throws IOException {
		File file = new File(lockFileName);
		org.apache.commons.io.FileUtils.writeStringToFile(file, userId.toString());
	}

	/**
	 * Reads the userId from the passed .lock file.
	 * 
	 * @param lockFileName
	 *            Name of the lock file to be red.
	 * @return userId
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public Long readLockFile(String lockFileName) throws IOException {
		Long userId = 0L;

		org.apache.commons.io.FileUtils utils = new org.apache.commons.io.FileUtils();
		String line = utils.readFileToString(new File(lockFileName));

		userId = Long.parseLong(line.trim());
		return userId;
	}
	
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath String 原文件路径 如：c:/fqf.txt
	 * @param newPath String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 写src到dst文件中
	 * 
	 * @param src
	 * @param dst
	 */
	public static void copyByteToFile(byte[] src, File dst) {
		OutputStream out = null;
		try {
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true)); // plupload
			} else {
				out = new BufferedOutputStream(new FileOutputStream(dst));
			}
			out.write(src);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除单个文件
	 * 
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * 写入文件到zip
     * @param file 要写入党文件或文件夹
     * @param parentPath 父文件夹名
     * @param zos zip输出流
     */
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if(file.exists()){
            if(file.isDirectory()){//处理文件夹
                parentPath+=file.getName()+File.separator;
                File [] files=file.listFiles();
                for(File f:files){
                    writeZip(f, parentPath, zos);
                }
            }else{
                FileInputStream fis=null;
                try {
                    fis=new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                        zos.flush();
                    }
                     
                 
                } catch (FileNotFoundException e) {
                	e.printStackTrace(); 
                } catch (IOException e) {
                	e.printStackTrace(); 
                }finally{
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                    }catch(IOException e){
                    	e.printStackTrace(); 
                    }
                }
            }
        }
    }    
	
	/**
     * 创建zip并下载
     * @param response 
     * @param sourcePath 要打包的文件夹或文件路径
     * @param fileName 打包后的zip文件名
     */
    public static void createZip(HttpServletResponse response,String sourcePath,String fileName) {
    		OutputStream os = null;
    		ZipOutputStream zos = null;
    		try {
    		String fn = new String(fileName.getBytes() , "ISO-8859-1");
    		response.setContentType("APPLICATION/OCTET-STREAM");
    		response.setHeader("Content-Disposition", "attachment; filename="
    				+ fn +".zip");
    		System.out.println(fileName);
    		
				os=response.getOutputStream();
				zos = new ZipOutputStream(new BufferedOutputStream(os));
				writeZip(new File(sourcePath), "", zos);
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
            try {
                if (zos != null) {
                    zos.close();
                }
                if (os != null) {
            		os.flush();
            		os.close();
            	}
            } catch (IOException e) {
            	e.printStackTrace(); 
            }
            FileUtils.deleteDirectory(sourcePath);
			}
    }
}
