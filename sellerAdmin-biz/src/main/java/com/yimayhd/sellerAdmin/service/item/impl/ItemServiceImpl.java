package com.yimayhd.sellerAdmin.service.item.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.result.ICPageResult;

import com.yimayhd.resourcecenter.model.enums.DestinationOutType;
import com.yimayhd.sellerAdmin.biz.DestinationBiz;
import com.yimayhd.sellerAdmin.repo.DestinationRepo;
import com.yimayhd.sellerAdmin.util.ItemUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.converter.ItemConverter;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.line.City;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.repo.CityRepo;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.repo.ItemRepo;
import com.yimayhd.sellerAdmin.service.item.ItemService;
import com.yimayhd.user.client.dto.CityDTO;

/**
 * 商品服务（实现）
 *
 * @author yebin
 */
public class ItemServiceImpl implements ItemService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private DestinationBiz destinationBiz;

    @Override
    public WebResult<PageVO<ItemListItemVO>> getItemList(long sellerId, ItemListQuery query) {
        try {
            if (sellerId <= 0) {
                log.warn("Params: sellerId=" + sellerId);
                log.warn("ItemService.getItemList param error");
                return WebResult.failure(WebReturnCode.PARAM_ERROR, "参数错误");
            }
            if (query == null) {
                query = new ItemListQuery();
            }
            ItemQryDTO itemQryDTO = ItemConverter.toItemQryDTO(sellerId, query);
            if (itemQryDTO == null) {
                return WebResult.success(new PageVO<ItemListItemVO>(query.getPageNo(), query.getPageSize(), 0));
            }
            //	ItemPageResult itemPageResult = itemRepo.getItemList(itemQryDTO);
            ICPageResult<ItemInfo> icPageResult = itemRepo.getItemList(itemQryDTO);
            if (icPageResult != null && icPageResult.getList() == null) {
                int toalCount = icPageResult.getTotalCount();
                if (toalCount > 0) {
                    int totalPage = toalCount % icPageResult.getPageSize() == 0 ? (toalCount / icPageResult.getPageSize()) : (toalCount / icPageResult.getPageSize() + 1);
                    itemQryDTO.setPageNo(totalPage);
                    query.setPageNo(totalPage);
                    icPageResult = itemRepo.getItemList(itemQryDTO);
                } else {
                    return WebResult.success(new PageVO<ItemListItemVO>(query.getPageNo(), query.getPageSize(), 0));
                }
            }
            if (icPageResult == null) {

                return WebResult.success(new PageVO<ItemListItemVO>(query.getPageNo(), query.getPageSize(), 0));
            }
            List<ItemInfo> itemDOList = icPageResult.getList();
            List<ItemListItemVO> resultList = new ArrayList<ItemListItemVO>();
            if (CollectionUtils.isNotEmpty(itemDOList)) {
                List<Long> itemIds = new ArrayList<Long>();
                for (ItemInfo itemDO : itemDOList) {
                    resultList.add(ItemConverter.toItemListItemVO(itemDO));
                    itemIds.add(itemDO.getItemDTO().getId());
                }
                Map<Long, List<ComTagDO>> tagMap = commentRepo.getTagsByOutIds(itemIds, TagType.DESTPLACE);
                if (!org.springframework.util.CollectionUtils.isEmpty(tagMap)) {
                    for (ItemListItemVO itemListItemVO : resultList) {
                        if (ItemType.CITY_ACTIVITY.getValue() == itemListItemVO.getType()) {
                            int type = DestinationOutType.URBAN_LINE.getCode();
                            long itemId = itemListItemVO.getId();
                            if (tagMap.containsKey(itemId)) {
                                List<ComTagDO> comTagDOs = tagMap.get(itemId);
                                //List<CityVO> dests = toCityVO(comTagDOs);
                                List<CityVO> dests = destinationBiz.toCityVODestWithTags(comTagDOs, type);
                                itemListItemVO.setDests(dests);
                            }
                        }
                        if (ItemUtil.isLine(ItemType.get(itemListItemVO.getType()))) {
                            int type = DestinationOutType.GROUP_LINE.getCode();
                            long itemId = itemListItemVO.getId();
                            if (tagMap.containsKey(itemId)) {
                                List<ComTagDO> comTagDOs = tagMap.get(itemId);
                                //List<CityVO> dests = toCityVO(comTagDOs);
                                List<CityVO> dests = destinationBiz.toCityVODestWithTags(comTagDOs, type);
                                itemListItemVO.setDests(dests);
                            }
                        }
                    }
                }

            }
            PageVO<ItemListItemVO> pageVO = new PageVO<ItemListItemVO>(query.getPageNo(), query.getPageSize(),
                    icPageResult.getTotalCount(), resultList);
            return WebResult.success(pageVO);
        } catch (Exception e) {
            log.warn("Params: sellerId=" + sellerId);
            log.warn("Params: query=" + JSON.toJSONString(query));
            log.warn("ItemService.getItemList error", e);
            return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "系统错误 ");
        }
    }

    private List<CityVO> toCityVO(List<ComTagDO> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return new ArrayList<CityVO>(0);
        }
        List<String> codes = new ArrayList<String>();
        for (ComTagDO comTagDO : tags) {
            codes.add(comTagDO.getName());
        }
        Map<String, CityDTO> citiesByCodes = cityRepo.getCitiesByCodes(codes);
        List<CityVO> departs = new ArrayList<CityVO>();
        for (ComTagDO comTagDO : tags) {
            if (citiesByCodes.containsKey(comTagDO.getName())) {
                CityDTO cityDTO = citiesByCodes.get(comTagDO.getName());
                departs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), new City(cityDTO.getCode(), cityDTO.getName(), cityDTO.getFirstLetter())));
            }
        }
        return departs;
    }

    @Override
    public WebOperateResult shelve(long sellerId, long itemId) {
        try {
            ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
            itemPublishDTO.setSellerId(sellerId);
            itemPublishDTO.setItemId(itemId);
            itemRepo.shelve(itemPublishDTO);
            return WebOperateResult.success();
        } catch (Exception e) {
            log.warn("Params: sellerId=" + sellerId);
            log.warn("Params: itemId=" + itemId);
            log.warn("ItemService.shelve error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
    }

    @Override
    public WebOperateResult unshelve(long sellerId, long itemId) {
        try {
            ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
            itemPublishDTO.setSellerId(sellerId);
            itemPublishDTO.setItemId(itemId);
            itemRepo.unshelve(itemPublishDTO);
            return WebOperateResult.success();
        } catch (Exception e) {
            log.warn("Params: sellerId=" + sellerId);
            log.warn("Params: itemId=" + itemId);
            log.warn("ItemService.unshelve error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
    }

    @Override
    public WebOperateResult delete(long sellerId, long itemId) {
        try {
            ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
            itemPublishDTO.setSellerId(sellerId);
            itemPublishDTO.setItemId(itemId);
            itemRepo.delete(itemPublishDTO);
            return WebOperateResult.success();
        } catch (Exception e) {
            log.warn("Params: sellerId=" + sellerId);
            log.warn("Params: itemId=" + itemId);
            log.warn("ItemService.unshelve error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
    }

    @Override
    public WebOperateResult batchShelve(long sellerId, List<Long> itemIds) {
        try {
            ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
            itemBatchPublishDTO.setSellerId(sellerId);
            itemBatchPublishDTO.setItemIdList(itemIds);
            itemRepo.batchShelve(itemBatchPublishDTO);
            return WebOperateResult.success();
        } catch (Exception e) {
            log.warn("Params: sellerId=" + sellerId);
            log.warn("Params: itemIds=" + itemIds);
            log.warn("ItemService.batchShelve error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
    }

    @Override
    public WebOperateResult batchUnshelve(long sellerId, List<Long> itemIds) {
        try {
            ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
            itemBatchPublishDTO.setSellerId(sellerId);
            itemBatchPublishDTO.setItemIdList(itemIds);
            itemRepo.batchUnshelve(itemBatchPublishDTO);
            return WebOperateResult.success();
        } catch (Exception e) {
            log.warn("Params: sellerId=" + sellerId);
            log.warn("Params: itemIds=" + itemIds);
            log.warn("ItemService.batchUnshelve error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
    }

    @Override
    public WebOperateResult batchDelete(long sellerId, List<Long> itemIds) {
        try {
            ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
            itemBatchPublishDTO.setSellerId(sellerId);
            itemBatchPublishDTO.setItemIdList(itemIds);
            itemRepo.batchDelete(itemBatchPublishDTO);
            return WebOperateResult.success();
        } catch (Exception e) {
            log.warn("Params: sellerId=" + sellerId);
            log.warn("Params: itemIds=" + itemIds);
            log.warn("ItemService.batchUnshelve error", e);
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR);
        }
    }
}
