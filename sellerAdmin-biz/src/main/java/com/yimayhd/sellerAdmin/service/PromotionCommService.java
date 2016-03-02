package com.yimayhd.sellerAdmin.service;

import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.query.ActPromotionPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ActActivityEditVO;

/**
 * Created by czf on 2016/1/19.
 */
public interface PromotionCommService {
    /**
     * 根据条件查询列表
     *
     * @param actPromotionPageQuery 查询条件
     * @throws Exception
     */
    PageVO<ActActivityDO> getList(ActPromotionPageQuery actPromotionPageQuery) throws Exception;

    /**
     * 修改优惠
     *
     * @param actActivityEditVO
     *            需要修改的字段和主键
     * @throws Exception
     */
    void modify(ActActivityEditVO actActivityEditVO) throws Exception;

    /**
     * 添加优惠
     *
     * @return 优惠
     * @param entity
     *            数据实体
     * @throws Exception
     */
    boolean add(ActActivityEditVO entity) throws Exception;

    /**
     * 根据主键获取优惠
     *
     * @param id
     *            主键long类型的
     * @throws Exception
     */
    ActActivityEditVO getById(long id) throws Exception;

    /**
     * 优惠上架
     * @param id 优惠ID
     */
    boolean publish(long id)throws Exception;

    /**
     * 优惠下架
     * @param id 优惠ID
     */
    boolean close(long id)throws Exception;

    boolean checkActivityName(String name, int type);
}
