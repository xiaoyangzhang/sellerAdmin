package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.ScopeItemCategoryDO;

public interface ScopeItemCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ScopeItemCategoryDO record);

    int insertSelective(ScopeItemCategoryDO record);

    ScopeItemCategoryDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScopeItemCategoryDO record);

    int updateByPrimaryKey(ScopeItemCategoryDO record);
}