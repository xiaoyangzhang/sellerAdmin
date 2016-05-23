package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.BusinessScopeDO;

public interface BusinessScopeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessScopeDO record);

    int insertSelective(BusinessScopeDO record);

    BusinessScopeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessScopeDO record);

    int updateByPrimaryKey(BusinessScopeDO record);
}