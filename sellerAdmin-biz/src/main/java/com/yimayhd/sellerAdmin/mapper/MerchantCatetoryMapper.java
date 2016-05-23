package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.MerchantCatetoryDO;

public interface MerchantCatetoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantCatetoryDO record);

    int insertSelective(MerchantCatetoryDO record);

    MerchantCatetoryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MerchantCatetoryDO record);

    int updateByPrimaryKey(MerchantCatetoryDO record);
}