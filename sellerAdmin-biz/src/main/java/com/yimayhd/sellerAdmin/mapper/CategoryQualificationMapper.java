package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.CategoryQualificationDO;

public interface CategoryQualificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CategoryQualificationDO record);

    int insertSelective(CategoryQualificationDO record);

    CategoryQualificationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CategoryQualificationDO record);

    int updateByPrimaryKey(CategoryQualificationDO record);
}