package com.yimayhd.sellerAdmin.model.vo;

import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.sellerAdmin.model.query.ScenicSpotListQuery;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicSpotVO {
    private ScenicDO scenicDO;
    private ScenicSpotListQuery scenicSpotListQuery;
	public ScenicDO getScenicDO() {
		return scenicDO;
	}
	public void setScenicDO(ScenicDO scenicDO) {
		this.scenicDO = scenicDO;
	}
	public ScenicSpotListQuery getScenicSpotListQuery() {
		return scenicSpotListQuery;
	}
	public void setScenicSpotListQuery(ScenicSpotListQuery scenicSpotListQuery) {
		this.scenicSpotListQuery = scenicSpotListQuery;
	}
    
    

   
}
