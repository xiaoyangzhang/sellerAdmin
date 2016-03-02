package com.yimayhd.sellerAdmin.util.excel;


import com.yimayhd.sellerAdmin.exception.NoticeException;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel相关操作<br>
 * Excel文件的导入导出等
 * 
 * @author 88144881
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class ExcelFactory {
    public static final String EXCEL_TYPE_2003 = "2003";
    public static final String EXCEL_TYPE_2007 = "2007";

    protected final int SHEET_NUM = 10000;
    protected final int DEFAULT_COLUMN_WIDTH = 20;
    protected final short DEFAULT_ROW_HEIGHT = 10;
    private static ExcelFactory factory2007;
    private static ExcelFactory factory2003;

    protected List<String> dataFormat = new ArrayList<String>();
    protected DateFormat dateToStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public ExcelFactory() {
        dataFormat.add("dd/MM/yyy");
    }

    /**
     * 功能描述: <br>
     * 解析Excel2003
     * 
     * @param clz
     * @param file excel文件
     * @param startRow 从此开始解析
     * @param startCol 从此列开始解析
     * @return
     */
//    public abstract <T> List<T> excel2List(Class<T> clz, File file, int startRow, int startCol);

    /**
     * 功能描述: <br>
     * 将List转换为Excel文件
     * 
     * @param os 输出流
     * @param list 数据集合
     * @param isMerge 是否合并相同内容的单元格
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public abstract void list2Excel(OutputStream os, List<?> list,List<BasicNameValuePair> headList, boolean isMerge) throws Exception;

    /**
     * 功能描述: <br>
     * 将HashMap类型的List转换为Excel文件
     * 
     * @param os 输出流
     * @param list 数据集合
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public abstract void mapList2Excel(OutputStream os, List<Map> list) throws Exception;

    /**
     * 功能描述: <br>
     * Excel导出根据后缀判断导出类型2003,或2007
     * 
     * @param fileName 文件名
     * @param list 数据集合
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void exportExcel(HttpServletResponse response,String fileName, List<?> list,List<BasicNameValuePair> headList)throws Exception{
        exportExcel(response,fileName, list,headList, false);
    }

    /**
     * 功能描述: <br>
     * Excel导出根据后缀判断导出类型2003,或2007
     * 
     * @param fileName 文件名
     * @param list 数据集合
     * @param isMerge 是否合并
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void exportExcel(HttpServletResponse response,String fileName, List<?> list,List<BasicNameValuePair> headList, boolean isMerge)throws Exception{
        response.setContentType("application/msexcel");
        OutputStream os = null;
        String name = new String(fileName.getBytes("GBK"), "ISO8859_1");
        response.setHeader("Content-disposition", "attachment;filename=\"" + name + "\"");
        os = response.getOutputStream();
        getFactoryByFileName(fileName).list2Excel(os, list,headList, isMerge);
    }

    /**
     * 功能描述: <br>
     * 根据文件后缀将不同版本的Excel转换为List
     * 
     * @param clz 映射类
     * @param file excel文件
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    /*public static <T> List<T> importExcel(Class<T> clz, File file) {
        return importExcel(clz, file, 1, 0);
    }
*/
    /**
     * 功能描述: <br>
     * 根据文件后缀将不同版本的Excel转换为List
     * 
     * @param clz 映射类
     * @param file excel文件
     * @param startRow 起始行
     * @param startCol 起始列
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    /*public static <T> List<T> importExcel(Class<T> clz, File file, int startRow, int startCol) {
        HttpServletRequest request = ServletActionContext.getRequest();
        MultiPartRequest multi = null;
        try {
            Field fd = request.getClass().getDeclaredField("multi");
            fd.setAccessible(true);
            multi = (MultiPartRequest) fd.get(request);
        } catch (Exception e) {
            throw new BaseException(e);
        }
        String[] names = multi.getFileNames("file");
        if (names.length < 1) {
            throw new ClientException("未找到上传的文件名");
        }
        return getFactoryByFileName(names[0]).excel2List(clz, file, startRow, startCol);
    }*/

    /**
     * 功能描述: <br>
     * 根据文件名后缀获取Excel工具类
     * 
     * @param fileName
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static ExcelFactory getFactoryByFileName(String fileName)throws Exception{
        if (fileName.endsWith("xls")) {
            return getFactory2003();
        }
        /*if (fileName.endsWith("xlsx")) {
            return getFactory2007();
        }*/
        throw new NoticeException("未匹配到文件后缀:" + fileName.substring(fileName.lastIndexOf("."), fileName.length()));
    }

    /**
     * 获取set方法名
     * 
     * @param fieldName
     * @return
     */
    protected String setMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 获取get方法名
     * 
     * @param fieldName
     * @return
     */
    protected String getMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 基本数据类型转换
     * 
     * @param cls 类型
     * @param str 值
     * @return 字段类型的值
     * @throws ParseException 日期转换异常
     */
    protected Object convertPrimitive(Class cls, String str) {
        if (str == null || "".equals(str.trim())) {
            if (cls == byte.class)
                return 0;
            if (cls == short.class)
                return 0;
            if (cls == int.class)
                return 0;
            if (cls == long.class)
                return 0L;
            if (cls == float.class)
                return 0.0f;
            if (cls == double.class)
                return 0.0d;
            if (cls == char.class)
                return 0;
            if (cls == boolean.class)
                return false;
            return null;
        }

        if (cls == Byte.class || cls == byte.class)
            return new BigDecimal(str).byteValue();
        if (cls == Integer.class || cls == int.class)
            return new BigDecimal(str).intValue();
        if (cls == Short.class || cls == short.class)
            return new BigDecimal(str).shortValue();
        if (cls == Long.class || cls == long.class)
            return new BigDecimal(str).longValue();
        if (cls == Float.class || cls == float.class)
            return new BigDecimal(str).floatValue();
        if (cls == Double.class || cls == double.class)
            return new BigDecimal(str).doubleValue();
        if (cls == Character.class || cls == char.class)
            return str.length() > 0 ? str.charAt(0) : 0;
        if (cls == Boolean.class || cls == boolean.class)
            return Boolean.parseBoolean(str);
        if (cls.isEnum() || Enum.class.isAssignableFrom(cls))
            return Enum.valueOf(cls, str);
        if (cls == BigDecimal.class)
            return new BigDecimal(str);

        if (cls == Date.class) {
            DateFormat formatter = null;
            for (String format : dataFormat) {
                formatter = new SimpleDateFormat(format, Locale.US);
                try {
                    return formatter.parse(str);
                } catch (ParseException e) {
                    continue;
                }
            }
            throw new RuntimeException("无法匹配日期格式：" + str);
        }
        return str.trim();
    }

    /**
     * 功能描述: <br>
     * 字段合法性验证
     * 
     * @param fd
     * @param value
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    /*protected void validateField(Field fd, Object value, int row, StringBuffer msg) {
        String vdInfo = validateField(fd, value);
        if (vdInfo != null) {
            msg.append("第 " + (row) + " 行, " + vdInfo);
        }
    }*/

    /**
     * 功能描述: <br>
     * 字段合法性验证
     * 
     * @param fd
     * @param value
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    /*protected String validateField(Field fd, Object value) {
        String msg = null;
        if (fd.isAnnotationPresent(Validation.class)) {

            Validation vald = fd.getAnnotation(Validation.class);
            if (value == null && !vald.allowBlank()) {
                FieldDesc fdesc = fd.getAnnotation(FieldDesc.class);
                msg = fdesc.value() + " 不能为空<BR/>";
            }
        }
        return msg;
    }*/

    /**
     * 对象转字符串
     * 
     * @param obj
     * @return
     */
    protected String objectToString(Object obj) {
        if (obj.getClass() == Date.class) {
            return dateToStr.format((Date) obj);
        }
        return String.valueOf(obj);
    }

    /**
     * 功能描述: <br>
     * 获取操作Excel2003的工具类
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static ExcelFactory getFactory2003() {
        if (factory2003 == null) {
            factory2003 = new JxlFor2003();
        }
        return factory2003;
    }

    /**
     * 功能描述: <br>
     * 获取操作Excel2007的工具类
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    /*public static ExcelFactory getFactory2007() {
        if (factory2007 == null) {
            factory2007 = new PoiFor2007();
        }
        return factory2007;
    }*/
}
