package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.*;
import com.yimayhd.ic.client.model.param.item.*;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubAddDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubUpdateDTO;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.ic.client.model.result.item.*;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.cityactivity.CityActivityPublishService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.TagBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.CityActivityConverter;
import com.yimayhd.sellerAdmin.converter.PictureTextConverter;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.*;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.repo.PictureTextRepo;
import com.yimayhd.sellerAdmin.service.CityActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czf on 2015/11/24.
 */
public class CityActivityServiceImpl implements CityActivityService {
    private static final Logger log = LoggerFactory.getLogger(CityActivityServiceImpl.class);
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Autowired
    private CityActivityPublishService cityActivityPublishServiceRef;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PictureTextRepo pictureTextRepo;
    @Autowired
    private TagBiz tagBiz;

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
        List<CityVO> cityVOList = tagBiz.getItemCityVOs(id, TagType.DESTPLACE);
        List<Long> themeIds = tagBiz.getItemThemeIds(id, TagType.CITYACTIVITY);
        // 图文详情
        PicTextResult picTextResult = pictureTextRepo.getPictureText(id, PictureText.ITEM);
        CityActivityItemVO cityActivityItemVO = CityActivityConverter.convertItemVO(cityActivityResult, themeIds, cityVOList, picTextResult);
        return cityActivityItemVO;
    }

    @Override
    public WebResultSupport addCityActivityItem(CityActivityItemVO cityActivityItemVO) throws Exception {
        WebResultSupport webResultSupport = new WebResultSupport();
        CityActivityPubAddDTO cityActivityPubAddDTO = CityActivityConverter.convertPubAddDTO(cityActivityItemVO);
        CityActivityPublishResult result = cityActivityPublishServiceRef.add(cityActivityPubAddDTO);
        if(null == result){
            log.error("cityActivityPublishServiceRef.add result is null and param: " + JSON.toJSONString(cityActivityPubAddDTO));
            throw new BaseException("返回结果错误,同城商品新增失败 ");
        } else if(!result.isSuccess()){
            log.error("cityActivityPublishServiceRef.add error:" + JSON.toJSONString(result) + "and param: " + JSON.toJSONString(cityActivityPubAddDTO));
            throw new BaseException(result.getResultMsg());
        }
        long itemId = result.getItemId();
        List<Long> themeIds = cityActivityItemVO.getThemes();
        commentRepo.saveTagRelation(itemId, TagType.CITYACTIVITY, themeIds);
        CityVO dest = cityActivityItemVO.getDest();
        List<Long> destIds = new ArrayList<Long>();
        destIds.add(dest.getId());
        commentRepo.saveTagRelation(itemId, TagType.DESTPLACE, destIds);
        ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(itemId, PictureText.ITEM,
                cityActivityItemVO.getPictureTextVO());
        pictureTextRepo.editPictureText(comentEditDTO);
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
        
        long itemId = cityActivityItemVO.getItemVO().getId();
        CityVO dest = cityActivityItemVO.getDest();
        List<Long> destIds = new ArrayList<Long>();
        destIds.add(dest.getId());
        commentRepo.addLineTagRelationInfo(itemId, TagType.DESTPLACE, destIds);
        List<Long> themeIds = cityActivityItemVO.getThemes();
        commentRepo.saveTagRelation(itemId, TagType.CITYACTIVITY, themeIds);

        ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(itemId, PictureText.ITEM,
                cityActivityItemVO.getPictureTextVO());
        pictureTextRepo.editPictureText(comentEditDTO);
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
