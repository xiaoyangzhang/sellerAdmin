package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.service.UserPermissionService;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by czf on 2016/3/3.
 */
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserPermissionService userPermissionServiceRef;
    @Override
    public List<HaMenuDO> getMenuListByUserId(long id) throws Exception {

        MemPageResult<HaMenuDO> haMenuDOMemPageResult = userPermissionServiceRef.getMenuListByUserId(id);
        if(haMenuDOMemPageResult == null){
            log.error("UserServiceImpl.getMenuListByUserId.userPermissionServiceRef.getMenuListByUserId return null ,param : {}",id);
            throw new NoticeException("返回结果错误");
        }else if(!haMenuDOMemPageResult.isSuccess()){
            log.error("UserServiceImpl.getMenuListByUserId.userPermissionServiceRef.getMenuListByUserId error ,the return is : {} , param : {}", JSON.toJSONString(haMenuDOMemPageResult), id);
            throw new NoticeException(haMenuDOMemPageResult.getErrorMsg());
        }
        return haMenuDOMemPageResult.getList();
    }
}
