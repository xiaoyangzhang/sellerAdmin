package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by wangdi on 16/5/18.
 */
public class ScenicManageDomainChecker {
    private ScenicManageVO scenicManageVO;


    public ScenicManageDomainChecker(ScenicManageVO scenicManageVO){
        this.scenicManageVO = scenicManageVO;
    }

    /**
     * 景区资源列表查询 参数验证
     * @return
     */
    public WebResult checkQueryScenicManageVOListByData(){

        if (scenicManageVO == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        if (scenicManageVO.getPageNo() == null || scenicManageVO.getPageSize() == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "分页数据错误");
        }

        return WebResult.success(null);
    }

    /**
     * 获取查询景区资源参数
     * @return
     */
    public ScenicPageQuery getBizQueryModel(){
        ScenicPageQuery scenicPageQuery = new ScenicPageQuery();
        if(scenicManageVO==null){
            return scenicPageQuery;
        }
        if(scenicManageVO.getId()!=null){

        }
        if(StringUtils.isNotBlank(scenicManageVO.getName())){

        }
        if(scenicManageVO.getProvinceId()!=null){

        }
        if(scenicManageVO.getCityId()!=null){

        }
        if(scenicManageVO.getTownId()!=null){

        }
        return  scenicPageQuery;
    }

    /**
     * 景区详情参数校验
     * @return
     */
    public WebResult checkQueryScenicManageVOByData(){
        if (scenicManageVO == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        if (scenicManageVO.getId()==null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR, "景区ID不能为空");
        }

        return WebResult.success(null);
    }
    public ScenicManageVO getScenicManageVO() {
        return scenicManageVO;
    }

    public void setScenicManageVO(ScenicManageVO scenicManageVO) {
        this.scenicManageVO = scenicManageVO;
    }
}
