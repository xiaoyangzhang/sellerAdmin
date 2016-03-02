package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.base.BaseMapper;
import com.yimayhd.sellerAdmin.model.HaMenuDO;

import java.util.List;

/**
 * 菜单表
 * @author czf
 */
public interface HaMenuMapper extends BaseMapper<HaMenuDO>{

    List<HaMenuDO> getMenuListByUserId(long id)throws Exception;
    List<HaMenuDO> getUrlListByUserId(long id)throws Exception;
    List<HaMenuDO> getMenuList() throws Exception;
}
