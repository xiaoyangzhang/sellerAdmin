package com.yimayhd.sellerAdmin.service.hotelManage.impl;


import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.checker.ScenicManageDomainChecker;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import com.yimayhd.sellerAdmin.repo.ScenicManageRepo;
import com.yimayhd.sellerAdmin.service.hotelManage.ScenicManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangdi on 16/5/16.
 */
public class ScenicManageServiceImpl  implements ScenicManageService {
    @Autowired
    private ScenicManageRepo scenicManageRepo;
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Autowired
    private ItemPublishService itemPublishServiceRef;
    @Autowired
    private CacheManager cacheManager ;

    private static final Logger log = LoggerFactory.getLogger("scenicManage-business.log");

    /**
     * 查询景区列表
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<PageVO<ScenicManageVO>> queryScenicManageVOListByData(final ScenicManageVO scenicManageVO) {
        ScenicManageDomainChecker domain = new ScenicManageDomainChecker(scenicManageVO);
        WebResult<PageVO<ScenicManageVO>> result = new WebResult<PageVO<ScenicManageVO>>();
        domain.setScenicManageVO(scenicManageVO);
        domain.setWebPageResult(result);
        try{
            WebResult chekResult = domain.checkQueryScenicManageVOListByData();
            if(!chekResult.isSuccess()){
                log.error("ScenicManageServiceImpl.queryScenicManageVOListByData is fail. code={}, message={} ",
                        chekResult.getErrorCode(), chekResult.getResultMsg());
                return chekResult;
            }
            result = scenicManageRepo.queryScenicManageVOListByData(domain);
        }catch(Exception e){
            e.printStackTrace();
            log.error("查询酒店资源接口异常",e);
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
        ScenicManageDomainChecker domain = new ScenicManageDomainChecker(scenicManageVO);
        WebResult<ScenicManageVO> result = new WebResult<ScenicManageVO>();
        domain.setWebResult(result);
        try{
            WebResult chekResult = domain.checkQueryScenicManageVOByData();
            if(!chekResult.isSuccess()){
                log.error("ScenicManageServiceImpl.queryScenicManageVOByData is fail. code={}, message={} ",
                        chekResult.getErrorCode(), chekResult.getResultMsg());
                return chekResult;
            }
            result  = scenicManageRepo.queryScenicManageVOByData(domain);
        }catch(Exception e){
            log.error("查询景区详情异常",e);
            e.printStackTrace();
            result.initFailure(WebReturnCode.PARAM_ERROR,"查询详情异常");
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
        ScenicManageDomainChecker domain = new ScenicManageDomainChecker(scenicManageVO);
        WebResult<ScenicManageVO> result = new WebResult<ScenicManageVO>();
        domain.setScenicManageVO(scenicManageVO);
        domain.setWebResult(result);
        try{
            WebResult chekResult = domain.checkAddScenicManageVOByDdata();
            if(!chekResult.isSuccess()){
                log.error("ScenicManageServiceImpl.addScenicManageVOByDdata is fail. code={}, message={} ",
                        chekResult.getErrorCode(), chekResult.getResultMsg());
                return chekResult;
            }
            result  = scenicManageRepo.addScenicManageVOByDdata(domain);
        }catch(Exception e){
            log.error("添加景区商品信息异常",e);
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 修改景区商品信息
     * @param scenicManageVO
     * @return
     */
    @Override
    public WebResult<ScenicManageVO> editScenicManageVOByDdata(ScenicManageVO scenicManageVO) {

        ScenicManageDomainChecker domain = new ScenicManageDomainChecker(scenicManageVO);
        WebResult<ScenicManageVO> result = new WebResult<ScenicManageVO>();
        domain.setScenicManageVO(scenicManageVO);
        domain.setWebResult(result);
        try{
            WebResult chekResult = domain.checkAddScenicManageVOByDdata();
            if(!chekResult.isSuccess()){
                log.error("ScenicManageServiceImpl.editScenicManageVOByDdata is fail. code={}, message={} ",
                        chekResult.getErrorCode(), chekResult.getResultMsg());
                return chekResult;
            }
            result  = scenicManageRepo.editScenicManageVOByDdata(domain);
        }catch(Exception e){
            log.error("编辑景区商品信息异常",e);
            e.printStackTrace();
        }
        return result;
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

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
