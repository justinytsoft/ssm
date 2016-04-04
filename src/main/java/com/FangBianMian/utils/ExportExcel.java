package com.FangBianMian.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by Administrator on 2016-1-24.
 * @author hansen
 */
public class ExportExcel {
	
    /**
     * 将数据导出到EXCEL
     * @param <T>
     * @param datas 需要导出的数据
     * @param styleStream excel的样式
     * sheetName
     * whichRow
     * whichCell
     * cellWidth
     * fields
     * @return 
     * @throws Exception
     */
	public static <T> HSSFWorkbook exportExcel(List<T> datas, InputStream styleStream) throws Exception {
		
		Properties p = new Properties();
		p.load(new InputStreamReader(styleStream, "UTF-8"));  
		

		String sheetName = p.getProperty("sheetName");
		int whichRow = Integer.parseInt(p.getProperty("whichRow"));
		int whichCell = Integer.parseInt(p.getProperty("whichCell"));
		int cellWidth = Integer.parseInt(p.getProperty("cellWidth"));
		short cellStyle = HSSFCellStyle.ALIGN_CENTER_SELECTION;
		String[] fields = p.getProperty("fields").split(",");//{"公司名称","公司法人","公司联系人","联系电话"};
		
		
		//第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        //第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        //第三步，在sheet中的第0行添加表头,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(whichRow);

        //第四步， 设置表头居中样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(cellStyle); //创建一个居中格式

        HSSFCell cell = null;
        
        int headerWhichCell = whichCell;
        
        //创建表头单元格，并设置值
        for(int i = 0; i < fields.length; i++){
        	
        	cell = row.createCell(headerWhichCell);
        	cell.setCellValue(fields[i]); 
        	cell.setCellStyle(style);
        	sheet.setColumnWidth(headerWhichCell++, cellWidth);
        }
        
        //创建数据单元格，并设置值
        for(int i = 0; i < datas.size(); i++){
        	
        	int bodyWhichCell = whichCell;
            row = sheet.createRow(++whichRow);
            List<Object> list = getValueList(datas.get(i)); 
            
            for(int j = 0; j < list.size(); j++) {
                if(list.get(j) != null){
                    cell = row.createCell(bodyWhichCell++);
                    cell.setCellValue(list.get(j).toString());
                    cell.setCellStyle(style);
                }
            }
        }
        
        //让浏览器下载
       return wb;
	}
	
    /**
     * 通过反射得到所有的value
     * @param object 需要得到的对象
     * @param <T>
     * @return
     * @throws Exception
     */
    private static List<Object> getValueList(Object object)throws Exception{
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Object> values = new ArrayList<Object>();
        Object val = null;
        for(Field field:fields){
            java.lang.reflect.Type type = field.getGenericType();
            String typeStr = type.toString();
            String  key = field.getName();
           if(
        		   typeStr.equals("class java.lang.String") 
        		   || typeStr.equals("class java.lang.Byte") || typeStr.equals("byte")
        		   || typeStr.equals("class java.lang.Character") || typeStr.equals("char")
        		   || typeStr.equals("class java.lang.Short") || typeStr.equals("short")
        		   || typeStr.equals("class java.lang.Integer") || typeStr.equals("int")
        		   || typeStr.equals("class java.lang.Long") || typeStr.equals("long")
        		   || typeStr.equals("class java.lang.Float") || typeStr.equals("float")
        		   || typeStr.equals("class java.lang.Double") || typeStr.equals("double")
        		   || typeStr.equals("class java.lang.Boolean") || typeStr.equals("boolean")
        		   																			){
                String methodName = "get"+getMethodName(key);
                Method method = clazz.getMethod(methodName);
                values.add(method.invoke(object));
                continue;
            }else if(type.toString().equals("class java.util.Date")){
                String methodName = "get"+getMethodName(key);
                Method method = clazz.getMethod(methodName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(null != method.invoke(object)){
                	values.add(sdf.format(method.invoke(object)));
                }else{
                	values.add("");
                }
                continue;
            }
        }
        return values;
    }
	
    /**
     * 把传进来的字段首字母大写
     * @param fildeName 字段名
     * @return
     * @throws Exception
     */
    private static String getMethodName(String fildeName)throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
