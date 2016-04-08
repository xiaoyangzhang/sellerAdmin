package com.yimayhd.sellerAdmin.model;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.model.line.CityVO;
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
    private List<ComTagDO> themes;
    private CityVO dest;

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

    public List<ComTagDO> getThemes() {
        return themes;
    }

    public void setThemes(List<ComTagDO> themes) {
        this.themes = themes;
    }

    public CityVO getDest() {
        return dest;
    }

    public void setDest(CityVO dest) {
        this.dest = dest;
    }
}
