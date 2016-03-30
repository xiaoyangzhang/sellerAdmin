package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.IMallRefundRecordExportVO;
import com.yimayhd.sellerAdmin.model.query.RefundListQuery;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundDetailDO;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface RefundService {

    /**
     * 获取退款列表(可带查询条件)
     * @param sellerId 商家ID
     * @param refundListQuery 查询条件
     * @return
     * @throws Exception
     */
    PageVO<IMallRefundRecordDO> getList(long sellerId,RefundListQuery refundListQuery)throws Exception;

    /**
     * 导出退款列表
     * @param sellerId 商家ID
     * @param refundListQuery
     * @return
     */
    List<IMallRefundRecordExportVO> exportRefundList(long sellerId,RefundListQuery refundListQuery)throws Exception;


    /**
     * 根据交易id获取详情(退款)
     * @param recordId
     * @param sellerId
     * @return
     * @throws Exception
     */
    List<IMallRefundDetailDO> getOrderByRecordId(long sellerId,long recordId)throws Exception;

}
