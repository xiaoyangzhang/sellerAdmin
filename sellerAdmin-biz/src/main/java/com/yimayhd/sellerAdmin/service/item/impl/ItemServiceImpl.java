package com.yimayhd.sellerAdmin.service.item.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.converter.ItemConverter;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
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
 *
 */
public class ItemServiceImpl implements ItemService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ItemRepo itemRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private CityRepo cityRepo;

	@Override
	public PageVO<ItemListItemVO> getItemList(long sellerId, ItemListQuery query) {
		ItemQryDTO itemQryDTO = ItemConverter.toItemQryDTO(sellerId, query);
		ItemPageResult itemPageResult = itemRepo.getItemList(itemQryDTO);
		if (itemPageResult == null) {
			log.error("查询商品列表返回结果为空" + JSON.toJSONString(itemQryDTO));
			throw new BaseException("返回结果错误,新增失败 ");
		}
		List<ItemDO> itemDOList = itemPageResult.getItemDOList();
		List<Long> itemIds = new ArrayList<Long>();
		List<ItemListItemVO> resultList = new ArrayList<ItemListItemVO>();
		for (ItemDO itemDO : itemDOList) {
			resultList.add(ItemConverter.toItemListItemVO(itemDO));
			itemIds.add(itemDO.getId());
		}
		Map<Long, List<ComTagDO>> tagMap = commentRepo.getTagsByOutIds(itemIds, TagType.DESRTPLACE);
		for (ItemListItemVO itemListItemVO : resultList) {
			long itemId = itemListItemVO.getId();
			if (tagMap.containsKey(itemId)) {
				List<ComTagDO> comTagDOs = tagMap.get(itemId);
				List<CityVO> dests = new ArrayList<CityVO>();
				if (CollectionUtils.isNotEmpty(comTagDOs)) {
					for (ComTagDO comTagDO : comTagDOs) {
						CityDTO cityDTO = cityRepo.getNameByCode(comTagDO.getName());
						dests.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
					}
				}
				itemListItemVO.setDests(dests);
			}
		}
		PageVO<ItemListItemVO> pageVO = new PageVO<ItemListItemVO>(query.getPageNo(), query.getPageSize(),
				itemPageResult.getRecordCount(), resultList);
		return pageVO;
	}

}
