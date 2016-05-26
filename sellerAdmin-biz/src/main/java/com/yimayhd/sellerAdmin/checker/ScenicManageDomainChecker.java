package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.param.item.ScenicPublishAddDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * Created by wangdi on 16/5/18.
 */
public class ScenicManageDomainChecker {
    private  ScenicManageVO scenicManageVO;
    private  ItemDO itemDO;
    private  ScenicDO scenicDO;
    private List<ItemSkuDO> itemSkuDOList;

    private CategoryDO category;
    private  WebResult<PageVO<ScenicManageVO>> webPageResult;

    private  WebResult<ScenicManageVO> webResult;

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
        if(scenicManageVO.getScenicId()!=null){

        }
        if(StringUtils.isNotBlank(scenicManageVO.getName())){
            scenicPageQuery.setName(scenicManageVO.getName());
        }

        return  scenicPageQuery;
    }

    /**
     * do 2 modle
     * @return
     */
    public ScenicManageVO doToModel(ScenicDO _do){
        ScenicManageVO scenicManageVO = new ScenicManageVO();
        if(_do==null){
            return null;
        }
        scenicManageVO.setScenicId(_do.getId());
        scenicManageVO.setName(_do.getName());
        scenicManageVO.setLevel(_do.getLevel());
        scenicManageVO.setLocationCityId(_do.getLocationCityId());
        scenicManageVO.setLocationProvinceId(_do.getLocationProvinceId());
        scenicManageVO.setLocationTownId(_do.getLocationTownId());
        scenicManageVO.setLocationText(_do.getLocationText());

        return scenicManageVO;

    }

    /**
     * 拼装景区商品信息
     * @return
     */
    public ScenicManageVO getBizScenicManageVO(){
        scenicManageVO.setScenicId(itemDO.getOutId());
        scenicManageVO.setItemId(itemDO.getId());
        scenicManageVO.setName(scenicDO.getName());
        scenicManageVO.setTitle(itemDO.getTitle());
        scenicManageVO.setCategoryId(category.getId());//类目ID
        scenicManageVO.setStartBookTimeLimit(itemDO.getItemFeature().getStartBookTimeLimit());//提前预定天数
        // 根据listsku拼装json信息
        scenicManageVO.setSupplierCalendar("");
        itemDO.getItemFeature().getTicketId();//门票ID
        itemDO.getItemFeature().getTicketTitle();//门票名称
        //动态添加 属性
        return scenicManageVO;
    }
    public void  getBizScenicPublishAddOrUpdateDTO(String className) {
//        try{
//            if(className.indexOf("serveMealAddDTO")>0){
//                ScenicPublishAddDTO scenicPublishAddDTO = (ScenicPublishAddDTO) Class.forName("className").newInstance();
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        ItemDO itemDO = new ItemDO();
       // itemDO.setItemFea;scenicManageVO.getScenicId()



    }
    /**
     * 景区详情参数校验
     * @return
     */
    public WebResult checkQueryScenicManageVOByData(){
        if (scenicManageVO == null) {
            return WebResult.failure(WebReturnCode.PARAM_ERROR);
        }
        if (scenicManageVO.getScenicId()==null) {
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

    public WebResult<PageVO<ScenicManageVO>> getWebPageResult() {
        return webPageResult;
    }

    public void setWebPageResult(WebResult<PageVO<ScenicManageVO>> webPageResult) {
        this.webPageResult = webPageResult;
    }

    public WebResult<ScenicManageVO> getWebResult() {
        return webResult;
    }

    public void setWebResult(WebResult<ScenicManageVO> webResult) {
        this.webResult = webResult;
    }

    public ItemDO getItemDO() {
        return itemDO;
    }

    public void setItemDO(ItemDO itemDO) {
        this.itemDO = itemDO;
    }

    public ScenicDO getScenicDO() {
        return scenicDO;
    }

    public void setScenicDO(ScenicDO scenicDO) {
        this.scenicDO = scenicDO;
    }

    public List<ItemSkuDO> getItemSkuDOList() {
        return itemSkuDOList;
    }

    public void setItemSkuDOList(List<ItemSkuDO> itemSkuDOList) {
        this.itemSkuDOList = itemSkuDOList;
    }

    public CategoryDO getCategory() {
        return category;
    }

    public void setCategory(CategoryDO category) {
        this.category = category;
    }
}
