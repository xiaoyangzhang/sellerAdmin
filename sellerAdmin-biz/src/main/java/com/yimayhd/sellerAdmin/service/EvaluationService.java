package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ComCommentVO;
import com.yimayhd.sellerAdmin.model.query.EvaluationListQuery;

/**
 * Created by czf on 2015/12/31.
 */
public interface EvaluationService {


    /**
     * 查询评论列表
     * @param evaluationListQuery 查询条件
     * @return
     * @throws Exception
     */
    PageVO<ComCommentVO> getList(EvaluationListQuery evaluationListQuery)throws Exception;

    /**
     * 评论恢复
     * @param id 评论ID
     */
    void regain(long id)throws Exception;

    /**
     * 评论违规
     * @param id 评论ID
     */
    void violation(long id)throws Exception;

    /**
     * 评论违规（批量）
     * @param idList
     */
    void batchViolation(List<Long> idList);

}
