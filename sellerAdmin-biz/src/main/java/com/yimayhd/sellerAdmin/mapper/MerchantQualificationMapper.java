package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.MerchantQualificationDO;

public interface MerchantQualificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantQualificationDO record);

    int insertSelective(MerchantQualificationDO record);

    MerchantQualificationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MerchantQualificationDO record);

    int updateByPrimaryKey(MerchantQualificationDO record);
}