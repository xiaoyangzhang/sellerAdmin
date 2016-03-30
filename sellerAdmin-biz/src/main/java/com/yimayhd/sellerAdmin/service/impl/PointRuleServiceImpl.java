package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.IMallPointRuleVO;
import com.yimayhd.sellerAdmin.service.PointRuleService;
import com.yimayhd.tradecenter.client.model.param.imall.pointrule.IMallPointRuleDTO;
import com.yimayhd.tradecenter.client.model.query.IMallPointRuleQuery;
import com.yimayhd.tradecenter.client.model.result.TCPageResult;
import com.yimayhd.tradecenter.client.model.result.TCResultDTO;
import com.yimayhd.tradecenter.client.model.result.imall.pointrule.IMallPointRuleResult;
import com.yimayhd.tradecenter.client.service.imall.IMallHaremService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/11/9.
 */
public class PointRuleServiceImpl implements PointRuleService {
    private static final Logger log = LoggerFactory.getLogger(PointRuleServiceImpl.class);
    @Autowired
    private IMallHaremService iMallHaremServiceRef;
    @Override
    public IMallPointRuleResult getSendPointRuleNow(long sellerId) throws Exception {
        IMallPointRuleQuery iMallPointRuleQuery = new IMallPointRuleQuery();
        iMallPointRuleQuery.setVendorId(sellerId);
        TCResultDTO<IMallPointRuleResult> tcResultDTO = iMallHaremServiceRef.queryValidRuleBySellerId(iMallPointRuleQuery);
        if(null == tcResultDTO){
            log.error("IMallHaremService.queryValidRuleBySellerId result is null and parame: " + JSON.toJSONString(iMallPointRuleQuery) + "and sellerId: " + sellerId);
            throw new BaseException("返回结果错误");
        } else if(!tcResultDTO.isSuccess()){
            log.error("IMallHaremService.queryValidRuleBySellerId error:" + JSON.toJSONString(tcResultDTO) + "and parame: " + JSON.toJSONString(iMallPointRuleQuery) + "and sellerId: " + sellerId);
            throw new BaseException(tcResultDTO.getResultMsg());
        }
        IMallPointRuleResult iMallPointRuleResult = null;
        iMallPointRuleResult = tcResultDTO.getT();
        return iMallPointRuleResult;

    }

    @Override
    public PageVO<IMallPointRuleResult> getSendPointRuleHistory(long sellerId,BaseQuery baseQuery) throws Exception {

        IMallPointRuleQuery iMallPointRuleQuery = new IMallPointRuleQuery();
        iMallPointRuleQuery.setVendorId(sellerId);
        iMallPointRuleQuery.setPageSize(baseQuery.getPageSize());
        iMallPointRuleQuery.setCurrentPage(baseQuery.getPageNo());
        TCPageResult<IMallPointRuleResult> tcPageResult =  iMallHaremServiceRef.queryRuleRecords(iMallPointRuleQuery);
        PageVO<IMallPointRuleResult> pageVO = new PageVO<IMallPointRuleResult>(baseQuery.getPageNo(),baseQuery.getPageSize(),0);
        if(null == tcPageResult){
            log.error("IMallHaremService.queryRuleRecords result is null and parame: " + JSON.toJSONString(iMallPointRuleQuery) + "and baseQuery:" + JSON.toJSONString(baseQuery) + "and sellerId: " + sellerId);
            throw new BaseException("返回结果错误");
        } else if(!tcPageResult.isSuccess()){
            log.error("IMallHaremService.queryRuleRecords error:" + JSON.toJSONString(tcPageResult) + "and parame: " + JSON.toJSONString(iMallPointRuleQuery) + "and baseQuery:" + JSON.toJSONString(baseQuery) + "and sellerId: " + sellerId);
            throw new BaseException(tcPageResult.getResultMsg());
        }
        pageVO = new PageVO<IMallPointRuleResult>(baseQuery.getPageNo(),baseQuery.getPageSize(),tcPageResult.getTotalCount(),tcPageResult.getList());
        return pageVO;
    }

    @Override
    public boolean add(long sellerId,IMallPointRuleVO iMallPointRuleVO) throws Exception {
        IMallPointRuleDTO iMallPointRuleDTO = IMallPointRuleVO.getIMallPointRuleDTO(iMallPointRuleVO);
        iMallPointRuleDTO.setVendorId(sellerId);
        TCResultDTO<IMallPointRuleResult> tcResultDTO= iMallHaremServiceRef.addPointRule(iMallPointRuleDTO);
        if(null == tcResultDTO){
            log.error("IMallHaremService.addPointRule result is null and parame: " + JSON.toJSONString(iMallPointRuleDTO) + "and iMallPointRuleVO:" + JSON.toJSONString(iMallPointRuleVO) + "and sellerId: " + sellerId);
            throw new BaseException("返回结果错误,新增失败");
        } else if(!tcResultDTO.isSuccess()){
            log.error("IMallHaremService.addPointRule error:" + JSON.toJSONString(tcResultDTO) + "and parame: " + JSON.toJSONString(iMallPointRuleDTO) + "and iMallPointRuleVO:" + JSON.toJSONString(iMallPointRuleVO) + "and sellerId: " + sellerId);
            throw new BaseException(tcResultDTO.getResultMsg());
        }
        return true;
    }
}
