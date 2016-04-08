package com.yimayhd.sellerAdmin.model;

import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.service.ScenicService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by weiwenliang on 2016/04/06.
 */
public class CityActivityItemVO{
    private ItemVO itemVO;
    private List<ItemSkuVO> itemSkuVOList;
    private CategoryVO categoryVO;
    private CityActivityVO cityActivityVO;

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

    public CityActivityVO getCityActivityVO() {
        return cityActivityVO;
    }

    public void setCityActivityVO(CityActivityVO cityActivityVO) {
        this.cityActivityVO = cityActivityVO;
    }
}
