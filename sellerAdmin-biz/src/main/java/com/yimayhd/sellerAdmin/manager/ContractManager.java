package com.yimayhd.sellerAdmin.manager;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.MemberContractCodeDTO;
import com.yimayhd.membercenter.enums.ContractType;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.sellerAdmin.domain.ContractPDFDomain;
import com.yimayhd.sellerAdmin.enums.MerchantParentCategoryMappingEnum;
import com.yimayhd.sellerAdmin.repo.CityRepo;
import com.yimayhd.sellerAdmin.repo.MemberContractRepo;
import com.yimayhd.sellerAdmin.service.TfsService;
import com.yimayhd.sellerAdmin.util.HtmlToPdfUtil;
import com.yimayhd.user.client.dto.CityDTO;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxiaopeng on 16/11/1.
 */
public class ContractManager {

    @Autowired
    private MemberContractRepo memberContractRepo;

    @Autowired
    private VelocityConfigurer velocityConfig;

    @Autowired
    private CityRepo cityRepo;

    private static final Logger logger = LoggerFactory.getLogger("ContractManager");


    @Autowired
    private TfsService tfsService;

    @Value("${resource.path.jiuxiu}")
    private String RESOURCE_PATH;

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
        String contractCode = memberContractRepo.getCode(memberContractCodeDTO);
        ContractPDFDomain contractPDFDomain = new ContractPDFDomain();
        contractPDFDomain.setCode(contractCode);
        contractPDFDomain.setMerchantName(examineInfoDTO.getSellerName());
        contractPDFDomain.setAccountName(examineInfoDTO.getFinanceOpenName());
        contractPDFDomain.setAccountNo(examineInfoDTO.getAccountNum());
        contractPDFDomain.setAccountBank(getBankShow(examineInfoDTO));
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dStr = sdf.format(d);
        contractPDFDomain.setYear(dStr.substring(0, 4));
        contractPDFDomain.setMonth(dStr.substring(4, 6));
        contractPDFDomain.setDay(dStr.substring(6, 8));
        File file = create(contractPDFDomain, templatePrefix + memberContractCodeDTO.getContractType().getName() + templateSubfix);
        try {
            return tfsService.uploadToTFS(file.getPath());
        } catch (Exception e) {
            logger.error("createContract has exception={}", e);
            return null;
        } finally {
            file.delete();
        }
    }

    public File downloadContract(long sellerId, ExamineInfoDTO examineInfoDTO, Date renewDate) {

        MemberContractCodeDTO memberContractCodeDTO = new MemberContractCodeDTO();
        memberContractCodeDTO.setSellerId(sellerId);
        memberContractCodeDTO.setRenewDate(renewDate);
        if (examineInfoDTO.getType() == MerchantType.TALENT.getType()) {
            memberContractCodeDTO.setContractType(ContractType.DAREN);
        } else {
            memberContractCodeDTO.setContractType(MerchantParentCategoryMappingEnum.getByCategory(examineInfoDTO.getMerchantCategoryId()).getContractType());
        }
        String contractCode = memberContractRepo.getCode(memberContractCodeDTO);
        ContractPDFDomain contractPDFDomain = new ContractPDFDomain();
        contractPDFDomain.setCode(contractCode);
        contractPDFDomain.setMerchantName(examineInfoDTO.getSellerName());
        contractPDFDomain.setAccountName(examineInfoDTO.getFinanceOpenName());
        contractPDFDomain.setAccountNo(examineInfoDTO.getAccountNum());
        contractPDFDomain.setAccountBank(getBankShow(examineInfoDTO));
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dStr = sdf.format(d);
        contractPDFDomain.setYear(dStr.substring(0, 4));
        contractPDFDomain.setMonth(dStr.substring(4, 6));
        contractPDFDomain.setDay(dStr.substring(6, 8));

        return create(contractPDFDomain, templatePrefix + memberContractCodeDTO.getContractType().getName() + templateSubfix);
    }

    public File create(ContractPDFDomain contractPDFDomain, String templateUrl) {

        VelocityEngine velocityEngine = velocityConfig.getVelocityEngine();
        Template template = velocityEngine.getTemplate(templateUrl, "UTF-8");
        Template template1 = velocityEngine.getTemplate(templatePrefix+"Template.vm", "UTF-8");
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("pdfDomain", contractPDFDomain);
        velocityContext.put("resourcePath", RESOURCE_PATH);
        String str = "";
        String str1 = "";
        try {
            Writer writer = new StringWriter();
            template.merge(velocityContext, writer);
            writer.flush();
            str = writer.toString();
            writer.close();

            //template
            Writer writer1 = new StringWriter();
            template1.merge(velocityContext, writer1);
            writer1.flush();
            str1 = writer1.toString();
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(contractPDFDomain.getCode() + ".pdf");
        File fileContent = new File(contractPDFDomain.getCode() + "content.pdf");
        File fileTable = new File(contractPDFDomain.getCode() + "table.pdf");
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
            HtmlToPdfUtil.htmlToPdf(byteArrayInputStream, fileContent.getPath());
            ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(str1.getBytes("UTF-8"));
            HtmlToPdfUtil.htmlToPdf(byteArrayInputStream1, fileTable.getPath());
            String[] files = new String[]{fileContent.getPath(),fileTable.getPath()};
            HtmlToPdfUtil.mergePdfFiles(files, file.getPath());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            file.delete();
        }finally {
            fileContent.delete();
            fileTable.delete();
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

    private String getBankShow(ExamineInfoDTO examineInfoDTO) {
        List<String> citys = new ArrayList<>();
        String city = examineInfoDTO.getAccountBankCityCode();
        String prov = examineInfoDTO.getAccountBankProvinceCode();
        citys.add(prov);
        citys.add(city);
        Map<String, CityDTO> mapResult = cityRepo.getCitiesByCodes(citys);
        String accountBankProvince = mapResult.get(prov).getName();
        String accountBankCity = mapResult.get(city).getName();
        accountBankCity = accountBankCity.equals(accountBankProvince)?"":accountBankCity;
        return examineInfoDTO.getFinanceOpenBankName() + accountBankProvince + accountBankCity + examineInfoDTO.getAccountBankName();
    }

    public static void main(String[] args) {
        ContractManager contractManager = new ContractManager();
    }
}
