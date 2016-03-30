package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.CommentType;
import com.yimayhd.commentcenter.client.enums.SupportType;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.SnsSubjectVO;
import com.yimayhd.sellerAdmin.model.SubjectInfoAddVO;
import com.yimayhd.sellerAdmin.model.query.LiveListQuery;
import com.yimayhd.sellerAdmin.service.LiveService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.PhoneUtil;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.snscenter.client.dto.SubjectInfoAddDTO;
import com.yimayhd.snscenter.client.dto.SubjectInfoDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOQuery;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/2.
 */
public class LiveServiceImpl implements LiveService {

	private static final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);
	@Autowired
	private SnsCenterService snsCenterServiceRef;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private ComCenterService comCenterServiceRef;

	@Override
	public PageVO<SnsSubjectVO> getList(LiveListQuery liveListQuery) throws Exception {
		//返回结果
		PageVO<SnsSubjectVO> snsSubjectVOPageVO = new PageVO<SnsSubjectVO>(liveListQuery.getPageNo(),liveListQuery.getPageSize(),0);
		//查询条件对接
		SubjectInfoDTO subjectInfoDTO = new SubjectInfoDTO();
		subjectInfoDTO.setPageNo(liveListQuery.getPageNo());
		subjectInfoDTO.setPageSize(liveListQuery.getPageSize());
		//状态
		subjectInfoDTO.setStatus(liveListQuery.getLiveStatus());
		//内容
		if(StringUtils.isNotBlank(liveListQuery.getContent())){
			subjectInfoDTO.setTextContent(liveListQuery.getContent());
		}
		//开始结束时间
		if(StringUtils.isNotBlank(liveListQuery.getBeginDate())){
			subjectInfoDTO.setStartTime(DateUtil.formatMinTimeForDate(liveListQuery.getBeginDate()));
		}
		if(StringUtils.isNotBlank(liveListQuery.getEndDate())){
			subjectInfoDTO.setEndTime(DateUtil.formatMaxTimeForDate(liveListQuery.getEndDate()));
		}
		//用户id列表
		List<Long> userIdList = new ArrayList<Long>();
		//用户列表map
		Map<Long,UserDO> userDOMap = new HashMap<Long,UserDO>();
		//电话
		if(StringUtils.isNotBlank(liveListQuery.getTel())){
			// 查询用户
			BaseResult<UserDO> userResult =  userServiceRef.getUserDOByMobile(liveListQuery.getTel());
			if(null == userResult){
				log.error("LiveServiceImpl.getList-userService.getUserDOByMobile result is null and parame: " + liveListQuery.getTel());
				throw new BaseException("查询用户失败");
			} else if(!userResult.isSuccess()){
				log.error("LiveServiceImpl.getList-userService.getUserDOByMobile error:" + JSON.toJSONString(userResult) + "and parame: " + liveListQuery.getTel());
				throw new BaseException("查询用户失败," + userResult.getResultMsg());
			}
			if(userResult.getValue() != null && userResult.getValue().getId() != 0){
				userIdList.add(userResult.getValue().getId());
				//电话去+86并加密
				userResult.getValue().setMobileNo(PhoneUtil.mask(PhoneUtil.phoneFormat(userResult.getValue().getMobileNo())));
				userDOMap.put(userResult.getValue().getId(),userResult.getValue());
			}else{
				//没有查到用户，直接返回
				return snsSubjectVOPageVO;
			}

		}else if(StringUtils.isNotBlank(liveListQuery.getNickName())){
			// 查询用户
			UserDOQuery userDOQuery = new UserDOQuery();
			userDOQuery.setNickname(liveListQuery.getNickName());
			com.yimayhd.user.client.result.BasePageResult<UserDO> userListResult =  userServiceRef.findByConditionNoPage(userDOQuery);
			if(null == userListResult){
				log.error("LiveServiceImpl.getList-userService.findByConditionNoPage result is null and parame: " + JSON.toJSONString(userDOQuery));
				throw new BaseException("查询用户列表失败");
			} else if(!userListResult.isSuccess()){
				log.error("LiveServiceImpl.getList-userService.findByConditionNoPage error:" + JSON.toJSONString(userListResult) + "and parame: " + JSON.toJSONString(userDOQuery));
				throw new BaseException("查询用户列表失败," + userListResult.getResultMsg());
			}
			if(CollectionUtils.isNotEmpty(userListResult.getList())){
				for (UserDO userDO : userListResult.getList()){
					userIdList.add(userDO.getId());
					//电话去+86并加密
					userDO.setMobileNo(PhoneUtil.mask(PhoneUtil.phoneFormat(userDO.getMobileNo())));
					userDOMap.put(userDO.getId(),userDO);
				}
			}else{
				//没有查到用户，直接返回
				return snsSubjectVOPageVO;
			}
		}
		//昵称（用户id列表）
		subjectInfoDTO.setUserList(userIdList);

		BasePageResult<SnsSubjectDO> basePageResult = snsCenterServiceRef.getSubjectInfoPage(subjectInfoDTO);
		if(null == basePageResult){
			log.error("LiveServiceImpl.getList-snsCenterService.getSubjectInfoPage result is null and parame: " + JSON.toJSONString(subjectInfoDTO) + " and liveListQuery:" + JSON.toJSONString(liveListQuery));
			throw new BaseException("查询返回结果为空");
		} else if(!basePageResult.isSuccess()){
			log.error("LiveServiceImpl.getList-snsCenterService.getSubjectInfoPage error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(subjectInfoDTO) + " and liveListQuery:" + JSON.toJSONString(liveListQuery));
			throw new BaseException(basePageResult.getResultMsg());
		}
		if(CollectionUtils.isNotEmpty(basePageResult.getList())){
			List<SnsSubjectVO> snsSubjectVOList = new ArrayList<SnsSubjectVO>();
			List<Long> snsSubjectIdList = new ArrayList<Long>();
			for (SnsSubjectDO snsSubjectDO : basePageResult.getList()){
				snsSubjectIdList.add(snsSubjectDO.getId());
			}
			// 查询
			//查询条件中没有查用户的情况下，要重新查询用户信息
			if(userDOMap.size() == 0){
				List<Long> userIds = new ArrayList<Long>();
				for (SnsSubjectDO snsSubjectDO : basePageResult.getList()){
					userIds.add(snsSubjectDO.getUserId());
				}
				// 查询用户
				BaseResult<List<UserDO>> userListResult =  userServiceRef.getUserDOList(userIds);
				if(null == userListResult){
					log.error("LiveServiceImpl.getList-userService.findByConditionNoPage result is null and parame: " + JSON.toJSONString(userIds));
					throw new BaseException("查询用户列表失败");
				} else if(!userListResult.isSuccess()){
					log.error("LiveServiceImpl.getList-userService.findByConditionNoPage error:" + JSON.toJSONString(userListResult) + "and parame: " + JSON.toJSONString(userIds));
					throw new BaseException("查询用户列表失败," + userListResult.getResultMsg());
				}
				if(CollectionUtils.isNotEmpty(userListResult.getValue())){
					for (UserDO userDO : userListResult.getValue()){
						//电话去+86并加密
						userDO.setMobileNo(PhoneUtil.mask(PhoneUtil.phoneFormat(userDO.getMobileNo())));
						userDOMap.put(userDO.getId(), userDO);
					}
				}
			}
			//标签
			com.yimayhd.commentcenter.client.result.BaseResult<Map<Long,List<ComTagDO>>> tagInfoBaseResult= comCenterServiceRef.getTagInfoByOutIdsAndType(snsSubjectIdList, TagType.LIVESUPTAG.name());
			if(null == tagInfoBaseResult){
				log.error("LiveServiceImpl.getList-comCenterService.getTagInfoByOutIdsAndType result is null and parame1: " + JSON.toJSONString(snsSubjectIdList) + " and parame2:" + TagType.LIVESUPTAG.name());
				throw new BaseException("查询直播列表失败，查询标签结果为空");
			} else if(!tagInfoBaseResult.isSuccess()){
				log.error("LiveServiceImpl.getList-comCenterService.getTagInfoByOutIdsAndType error:" + JSON.toJSONString(tagInfoBaseResult) + "and parame1: " + JSON.toJSONString(snsSubjectIdList) + " and parame2:" + TagType.LIVESUPTAG.name());
				throw new BaseException("查询直播列表失败，查询标签错误," + tagInfoBaseResult.getResultMsg());
			}
			Map<Long,List<ComTagDO>> comTagMap = tagInfoBaseResult.getValue();
			//评论数
			com.yimayhd.commentcenter.client.result.BaseResult<Map<Long, Integer>> commentNumBaseResult = comCenterServiceRef.getCommentNumByIds(snsSubjectIdList, CommentType.LIVECOM.name());
			if(null == commentNumBaseResult){
				log.error("LiveServiceImpl.getList-comCenterService.getCommentNumByIds result is null and parame1: " + JSON.toJSONString(snsSubjectIdList) + " and parame2:" + TagType.LIVESUPTAG.name());
				throw new BaseException("查询直播列表失败，查询评论结果为空");
			} else if(!commentNumBaseResult.isSuccess()){
				log.error("LiveServiceImpl.getList-comCenterService.getCommentNumByIds error:" + JSON.toJSONString(commentNumBaseResult) + "and parame1: " + JSON.toJSONString(snsSubjectIdList) + " and parame2:" + TagType.LIVESUPTAG.name());
				throw new BaseException("查询直播列表失败，查询评论错误," + commentNumBaseResult.getResultMsg());
			}
			Map<Long, Integer> commentNumMap = commentNumBaseResult.getValue();
			//点赞数
			com.yimayhd.commentcenter.client.result.BaseResult<Map<Long, Integer>> supportNumBaseResult = comCenterServiceRef.getSupportNumByOutIds(snsSubjectIdList, SupportType.LIVESUP);
			if(null == supportNumBaseResult){
				log.error("LiveServiceImpl.getList-comCenterService.getCommentNumByIds result is null and parame1: " + JSON.toJSONString(snsSubjectIdList) + " and parame2:" + JSON.toJSONString(SupportType.LIVESUP));
				throw new BaseException("查询直播列表失败，查询评论结果为空");
			} else if(!supportNumBaseResult.isSuccess()){
				log.error("LiveServiceImpl.getList-comCenterService.getCommentNumByIds error:" + JSON.toJSONString(supportNumBaseResult) + "and parame1: " + JSON.toJSONString(snsSubjectIdList) + " and parame2:" + JSON.toJSONString(SupportType.LIVESUP));
				throw new BaseException("查询直播列表失败，查询评论错误," + supportNumBaseResult.getResultMsg());
			}
			Map<Long, Integer> supportNumMap = supportNumBaseResult.getValue();
			for (SnsSubjectDO snsSubjectDO : basePageResult.getList()){
				//转换类型
				SnsSubjectVO snsSubjectVO = SnsSubjectVO.getSnsSubjectVO(snsSubjectDO);
				//设置user
				snsSubjectVO.setUserDO(userDOMap.get(snsSubjectVO.getUserId()));
				//标签
				if(CollectionUtils.isNotEmpty(comTagMap.get(snsSubjectVO.getId()))) {
					snsSubjectVO.setTag(comTagMap.get(snsSubjectVO.getId()).get(0));
				}
				//设置评论数
				snsSubjectVO.setCommentNum(commentNumMap.get(snsSubjectVO.getId()));
				//设置点赞数
				snsSubjectVO.setSupportNum(supportNumMap.get(snsSubjectVO.getId()));
				snsSubjectVOList.add(snsSubjectVO);
			}
			snsSubjectVOPageVO = new PageVO<SnsSubjectVO>(liveListQuery.getPageNo(),liveListQuery.getPageSize(),basePageResult.getTotalCount(),snsSubjectVOList);
		}
		return snsSubjectVOPageVO;
	}

	@Override
	public SnsSubjectVO getById(long id) throws Exception {
		SubjectInfoDTO subjectInfoDTO = new SubjectInfoDTO();
		subjectInfoDTO.setId(id);
		com.yimayhd.snscenter.client.result.BaseResult<SnsSubjectDO> snsSubjectDOBaseResult= snsCenterServiceRef.getSubjectInfo(subjectInfoDTO);
		if(null == snsSubjectDOBaseResult){
			log.error("LiveServiceImpl.getById-snsCenterService.getSubjectInfo result is null and parame: " + JSON.toJSONString(subjectInfoDTO) + " and id:" + id);
			throw new BaseException("查询返回结果为空");
		} else if(!snsSubjectDOBaseResult.isSuccess()){
			log.error("LiveServiceImpl.getById-snsCenterService.getSubjectInfo error:" + JSON.toJSONString(snsSubjectDOBaseResult) + "and parame: " + JSON.toJSONString(subjectInfoDTO) + " and id:" + id);
			throw new BaseException(snsSubjectDOBaseResult.getResultMsg());
		}

		//查询标签
		com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> baseResult = comCenterServiceRef.getTagInfoByOutIdAndType(id, TagType.LIVESUPTAG.name());
		if(null == baseResult){
			log.error("LiveServiceImpl.getById-snsCenterService.getTagInfoByOutIdAndType result is null and parame1: " + id + " and parame2:" + TagType.LIVESUPTAG.name());
			throw new BaseException("查询直播失败，查询标签结果为空");
		} else if(!baseResult.isSuccess()){
			log.error("LiveServiceImpl.getById-snsCenterService.getTagInfoByOutIdAndType error:" + JSON.toJSONString(baseResult) + "and parame1: " + id + " and parame2:" + TagType.LIVESUPTAG.name());
			throw new BaseException("查询直播失败，查询标签错误," + baseResult.getResultMsg());
		}
		SnsSubjectVO snsSubjectVO = SnsSubjectVO.getSnsSubjectVO(snsSubjectDOBaseResult.getValue());
		//标签为单选
		if(CollectionUtils.isNotEmpty(baseResult.getValue())){
			snsSubjectVO.setTagId(baseResult.getValue().get(0).getId());
		}
		//查询用户
		try {
			UserDO userDO = userServiceRef.getUserDOById(snsSubjectVO.getUserId());
			if(userDO == null){
				log.error("LiveServiceImpl.getById-userService.getUserDOById return result is null and parame:" + snsSubjectVO.getUserId());
				//throw new BaseException("未查到用户");
			}
			snsSubjectVO.setUserDO(userDO);
		}catch (Exception e){
			log.error("LiveServiceImpl.getById-userService.getUserDOById error:" + JSON.toJSONString(e) +  "and parame:" + snsSubjectVO.getUserId());
			//throw new BaseException("未查到用户");
		}
		return snsSubjectVO;
	}

	@Override
	public SnsSubjectVO add(SubjectInfoAddVO subjectInfoAddVO) throws Exception {

		SubjectInfoAddDTO subjectInfoAddDTO = SubjectInfoAddVO.getSubjectInfoAddDTO(subjectInfoAddVO);
		com.yimayhd.snscenter.client.result.BaseResult<SnsSubjectDO> baseResult = snsCenterServiceRef.addSubjectInfo(subjectInfoAddDTO);
		if(null == baseResult){
			log.error("LiveServiceImpl.add-snsCenterService.addSubjectInfo result is null and parame: " + JSON.toJSONString(subjectInfoAddDTO));
			throw new BaseException("新增直播失败，返回结果为空");
		} else if(!baseResult.isSuccess()){
			log.error("LiveServiceImpl.add-snsCenterService.addSubjectInfo error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(subjectInfoAddDTO));
			throw new BaseException("新增直播失败，新增错误," + baseResult.getResultMsg());
		}
		//保存标签(标签不必填)
		if(0 != subjectInfoAddVO.getTagId()) {
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setOutId(baseResult.getValue().getId());
			tagRelationInfoDTO.setTagType(TagType.LIVESUPTAG.getType());
			List<Long> tagIdList = new ArrayList<Long>();
			tagIdList.add(subjectInfoAddDTO.getTagId());
			tagRelationInfoDTO.setList(tagIdList);
			tagRelationInfoDTO.setOrderTime(subjectInfoAddDTO.getGmtCreated());
			com.yimayhd.commentcenter.client.result.BaseResult<Boolean> addTagBaseResult = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
			if (null == addTagBaseResult) {
				log.error("LiveServiceImpl.add-comCenterService.addTagRelationInfo result is null and parame: " + JSON.toJSONString(tagRelationInfoDTO) + "and subjectInfoAddVO" + JSON.toJSONString(subjectInfoAddVO));
				throw new BaseException("新增直播成功，新增直播标签失败，返回结果为空");
			} else if (!addTagBaseResult.isSuccess()) {
				log.error("LiveServiceImpl.add-comCenterService.addTagRelationInfo error:" + JSON.toJSONString(addTagBaseResult) + "and parame: " + JSON.toJSONString(tagRelationInfoDTO) + "and subjectInfoAddVO" + JSON.toJSONString(subjectInfoAddVO));
				throw new BaseException("新增直播失败，新增直播标签失败，" + addTagBaseResult.getResultMsg());
			}
		}

		return null;
	}

	@Override
	public void modify(SubjectInfoAddVO subjectInfoAddVO) throws Exception {

		//不用查询DB
		//修改
		SubjectInfoAddDTO subjectInfoAddDTO = SubjectInfoAddVO.getSubjectInfoAddDTO(subjectInfoAddVO);
		com.yimayhd.snscenter.client.result.BaseResult<SnsSubjectDO> baseResult = snsCenterServiceRef.updateSubjectInfo(subjectInfoAddDTO);
		if(null == baseResult){
			log.error("LiveServiceImpl.add-snsCenterService.addSubjectInfo result is null and parame: " + JSON.toJSONString(subjectInfoAddDTO));
			throw new BaseException("修改直播失败，返回结果为空");
		} else if(!baseResult.isSuccess()){
			log.error("LiveServiceImpl.add-snsCenterService.addSubjectInfo error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(subjectInfoAddDTO));
			throw new BaseException("修改直播失败，修改错误," + baseResult.getResultMsg());
		}
		//修改标签(修改时不能删除标签)
		if(0 != subjectInfoAddVO.getTagId()) {
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setOutId(subjectInfoAddVO.getId());
			tagRelationInfoDTO.setTagType(TagType.LIVESUPTAG.getType());
			List<Long> tagIdList = new ArrayList<Long>();
			tagIdList.add(subjectInfoAddDTO.getTagId());
			tagRelationInfoDTO.setList(tagIdList);
			tagRelationInfoDTO.setOrderTime(subjectInfoAddDTO.getGmtCreated());
			com.yimayhd.commentcenter.client.result.BaseResult<Boolean> addTagBaseResult = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
			if (null == addTagBaseResult) {
				log.error("LiveServiceImpl.add-comCenterService.addTagRelationInfo result is null and parame: " + JSON.toJSONString(tagRelationInfoDTO) + "and subjectInfoAddVO" + JSON.toJSONString(subjectInfoAddVO));
				throw new BaseException("新增直播成功，新增直播标签失败，返回结果为空");
			} else if (!addTagBaseResult.isSuccess()) {
				log.error("LiveServiceImpl.add-comCenterService.addTagRelationInfo error:" + JSON.toJSONString(addTagBaseResult) + "and parame: " + JSON.toJSONString(tagRelationInfoDTO) + "and subjectInfoAddVO" + JSON.toJSONString(subjectInfoAddVO));
				throw new BaseException("新增直播失败，新增直播标签失败，" + addTagBaseResult.getResultMsg());
			}
		}
	}

	@Override
	public void regain(long id) throws Exception {
		SubjectInfoDTO subjectInfoDTO = new SubjectInfoDTO();
		List<Long> subjectList = new ArrayList<Long>();
		subjectList.add(id);
		subjectInfoDTO.setSubjectList(subjectList);
		//subjectInfoDTO.setId(id);
		subjectInfoDTO.setStatus(BaseStatus.AVAILABLE.getType());
		com.yimayhd.snscenter.client.result.BaseResult<Boolean> baseResult = snsCenterServiceRef.updateSubjectStatus(subjectInfoDTO);
		if(null == baseResult){
			log.error("LiveServiceImpl.regain-snsCenterService.updateSubjectStatus result is null and parame: " + JSON.toJSONString(subjectInfoDTO) + " and id:" + id);
			throw new BaseException("查询返回结果为空");
		} else if(!baseResult.isSuccess()){
			log.error("LiveServiceImpl.regain-snsCenterService.updateSubjectStatus error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(subjectInfoDTO) + " and id:" + id);
			throw new BaseException(baseResult.getResultMsg());
		}
	}

	@Override
	public void violation(long id) throws Exception {
		SubjectInfoDTO subjectInfoDTO = new SubjectInfoDTO();
		List<Long> subjectList = new ArrayList<Long>();
		subjectList.add(id);
		subjectInfoDTO.setSubjectList(subjectList);
		//subjectInfoDTO.setId(id);
		subjectInfoDTO.setStatus(BaseStatus.DELETED.getType());
		com.yimayhd.snscenter.client.result.BaseResult<Boolean> baseResult = snsCenterServiceRef.updateSubjectStatus(subjectInfoDTO);
		if(null == baseResult){
			log.error("LiveServiceImpl.regain-snsCenterService.updateSubjectStatus result is null and parame: " + JSON.toJSONString(subjectInfoDTO) + " and id:" + id);
			throw new BaseException("查询返回结果为空");
		} else if(!baseResult.isSuccess()){
			log.error("LiveServiceImpl.regain-snsCenterService.updateSubjectStatus error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(subjectInfoDTO) + " and id:" + id);
			throw new BaseException(baseResult.getResultMsg());
		}
	}

	@Override
	public void batchViolation(List<Long> idList) {
		SubjectInfoDTO subjectInfoDTO = new SubjectInfoDTO();
		subjectInfoDTO.setSubjectList(idList);
		subjectInfoDTO.setStatus(BaseStatus.DELETED.getType());
		com.yimayhd.snscenter.client.result.BaseResult<Boolean> baseResult = snsCenterServiceRef.updateSubjectStatus(subjectInfoDTO);
		if(null == baseResult){
			log.error("LiveServiceImpl.regain-snsCenterService.updateSubjectStatus result is null and parame: " + JSON.toJSONString(subjectInfoDTO) + " and idList:" + JSON.toJSONString(idList));
			throw new BaseException("查询返回结果为空");
		} else if(!baseResult.isSuccess()){
			log.error("LiveServiceImpl.regain-snsCenterService.updateSubjectStatus error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(subjectInfoDTO) + " and idList:" + JSON.toJSONString(idList));
			throw new BaseException(baseResult.getResultMsg());
		}
	}
}
