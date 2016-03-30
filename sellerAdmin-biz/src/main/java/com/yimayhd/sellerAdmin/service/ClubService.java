package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ClubAdd;
import com.yimayhd.sellerAdmin.model.query.ClubInfo;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.snscenter.client.dto.ClubDOInfoDTO;

/**
 * Created by Administrator on 2015/11/2.
 */
public interface ClubService {
	/**
	 * 获取俱乐部列表(可带查询条件)
	 * 
	 * @return 俱乐部列表
	 */
	PageVO<ClubDO> pageQueryClub(ClubDOInfoDTO club) throws Exception;

	/**
	 * 获取俱乐部详情
	 * 
	 * @return 俱乐部详情
	 */
	ClubInfo getClubInfoDOById(long id) throws Exception;

	/**
	 * 添加修改俱乐部
	 * 
	 * @param club
	 * @return
	 * @throws Exception
	 */
	ClubAdd saveOrUpdate(ClubAdd clubAdd, List<Long> themeIds) throws Exception;


	/**
	 * @Title: batchUpOrDownStatus @Description:(批量上下架) @author create by
	 * yushengwei @ 2015年12月26日 下午4:01:58 @param @param ids @param @param
	 * status @param @return @return boolean 返回类型 @throws
	 */
	boolean batchUpOrDownStatus(List<Long> ids, int status) throws Exception;

}
