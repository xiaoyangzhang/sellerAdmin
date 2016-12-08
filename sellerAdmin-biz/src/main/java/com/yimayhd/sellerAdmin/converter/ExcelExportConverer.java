package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.sellerAdmin.enums.*;
import com.yimayhd.sellerAdmin.model.trade.MainOrder;
import com.yimayhd.sellerAdmin.model.trade.SubOrder;
import com.yimayhd.sellerAdmin.util.excel.domain.BizOrderExportDomain;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.model.result.order.metaq.OrderInfoTO;
import com.yimayhd.user.client.domain.UserDO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuxiaopeng on 16/11/17.
 */
public class ExcelExportConverer {

    public static List<BizOrderExportDomain> exportOrder(MainOrder mainOrder, List<OrderInfoTO> orders) {

        TcMainOrder tcMainOrder = mainOrder.getTcMainOrder();
        ContactUser contactUserInfo = mainOrder.getTcMainOrder().getContactInfo();
        List<BizOrderExportDomain> bizOrderExportDomains = new ArrayList<>();

        for (SubOrder subOrder : mainOrder.getSubOrderList()) {
            BizOrderExportDomain bizOrderExportDomain = new BizOrderExportDomain();

            if(null!=contactUserInfo) {
                bizOrderExportDomain.setContactsName(contactUserInfo.getContactName());
                bizOrderExportDomain.setContactsTel(contactUserInfo.getContactPhone());
                bizOrderExportDomain.setContactsEmail(contactUserInfo.getContactEmail());
            }
            UserDO userDO = mainOrder.getUser();
            bizOrderExportDomain.setBizOrderId(subOrder.getTcDetailOrder().getBizOrder().getBizOrderId());
            bizOrderExportDomain.setParentBizOrderId(subOrder.getTcDetailOrder().getParentId());
            bizOrderExportDomain.setCommodityId(subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getItemId());
            bizOrderExportDomain.setCommodityName(subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getItemTitle());
            bizOrderExportDomain.setResourceName(getResourceNameStr(tcMainOrder,subOrder.getOrderTypeStr()));
            bizOrderExportDomain.setUnitPrice(subOrder.getTcDetailOrder().getItemPrice());
            bizOrderExportDomain.setItemNum(subOrder.getTcDetailOrder().getBizOrder().getBuyAmount());
            bizOrderExportDomain.setItemType(BizItemExportType.get(subOrder.getOrderTypeStr()).getShowText());
            if(null!=userDO) {
                bizOrderExportDomain.setBuyerName(userDO.getNickname());
                bizOrderExportDomain.setBuyerTel(userDO.getUnmaskMobile());
            }

            bizOrderExportDomain.setBizOrderType(getStatusStr(subOrder.getOrderStatusStr(), subOrder.getOrderTypeStr()));
            bizOrderExportDomain.setDiscount(mainOrder.getValue());
            bizOrderExportDomain.setBizOrderTotalPrice(subOrder.getItemPrice_()*subOrder.getTcDetailOrder().getBizOrder().getBuyAmount());
            if(subOrder.getOrderStatusStr().equals("WAITING_PAY")||"CANCEL".equals(subOrder.getOrderStatusStr())) {
                bizOrderExportDomain.setRealCollection(0);
            } else {
                try {
                    if(null==subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getFeature(BizOrderFeatureKey.ACTUAL_AMOUNT_PAID)) {
                        bizOrderExportDomain.setRealCollection(Long.parseLong(subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getFeature(BizOrderFeatureKey.SUB_ORDER_ACTUAL_FEE)));
                    } else {
                        bizOrderExportDomain.setRealCollection(Long.parseLong(subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getFeature(BizOrderFeatureKey.ACTUAL_AMOUNT_PAID)));
                    }
                } catch (NumberFormatException e) {
                    bizOrderExportDomain.setRealCollection(subOrder.getTcDetailOrder().getBizOrder().getActualTotalFee());
                }
            }

            try {
                bizOrderExportDomain.setPayScore(Long.parseLong(subOrder.getTcDetailOrder().getBizOrder().getBizOrderDO().getFeature(BizOrderFeatureKey.USE_POINT_NUM)));
            } catch (NumberFormatException e) {
                bizOrderExportDomain.setPayScore(0);
            }
            bizOrderExportDomain.setBizOrderCreateTime(new Date(subOrder.getTcDetailOrder().getBizOrder().getCreateTime()));
            bizOrderExportDomain.setBizOrderPayTime(0==subOrder.getTcDetailOrder().getBizOrder().getPayTime()?null:new Date(subOrder.getTcDetailOrder().getBizOrder().getPayTime()));
            bizOrderExportDomain.setBuyerNotes(tcMainOrder.getOtherInfo());
            bizOrderExportDomain.setSellerNotes(tcMainOrder.getSellerMemo());
            bizOrderExportDomain.setCloseBizReason(tcMainOrder.getCloseReason());

            String tradeInfos = new String();
            List<ContactUser> contactUsers = tcMainOrder.getTouristList();
            if(null!=contactUsers) {
                int i =1;
                for (ContactUser contactUser : contactUsers) {
                    String cardType;
                    try {
                        cardType = CertificatesType.getByType(Integer.parseInt(contactUser.getCertificatesType())).getDesc();
                    } catch (Exception e) {
                        cardType = CertificatesType.CARD.getDesc();
                    }
                    String contractName = contactUser.getContactName()==null?"空":contactUser.getContactName();
                    String contractPhone = contactUser.getContactPhone()==null?"空":contactUser.getContactPhone();
                    String contractNum = contactUser.getCertificatesNum()==null?"空":contactUser.getCertificatesNum();
                    tradeInfos = tradeInfos + i + ".游客姓名:" + contractName + ",手机号:" + contractPhone + ",证件类型:" + cardType + ",证件号:" + contractNum + ";";
                    i++;
                }
                bizOrderExportDomain.setTravelListStr(tradeInfos);
            }
            LogisticsOrderDO logisticsOrderDO = mainOrder.getLogisticsOrderDO();
            for (OrderInfoTO orderInfoTO : orders) {
                if(orderInfoTO.getBizOrderDO().getBizOrderId()==bizOrderExportDomain.getBizOrderId()) {
                    logisticsOrderDO = orderInfoTO.getLogisticsOrderDO();
                    break;
                }
            }
            if(null!=logisticsOrderDO) {
                bizOrderExportDomain.setConsigneeName(logisticsOrderDO.getFullName());
                bizOrderExportDomain.setConsigneeTel(logisticsOrderDO.getMobilePhone());
                bizOrderExportDomain.setReceivingProvince(logisticsOrderDO.getProv());
                bizOrderExportDomain.setReceivingCity(logisticsOrderDO.getCity());
                bizOrderExportDomain.setReceivingArea(logisticsOrderDO.getArea());
                bizOrderExportDomain.setReceivingAdress(logisticsOrderDO.getAddress());
            }
            bizOrderExportDomains.add(bizOrderExportDomain);
        }
        return bizOrderExportDomains;
    }
    
    private static String getStatusStr(String status, String itemType) {

        if(itemType.equals("TOUR_LINE") || itemType.equals("FREE_LINE")
                || itemType.equals("CITY_ACTIVITY")
                || itemType.equals("TOUR_LINE_ABOARD") || itemType.equals("FREE_LINE_ABOARD")) {
            return BizLineStatusType.get(status).getText();
        } else if(itemType.equals("NORMAL")) {
            return BizCommonItemStatusType.get(status).getText();
        } else if(itemType.equals("SPOTS")) {
            return BizJingQuStatusType.get(status).getText();
        } else if(itemType.equals("HOTEL")||itemType.equals("HOTEL_OFFLINE")) {
            return BizHotelStatusType.get(status).getText();
        }
        return "";
    }

    private static String getResourceNameStr(TcMainOrder tcMainOrder, String itemType) {

       if(itemType.equals("SPOTS")) {
            return tcMainOrder.getScenicTitle();
       } else if(itemType.equals("HOTEL")||itemType.equals("HOTEL_OFFLINE")) {
            return tcMainOrder.getHotelTitle();
       }
       return "";
    }


    public static List<BizOrderExportDomain> exportOrderList(List<MainOrder> mainOrders,List<OrderInfoTO> orders) {
        List<BizOrderExportDomain> bizOrderExportDomains = new ArrayList<>();
        for (MainOrder mainOrder : mainOrders) {
            bizOrderExportDomains.addAll(exportOrder(mainOrder, orders));
        }
        return bizOrderExportDomains;
    }
}
