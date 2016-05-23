package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.MerchantCatetoryScopeDO;

public interface MerchantCatetoryScopeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantCatetoryScopeDO record);

    int insertSelective(MerchantCatetoryScopeDO record);

    MerchantCatetoryScopeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MerchantCatetoryScopeDO record);

    int updateByPrimaryKey(MerchantCatetoryScopeDO record);
}