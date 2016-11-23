package com.yimayhd.sellerAdmin.repo;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.membercenter.client.dto.MemberContractCodeDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.MemberContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuxiaopeng on 16/11/3.
 */
public class MemberContractRepo {

    @Autowired
    private MemberContractService memberContractServiceRef;

    private static final Logger logger = LoggerFactory.getLogger("MemberContractRepo");

    @MethodLogger
    public String getCode(MemberContractCodeDTO memberContractCodeDTO) {

        if (null == memberContractCodeDTO || memberContractCodeDTO.getSellerId() == 0 || memberContractCodeDTO.getType() == 0) {
            logger.error("MemberContractRepo getCode param is null");
            return null;
        }

        MemResult<String> result = memberContractServiceRef.getContractCode(memberContractCodeDTO);
        if (null == result || !result.isSuccess()) {
            logger.error("MemberContractRepo getCode memberContractCodeDTO={}, result={}", JSONObject.toJSONString(memberContractCodeDTO), JSONObject.toJSONString(result));
            return null;
        }
        logger.info("MemberContractRepo getCode memberContractCodeDTO={}, result={}", JSONObject.toJSONString(memberContractCodeDTO), JSONObject.toJSONString(result));
        return result.getValue();
    }
}
