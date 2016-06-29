package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.BizOrderExportVO;
import com.yimayhd.sellerAdmin.model.BizOrderVO;
import com.yimayhd.sellerAdmin.model.PayOrderExportVO;
import com.yimayhd.sellerAdmin.model.query.PayListQuery;
import com.yimayhd.sellerAdmin.model.query.TradeListQuery;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface TradeService {
    /**
     * 获取交易列表(可带查询条件)
     * @param sellerId 商家ID
     * @return 交易列表
     */
    PageVO<BizOrderVO> getOrderList(long sellerId,TradeListQuery tradeListQuery)throws Exception;
    /**
     * 导出交易列表
     * @param sellerId 商家ID
     * @param tradeListQuery
     * @return
     */
    List<BizOrderExportVO> exportOrderList(long sellerId,TradeListQuery tradeListQuery)throws Exception;

    /**
     * 支付列表
     * @param sellerId 商家ID
     * @param payListQuery
     * @return
     */
    PageVO<PayOrderDO> getPayOrderList(long sellerId,PayListQuery payListQuery)throws Exception;

    /**
     * 导出支付列表
     * @param sellerId 商家ID
     * @param payListQuery
     * @return
     */
    List<PayOrderExportVO> exportPayOrderList(long sellerId,PayListQuery payListQuery)throws Exception;

    /**
     * 根据交易id获取详情
     * @return
     * @throws Exception
     */
    List<BizOrderDO> getOrderByOrderId(long orderId)throws Exception;

}
