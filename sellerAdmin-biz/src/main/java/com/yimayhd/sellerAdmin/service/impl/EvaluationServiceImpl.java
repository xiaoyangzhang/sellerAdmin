package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComCommentDO;
import com.yimayhd.commentcenter.client.dto.CommentDTO;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ComCommentVO;
import com.yimayhd.sellerAdmin.model.query.EvaluationListQuery;
import com.yimayhd.sellerAdmin.service.EvaluationService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.PhoneUtil;
import com.yimayhd.ic.client.model.enums.BaseStatus;
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
 * Created by czf on 2015/12/31.
 */
public class EvaluationServiceImpl implements EvaluationService {

    private static final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    @Autowired
    private ComCenterService comCenterServiceRef;
    @Autowired
    private UserService userServiceRef;

    @Override
    public PageVO<ComCommentVO> getList(EvaluationListQuery evaluationListQuery) throws Exception {
        //返回结果
        PageVO<ComCommentVO> comCommentVOPageVO = new PageVO<ComCommentVO>(evaluationListQuery.getPageNo(),evaluationListQuery.getPageSize(),0);
        //查询条件对接
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPageSize(evaluationListQuery.getPageSize());
        commentDTO.setPageNo(evaluationListQuery.getPageNo());
        //开始结束时间
        if(StringUtils.isNotBlank(evaluationListQuery.getBeginDate())){
            commentDTO.setStartDate(DateUtil.formatMinTimeForDate(evaluationListQuery.getBeginDate()));
        }
        if(StringUtils.isNotBlank(evaluationListQuery.getEndDate())){
            commentDTO.setEndDate(DateUtil.formatMaxTimeForDate(evaluationListQuery.getEndDate()));
        }
        //用户id列表
        List<Long> userIdList = new ArrayList<Long>();
        //用户列表map
        Map<Long,UserDO> userDOMap = new HashMap<Long,UserDO>();
        //电话
        if(StringUtils.isNotBlank(evaluationListQuery.getTel())){
            // 查询用户
            BaseResult<UserDO> userResult =  userServiceRef.getUserDOByMobile(evaluationListQuery.getTel());
            if(null == userResult){
                log.error("EvaluationServiceImpl.getList-userService.getUserDOByMobile result is null and parame: " + evaluationListQuery.getTel());
                throw new BaseException("查询用户失败");
            } else if(!userResult.isSuccess()){
                log.error("EvaluationServiceImpl.getList-userService.getUserDOByMobile error:" + JSON.toJSONString(userResult) + "and parame: " + evaluationListQuery.getTel());
                throw new BaseException("查询用户失败," + userResult.getResultMsg());
            }
            if(userResult.getValue() != null && userResult.getValue().getId() != 0){
                userIdList.add(userResult.getValue().getId());
                //电话去+86并加密
                userResult.getValue().setMobileNo(PhoneUtil.mask(PhoneUtil.phoneFormat(userResult.getValue().getMobileNo())));
                userDOMap.put(userResult.getValue().getId(),userResult.getValue());
            }else{
                //没有查到用户，直接返回
                return comCommentVOPageVO;
            }

        }else if(StringUtils.isNotBlank(evaluationListQuery.getNickName())){
            // 查询用户
            UserDOQuery userDOQuery = new UserDOQuery();
            userDOQuery.setNickname(evaluationListQuery.getNickName());
            com.yimayhd.user.client.result.BasePageResult<UserDO> userListResult =  userServiceRef.findByConditionNoPage(userDOQuery);
            if(null == userListResult){
                log.error("EvaluationServiceImpl.getList-userService.findByConditionNoPage result is null and parame: " + JSON.toJSONString(userDOQuery));
                throw new BaseException("查询用户列表失败");
            } else if(!userListResult.isSuccess()){
                log.error("EvaluationServiceImpl.getList-userService.findByConditionNoPage error:" + JSON.toJSONString(userListResult) + "and parame: " + JSON.toJSONString(userDOQuery));
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
                return comCommentVOPageVO;
            }
        }
        //昵称（用户id列表）
        commentDTO.setOutIdList(userIdList);

        //状态
        commentDTO.setState(evaluationListQuery.getEvaluationStatus());
        BasePageResult<ComCommentDO> commentDOBasePageResult = comCenterServiceRef.getCommentInfoPage(commentDTO);
        if(null == commentDOBasePageResult){
            log.error("EvaluationServiceImpl.getList-comCenterService.getCommentInfoPage result is null and parame: " + JSON.toJSONString(commentDTO) + " and liveListQuery:" + JSON.toJSONString(evaluationListQuery));
            throw new BaseException("查询返回结果为空");
        } else if(!commentDOBasePageResult.isSuccess()){
            log.error("EvaluationServiceImpl.getList-comCenterService.getCommentInfoPage error:" + JSON.toJSONString(commentDOBasePageResult) + "and parame: " + JSON.toJSONString(commentDTO) + " and liveListQuery:" + JSON.toJSONString(evaluationListQuery));
            throw new BaseException(commentDOBasePageResult.getResultMsg());
        }
        if(CollectionUtils.isNotEmpty(commentDOBasePageResult.getList())){
            List<ComCommentVO> comCommentVOList = new ArrayList<ComCommentVO>();
            //查询条件中没有查用户的情况下，要重新查询用户信息
            if(userDOMap.size() == 0){
                List<Long> userIds = new ArrayList<Long>();
                for (ComCommentDO comCommentDO : commentDOBasePageResult.getList()){
                    userIds.add(comCommentDO.getUserId());
                }
                // 查询用户 TODO
                BaseResult<List<UserDO>> userListResult =  userServiceRef.getUserDOList(userIds);
                if(null == userListResult){
                    log.error("EvaluationServiceImpl.getList-userService.findByConditionNoPage result is null and parame: " + JSON.toJSONString(userIds));
                    throw new BaseException("查询用户列表失败");
                } else if(!userListResult.isSuccess()){
                    log.error("EvaluationServiceImpl.getList-userService.findByConditionNoPage error:" + JSON.toJSONString(userListResult) + "and parame: " + JSON.toJSONString(userIds));
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
            for (ComCommentDO comCommentDO : commentDOBasePageResult.getList()){
                //转换类型
                ComCommentVO comCommentVO = ComCommentVO.getComCommentVO(comCommentDO);
                //设置user
                comCommentVO.setUserDO(userDOMap.get(comCommentDO.getUserId()));
                comCommentVOList.add(comCommentVO);
            }
            comCommentVOPageVO = new PageVO<ComCommentVO>(evaluationListQuery.getPageNo(),evaluationListQuery.getPageSize(),commentDOBasePageResult.getTotalCount(),comCommentVOList);
        }
        return comCommentVOPageVO;
    }

    @Override
    public void regain(long id) throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        List<Long> idList = new ArrayList<Long>();
        idList.add(id);
        commentDTO.setIdList(idList);
        commentDTO.setState(BaseStatus.AVAILABLE.getType());
        com.yimayhd.commentcenter.client.result.BaseResult<Boolean> baseResult = comCenterServiceRef.updateCommentStateByIdList(commentDTO);
        if(null == baseResult){
            log.error("EvaluationServiceImpl.regain-comCenterService.updateSubjectStatus result is null and parame: " + JSON.toJSONString(commentDTO) + " and id:" + id);
            throw new BaseException("返回结果为空，恢复失败");
        } else if(!baseResult.isSuccess()){
            log.error("EvaluationServiceImpl.regain-comCenterService.updateSubjectStatus error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(commentDTO) + " and id:" + id);
            throw new BaseException(baseResult.getResultMsg() + ",返回结果为错误，恢复失败");
        }
    }

    @Override
    public void violation(long id) throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        List<Long> idList = new ArrayList<Long>();
        idList.add(id);
        commentDTO.setIdList(idList);
        commentDTO.setState(BaseStatus.DELETED.getType());
        com.yimayhd.commentcenter.client.result.BaseResult<Boolean> baseResult = comCenterServiceRef.updateCommentStateByIdList(commentDTO);
        if(null == baseResult){
            log.error("EvaluationServiceImpl.violation-comCenterService.updateSubjectStatus result is null and parame: " + JSON.toJSONString(commentDTO) + " and id:" + id);
            throw new BaseException("返回结果为空，违规失败");
        } else if(!baseResult.isSuccess()){
            log.error("EvaluationServiceImpl.violation-comCenterService.updateSubjectStatus error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(commentDTO) + " and id:" + id);
            throw new BaseException(baseResult.getResultMsg() + ",返回结果为错误，违规失败");
        }
    }

    @Override
    public void batchViolation(List<Long> idList) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setIdList(idList);
        commentDTO.setState(BaseStatus.DELETED.getType());
        com.yimayhd.commentcenter.client.result.BaseResult<Boolean> baseResult = comCenterServiceRef.updateCommentStateByIdList(commentDTO);
        if(null == baseResult){
            log.error("EvaluationServiceImpl.batchViolation-comCenterService.updateSubjectStatus result is null and parame: " + JSON.toJSONString(commentDTO) + " and idList:" + JSON.toJSONString(idList));
            throw new BaseException("返回结果为空，违规失败");
        } else if(!baseResult.isSuccess()){
            log.error("EvaluationServiceImpl.batchViolation-comCenterService.updateSubjectStatus error:" + JSON.toJSONString(baseResult) + "and parame: " + JSON.toJSONString(commentDTO) + " and idList:" + JSON.toJSONString(idList));
            throw new BaseException(baseResult.getResultMsg() + ",返回结果为错误，违规失败");
        }
    }
}
