package com.yimayhd.sellerAdmin.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by liuxiaopeng on 16/10/27.
 */
public class HtmlToPdfUtil {

    public static void htmlToPdf(String htmlUrl, String pdfUrl) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfUrl));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new FileInputStream(htmlUrl), Charset.forName("UTF-8"));
        document.close();
    }

    public static void main(String[] args) {
//        htmlToPdf();
    }
}
