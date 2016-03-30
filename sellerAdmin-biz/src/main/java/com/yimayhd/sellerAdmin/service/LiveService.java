package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.SnsSubjectVO;
import com.yimayhd.sellerAdmin.model.SubjectInfoAddVO;
import com.yimayhd.sellerAdmin.model.query.LiveListQuery;

/**
 * Created by Administrator on 2015/11/16.
 */
public interface LiveService {
    /**
     * 获取直播列表(可带查询条件)
     * @param liveListQuery 查询条件
     * @return 直播列表
     */
    PageVO<SnsSubjectVO> getList(LiveListQuery liveListQuery)throws Exception;
    /**
     * 获取直播详情
     * @param id 直播ID
     * @return 直播详情
     */
    SnsSubjectVO getById(long id)throws Exception;

    /**
     * 新增直播
     * @param subjectInfoAddVO 直播内容
     * @return 直播对象
     * @throws Exception
     */
    SnsSubjectVO add(SubjectInfoAddVO subjectInfoAddVO)throws Exception;

    /**
     * 修改直播
     * @param subjectInfoAddVO 直播内容
     * @throws Exception
     */
    void modify(SubjectInfoAddVO subjectInfoAddVO)throws Exception;

    /**
     * 直播恢复
     * @param id 直播ID
     */
    void regain(long id)throws Exception;

    /**
     * 直播违规
     * @param id 直播ID
     */
    void violation(long id)throws Exception;

    /**
     * 直播违规（批量）
     * @param idList 直播idList
     */
    void batchViolation(List<Long> idList);

}
