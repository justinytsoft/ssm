package com.FangBianMian.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 * Class for resizing images
 */
public class ImageResizer {

  private static final String IMAGE_JPEG = "jpeg";

  private ImageIcon image;
  private ImageIcon thumb;

  /**
   * Default constructor
   * 
   * @param fileName File to be created
   */
  private ImageResizer(String fileName) {
    this.image = new ImageIcon(fileName);
  }

  /**
   * Resize an image according to the specified width. (no matter which height
   * it has)
   * 
   * @param width New width
   * @return A resized image with the passed parameters
   */
  private Image getResizedImage(int width) {
    // if size doesn't exceed maximum, don't force it!
    if (image.getIconWidth() < width) {
      width = image.getIconWidth();
    }

    // just conserve the width fixed
    thumb = new ImageIcon(image.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));

    return thumb.getImage();
  }
  
  /**
   * Resize an image according to the specified width and height.
   * 
   * @param width New width
   * @param height New height
   * @return A resized image with the passed parameters
   */
  private Image getResizedImage(int width, int height) {
    // if size doesn't exceed maximum, don't force it!
    if (image.getIconWidth() < width && image.getIconHeight() < height) {
      width = image.getIconWidth();
      height = image.getIconHeight();
    }

   thumb = new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    
    // just conserve the width fixed
    //thumb = new ImageIcon(image.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));

    // to validate height limits
//    if (thumb.getIconHeight() > height) {
//      thumb = new ImageIcon(image.getImage().getScaledInstance(-1, height, Image.SCALE_SMOOTH));
//    }

    return thumb.getImage();
  }

  /**
   * Saves new file with less size.
   * 
   * @param file file to be saved
   * @param imageType type of the image
   * @throws IOException if any error arises
   */
  private void saveResizedImage(File file, String imageType) throws IOException {
    if (thumb != null) {
      BufferedImage bi = new BufferedImage(thumb.getIconWidth(), thumb.getIconHeight(), BufferedImage.TYPE_INT_RGB);
      try {
        Graphics g = bi.getGraphics();
        g.drawImage(thumb.getImage(), 0, 0, null);
        ImageIO.write(bi, imageType, file);
      } finally {
        bi.flush();
      }
    }
  }

  /**
   * Resize an image to a specific size (large, medium or thumbnail)
   * 
   * @param originalFileName Original File
   * @param sizeType Large, Medium or thumbnail
   * @param isHeightFixed <code>true</code> if height has to be contemplated
   *        in the resized image
   * @return Name of the new created image
   * @throws IOException
   */
  public static String resizeImage(String originalFileName, int width, int height, String suffix,
      Boolean isHeightFixed) throws IOException {

    // only applies for image files
    String fileName = originalFileName.toLowerCase();
    /*if (!fileName.endsWith("jpg")  && !fileName.endsWith("jpeg")) {
      return null;
    }*/

    String resizedFileName = "";
    String[] parsedName = new FileUtils().getFullFileNameAndExtension(originalFileName);
    ImageResizer resizer = new ImageResizer(originalFileName);

    // set the scale
    if (isHeightFixed) {
    	resizer.getResizedImage(width, height);
    } else {
    	resizer.getResizedImage(width);
    }

    resizedFileName = parsedName[0] + parsedName[1] + suffix + parsedName[2];

    resizer.saveResizedImage(new File(resizedFileName), fileName.substring(fileName.lastIndexOf(".")+1));

    return resizedFileName;
  }

  /**
   * Resize an image to a specific size (large, medium or thumbnail) It doesn't
   * take care about the height no matter which value has.
   * 
   * @param originalFileName Original File
   * @param sizeType Large, Medium or thumbnail
   * @return Name of the new created image
   * @throws IOException
   */
  public static String resizeImage(String originalFileName, int width, int height, String suffix) throws IOException {
    return resizeImage(originalFileName, width, height, suffix, Boolean.FALSE);
  }
  
  public static boolean needRotate(String fileName) {
	  boolean need = false;
	  try {
		  File file = new File(fileName);
		  BufferedImage localImage = ImageIO.read(file);
		  float width = localImage.getWidth();
		  float height = localImage.getHeight();
		  float result = height / width;
		  if ((0.7 < result) && (result < 0.8)) {
			  need = true;
          }
		  //if ((width == 2048 && height == 1536)
		  //	  || (width == 2592 && height == 1936)) {
		  //	  need = true;
		  //}
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  return need;
  }
  
  public static String rotateImage(File orgFile) {
	  BufferedImage bufI = null;
	  try {
		  BufferedImage localImage = ImageIO.read(orgFile);
		  int width = localImage.getWidth();
		  int height = localImage.getHeight();
		  int degree = 90;
		  Image srcImage = rotateImage(localImage, degree);
		  if (degree == 90 || degree == 270) {
			  int temp = width;
			  width = height;
			  height = temp;
		  }
		  bufI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		  Graphics g = bufI.getGraphics();
		  g.drawImage(srcImage, 0, 0, width,height, null);
		  ImageIO.write(bufI, IMAGE_JPEG, orgFile);

	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  return "";
  }
  
  public static BufferedImage rotateImage(final BufferedImage bufferedimage,
			final int degree) {
		int width = bufferedimage.getWidth();
		int height = bufferedimage.getHeight();
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		int w = 0, h = 0;

		if (degree == 90) {
			int temp = width;
			w = height;
			h = temp;
		} else {
			w = width;
			h = height;
		}
		(graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
				.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
						java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		int x = w / 2;
		int y = h / 2;

		if (degree == 90) {
			if (x < y) {
				y = x;
			} else {
				y = x;
			}
		}

		graphics2d.rotate(Math.toRadians(degree), x, y);

		graphics2d.drawImage(bufferedimage, 0, 0, null);
		graphics2d.dispose();
		return img;
	}
  
  /* 
   * 根据尺寸图片居中裁剪 
   */  
   public static void cutCenterImage(String src,String dest,int w,int h) throws IOException{   
       Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");   
       ImageReader reader = iterator.next();   
       InputStream in=new FileInputStream(src);  
       //InputStream in = new ByteArrayInputStream(bytes);
       ImageInputStream iis = ImageIO.createImageInputStream(in);   
       reader.setInput(iis, true);   
       ImageReadParam param = reader.getDefaultReadParam();   
       int imageIndex = 0; 
       float sc = (float)w/(float)h;
       float osc = (float)reader.getWidth(imageIndex)/(float)reader.getHeight(imageIndex);
       int iw = 0;
       int ih = 0;
       if(sc > osc){
       	  iw = reader.getWidth(imageIndex);
       	  float fh = (float)iw/sc;
       	  ih = (int)fh;
       } else {
    	  ih = reader.getHeight(imageIndex);
    	  float fw = (float)ih*sc;
    	  iw = (int)fw;
       }
       Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-iw)/2, (reader.getHeight(imageIndex)-ih)/2, iw, ih);    
       param.setSourceRegion(rect);
       BufferedImage bi = reader.read(0,param);     
       ImageIO.write(bi, "jpg", new File(dest));             
       iis.close();
       in.close();
   }  
  
   /**
    * 改变大小并且裁剪
    * @param originalFileName
    * @param width
    * @param height
    * @param suffix
    * @return
    * @throws IOException
    */
   public static String resizeCutImage(String originalFileName, String suffix,int width)
		      throws IOException {
	   String fileName = originalFileName.toLowerCase();
	    if (!fileName.endsWith("jpg")  && !fileName.endsWith("jpeg")) {
	      return null;
	    }

	    String resizedFileName = "";
	    String[] parsedName = new FileUtils().getFullFileNameAndExtension(originalFileName);
	    	
	    	Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg"); 
	       ImageReader reader = iterator.next();
	       InputStream in=new FileInputStream(originalFileName);  
	       ImageInputStream iis = ImageIO.createImageInputStream(in);   
	       reader.setInput(iis, true);
	       try{
	    	   reader.read(0);
	       } catch(IIOException e){
	    	   Raster raster = reader.readRaster(0, null); 
	       		BufferedImage bu= new BufferedImage(raster.getWidth(), raster.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);  
	       		bu.getRaster().setRect(raster);    
	       		ImageIO.write(bu, "jpg",new File(originalFileName));             
	       		System.out.println("---------");
	       }
	       iis.close();
	       in.close();
	    
	    ImageResizer resizer = new ImageResizer(originalFileName);

	    // set the scale
	    
	    	resizer.getResizedCutImage(width);

	    resizedFileName = parsedName[0] + parsedName[1] + suffix + parsedName[2];

	    resizer.saveResizedImage(new File(resizedFileName), IMAGE_JPEG);

	    return resizedFileName;
   }
   
   private Image getResizedCutImage(int width) {
	    // just conserve the width fixed
	    thumb = new ImageIcon(image.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));

	    return thumb.getImage();
	  }
   
   public static void saveResizedCutImage(String originalFileName,String file,int cx,int cy, int left,int top) throws IOException {
	   Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg"); 
       ImageReader reader = iterator.next();   
       InputStream in=new FileInputStream(originalFileName);  
       //InputStream in = new ByteArrayInputStream(bytes);
       ImageInputStream iis = ImageIO.createImageInputStream(in);   
       reader.setInput(iis, true);   
       ImageReadParam param = reader.getDefaultReadParam();
       if(reader.getWidth(0) < cx)cx=reader.getWidth(0);
       if(reader.getHeight(0) < cy)cy=reader.getHeight(0);
       Rectangle rect = new Rectangle(left < 0 ? 0:left,top < 0? 0:top,cx,cy);
       param.setSourceRegion(rect);
       BufferedImage bi = reader.read(0,param);     
       ImageIO.write(bi, "jpg",new File(file));             
       iis.close();
       in.close();
   }
   
}
