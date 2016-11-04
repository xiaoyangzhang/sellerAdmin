package com.yimayhd.sellerAdmin.manager;

import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.MemberContractCodeDTO;
import com.yimayhd.sellerAdmin.domain.ContractPDFDomain;
import com.yimayhd.sellerAdmin.enums.MerchantParentCategoryMappingEnum;
import com.yimayhd.sellerAdmin.repo.MemberContractRepo;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.Date;

/**
 * Created by liuxiaopeng on 16/11/1.
 */
public class ContractManager {

    @Autowired
    private MemberContractRepo memberContractRepo;

    @Resource
    private VelocityConfigurer velocityConfig;

    public String createContract(long sellerId, ExamineInfoDTO examineInfoDTO) {

        MemberContractCodeDTO memberContractCodeDTO = new MemberContractCodeDTO();
        memberContractCodeDTO.setSellerId(sellerId);
        memberContractCodeDTO.setContractType(MerchantParentCategoryMappingEnum.getByCategory(examineInfoDTO.getMerchantCategoryId()).getContractType());
        String contractCode = memberContractRepo.getCode(memberContractCodeDTO);
        ContractPDFDomain contractPDFDomain = new ContractPDFDomain();
        contractPDFDomain.setCode(contractCode);
        contractPDFDomain.setMerchantName(examineInfoDTO.getSellerName());
        contractPDFDomain.setAccountName(examineInfoDTO.getFinanceOpenName());
        contractPDFDomain.setAccountNo(examineInfoDTO.getAccountNum());
        contractPDFDomain.setAccountBank(examineInfoDTO.getFinanceOpenBankName() + examineInfoDTO.getAccountBankProvince() + examineInfoDTO.getAccountBankCity() + examineInfoDTO.getAccountBankName());
        Date d = new Date();
        contractPDFDomain.setYear(d.getYear() + "");
        contractPDFDomain.setMonth(d.getMonth() + "");
        contractPDFDomain.setDay(d.getDay() + "");


        return null;
    }

    public String create() {

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();
//        VelocityEngine velocityEngine = velocityConfig.getVelocityEngine();
//        velocityEngine.addProperty("resourceLoaderPath","/WEB-INF/view");
//        velocityEngine.addProperty("configLocation", "/WEB-INF/velocity.properties");
//        Template template = velocityEngine.getTemplate("contracttemplate/DianpuContractTemplate.vm", "UTF-8");
        Template template = velocityEngine.getTemplate("/WEB-INF/view/success.vm", "UTF-8");
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("1",1);
        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext,stringWriter);
        System.out.print(stringWriter.toString());
        return null;
    }

    public static void main(String[] args) {
        ContractManager contractManager = new ContractManager();
        contractManager.create();
    }
}
