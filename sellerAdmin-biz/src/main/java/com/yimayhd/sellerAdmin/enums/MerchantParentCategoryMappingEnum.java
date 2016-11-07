package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.membercenter.enums.ContractType;
import com.yimayhd.membercenter.enums.MerchantCategoryType;
import com.yimayhd.membercenter.enums.MerchantType;

/**
 * Created by liuxiaopeng on 16/11/3.
 */
public enum  MerchantParentCategoryMappingEnum {

    SCENIC_SPOT(MerchantCategoryType.SCENIC_SPOT.getSubType(), ContractType.JINGQU),
    AMUSEMENT_PARK(MerchantCategoryType.AMUSEMENT_PARK.getSubType(), ContractType.JINGQU),
    TICKET_AGENT(MerchantCategoryType.TICKET_AGENT.getSubType(), ContractType.JINGQU),
    SCENIC_SPOT_GROUP(MerchantCategoryType.SCENIC_SPOT_GROUP.getSubType(), ContractType.JINGQU),
    TOURISM_PROMOTION(MerchantCategoryType.TOURISM_PROMOTION.getSubType(), ContractType.JINGQU),
    HOTEL_GROUP(MerchantCategoryType.HOTEL_GROUP.getSubType(), ContractType.JIUDIAN),
    MONOMER_HOTEL(MerchantCategoryType.MONOMER_HOTEL.getSubType(), ContractType.JIUDIAN),
    HOTEL_AGENT(MerchantCategoryType.HOTEL_AGENT.getSubType(), ContractType.JIUDIAN),
    COMPANY(MerchantCategoryType.COMPANY.getSubType(), ContractType.TONGCHENG),
    INDIVIDUAL_BUSINESS(MerchantCategoryType.INDIVIDUAL_BUSINESS.getSubType(), ContractType.TONGCHENG),
    PARTNERSHIP_BUSINESS(MerchantCategoryType.PARTNERSHIP_BUSINESS.getSubType(), ContractType.TONGCHENG),
    OTHER_ORG(MerchantCategoryType.OTHER_ORG.getSubType(), ContractType.TONGCHENG),
    HOME_HEAD_AGENCY(MerchantCategoryType.HOME_HEAD_AGENCY.getSubType(), ContractType.LVXING),
    HOME_BRANCH_AGENCY(MerchantCategoryType.HOME_BRANCH_AGENCY.getSubType(), ContractType.LVXING),
    BROAD_HEAD_AGENCY(MerchantCategoryType.BROAD_HEAD_AGENCY.getSubType(), ContractType.LVXING),
    TOUR_BUSINESS_SERVICE(MerchantCategoryType.TOUR_BUSINESS_SERVICE.getSubType(), ContractType.LVXING),
    BROAD_BRANCH_AGENCY(MerchantCategoryType.BROAD_BRANCH_AGENCY.getSubType(), ContractType.LVXING);

    private int category;

    private ContractType contractType;

    MerchantParentCategoryMappingEnum(int category, ContractType contractType) {
        this.category = category;
        this.contractType = contractType;
    }

    public int getCategory() {
        return category;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public static MerchantParentCategoryMappingEnum getByCategory(long value) {
        if (value == 0) {
            return null;
        }
        for (MerchantParentCategoryMappingEnum merchantParentCategoryMappingEnum : values()) {
            if (merchantParentCategoryMappingEnum.category == value) {
                return merchantParentCategoryMappingEnum;
            }
        }
        return null;
    }
}
