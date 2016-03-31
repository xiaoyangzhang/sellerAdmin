package com.yimayhd.sellerAdmin.model;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by czf on 2015/12/25.
 */
public class ScenicVO extends ScenicDO {
    private MasterRecommend masterRecommend;
    private String picListStr;//图片集的str
    private String picturesStr;//详情页展示图(对应pictures)
    private double priceY;//价格元
    private String coverPics;//封面大图String
    private List<PictureVO> pictureList;//图片集

    private NeedKnow needKnowOb;

    public static ScenicDO getScenicDO(ScenicVO scenicVO){
        ScenicDO scenicDO = new ScenicVO();
        BeanUtils.copyProperties(scenicVO, scenicDO);
        //pictures
        if(StringUtils.isNotBlank(scenicVO.getPicturesStr())){
            scenicDO.setPictures(Arrays.asList(scenicVO.getPicturesStr().split("\\|")));
        }
        if(StringUtils.isNotBlank(scenicVO.getCoverPics())){
        	List<String> pic = Arrays.asList(scenicVO.getCoverPics().split("\\|"));
            scenicDO.setPictures(pic);
            scenicDO.setCoverUrl(pic.get(0));
        }
        //NeedKnowOb 在serviceImpl中处理
        //图片集处理(因为有outId还是,只处理新增的)
        //元转分
        scenicDO.setPrice(NumUtil.doubleToLong(scenicVO.getPriceY()));

        return scenicDO;
    }
    public static ScenicVO getScenicVO(ScenicDO scenicDO){
        ScenicVO scenicVO = new ScenicVO();
        BeanUtils.copyProperties(scenicDO,scenicVO);
        //分转元
        scenicVO.setPriceY(NumUtil.moneyTransformDouble(scenicVO.getPrice()));
        return scenicVO;
    }

    public String getPicListStr() {
        return picListStr;
    }

    public void setPicListStr(String picListStr) {
        this.picListStr = picListStr;
    }

    public MasterRecommend getMasterRecommend() {
        return masterRecommend;
    }

    public void setMasterRecommend(MasterRecommend masterRecommend) {
        this.masterRecommend = masterRecommend;
    }

    public NeedKnow getNeedKnowOb() {
        return needKnowOb;
    }

    public void setNeedKnowOb(NeedKnow needKnowOb) {
        this.needKnowOb = needKnowOb;
    }

    public String getPicturesStr() {
        return picturesStr;
    }

    public void setPicturesStr(String picturesStr) {
        this.picturesStr = picturesStr;
    }
	public double getPriceY() {
		return priceY;
	}
	public void setPriceY(double priceY) {
		this.priceY = priceY;
	}
	public String getCoverPics() {
		return coverPics;
	}
	public void setCoverPics(String coverPics) {
		this.coverPics = coverPics;
	}
	public List<PictureVO> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<PictureVO> pictureList) {
		this.pictureList = pictureList;
	}
    
	
	
    
}
