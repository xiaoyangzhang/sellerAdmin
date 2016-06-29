package com.yimayhd.sellerAdmin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.sellerAdmin.model.enums.PayStatus;
import com.yimayhd.sellerAdmin.util.NumUtil;
import com.yimayhd.sellerAdmin.util.PhoneUtil;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallInfo;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.SkuInfo;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.tradecenter.client.model.enums.TcPayChannel;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;

/**
 * Created by czf on 2015/12/8.
 */
public class BizOrderExportVO implements Serializable{
    private static final long serialVersionUID = -1178403102272771239L;
    private long bizOrderId;
    private int bizType;
    private int subBizType;
    private int domain;
    private int isMain;
    private int isDetail;
    private long payOrderId;
    private long logisticsOrderId;
    private long itemId;
    private long itemSkuId;
    private String itemTitle;
    private String itemSubTitle;
    private String snapPath;
    private int status;
    private int payStatus;
    private int logisticsStatus;
    private int rateStatus;
    private long buyAmount;
    private long itemPrice;
    private long itemCredit;
    private long itemGold;
    private long sellerId;
    private long buyerId;
    private String buyerNick;
    private String sellerNick;
    private Date endTime;
    private Date payTime;
    private long parentId;
    private String itemPic;
    private Date gmtModified;
    private Date gmtCreated;
    private Map<String, String> feature;
    private long featureV;
    private String outId;
    private int outBizType;
    private long actualTotalFee;
    private long categoryId;
    private Date gmtUsefulDate;
    private String itemFeature;
    private int showFlag;
    private SkuInfo skuInfo;
    private List<BizOrderDO> detailOrderList;
    private int refundStatus;
    private JSONObject extFeatureMap;
    private long extFeatureV;
    private long version;
    private long usePoint;//使用积分
    private long givePoint;//赠送积分
    private int payChannel;//
    private String payChannelName;//支付方式名称
    private String dt;//部门
    private String jn;//工号
    private String dc;//终端
    private String pn;//电话
    private long stt;//世界戳
    private String number;//单号
    private Date sttDate;//世界戳

    private double actualTotalFeeY;//交易金额元
    private String payStatusName;//支付方式名称

    public static BizOrderExportVO getBizOrderExportVO(BizOrderDO bizOrderDO) throws Exception{
        BizOrderExportVO bizOrderExportVO = new BizOrderExportVO();
        BeanUtils.copyProperties(bizOrderDO, bizOrderExportVO);
        IMallInfo iMallInfo = BizOrderUtil.getIMallInfo(bizOrderDO);
        if(null != iMallInfo) {
            BeanUtils.copyProperties(iMallInfo, bizOrderExportVO);
            //电话去+86
            bizOrderExportVO.setPn(PhoneUtil.mask(PhoneUtil.phoneFormat(bizOrderExportVO.getPn())));
            bizOrderExportVO.setSttDate(new Date(bizOrderExportVO.getStt()));
        }
        bizOrderExportVO.setUsePoint(BizOrderUtil.getUsePointNum(bizOrderDO));
        bizOrderExportVO.setGivePoint(BizOrderUtil.getLong(bizOrderDO, BizOrderFeatureKey.GIVE_POINT));
        bizOrderExportVO.payChannel = BizOrderUtil.getInt(bizOrderDO, BizOrderFeatureKey.PAY_CHANNEL);
        TcPayChannel tcPayChannel = TcPayChannel.getPayChannel(bizOrderExportVO.getPayChannel());
        bizOrderExportVO.setPayChannelName(null == tcPayChannel ? "其他" : tcPayChannel.getDesc());

        PayStatus payStatus = PayStatus.getByStatus(bizOrderExportVO.getPayStatus());
        bizOrderExportVO.setPayStatusName(null == payStatus ? "其他" : payStatus.getDes());
        //分转元
        bizOrderExportVO.setActualTotalFeeY(NumUtil.moneyTransformDouble(bizOrderExportVO.getActualTotalFee()));
        return bizOrderExportVO;
    }

    public long getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(long bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public int getSubBizType() {
        return subBizType;
    }

    public void setSubBizType(int subBizType) {
        this.subBizType = subBizType;
    }

    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }

    public int getIsMain() {
        return isMain;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public int getIsDetail() {
        return isDetail;
    }

    public void setIsDetail(int isDetail) {
        this.isDetail = isDetail;
    }

    public long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(long payOrderId) {
        this.payOrderId = payOrderId;
    }

    public long getLogisticsOrderId() {
        return logisticsOrderId;
    }

    public void setLogisticsOrderId(long logisticsOrderId) {
        this.logisticsOrderId = logisticsOrderId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemSubTitle() {
        return itemSubTitle;
    }

    public void setItemSubTitle(String itemSubTitle) {
        this.itemSubTitle = itemSubTitle;
    }

    public String getSnapPath() {
        return snapPath;
    }

    public void setSnapPath(String snapPath) {
        this.snapPath = snapPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(int logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public int getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(int rateStatus) {
        this.rateStatus = rateStatus;
    }

    public long getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(long buyAmount) {
        this.buyAmount = buyAmount;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getItemCredit() {
        return itemCredit;
    }

    public void setItemCredit(long itemCredit) {
        this.itemCredit = itemCredit;
    }

    public long getItemGold() {
        return itemGold;
    }

    public void setItemGold(long itemGold) {
        this.itemGold = itemGold;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Map<String, String> getFeature() {
        return feature;
    }

    public void setFeature(Map<String, String> feature) {
        this.feature = feature;
    }

    public long getFeatureV() {
        return featureV;
    }

    public void setFeatureV(long featureV) {
        this.featureV = featureV;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public int getOutBizType() {
        return outBizType;
    }

    public void setOutBizType(int outBizType) {
        this.outBizType = outBizType;
    }

    public long getActualTotalFee() {
        return actualTotalFee;
    }

    public void setActualTotalFee(long actualTotalFee) {
        this.actualTotalFee = actualTotalFee;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getGmtUsefulDate() {
        return gmtUsefulDate;
    }

    public void setGmtUsefulDate(Date gmtUsefulDate) {
        this.gmtUsefulDate = gmtUsefulDate;
    }

    public String getItemFeature() {
        return itemFeature;
    }

    public void setItemFeature(String itemFeature) {
        this.itemFeature = itemFeature;
    }

    public int getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(int showFlag) {
        this.showFlag = showFlag;
    }

    public SkuInfo getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(SkuInfo skuInfo) {
        this.skuInfo = skuInfo;
    }

    public List<BizOrderDO> getDetailOrderList() {
        return detailOrderList;
    }

    public void setDetailOrderList(List<BizOrderDO> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public JSONObject getExtFeatureMap() {
        return extFeatureMap;
    }

    public void setExtFeatureMap(JSONObject extFeatureMap) {
        this.extFeatureMap = extFeatureMap;
    }

    public long getExtFeatureV() {
        return extFeatureV;
    }

    public void setExtFeatureV(long extFeatureV) {
        this.extFeatureV = extFeatureV;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(long usePoint) {
        this.usePoint = usePoint;
    }

    public long getGivePoint() {
        return givePoint;
    }

    public void setGivePoint(long givePoint) {
        this.givePoint = givePoint;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannelName() {
        return payChannelName;
    }

    public void setPayChannelName(String payChannelName) {
        this.payChannelName = payChannelName;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getJn() {
        return jn;
    }

    public void setJn(String jn) {
        this.jn = jn;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public long getStt() {
        return stt;
    }

    public void setStt(long stt) {
        this.stt = stt;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getSttDate() {
        return sttDate;
    }

    public void setSttDate(Date sttDate) {
        this.sttDate = sttDate;
    }

    public double getActualTotalFeeY() {
        return actualTotalFeeY;
    }

    public void setActualTotalFeeY(double actualTotalFeeY) {
        this.actualTotalFeeY = actualTotalFeeY;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }
}
