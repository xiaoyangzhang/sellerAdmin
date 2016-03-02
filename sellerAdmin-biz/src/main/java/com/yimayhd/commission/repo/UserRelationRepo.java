package com.yimayhd.commission.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.marketing.client.model.query.UserRelationQuery;
import com.yimayhd.marketing.client.model.result.SpmResult;
import com.yimayhd.marketing.client.model.result.userrelation.UserInfoResult;
import com.yimayhd.marketing.client.service.UserRelationService;

public class UserRelationRepo {

	private static final Logger logger = LoggerFactory.getLogger(UserRelationRepo.class);
	
	@Autowired
	private UserRelationService userRelationService;
	
	public SpmResult<List<UserInfoResult>> getUserInfoList(UserRelationQuery query){
		
		logger.info("UserRelationRepo.getUserInfoList begin,param:" + JSON.toJSONString(query));
		SpmResult<List<UserInfoResult>> baseResult = null;
		try{
			baseResult = userRelationService.queryUserInfo(query);
		}catch(Exception e){
			logger.error("UserRelationRepo.getUserInfoList exceptions occur,param:{},ex:e",
					JSON.toJSONString(query), e);
		}
				
		return baseResult;
	}
}
