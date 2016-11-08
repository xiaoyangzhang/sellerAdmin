package com.yimayhd.sellerAdmin.manager;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.MemberContractCodeDTO;
import com.yimayhd.membercenter.enums.ContractType;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.sellerAdmin.domain.ContractPDFDomain;
import com.yimayhd.sellerAdmin.enums.MerchantParentCategoryMappingEnum;
import com.yimayhd.sellerAdmin.repo.MemberContractRepo;
import com.yimayhd.sellerAdmin.service.TfsService;
import com.yimayhd.sellerAdmin.util.HtmlToPdfUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuxiaopeng on 16/11/1.
 */
public class ContractManager {

    @Autowired
    private MemberContractRepo memberContractRepo;

    @Autowired
    private VelocityConfigurer velocityConfig;

    @Autowired
    private TfsService tfsService;

    private String templatePrefix = "/contracttemplate/";

    private String templateSubfix = "ContractTemplate.vm";

    public String createContract(long sellerId, ExamineInfoDTO examineInfoDTO, Date renewDate) {

        MemberContractCodeDTO memberContractCodeDTO = new MemberContractCodeDTO();
        memberContractCodeDTO.setSellerId(sellerId);
        memberContractCodeDTO.setRenewDate(renewDate);
        if (examineInfoDTO.getType() == MerchantType.TALENT.getType()) {
            memberContractCodeDTO.setContractType(ContractType.DAREN);
        } else {
            memberContractCodeDTO.setContractType(MerchantParentCategoryMappingEnum.getByCategory(examineInfoDTO.getMerchantCategoryId()).getContractType());
        }
        memberContractCodeDTO.setContractType(MerchantParentCategoryMappingEnum.getByCategory(3).getContractType());
        String contractCode = memberContractRepo.getCode(memberContractCodeDTO);
        ContractPDFDomain contractPDFDomain = new ContractPDFDomain();
        contractPDFDomain.setCode(contractCode);
        contractPDFDomain.setMerchantName(examineInfoDTO.getSellerName());
        contractPDFDomain.setAccountName(examineInfoDTO.getFinanceOpenName());
        contractPDFDomain.setAccountNo(examineInfoDTO.getAccountNum());
        contractPDFDomain.setAccountBank(examineInfoDTO.getFinanceOpenBankName()+examineInfoDTO.getAccountBankName());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dStr = sdf.format(d);
        contractPDFDomain.setYear(dStr.substring(0,4));
        contractPDFDomain.setMonth(dStr.substring(4,6));
        contractPDFDomain.setDay(dStr.substring(6,8));

        return create(contractPDFDomain, templatePrefix + memberContractCodeDTO.getContractType().getName() + templateSubfix);
    }

    public String create(ContractPDFDomain contractPDFDomain, String templateUrl) {

        VelocityEngine velocityEngine = velocityConfig.getVelocityEngine();
        Template template = velocityEngine.getTemplate(templateUrl, "UTF-8");
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("pdfDomain", contractPDFDomain);
        String str = "";
        try {
            Writer writer = new StringWriter();
            template.merge(velocityContext, writer);
            writer.flush();
            str = writer.toString();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(contractPDFDomain.getCode() + ".pdf");
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
            HtmlToPdfUtil.htmlToPdf(byteArrayInputStream, file.getPath());
            return tfsService.uploadToTFS(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            file.delete();
        }
        return null;
    }

//    public String create(ContractPDFDomain contractPDFDomain) {
//
//        VelocityEngine velocityEngine = velocityConfig.getVelocityEngine();
//        Template template = velocityEngine.getTemplate("/contracttemplate/DianpuContractTemplate", "UTF-8");
//        VelocityContext velocityContext = new VelocityContext();
//        velocityContext.put("pdfDomain", contractPDFDomain);
//        String str = "";
//        File file = new File("/Users/liuxiaopeng/Documents/template1.html");
//        try {
//            Writer writer = new FileWriterWithEncoding(file.getPath(), "UTF-8");
//            template.merge(velocityContext, writer);
//            writer.flush();
//            str = writer.toString();
//            writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            HtmlToPdfUtil.htmlToPdf(fileInputStream, "/Users/liuxiaopeng/Documents/template2.pdf");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static void main(String[] args) {
        ContractManager contractManager = new ContractManager();
    }
}
