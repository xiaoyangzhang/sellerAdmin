package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.membercenter.enums.ContractType;
import com.yimayhd.membercenter.enums.MerchantType;

/**
 * Created by liuxiaopeng on 16/11/3.
 */
public enum  MerchantParentCategoryMappingEnum {

    JINGQU(MerchantType.SCENIC.getType(), ContractType.JINGQU),
    JIUDIAN(MerchantType.HOTEL.getType(), ContractType.JIUDIAN),
    TONGCHENG(MerchantType.CITY_COR.getType(), ContractType.JINGQU),
    TOUR_COR(MerchantType.TOUR_COR.getType(), ContractType.LVXING),
    TRAVEL_AGENCY(MerchantType.TRAVEL_AGENCY.getType(), ContractType.LVXING),
    TRAVLE_SERVICE(MerchantType.TRAVLE_SERVICE.getType(), ContractType.LVXING),
    DAREN(MerchantType.TALENT.getType(), ContractType.DAREN);

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
