package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.IMallRefundRecordExportVO;
import com.yimayhd.sellerAdmin.model.query.RefundListQuery;
import com.yimayhd.sellerAdmin.service.RefundService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundDetailDO;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;
import com.yimayhd.tradecenter.client.model.query.IMallRefundRecordQuery;
import com.yimayhd.tradecenter.client.model.result.TCPageResult;
import com.yimayhd.tradecenter.client.model.result.TCResultDTO;
import com.yimayhd.tradecenter.client.service.imall.IMallHaremService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czf on 2015/10/27.
 */
public class RefundServiceImpl implements RefundService {
    private static final Logger log = LoggerFactory.getLogger(RefundServiceImpl.class);
    private static final int EXPORT_PAGE_NUMBER = 1;//导出数据时的页码
    private static final int EXPORT_PAGE_SIZE = 100000;//导出数据的条数上限
    @Autowired
    private IMallHaremService iMallHaremServiceRef;
    @Override
    public PageVO<IMallRefundRecordDO> getList(long sellerId,RefundListQuery refundListQuery) throws Exception{
        //查询条件
        IMallRefundRecordQuery tcRefundRecordQuery = new IMallRefundRecordQuery();
        tcRefundRecordQuery.setSellerId(sellerId);
        if(!StringUtils.isEmpty(refundListQuery.getTradeId())){
            tcRefundRecordQuery.setTradeId(refundListQuery.getTradeId());
        }
        if(!StringUtils.isEmpty(refundListQuery.getBeginDate())) {
            tcRefundRecordQuery.setGmtCreatedStart(DateUtil.formatMinTimeForDate(refundListQuery.getBeginDate()));
        }
        if(!StringUtils.isEmpty(refundListQuery.getEndDate())) {
            tcRefundRecordQuery.setGmtCreatedEnd(DateUtil.formatMaxTimeForDate(refundListQuery.getEndDate()));
        }
        tcRefundRecordQuery.setCurrentPage(refundListQuery.getPageNo());
        tcRefundRecordQuery.setPageSize(refundListQuery.getPageSize());

        TCPageResult<IMallRefundRecordDO> tcPageResult = iMallHaremServiceRef.queryRefundRecords(tcRefundRecordQuery);
        //返回结果
        PageVO<IMallRefundRecordDO> pageVO = new PageVO<IMallRefundRecordDO>(refundListQuery.getPageNo(), refundListQuery.getPageSize(),0);;
        if(null != tcPageResult && tcPageResult.isSuccess()) {
            pageVO = new PageVO<IMallRefundRecordDO>(refundListQuery.getPageNo(), refundListQuery.getPageSize(), (int) tcPageResult.getTotalCount(), tcPageResult.getList());
        }
        return pageVO;
    }

    @Override
    public List<IMallRefundRecordExportVO> exportRefundList(long sellerId, RefundListQuery refundListQuery) throws Exception {
        //返回结果
        List<IMallRefundRecordExportVO> iMallRefundRecordExportVOList = null;
        //查询条件
        IMallRefundRecordQuery tcRefundRecordQuery = new IMallRefundRecordQuery();
        tcRefundRecordQuery.setSellerId(sellerId);
        if(!StringUtils.isEmpty(refundListQuery.getTradeId())){
            tcRefundRecordQuery.setTradeId(refundListQuery.getTradeId());
        }
        if(!StringUtils.isEmpty(refundListQuery.getBeginDate())) {
            tcRefundRecordQuery.setGmtCreatedStart(DateUtil.formatMinTimeForDate(refundListQuery.getBeginDate()));
        }
        if(!StringUtils.isEmpty(refundListQuery.getEndDate())) {
            tcRefundRecordQuery.setGmtCreatedEnd(DateUtil.formatMaxTimeForDate(refundListQuery.getEndDate()));
        }
        tcRefundRecordQuery.setCurrentPage(EXPORT_PAGE_NUMBER);
        tcRefundRecordQuery.setPageSize(EXPORT_PAGE_SIZE);
        TCPageResult<IMallRefundRecordDO> tcPageResult = iMallHaremServiceRef.queryRefundRecords(tcRefundRecordQuery);
        if(null != tcPageResult && tcPageResult.isSuccess() && CollectionUtils.isNotEmpty(tcPageResult.getList())) {
            iMallRefundRecordExportVOList = new ArrayList<IMallRefundRecordExportVO>();
            for (IMallRefundRecordDO iMallRefundRecordDO : tcPageResult.getList()){
                iMallRefundRecordExportVOList.add(IMallRefundRecordExportVO.getIMallRefundRecordExportVO(iMallRefundRecordDO));
            }
        }
        return iMallRefundRecordExportVOList;
    }

    @Override
    public List<IMallRefundDetailDO> getOrderByRecordId(long sellerId,long recordId) throws Exception {
        IMallRefundRecordQuery iMallRefundRecordQuery = new IMallRefundRecordQuery();
        iMallRefundRecordQuery.setRecordId(recordId);
        iMallRefundRecordQuery.setSellerId(sellerId);
        TCResultDTO<List<IMallRefundDetailDO>> tcResultDTO = iMallHaremServiceRef.queryRefundDetail(iMallRefundRecordQuery);
        if(tcResultDTO.isSuccess()){
            return tcResultDTO.getT();
        }else{
            log.error("itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(tcResultDTO));
            throw new NoticeException(tcResultDTO.getResultMsg());
        }
    }
}
