package com.yimayhd.gf.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemSkuVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;

import java.util.List;

/**
 * Created by czf on 2015/11/24.
 */
public interface CommodityService {
    /**
     * 获取商品列表
     * @param commodityListQuery
     * @return
     * @throws Exception
     */
    PageVO<ItemVO> getList(CommodityListQuery commodityListQuery)throws Exception;

    /**
     * 根据id获取商品信息
     * @param id
     * @return
     * @throws Exception
     */
    ItemResultVO getCommodityById(long id)throws Exception;

    /**
     * 商品上架
     * @param sellerId 商家id
     * @param id 商品ID
     */
    void publish(long sellerId, long id)throws Exception;

    /**
     * 商品上架
     * @param sellerId 商家id
     * @param id 商品ID
     */
    void close(long sellerId, long id)throws Exception;

    /**
     *
     * @param sellerId
     * @param idList
     */
    void batchPublish(long sellerId, List<Long> idList);

    /**
     *
     * @param sellerId
     * @param idList
     */
    void batchClose(long sellerId, List<Long> idList);
    /**
     * 新增普通商品
     * @param itemVO 普通商品表单对象
     * @throws Exception
     */
    void addCommonItem(ItemVO itemVO)throws Exception;

    /**
     * 修改普通商品
     * @param itemVO 普通商品表单对象
     * @throws Exception
     */
    void modifyCommonItem(ItemVO itemVO)throws Exception;


    /**
     * 根据商品ID查询sku
     * @param itemId
     * @return
     * @throws Exception
     */
    List<ItemSkuVO> getSkuListByItemId(Long itemId)throws Exception;

}
