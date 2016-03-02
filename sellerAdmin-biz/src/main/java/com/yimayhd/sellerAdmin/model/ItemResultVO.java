package com.yimayhd.sellerAdmin.model;

import com.yimayhd.ic.client.model.result.item.ItemResult;

import java.util.List;

/**
 * Created by Administrator on 2015/12/19.
 */
public class ItemResultVO extends ItemResult {
    private ItemVO itemVO;
    private List<ItemSkuVO> itemSkuVOList;
    private CategoryVO categoryVO;

    public ItemVO getItemVO() {
        return itemVO;
    }

    public void setItemVO(ItemVO itemVO) {
        this.itemVO = itemVO;
    }

    public List<ItemSkuVO> getItemSkuVOList() {
        return itemSkuVOList;
    }

    public void setItemSkuVOList(List<ItemSkuVO> itemSkuVOList) {
        this.itemSkuVOList = itemSkuVOList;
    }

    public CategoryVO getCategoryVO() {
        return categoryVO;
    }

    public void setCategoryVO(CategoryVO categoryVO) {
        this.categoryVO = categoryVO;
    }
}
