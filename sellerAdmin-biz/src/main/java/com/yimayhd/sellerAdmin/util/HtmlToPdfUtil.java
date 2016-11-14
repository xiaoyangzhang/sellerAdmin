package com.yimayhd.sellerAdmin.util;



import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by liuxiaopeng on 16/10/27.
 */
public class HtmlToPdfUtil {

    public static void htmlToPdf(InputStream inputStream, String pdfUrl) throws IOException, DocumentException/*, DocumentException*/ {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfUrl));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, inputStream, Charset.forName("UTF-8"));
        document.close();
    }

    public static void mergePdfFiles(String[] files, String savepath)
    {
        try
        {
            Document document = new Document(new PdfReader(files[0]).getPageSize(1));

            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));

            document.open();

            for(int i=0; i<files.length; i++)
            {
                PdfReader reader = new PdfReader(files[i]);

                int n = reader.getNumberOfPages();

                for(int j=1; j<=n; j++)
                {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch(DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            htmlToPdf(new FileInputStream(new File("/Users/liuxiaopeng/Documents/template1.html")), "template2.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
