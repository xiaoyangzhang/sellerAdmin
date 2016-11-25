package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.sellerAdmin.enums.CertificatesType;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.SubOrder;
import com.yimayhd.sellerAdmin.util.excel.domain.BizOrderExportDomain;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuxiaopeng on 16/11/17.
 */
public class ExcelExportConverer {

    public static List<BizOrderExportDomain> exportOrder(MainOrder mainOrder) {

        TcMainOrder tcMainOrder = mainOrder.getTcMainOrder();
        LogisticsOrderDO logisticsOrderDO = mainOrder.getLogisticsOrderDO();
        ContactUser contactUserInfo = mainOrder.getTcMainOrder().getContactInfo();

        List<BizOrderExportDomain> bizOrderExportDomains = new ArrayList<>();

        for (SubOrder subOrder : mainOrder.getSubOrderList()) {
            BizOrderExportDomain bizOrderExportDomain = new BizOrderExportDomain();
            if(null!=logisticsOrderDO) {
                bizOrderExportDomain.setConsigneeName(logisticsOrderDO.getFullName());
                bizOrderExportDomain.setConsigneeTel(logisticsOrderDO.getMobilePhone());
                bizOrderExportDomain.setReceivingProvince(logisticsOrderDO.getProv());
                bizOrderExportDomain.setReceivingCity(logisticsOrderDO.getCity());
                bizOrderExportDomain.setReceivingAdress(logisticsOrderDO.getArea());
                bizOrderExportDomain.setReceivingAdress(logisticsOrderDO.getAddress());
            }
            if(null!=contactUserInfo) {
                bizOrderExportDomain.setContactsName(contactUserInfo.getContactName());
                bizOrderExportDomain.setContactsTel(contactUserInfo.getContactPhone());
                bizOrderExportDomain.setContactsEmail(contactUserInfo.getContactEmail());
            }
            bizOrderExportDomain.setBizOrderId(subOrder.getTcDetailOrder().getBizOrder().getBizOrderId());
            bizOrderExportDomain.setParentBizOrderId(tcMainOrder.getBizOrder().getBizOrderId());
            bizOrderExportDomain.setCommodityId(subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getItemId());
            bizOrderExportDomain.setCommodityName(subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getItemTitle());
            bizOrderExportDomain.setUnitPrice(subOrder.getItemPrice_());
            bizOrderExportDomain.setItemNum(subOrder.getTcDetailOrder().getBizOrder().getBuyAmount());
            bizOrderExportDomain.setItemType(subOrder.getOrderTypeStr());
            bizOrderExportDomain.setBuyerName(mainOrder.getUser().getName());
            bizOrderExportDomain.setBuyerTel(mainOrder.getUser().getUnmaskMobile());
            bizOrderExportDomain.setBizOrderType(subOrder.getOrderStatusStr());
            bizOrderExportDomain.setItemPrice(subOrder.getItemPrice_());
            bizOrderExportDomain.setDiscount(mainOrder.getValue());
            bizOrderExportDomain.setBizOrderTotalPrice(subOrder.getSubOrderTotalFee());
            bizOrderExportDomain.setRealCollection(subOrder.getSubOrderTotalFee());
            bizOrderExportDomain.setPayScore(mainOrder.getUserPointNum());
            bizOrderExportDomain.setBizOrderCreateTime(new Date(subOrder.getTcDetailOrder().getBizOrder().getCreateTime()));
            bizOrderExportDomain.setBizOrderPayTime(new Date(subOrder.getTcDetailOrder().getBizOrder().getPayTime()));
            bizOrderExportDomain.setBuyerNotes(tcMainOrder.getOtherInfo());

            String tradeInfos = new String();
            List<ContactUser> contactUsers = tcMainOrder.getTouristList();
            if(null!=contactUsers) {
                for (ContactUser contactUser : contactUsers) {
                    String cardType;
                    try {
                        cardType = CertificatesType.getByType(Integer.parseInt(contactUser.getCertificatesType())).getDesc();
                    } catch (Exception e) {
                        cardType = CertificatesType.CARD.getDesc();
                    }
                    tradeInfos = "游客姓名:" + contactUser.getContactName() + ",手机号:" + contactUser.getContactPhone() + ",证件类型:" + cardType + ",证件号:" + contactUser.getCertificatesNum();
                }
                bizOrderExportDomain.setTravelListStr(tradeInfos);
            }
            bizOrderExportDomains.add(bizOrderExportDomain);
        }
        return bizOrderExportDomains;
    }

    public static List<BizOrderExportDomain> exportOrderList(List<MainOrder> orders) {
        List<BizOrderExportDomain> bizOrderExportDomains = new ArrayList<>();
        for (MainOrder mainOrder : orders) {
            bizOrderExportDomains.addAll(exportOrder(mainOrder));
        }
        return bizOrderExportDomains;
    }
}
