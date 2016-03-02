package com.yimayhd.sellerAdmin.util.excel;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.http.message.BasicNameValuePair;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JxlFor2003 extends ExcelFactory {


    /*
     * (non-Javadoc)
     * @see com.suning.bi.manage.common.util.ExcelFactory#excel2List(java.lang.Class, java.io.File, int, int)
     */
    /*@Override
    public List excel2List(Class clz, File file, int startRow, int startCol) {

        Field[] fds = clz.getDeclaredFields();
        List<Method> mls = new ArrayList<Method>();
        List<Field> fls = new ArrayList<Field>();

        List ls = new ArrayList();
        StringBuffer msg = new StringBuffer();
        Workbook book = null;
        try {
            for (Field fd : fds) {
                if (fd.isAnnotationPresent(ExceptField.class)) {
                    continue;
                }
                Method m = clz.getMethod(setMethodName(fd.getName()), fd.getType());
                mls.add(m);
                fls.add(fd);
            }

            Constructor constructor = clz.getDeclaredConstructor(new Class[0]);

            Object objNew = null;
            Cell cell = null;
            Object value = null;
            book = Workbook.getWorkbook(file);
            Sheet[] sheets = book.getSheets();
            if (sheets != null) {
                for (int i = 0; i < sheets.length; i++) {
                    Sheet sheet = book.getSheet(i);
                    for (int j = startRow, rlen = sheet.getRows(); j < rlen; j++) {
                        objNew = constructor.newInstance(new Class[0]);
                        for (int k = startCol, clen = sheet.getColumns(), index = 0; k < clen; k++) {
                            cell = sheet.getCell(k, j);
                            value = cell == null ? null
                                    : convertPrimitive(fls.get(index).getType(), cell.getContents());
                            validateField(fls.get(index), value, j + 1, msg);
                            mls.get(index).invoke(objNew, value);
                            index++;
                        }
                        ls.add(objNew);
                    }
                }
            }
        } catch (Exception e) {
            throw new BaseException("Excel解析失败", e);
        } finally {
            if (book != null) {
                book.close();
            }
        }

        if (msg.length() > 0) {
            throw new ClientException(msg.toString());
        }
        return ls;
    }*/

    /*
     * (non-Javadoc)
     * @see com.suning.bi.manage.common.util.ExcelFactory#list2Excel(java.io.OutputStream, java.util.List, boolean)
     */
    @Override
    public void list2Excel(OutputStream os, List<?> list,List<BasicNameValuePair> headList, boolean isMerge) throws Exception{
        if (list == null || list.size() == 0)
            return;

        Class clz = list.get(0).getClass();
        Field[] fds = clz.getDeclaredFields();

        List<Method> mls = new ArrayList();
        List<String> headers = new ArrayList();
        WritableWorkbook wwb = null;
        for (int i = 0; i < headList.size(); i++) {
            for (Field fd : fds) {
                if(headList.get(i).getName().equals( fd.getName())){
                    Method m = clz.getMethod(getMethodName(fd.getName()));
                    mls.add(m);
                    headers.add(headList.get(i).getValue());
                    break;
                }
            }
        }
            wwb = Workbook.createWorkbook(os);
            WritableSheet ws = null;
            WritableCellFormat cf = new WritableCellFormat();
//            cf.setBackground(Colour.ORANGE);
            Method m = null;
            for (int i = 0; i < list.size(); i++) {
                if (i % SHEET_NUM == 0) {
                    ws = wwb.createSheet("sheet" + (i / SHEET_NUM + 1), i);
                    ws.getSettings().setDefaultRowHeight(DEFAULT_ROW_HEIGHT);
                    for (int h = 0; h < headers.size(); h++) {
                        ws.setColumnView(h, 20);
                        ws.addCell(new jxl.write.Label(h, 0, headers.get(h), cf));
                    }
                }
                int row = i % SHEET_NUM;
                for (int j = 0; j < mls.size(); j++) {
                    m = mls.get(j);
                    if (null == m.invoke(list.get(i))) {
                        continue;
                    }
                    ws.addCell(new Label(j, row + 1, objectToString(m.invoke(list.get(i)))));
                    if (isMerge && i % SHEET_NUM > 0) {
                        Object prev = m.invoke(list.get(i - 1));
                        Object current = m.invoke(list.get(i));
                        if ((prev == null && current == null)
                                || (prev != null && current != null && prev.equals(current))) {
                            ws.mergeCells(j, row, j, row + 1);
                        }
                    }
                }
            }
            wwb.write();
            wwb.close();
            if (os != null) {
                os.flush();
                os.close();
            }
    }



    /*
         * (non-Javadoc)
         * @see com.suning.bi.manage.common.util.ExcelFactory#mapList2Excel(java.io.OutputStream, java.util.List)
         */
    @Override
    public void mapList2Excel(OutputStream os, List<Map> list) throws Exception {
        if (list == null || list.size() == 0)
            return;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<String> headers = new ArrayList(list.get(0).keySet());

        WritableWorkbook wwb = Workbook.createWorkbook(os);
        WritableSheet ws = null;
        WritableCellFormat cf = new WritableCellFormat();
        cf.setBackground(Colour.ORANGE);
        for (int i = 0, len = list.size(); i < len; i++) {
            Map<String, Object> mapo = list.get(i);
            if (i % SHEET_NUM == 0) {
                ws = wwb.createSheet("sheet" + (i / SHEET_NUM + 1), i);
                ws.getSettings().setDefaultRowHeight(DEFAULT_ROW_HEIGHT);
                for (int h = 0, hlen = headers.size(); h < hlen; h++) {
                    ws.setColumnView(h, 20);
                    ws.addCell(new jxl.write.Label(h, 0, headers.get(h), cf));
                }
            }
            int row = i % SHEET_NUM;
            for (int j = 0, hlen = headers.size(); j < hlen; j++) {
                Object value = mapo.get(headers.get(j));
                if (null == value) {
                    continue;
                }
                if(Date.class==value.getClass()){
                    value = sdf.format(value);
                }
                ws.addCell(new Label(j, row + 1, value.toString()));
            }
        }
        wwb.write();
        wwb.close();
        os.flush();
        os.close();
    }

}
