package com.yimayhd.sellerAdmin.service;


import java.util.List;

import com.yimayhd.membercenter.client.domain.HaMenuDO;

/**
 * Created by czf on 2016/3/3.
 */
public interface UserService {
    List<HaMenuDO> getMenuListByUserId(long id) throws Exception;

    List<HaMenuDO> getUrlListByUserId(long id) throws Exception;
}
