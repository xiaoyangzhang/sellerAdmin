package com.yimayhd.sellerAdmin.service.hotelManage.impl;


import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ScenicPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.ScenicPublishUpdateDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.ScenicManageDomainChecker;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.service.hotelManage.ScenicManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangdi on 16/5/16.
 */
public class ScenicManageServiceImpl  implements ScenicManageService {


    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Autowired
    private ItemPublishService itemPublishServiceRef;
    private static final Logger log = LoggerFactory.getLogger(ScenicManageServiceImpl.class);

    /**
     * 查询景区列表
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<PageVO<ScenicDO>> queryScenicManageVOListByData(final ScenicManageVO scenicManageVO) {
        ScenicManageDomainChecker checker = new ScenicManageDomainChecker(scenicManageVO);
        WebResult<PageVO<ScenicDO>> result = new WebResult<PageVO<ScenicDO>>();
        try{
            WebResult chekResult = checker.checkQueryScenicManageVOListByData();
            if(!chekResult.isSuccess()){
                log.error("ScenicManageServiceImpl.queryScenicManageVOListByData is fail. code={}, message={} ",
                        chekResult.getErrorCode(), chekResult.getResultMsg());
                return chekResult;
            }
            ScenicPageQuery pageQuery = checker.getBizQueryModel();
            ICPageResult<ScenicDO> callBack = itemQueryServiceRef.pageQueryScenic(pageQuery);
            if(callBack==null){
                log.error("查询pageQueryScenic接口返回参数为null");
                throw new  Exception("查询pageQueryHotel返回结果异常");
            }
            PageVO<ScenicDO> pageModel = new PageVO<ScenicDO>(callBack.getPageNo(),callBack.getPageSize(),callBack.getTotalCount(),callBack.getList());
            result.setValue(pageModel);
        }catch(Exception e){
            e.printStackTrace();
            log.error("查询酒店资源接口异常");
        }

        return result;
    }

    /**
     * 查询景区详情
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<ScenicManageVO> queryScenicManageVOByData(final ScenicManageVO scenicManageVO) {
        ScenicManageDomainChecker checker = new ScenicManageDomainChecker(scenicManageVO);
        WebResult<ScenicManageVO> result = new WebResult<ScenicManageVO>();
        try{
            WebResult chekResult = checker.checkQueryScenicManageVOByData();
            if(!chekResult.isSuccess()){
                log.error("ScenicManageServiceImpl.queryScenicManageVOByData is fail. code={}, message={} ",
                        chekResult.getErrorCode(), chekResult.getResultMsg());
                return chekResult;
            }
            ICResult<ScenicDO>  scenicResult = itemQueryServiceRef.getScenic(scenicManageVO.getId());
            if(scenicResult==null||scenicResult.getModule()==null){
                log.error("ScenicManageServiceImpl.queryScenicManageVOByData is fail. code={}, message={} ",
                        chekResult.getErrorCode(), chekResult.getResultMsg());
                return WebResult.failure(WebReturnCode.PARAM_ERROR, "景区资源信息错误");
            }
            ItemOptionDTO dto = new ItemOptionDTO();

            SingleItemQueryResult callBack =  itemQueryServiceRef.querySingleItem(scenicManageVO.getId(), dto);
            // 景区详情 model
        }catch(Exception e){
            log.error("查询景区详情异常");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 添加景区商品信息
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<ScenicManageVO> addScenicManageVOByDdata(ScenicManageVO scenicManageVO) {

        ItemPubResult result = itemPublishServiceRef.addPublishScenic(new ScenicPublishAddDTO());
        return null;
    }

    /**
     * 修改景区商品信息
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<Boolean> editScenicManageVOByDdata(ScenicManageVO scenicManageVO) {
        ItemPubResult result =itemPublishServiceRef.updatePublishScenic(new ScenicPublishUpdateDTO());
        return null;
    }

    public ItemQueryService getItemQueryServiceRef() {
        return itemQueryServiceRef;
    }

    public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
        this.itemQueryServiceRef = itemQueryServiceRef;
    }

    public ItemPublishService getItemPublishServiceRef() {
        return itemPublishServiceRef;
    }

    public void setItemPublishServiceRef(ItemPublishService itemPublishServiceRef) {
        this.itemPublishServiceRef = itemPublishServiceRef;
    }
}
