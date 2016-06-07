package com.yimayhd.gf.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoAddDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.errorcode.ComCenterReturnCodes;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.gf.model.query.GFTagVoQuery;
import com.yimayhd.gf.repo.GFTagRepo;
import com.yimayhd.sellerAdmin.base.PageVO;

public class GFTagBiz {

	private static final Logger LOGGER = LoggerFactory.getLogger(GFTagBiz.class);
	@Autowired
	private GFTagRepo gfTagRepo;
	
	
	public PageVO<ComTagDO> getPageTag(GFTagVoQuery gfTagVoQuery) throws Exception {
		int totalCount = 0;
		List<ComTagDO> list = new ArrayList<ComTagDO>();
		if(null != gfTagVoQuery && null != TagType.getByType(gfTagVoQuery.getType()) ){
			TagInfoDTO tagInfoDTO = new TagInfoDTO();
			tagInfoDTO.setName(gfTagVoQuery.getName());
			tagInfoDTO.setState(gfTagVoQuery.getStatus());
			tagInfoDTO.setPageNo(gfTagVoQuery.getPageNo());
			tagInfoDTO.setPageSize(gfTagVoQuery.getPageSize());
			tagInfoDTO.setOutType(gfTagVoQuery.getType());
			BasePageResult<ComTagDO> basePageResult = gfTagRepo.getPageTag(tagInfoDTO);
			if(null != basePageResult && basePageResult.isSuccess() && CollectionUtils.isNotEmpty(basePageResult.getList())){//res.getValue()
				totalCount=basePageResult.getTotalCount();
				list = basePageResult.getList();
			}
		}else{
			LOGGER.error("Request {} error: query={}", "comCenterService.selectTagListByTagType",JSON.toJSONString(gfTagVoQuery));
		}
		return  new PageVO<ComTagDO>(gfTagVoQuery.getPageNo(), gfTagVoQuery.getPageSize(), totalCount, list);
	}


	public BaseResult<ComTagDO> addComTagInfo(TagInfoAddDTO tagInfoAddDTO) {
		BaseResult<ComTagDO> baseResult = new BaseResult<ComTagDO>();
		try {
			if(tagInfoAddDTO==null){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			baseResult = gfTagRepo.addComTagInfo(tagInfoAddDTO);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}


	public BaseResult<ComTagDO> selectTagInfoById(long id) {
		BaseResult<ComTagDO> baseResult = new BaseResult<ComTagDO>();
		try {
			if(id<=0){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			baseResult = gfTagRepo.selectTagInfoById(id);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}


	public BaseResult<ComTagDO> updateComTagInfo(TagInfoAddDTO tagInfoAddDTO) {
		BaseResult<ComTagDO> baseResult = new BaseResult<ComTagDO>();
		try {
			if(tagInfoAddDTO==null){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			baseResult = gfTagRepo.updateComTagInfo(tagInfoAddDTO);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}


	public BaseResult<Boolean> batchEnableStatus(ArrayList<Long> themeIdList) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		if(themeIdList.size()<0||themeIdList==null){
			baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return baseResult;
		}
		baseResult = gfTagRepo.batchEnableStatus(themeIdList);
		if(baseResult.isSuccess()){
			return baseResult;
		}
		return baseResult;
	}


	public BaseResult<Boolean> batchDisableStatus(ArrayList<Long> themeIdList) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		if(themeIdList.size()<0||themeIdList==null){
			baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return baseResult;
		}
		baseResult = gfTagRepo.batchDisableStatus(themeIdList);
		if(baseResult.isSuccess()){
			return baseResult;
		}
		return baseResult;
	}


	public BaseResult<Boolean> enableScenicItem(long id) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		if(id<=0){
			baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return baseResult;
		}
		baseResult = gfTagRepo.enableScenicItem(id);
		if(baseResult.isSuccess()){
			return baseResult;
		}
		return baseResult;
	}


	public BaseResult<Boolean> disableScenicItem(Long id) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		if(id<=0){
			baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
			return baseResult;
		}
		baseResult = gfTagRepo.disableScenicItem(id);
		if(baseResult.isSuccess()){
			return baseResult;
		}
		return baseResult;
	}
}
