package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ClubAdd;
import com.yimayhd.sellerAdmin.model.query.ClubInfo;
import com.yimayhd.sellerAdmin.service.ClubService;
import com.yimayhd.sellerAdmin.service.UserRPCService;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.snscenter.client.domain.ClubInfoDO;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.snscenter.client.dto.ClubDOInfoDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.user.client.domain.UserDO;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ClubServiceImpl implements ClubService {

	private static final Logger log = LoggerFactory.getLogger(ClubServiceImpl.class);

	@Autowired
	SnsCenterService snsCenterService;

	@Autowired
	ComCenterService comCenterService;
	
	@Autowired
	UserRPCService userRPCService;

	@Override
	public PageVO<ClubDO> pageQueryClub(ClubDOInfoDTO query) throws Exception {
		RepoUtils.requestLog(log, "snsCenterService.getClubInfoListByQuery", query);
		BasePageResult<ClubDO> res = snsCenterService.getClubInfoListByQuery(query);
		int totalCount = 0;
		List<ClubDO> itemList = new ArrayList<ClubDO>();
		if (null != res && res.isSuccess()) {
			totalCount = res.getTotalCount();
			if (CollectionUtils.isNotEmpty(res.getList())) {
				itemList.addAll(res.getList());
			}
			log.info("Request {} success", "snsCenterService.getClubInfoListByQuery");
		} else {
			log.error("Request {} error: query={} result:", "snsCenterService.getClubInfoListByQuery",JSON.toJSONString(query),JSON.toJSONString(res));
			throw new BaseException("get page club list failure");
		}
		return new PageVO<ClubDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public ClubInfo getClubInfoDOById(long id) throws Exception {
		BaseResult<ClubInfoDO> res = snsCenterService.getClubInfoByClubId(id);
		if(null != res && res.isSuccess() && null != res.getValue() ){
			ClubInfo clubInfo = new ClubInfo();
			ClubInfoDO clubInfoDO =   res.getValue();
			clubInfo.setBackImg(clubInfoDO.getBackImg());
			clubInfo.setClubDes(clubInfoDO.getClubDes());
			clubInfo.setClubName(clubInfoDO.getClubName());
			clubInfo.setCreateId(clubInfoDO.getCreateId());
			clubInfo.setCreateTime(clubInfoDO.getCreateTime());
			clubInfo.setId(clubInfoDO.getId());
			clubInfo.setLogoUrl(clubInfoDO.getLogoUrl());
			clubInfo.setMemberCount(clubInfoDO.getMemberCount());
			clubInfo.setModifyTime(clubInfoDO.getModifyTime());
			clubInfo.setScore(clubInfoDO.getScore());
			clubInfo.setState(clubInfoDO.getState());
			clubInfo.setType(clubInfoDO.getType());
			//set创建人name
			UserDO ud = userRPCService.getUserById(clubInfo.getCreateId());
			if(null != ud ){
				clubInfo.setCreateUserName(ud.getName());
			}
			//set 关联的主题数据
			com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>>  resa = comCenterService.getTagInfoByOutIdAndType(clubInfo.getId(),TagType.CLUBTAG.name());
			if(null != resa && resa.isSuccess() && CollectionUtils.isNotEmpty(resa.getValue())){
				List<Long> listId = new ArrayList<Long>();
				List<ComTagDO> list = resa.getValue();
				for (ComTagDO comTagDO : list) {
					listId.add(comTagDO.getId());
				}
				clubInfo.setListThemeId(listId);
			}
			return clubInfo;
		}else{
			log.error("Request {} error: query={} result:", "snsCenterService.getClubInfoByClubId",id,JSON.toJSONString(res));
		}
		return null;
	}

	@Override
	public ClubAdd saveOrUpdate(ClubAdd clubAdd, List<Long> themeIds) throws Exception {
		if (null == clubAdd || CollectionUtils.isEmpty(themeIds)) {
			throw new Exception("add error,Parameters [clubAdd or themeIds ] cannot be empty");
		}
		BaseResult<ClubInfoDO> res = null;
		if(0 == clubAdd.getId()){
			 res = snsCenterService.addClubInfo(clubAdd);	
		}else{
			res = snsCenterService.editClubInfo(clubAdd);
		}
		
		if (null != res && res.isSuccess() && null != res.getValue()) {
			clubAdd.setId(res.getValue().getId());
			clubAdd.setCreateTime(res.getValue().getCreateTime());
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setList(themeIds);
			tagRelationInfoDTO.setOutId(res.getValue().getId());
			tagRelationInfoDTO.setTagType(TagType.CLUBTAG.getType());
			tagRelationInfoDTO.setOrderTime(res.getValue().getModifyTime());

			com.yimayhd.commentcenter.client.result.BaseResult<Boolean> resTag = comCenterService.addTagRelationInfo(tagRelationInfoDTO);
			if (null != resTag && resTag.isSuccess()) {
				return clubAdd;
			} else {
				log.error(" add,Club related theme failure,clubAdd=" + JSON.toJSONString(clubAdd)
						+ " ,tagRelationInfoDTO=" + JSON.toJSONString(tagRelationInfoDTO));
			}
		}else{
			log.error(" add,Club related theme failure,clubAdd=" + JSON.toJSONString(clubAdd)
					+ " ,snsCenterService.addClubInfo=" + JSON.toJSONString(res));
		}
		return null;
	}

	@Override
	public boolean batchUpOrDownStatus(List<Long> ids, int status) throws Exception {
		if (CollectionUtils.isEmpty(ids)) {
			throw new Exception("Parameters [ids] cannot be empty");
		}
		ClubDOInfoDTO clubDOInfoDTO = new ClubDOInfoDTO();
		clubDOInfoDTO.setIdList(ids);
		if (status == BaseStatus.AVAILABLE.getType()) {
			clubDOInfoDTO.setState(BaseStatus.DELETED.getType());
		} else {
			clubDOInfoDTO.setState(BaseStatus.AVAILABLE.getType());
		}
		BaseResult<Boolean> res = snsCenterService.updateClubStateByIdList(clubDOInfoDTO);
		if (null != res && res.isSuccess()) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		
		System.out.println(String.format("Request {} error: query={} result:", "snsCenterService.getClubInfoListByQuery","**********","$$$$$$$$$$"));
	}
}
