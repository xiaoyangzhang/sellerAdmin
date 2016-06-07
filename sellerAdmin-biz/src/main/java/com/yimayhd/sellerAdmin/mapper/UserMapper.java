package com.yimayhd.sellerAdmin.mapper;

import com.yimayhd.sellerAdmin.base.BaseMapper;
import com.yimayhd.sellerAdmin.model.User;

public interface UserMapper extends BaseMapper<User> {
    User login(User user)throws Exception;
    long getCountByTel(String tel)throws Exception;
    User getUserDetail(String id)throws Exception;
    void passwordModify(User user)throws Exception;
}