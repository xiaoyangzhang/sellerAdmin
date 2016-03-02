package com.yimayhd.gf.repo;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoAddDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.errorcode.ComCenterReturnCodes;
import com.yimayhd.commentcenter.client.query.TagPageQuery;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;

public class GFTagRepo {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GFTagRepo.class);
	
	@Autowired
	private ComCenterService comCenterService;

	public BasePageResult<ComTagDO> getPageTag(TagInfoDTO tagInfoDTO ){
		BasePageResult<ComTagDO> basePageResult =  new BasePageResult<ComTagDO>();
		try {
			if(tagInfoDTO==null){
				basePageResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return basePageResult;
			}
			basePageResult = comCenterService.selectTagInfoPage(tagInfoDTO);
			if(basePageResult.isSuccess()){
				return basePageResult;
			}
		} catch (Exception e) {
			LOGGER.error("getPageTag:tagInfoDTO={}",JSON.toJSONString(tagInfoDTO),e);
			basePageResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return basePageResult;
		}
		return basePageResult;
		
	}

	public BaseResult<ComTagDO> addComTagInfo(TagInfoAddDTO tagInfoAddDTO) {
		BaseResult<ComTagDO> baseResult = new BaseResult<ComTagDO>();
		try {
			if(tagInfoAddDTO==null){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			baseResult = comCenterService.addComTagInfo(tagInfoAddDTO);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("addComTagInfo:tagInfoAddDTO={}",JSON.toJSONString(tagInfoAddDTO),e);
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
			baseResult = comCenterService.selectByPrimaryKey(id);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("selectTagInfoById:id={}",JSON.toJSONString(id),e);
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
			baseResult = comCenterService.updateComTagInfo(tagInfoAddDTO);
			if(baseResult.isSuccess()){
				return baseResult;
			}
		} catch (Exception e) {
			LOGGER.error("updateComTagInfo:tagInfoAddDTO={}",JSON.toJSONString(tagInfoAddDTO),e);
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

	public BaseResult<Boolean> batchEnableStatus(ArrayList<Long> themeIdList) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		try {
			if(CollectionUtils.isEmpty(themeIdList)){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			
			TagPageQuery pageQuery =new TagPageQuery();
			pageQuery.setList(themeIdList);
			pageQuery.setOutType(TagType.GFPRODUCTTAG.getType());
			pageQuery.setState(BaseStatus.AVAILABLE.getType());
			BaseResult<ComTagDO> baseResult2 = comCenterService.updateTagInfoStateByIdList(pageQuery);
			if(!baseResult2.isSuccess()){
				LOGGER.error("batchEnableStatus return value is null !returnValue :"+ JSON.toJSONString(themeIdList));
				baseResult.setErrorCode(ComCenterReturnCodes.WRITE_DB_FAILED);
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}
	
	public BaseResult<Boolean> batchDisableStatus(ArrayList<Long> themeIdList) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		try {
			if(CollectionUtils.isEmpty(themeIdList)){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			
			TagPageQuery pageQuery =new TagPageQuery();
			pageQuery.setList(themeIdList);
			pageQuery.setOutType(TagType.GFPRODUCTTAG.getType());
			pageQuery.setState(BaseStatus.DELETED.getType());
			BaseResult<ComTagDO> baseResult2 = comCenterService.updateTagInfoStateByIdList(pageQuery);
			if(!baseResult2.isSuccess()){
				LOGGER.error("batchDisableStatus return value is null !returnValue :"+ JSON.toJSONString(themeIdList));
				baseResult.setErrorCode(ComCenterReturnCodes.WRITE_DB_FAILED);
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

	public BaseResult<Boolean> enableScenicItem(long id) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		try {
			if(id<=0){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			ArrayList<Long> themeIdList = new ArrayList<Long>();
			themeIdList.add(id);
			TagPageQuery pageQuery =new TagPageQuery();
			pageQuery.setList(themeIdList);
			pageQuery.setOutType(TagType.GFPRODUCTTAG.getType());
			pageQuery.setState(BaseStatus.AVAILABLE.getType());
			BaseResult<ComTagDO> baseResult2 = comCenterService.updateTagInfoStateByIdList(pageQuery);
			if(!baseResult2.isSuccess()){
				LOGGER.error("enableScenicItem return value is null !returnValue :"+ JSON.toJSONString(themeIdList));
				baseResult.setErrorCode(ComCenterReturnCodes.WRITE_DB_FAILED);
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

	public BaseResult<Boolean> disableScenicItem(Long id) {
		BaseResult<Boolean> baseResult = new BaseResult<Boolean>();
		try {
			if(id<=0){
				baseResult.setErrorCode(ComCenterReturnCodes.C_CONTENT_CAN_NOT_BE_NULL);
				return baseResult;
			}
			ArrayList<Long> themeIdList = new ArrayList<Long>();
			themeIdList.add(id);
			TagPageQuery pageQuery =new TagPageQuery();
			pageQuery.setList(themeIdList);
			pageQuery.setOutType(TagType.GFPRODUCTTAG.getType());
			pageQuery.setState(BaseStatus.DELETED.getType());
			BaseResult<ComTagDO> baseResult2 = comCenterService.updateTagInfoStateByIdList(pageQuery);
			if(!baseResult2.isSuccess()){
				LOGGER.error("disableScenicItem return value is null !returnValue :"+ JSON.toJSONString(themeIdList));
				baseResult.setErrorCode(ComCenterReturnCodes.WRITE_DB_FAILED);
				return baseResult;
			}
		} catch (Exception e) {
			baseResult.setErrorCode(ComCenterReturnCodes.SYSTEM_EXCEPTION_CODE);
			return baseResult;
		}
		return baseResult;
	}

	
}
