package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.MerchantItemCategoryDO;

public interface MerchantItemCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MerchantItemCategoryDO record);

    int insertSelective(MerchantItemCategoryDO record);

    MerchantItemCategoryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MerchantItemCategoryDO record);

    int updateByPrimaryKey(MerchantItemCategoryDO record);
}