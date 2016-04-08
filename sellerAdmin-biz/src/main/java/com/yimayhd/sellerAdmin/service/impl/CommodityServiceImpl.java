package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.*;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.HotelService;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.PictureTextConverter;
import com.yimayhd.sellerAdmin.exception.NoticeException;
import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.sellerAdmin.model.ItemResultVO;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;
import com.yimayhd.sellerAdmin.repo.PictureTextRepo;
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
public class CommodityServiceImpl implements CommodityService {
    private static final Logger log = LoggerFactory.getLogger(CommodityServiceImpl.class);
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Autowired
    private ItemPublishService itemPublishServiceRef;
    @Autowired
    private TfsService tfsService;
    @Autowired
    private HotelService hotelServiceRef;
    @Autowired
    private PictureTextRepo pictureTextRepo;
    @Override
    public PageVO<ItemVO> getList(CommodityListQuery commodityListQuery) throws Exception {
        ItemQryDTO itemQryDTO = new ItemQryDTO();
        List<Integer> domainList = new ArrayList<Integer>();
        domainList.add(Constant.DOMAIN_JIUXIU);
        itemQryDTO.setDomains(domainList);
        itemQryDTO.setPageNo(commodityListQuery.getPageNo());
        itemQryDTO.setPageSize(commodityListQuery.getPageSize());

        if(!StringUtils.isBlank(commodityListQuery.getCommName())) {
            itemQryDTO.setName(commodityListQuery.getCommName());
        }
        if(null != commodityListQuery.getId()) {
            itemQryDTO.setId(commodityListQuery.getId());
        }
        if(!StringUtils.isBlank(commodityListQuery.getBeginDate())) {
            Date beginTime = DateUtil.formatMinTimeForDate(commodityListQuery.getBeginDate());
            itemQryDTO.setBeginDate(beginTime);
        }
        if(!StringUtils.isBlank(commodityListQuery.getEndDate())) {
            Date endTime = DateUtil.formatMaxTimeForDate(commodityListQuery.getEndDate());
            itemQryDTO.setEndDate(endTime);
        }
        List<Integer> status = new ArrayList<Integer>();
        if(0 != commodityListQuery.getCommStatus()){
            status.add(commodityListQuery.getCommStatus());
        }else{
            status.add(ItemStatus.create.getValue());
            status.add(ItemStatus.valid.getValue());
            status.add(ItemStatus.invalid.getValue());
        }
        itemQryDTO.setStatus(status);
        //
        if (commodityListQuery.getItemType() != 0) {
            itemQryDTO.setItemType(commodityListQuery.getItemType());
        }
        //TODO
        //分类 暂时没想好怎么做

        //暂时用不着
        //itemQryDTO.setSellerId();

        ItemPageResult itemPageResult = itemQueryServiceRef.getItem(itemQryDTO);
        if(null == itemPageResult){
            log.error("CommodityServiceImpl.getList-ItemQueryService.getItem result is null and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!itemPageResult.isSuccess()){
            log.error("CommodityServiceImpl.getList-ItemQueryService.getItem error:" + JSON.toJSONString(itemPageResult) + "and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException(itemPageResult.getResultMsg());
        }
        List<ItemDO> itemDOList = itemPageResult.getItemDOList();
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        for(ItemDO itemDO:itemDOList){
            itemVOList.add(ItemVO.getItemVO(itemDO,new CategoryVO()));
        }

        PageVO<ItemVO> pageVO = new PageVO<ItemVO>(commodityListQuery.getPageNo(),commodityListQuery.getPageSize(),itemPageResult.getRecordCount(),itemVOList);
        return pageVO;
    }

    @Override
    public ItemResultVO getCommodityById(long id) throws Exception {
        ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        //全部设置成true
        itemOptionDTO.setCreditFade(true);
        itemOptionDTO.setNeedCategory(true);
        itemOptionDTO.setNeedSku(true);
        ItemResult itemResult = itemQueryServiceRef.getItem(id,itemOptionDTO);
        if(null == itemResult){
            log.error("itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + id);
            throw new BaseException("返回结果错误");
        }else if(!itemResult.isSuccess()){
            log.error("itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + id);
            throw new NoticeException(itemResult.getResultMsg());
        }
        ItemResultVO itemResultVO = new ItemResultVO();

        itemResultVO.setCategoryVO(CategoryVO.getCategoryVO(itemResult.getCategory()));
        itemResultVO.setItemVO(ItemVO.getItemVO(itemResult.getItem(), itemResultVO.getCategoryVO()));
        //商品的排序字段
        itemResultVO.getItemVO().setSort(itemResult.getSortNum());
        //h5
        PicTextResult picTextResult = pictureTextRepo.getPictureText(itemResultVO.getItemVO().getId(), PictureText.MUSTBUY);
        itemResultVO.getItemVO().setPictureTextVO(PictureTextConverter.toPictureTextVO(picTextResult));
        return itemResultVO;
    }

    @Override
    public ItemDO getCommHotelById(long id) throws Exception {
        ItemDO itemDOData = new ItemDO();
        itemDOData.setId(id);
        itemDOData.setRootCategoryId(1);
        itemDOData.setCategoryId(2);
        //酒店id
        //排序
        //时间区间
        itemDOData.setTitle("高级双床房");
        itemDOData.setSubTitle("房间35m，双床，可住2人");
        itemDOData.setPrice(9900);
        //会员限购
        itemDOData.setGmtCreated(new Date());
        itemDOData.setGmtModified(new Date());

        return itemDOData;
    }

    @Override
    public ItemDO addCommHotel(ItemVO itemVO) throws Exception {
        HotelPublishDTO hotelPublishDTO = new HotelPublishDTO();
        ItemDO itemDO = ItemVO.getItemDO(itemVO);
        itemDO.setDomain(Constant.DOMAIN_JIUXIU);
        hotelPublishDTO.setItemDO(itemDO);
        hotelPublishDTO.setSort(itemVO.getSort());

        ICResult<Long> result = hotelServiceRef.publishHotel(hotelPublishDTO);
        if(null == result){
            log.error("ItemPublishService.publish result is null and parame: " + JSON.toJSONString(hotelPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException("返回结果错误,酒店商品新增失败 ");
        } else if(!result.isSuccess()){
            log.error("ItemPublishService.publish error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(hotelPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException(result.getResultMsg());
        }
        return null;
    }

    @Override
    public void modifyCommHotel(ItemVO itemVO) throws Exception {
        //修改的时候要先取出来，在更新
        ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        ItemResult itemResult = itemQueryServiceRef.getItem(itemVO.getId(), itemOptionDTO);
        if(null == itemResult){
            log.error("itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
            throw new BaseException("查询商品，返回结果错误");
        }else if(!itemResult.isSuccess()){
            log.error("itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
            throw new NoticeException(itemResult.getResultMsg());
        }
        ItemDO itemDB = itemResult.getItem();
        if(null != itemDB) {
            HotelPublishDTO hotelPublishDTO = new HotelPublishDTO();
            hotelPublishDTO.setItemDO(itemDB);

            //组装
            ItemDO itemDO = ItemVO.getItemDO(itemVO);
            //酒店提前预定时间
            if (null != itemVO.getEndBookTimeLimit()) {
                ItemFeature itemFeature = null;
                if (null != itemDB.getItemFeature()) {
                    itemFeature = itemDB.getItemFeature();
                    itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, itemVO.getEndBookTimeLimit() * 24 * 3600);
                } else {
                    itemFeature = new ItemFeature(null);
                    itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, itemVO.getEndBookTimeLimit() * 24 * 3600);
                    itemDB.setItemFeature(itemFeature);
                }
            }
            //商品名称
            itemDB.setTitle(itemDO.getTitle());
            //商品说明
            itemDB.setOneWord(itemDO.getOneWord());
            //商品价格
            itemDB.setPrice(itemDO.getPrice());
            //商品库存
            itemDB.setStockNum(itemDO.getStockNum());
            //商品图片
            if(StringUtils.isNotBlank(itemVO.getSmallListPic())){
                itemDB.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC,itemVO.getSmallListPic());
            }
            if(StringUtils.isNotBlank(itemVO.getBigListPic())){
                itemDB.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC,itemVO.getBigListPic());
            }
            if(StringUtils.isNotBlank(itemVO.getCoverPics())){
                itemDB.addPicUrls(ItemPicUrlsKey.COVER_PICS, itemVO.getCoverPics());

            }
            //最晚到店时间列表(暂时只有酒店用)
            if(CollectionUtils.isNotEmpty(itemVO.getOpenTimeList())){
                itemDB.getItemFeature().put(ItemFeatureKey.LATEST_ARRIVE_TIME, itemVO.getOpenTimeList());
            }
            hotelPublishDTO.setSort(itemVO.getSort());
            ICResult<Boolean> result = hotelServiceRef.updatePublishHotel(hotelPublishDTO);
            if (null == result) {
                log.error("ItemPublishService.publish result is null and parame: " + JSON.toJSONString(hotelPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
                throw new BaseException("返回结果错误,酒店商品修改失败 ");
            } else if (!result.isSuccess()) {
                log.error("ItemPublishService.publish error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(hotelPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
                throw new BaseException(result.getResultMsg());
            }
        }
    }

    @Override
    public void setCommStatusList(List<Long> idList, int status) throws Exception {

    }

    public void publish(long sellerId,long id)throws Exception{
        ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
        itemPublishDTO.setSellerId(sellerId);
        itemPublishDTO.setItemId(id);
        ItemPubResult itemPubResult = itemPublishServiceRef.publish(itemPublishDTO);
        if(null == itemPubResult){
            log.error("ItemPublishService.publish result is null and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException("返回结果错误,商品上架失败 ");
        } else if(!itemPubResult.isSuccess()){
            log.error("ItemPublishService.publish error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException(itemPubResult.getResultMsg());
        }
    }

    public void close(long sellerId,long id)throws Exception{
        ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
        itemPublishDTO.setSellerId(sellerId);
        itemPublishDTO.setItemId(id);
        ItemCloseResult itemCloseResult = itemPublishServiceRef.close(itemPublishDTO);
        if(null == itemCloseResult){
            log.error("ItemPublishService.itemCloseResult result is null and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException("返回结果错误,商品下架失败 ");
        } else if(!itemCloseResult.isSuccess()){
            log.error("ItemPublishService.itemCloseResult error:" + JSON.toJSONString(itemCloseResult) + "and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException(itemCloseResult.getResultMsg());
        }
    }

    public void batchPublish(long sellerId,List<Long> idList){
        ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
        itemBatchPublishDTO.setSellerId(sellerId);
        itemBatchPublishDTO.setItemIdList(idList);
        ItemPubResult itemPubResult = itemPublishServiceRef.batchPublish(itemBatchPublishDTO);
        if(null == itemPubResult){
            log.error("ItemPublishService.batchPublish result is null and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException("返回结果错误,商品上架失败 ");
        } else if(!itemPubResult.isSuccess()){
            log.error("ItemPublishService.batchPublish error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException(itemPubResult.getResultMsg());
        }
    }

    public void batchClose(long sellerId,List<Long> idList){
        ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
        itemBatchPublishDTO.setSellerId(sellerId);
        itemBatchPublishDTO.setItemIdList(idList);
        ItemCloseResult itemCloseResult = itemPublishServiceRef.batchClose(itemBatchPublishDTO);
        if(null == itemCloseResult){
            log.error("ItemPublishService.batchClose result is null and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException("返回结果错误,商品下架失败 ");
        } else if(!itemCloseResult.isSuccess()){
            log.error("ItemPublishService.batchClose error:" + JSON.toJSONString(itemCloseResult) + "and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException(itemCloseResult.getResultMsg());
        }
    }


    @Override
    public void addCommonItem(ItemVO itemVO) throws Exception {
        //参数类型匹配
        CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
        ItemDO itemDO = ItemVO.getItemDO(itemVO);
        itemDO.setDomain(Constant.DOMAIN_JIUXIU);
        commonItemPublishDTO.setItemDO(itemDO);
        commonItemPublishDTO.setItemSkuDOList(itemDO.getItemSkuDOList());

        ItemPubResult itemPubResult = itemPublishServiceRef.publishCommonItem(commonItemPublishDTO);
        if(null == itemPubResult){
            log.error("ItemPublishService.publishCommonItem result is null and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!itemPubResult.isSuccess()){
            log.error("ItemPublishService.publishCommonItem error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException(itemPubResult.getResultMsg());
        }

        ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(itemPubResult.getItemId(), PictureText.MUSTBUY, JSON.parseObject(itemVO.getPictureTextVOStr(), PictureTextVO.class));
        pictureTextRepo.editPictureText(comentEditDTO);
    }

    @Override
    public void modifyCommonItem(ItemVO itemVO) throws Exception {


        //修改的时候要先取出来，在更新
        ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        ItemResult itemResult = itemQueryServiceRef.getItem(itemVO.getId(), itemOptionDTO);
        if(null == itemResult){
            log.error("itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
            throw new BaseException("查询商品，返回结果错误");
        }else if(!itemResult.isSuccess()){
            log.error("itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
            throw new NoticeException(itemResult.getResultMsg());
        }
        ItemDO itemDB = itemResult.getItem();
        if(null != itemDB) {
            //参数类型匹配
            CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
            //设置itemDB
             commonItemPublishDTO.setItemDO(itemDB);
            //设置sku
            ItemVO.setItemSkuDOListCommonItemPublishDTO(commonItemPublishDTO, itemVO);
            //商品名称
            itemDB.setTitle(itemVO.getTitle());
            //SubTitle
            itemDB.setSubTitle(itemVO.getSubTitle());
            //商品OneWord
            itemDB.setOneWord(itemVO.getOneWord());
            //商品Description
            itemDB.setDescription(itemVO.getDescription());
            //价格
            itemDB.setPrice(NumUtil.doubleToLong(itemVO.getPriceY()));
            //商品库存
            itemDB.setStockNum(itemVO.getStockNum());
            //商品图片
            if(StringUtils.isNotBlank(itemVO.getBigListPic())){
                itemDB.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC,itemVO.getBigListPic());
            }
            if(StringUtils.isNotBlank(itemVO.getSmallListPic())){
                itemDB.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, itemVO.getSmallListPic());
            }
            if(StringUtils.isNotBlank(itemVO.getCoverPics())){
                itemDB.addPicUrls(ItemPicUrlsKey.COVER_PICS, itemVO.getCoverPics());

            }
            //自定义属性
            itemDB.setItemPropertyList(itemVO.getItemPropertyList());
            //减库存方式
            itemDB.getItemFeature().put(ItemFeatureKey.REDUCE_TYPE, itemVO.getReduceType());
            //评分
            if(null != itemVO.getGrade()){
                ItemFeature itemFeature = null;
                if (null != itemDB.getItemFeature()) {
                    itemFeature = itemDB.getItemFeature();
                    itemFeature.put(ItemFeatureKey.GRADE, itemVO.getGrade());
                } else {
                    itemFeature = new ItemFeature(null);
                    itemFeature.put(ItemFeatureKey.GRADE, itemVO.getGrade());
                    itemDB.setItemFeature(itemFeature);
                }
            }
            //商品编码
            itemDB.setCode(itemVO.getCode());

            ItemPubResult itemPubResult = itemPublishServiceRef.updatePublishCommonItem(commonItemPublishDTO);
            if(null == itemPubResult){
                log.error("ItemPublishService.publishCommonItem result is null and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
                throw new BaseException("返回结果错误,修改失败");
            } else if(!itemPubResult.isSuccess()){
                log.error("ItemPublishService.publishCommonItem error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
                throw new BaseException(itemPubResult.getResultMsg());
            }
            try {
                PictureTextVO pictureTextVO = JSON.parseObject(itemVO.getPictureTextVOStr(), PictureTextVO.class);
                ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(itemVO.getId(),PictureText.MUSTBUY,pictureTextVO);
                pictureTextRepo.editPictureText(comentEditDTO);
            }catch (Exception e){
                log.error("商品保存成功，H5保存失败",e);
                throw new BaseException("商品修改成功，H5修改失败");
            }

        }
    }
}
