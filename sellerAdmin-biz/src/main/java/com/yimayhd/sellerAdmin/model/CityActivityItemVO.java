package com.yimayhd.sellerAdmin.model;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;
import com.yimayhd.sellerAdmin.service.ScenicService;

import java.io.Serializable;
import java.util.List;

/**
 * Created by weiwenliang on 2016/04/06.
 */
public class CityActivityItemVO{
    private ItemVO itemVO;
    private CategoryVO categoryVO;
    private CityActivityVO cityActivityVO;
    private List<Long> themes;
    private CityVO dest;
    private NeedKnowVO needKnowVO;
    private PictureTextVO pictureTextVO;

    public ItemVO getItemVO() {
        return itemVO;
    }

    public void setItemVO(ItemVO itemVO) {
        this.itemVO = itemVO;
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

    public List<Long> getThemes() {
        return themes;
    }

    public void setThemes(List<Long> themes) {
        this.themes = themes;
    }

    public CityVO getDest() {
        return dest;
    }

    public void setDest(CityVO dest) {
        this.dest = dest;
    }

    public NeedKnowVO getNeedKnowVO() {
        return needKnowVO;
    }

    public void setNeedKnowVO(NeedKnowVO needKnowVO) {
        this.needKnowVO = needKnowVO;
    }

    public PictureTextVO getPictureTextVO() {
        return pictureTextVO;
    }

    public void setPictureTextVO(PictureTextVO pictureTextVO) {
        this.pictureTextVO = pictureTextVO;
    }
}
