package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.client.enums.Domain;
import com.yimayhd.ic.client.model.domain.CityActivityDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.*;
import com.yimayhd.ic.client.model.param.item.*;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubAddDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubUpdateDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.ic.client.model.result.item.*;
import com.yimayhd.ic.client.service.item.HotelService;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.cityactivity.CityActivityPublishService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.CityActivityConverter;
import com.yimayhd.sellerAdmin.converter.ItemSkuConverter;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.*;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;
import com.yimayhd.sellerAdmin.service.CityActivityService;
import com.yimayhd.sellerAdmin.service.CommodityService;
import com.yimayhd.sellerAdmin.service.TfsService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.NumUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by czf on 2015/11/24.
 */
public class CityActivityServiceImpl implements CityActivityService {
    private static final Logger log = LoggerFactory.getLogger(CityActivityServiceImpl.class);
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Autowired
    private ItemPublishService itemPublishServiceRef;
    @Autowired
    private TfsService tfsService;
    @Autowired
    private HotelService hotelServiceRef;
    @Autowired
    private CityActivityPublishService cityActivityPublishServiceRef;

    @Override
    public CityActivityItemVO getCityActivityById(long id) throws Exception {
        CityActivityOptionDTO optionDTO = new CityActivityOptionDTO();
        //全部设置成true
        optionDTO.setNeedCategory(true);
        optionDTO.setNeedSku(true);
        CityActivityResult cityActivityResult = itemQueryServiceRef.getCityActivityResult(id, optionDTO);
        if(null == cityActivityResult){
            log.error("itemQueryServiceRef.getCityActivityResult return value is null and param: " + JSON.toJSONString(optionDTO) + " and id is : " + id);
            throw new BaseException("返回结果错误");
        }else if(!cityActivityResult.isSuccess()){
            log.error("itemQueryServiceRef.getCityActivityResult return value error ! returnValue : "+ JSON.toJSONString(cityActivityResult) + " and parame:" + JSON.toJSONString(optionDTO) + " and id is : " + id);
            throw new NoticeException(cityActivityResult.getResultMsg());
        }
        CityActivityItemVO cityActivityItemVO = CityActivityConverter.convertItemVO(cityActivityResult);
        return cityActivityItemVO;
    }

    @Override
    public WebResultSupport addCityActivityItem(CityActivityItemVO cityActivityItemVO) throws Exception {
        WebResultSupport webResultSupport = new WebResultSupport();
        CityActivityPubAddDTO cityActivityPubAddDTO = new CityActivityPubAddDTO();
        ItemVO itemVO = cityActivityItemVO.getItemVO();
        ItemDO itemDO = ItemVO.getItemDO(cityActivityItemVO.getItemVO());
        fillItemDO(itemDO, cityActivityItemVO.getCategoryVO());
        CityActivityDO cityActivityDO = CityActivityConverter.convertDO(cityActivityItemVO.getCityActivityVO());
        List<ItemSkuDO> skuDOList = new ArrayList<>();
        for(ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()) {
            ItemSkuDO itemSkuDO = ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO);
            itemSkuDO.setItemId(itemVO.getId());
            itemSkuDO.setSellerId(itemVO.getSellerId());
            itemSkuDO.setCategoryId(itemVO.getCategoryId());
            skuDOList.add(itemSkuDO);
        }
        cityActivityPubAddDTO.setDomain(Constant.DOMAIN_JIUXIU);
        cityActivityPubAddDTO.setItem(itemDO);
        cityActivityPubAddDTO.setCityActivity(cityActivityDO);
        cityActivityPubAddDTO.setItemSkuList(skuDOList);
        CityActivityPublishResult result = cityActivityPublishServiceRef.add(cityActivityPubAddDTO);
        if(null == result){
            log.error("cityActivityPublishServiceRef.add result is null and param: " + JSON.toJSONString(cityActivityPubAddDTO));
            throw new BaseException("返回结果错误,同城商品新增失败 ");
        } else if(!result.isSuccess()){
            log.error("cityActivityPublishServiceRef.add error:" + JSON.toJSONString(result) + "and param: " + JSON.toJSONString(cityActivityPubAddDTO));
            throw new BaseException(result.getResultMsg());
        }
        return webResultSupport;
    }

    @Override
    public WebResultSupport modifyCityActivityItem(CityActivityItemVO cityActivityItemVO) throws Exception {
        WebResultSupport result = new WebResultSupport();
        if(cityActivityItemVO == null) {
            result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
            return result;
        }
        CityActivityPubUpdateDTO cityActivityPubUpdateDTO = CityActivityConverter.convertPubUpdateDTO(cityActivityItemVO);
        if(cityActivityPubUpdateDTO == null) {
            result.setWebReturnCode(WebReturnCode.PARAM_ERROR);
            return result;
        }
        ICResultSupport icResultSupport = null;
        try {
            icResultSupport = cityActivityPublishServiceRef.update(cityActivityPubUpdateDTO);
        } catch (Exception e) {
            log.error("cityActivityPublishServiceRef.update(cityActivityPubUpdateDTO); failed. cityActivityPubUpdateDTO:" + JSON.toJSONString(cityActivityPubUpdateDTO), e);
        }
        if(null == icResultSupport){
            log.error("cityActivityPublishServiceRef.update result is null and param: " + JSON.toJSONString(cityActivityPubUpdateDTO));
            throw new BaseException("返回结果错误,同城商品修改失败 ");
        } else if(!icResultSupport.isSuccess()){
            log.error("cityActivityPublishServiceRef.update error:" + JSON.toJSONString(icResultSupport) + "and param: " + JSON.toJSONString(cityActivityPubUpdateDTO));
            throw new BaseException(icResultSupport.getResultMsg());
        }
        return result;
    }

    public void fillItemDO(ItemDO itemDO, CategoryVO categoryVO) {
        itemDO.setCategoryId(categoryVO.getId());
        itemDO.setRootCategoryId(categoryVO.getParentId());
        itemDO.setDomain(Constant.DOMAIN_JIUXIU);
        itemDO.setItemType(ItemType.CITY_ACTIVITY.getValue());
        itemDO.setOptions(ItemOptions.HAS_SKU.getType());
    }
}
