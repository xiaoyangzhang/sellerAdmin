package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.query.PromotionListQuery;
import com.yimayhd.sellerAdmin.model.PromotionVO;
import com.yimayhd.promotion.client.domain.PromotionDO;

/**
 * Created by czf on 2016/1/19.
 */
public interface PromotionShopService {
    /**
     * 根据条件查询列表
     *
     * @param promotionListQuery 查询条件
     * @throws Exception
     */
    PageVO<PromotionDO> getList(PromotionListQuery promotionListQuery) throws Exception;

    /**
     * 修改优惠
     *
     * @param entity
     *            需要修改的字段和主键
     * @throws Exception
     */
    void modify(PromotionVO entity) throws Exception;

    /**
     * 添加优惠
     *
     * @return 优惠
     * @param entity
     *            数据实体
     * @throws Exception
     */
    boolean add(PromotionVO entity) throws Exception;

    /**
     * 根据主键获取优惠
     *
     * @param id
     *            主键long类型的
     * @throws Exception
     */
    PromotionVO getById(long id) throws Exception;
}
