package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.model.QualificationDO;

public interface QualificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(QualificationDO record);

    int insertSelective(QualificationDO record);

    QualificationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QualificationDO record);

    int updateByPrimaryKey(QualificationDO record);
}