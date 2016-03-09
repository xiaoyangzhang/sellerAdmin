package com.yimayhd.sellerAdmin.model.travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yimayhd.sellerAdmin.constant.Constant;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 基本旅行线路对象
 * 
 * @author yebin
 *
 */
public abstract class BaseTravel {
	private static final int LINE_ADULT_VID = 1;
	private static final int LINE_SINGLE_ROOM_VID = 4;
	protected long categoryId;
	protected long options;
	protected BaseInfo baseInfo;// 基础信息
	protected PriceInfo priceInfo;// 价格信息
	protected boolean readonly = false;

	public void init(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception {
		LineDO line = lineResult.getLineDO();
		if (line != null) {
			this.baseInfo = new BaseInfo(line, comTagDOs);
		}

		/*
		 * RouteDO route = lineResult.getRouteDO(); if (route != null &&
		 * this.baseInfo != null) {
		 * this.baseInfo.setTripImage(route.getPicture()); }
		 */
		parseTripInfo(lineResult);
		ItemDO itemDO = lineResult.getItemDO();
		if (itemDO != null) {
			this.categoryId = itemDO.getCategoryId();
			this.options = itemDO.getOptions();
			this.readonly = itemDO.getStatus() == ItemStatus.valid.getValue();
			this.priceInfo = new PriceInfo(itemDO, lineResult.getItemSkuDOList());
			String picUrls = itemDO.getPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC);
			if (StringUtils.isNotBlank(picUrls) && this.baseInfo != null) {
				this.baseInfo.setOrderImage(picUrls);
			}
		}
	}

	protected abstract void parseTripInfo(LineResult lineResult);

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public PriceInfo getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(PriceInfo priceInfo) {
		this.priceInfo = priceInfo;
	}

	/**
	 * 获得线路发布DTO
	 * 
	 * @return
	 */
	public LinePublishDTO toLinePublishDTOForSave() {
		LinePublishDTO dto = new LinePublishDTO();
		dto.setLineDO(baseInfo.toLineDO());
		setRouteInfo(dto);
		dto.setItemDO(this.getItemDO());
		List<ItemSkuDO> itemSkuDOList = priceInfo.toItemSkuDOList(this.categoryId, this.getSellerId());
		dto.setItemSkuDOList(itemSkuDOList);
		return dto;
	}

	/**
	 * 获得线路发布DTO
	 * 
	 * @return
	 */
	public LinePublishDTO toLinePublishDTOForUpdate(LineResult lineResult) {
		LinePublishDTO dto = new LinePublishDTO();
		// LineDO
		LineDO lineDO = lineResult.getLineDO();
		LineDO lineDTO = this.baseInfo.modifyLineDO(lineDO);
		dto.setLineDO(lineDTO);
		// ItemDO
		ItemDO itemDO = lineResult.getItemDO();
		ItemDO ItemDTO = this.modifyItemDO(itemDO);
		dto.setItemDO(ItemDTO);
		// SKU List
		List<ItemSkuDO> itemSkuDOs = lineResult.getItemSkuDOList();
		Map<Long, ItemSkuDO> skuDOMap = new HashMap<Long, ItemSkuDO>();
		for (ItemSkuDO itemSkuDO : itemSkuDOs) {
			skuDOMap.put(itemSkuDO.getId(), itemSkuDO);
		}
		// SKU分离
		List<ItemSkuDO> itemSkuVOs = this.priceInfo.toItemSkuDOList(this.categoryId, this.getSellerId());
		Map<Long, ItemSkuDO> skuVOMap = new HashMap<Long, ItemSkuDO>();
		for (ItemSkuDO itemSkuDO : itemSkuVOs) {
			if (itemSkuDO.getId() > 0) {
				skuVOMap.put(itemSkuDO.getId(), itemSkuDO);
			}
		}
		List<ItemSkuDO> addSkuList = new ArrayList<ItemSkuDO>();
		List<ItemSkuDO> updateSkuList = new ArrayList<ItemSkuDO>();
		List<Long> deleteSkuList = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(itemSkuVOs)) {
			// 新增
			for (ItemSkuDO itemSkuDO : itemSkuVOs) {
				if (itemSkuDO.getId() <= 0) {
					// 新增的没有ItemId要补上
					itemSkuDO.setItemId(ItemDTO.getId());
					addSkuList.add(itemSkuDO);
				}
			}
			// 删除
			Set<Long> deletedSKUSet = this.priceInfo.getDeletedSKU();
			if (CollectionUtils.isNotEmpty(deletedSKUSet)) {
				deleteSkuList.addAll(deletedSKUSet);
			}
			// 修改
			Set<Long> updatedSKUSet = this.priceInfo.getUpdatedSKU();
			if (CollectionUtils.isNotEmpty(updatedSKUSet)) {
				updatedSKUSet.removeAll(deletedSKUSet);
				for (long skuId : updatedSKUSet) {
					if (skuId > 0) {
						ItemSkuDO skuVO = skuVOMap.get(skuId);
						ItemSkuDO skuDO = skuDOMap.get(skuId);
						if (skuVO == null) {
							deleteSkuList.add(skuId);
						} else {
							if (skuDO != null) {
								// 更新
								skuDO.setPrice(skuVO.getPrice());
								skuDO.setStockNum(skuVO.getStockNum());
								skuDO.setFeature(skuVO.getFeature());
								skuDO.setTitle(skuVO.getTitle());
								skuDO.setProperty(skuVO.getProperty());
								updateSkuList.add(skuDO);
							}
						}
					}
				}
			}
		}
		dto.setAddItemSkuList(addSkuList);
		dto.setUpdItemSkuList(updateSkuList);
		dto.setDelItemSkuList(deleteSkuList);
		// 设置其他
		modifyRouteInfo(dto, lineResult);
		return dto;
	}

	/**
	 * 获得商品标签
	 * 
	 * @return
	 */
	public List<Long> getTagIdList() {
		return baseInfo != null ? baseInfo.getTags() : new ArrayList<Long>();
	}

	/**
	 * 获取ItemDO
	 * 
	 * @return
	 */
	public ItemDO getItemDO() {
		// 初始化
		ItemDO itemDO = new ItemDO();
		itemDO.setId(this.priceInfo.getItemId());
		itemDO.setDomain(Constant.B2C_DOMAIN);
		itemDO.setCategoryId(this.categoryId);
		itemDO.setOptions(this.options);
		itemDO.setPayType(1);
		itemDO.setSource(1);
		itemDO.setVersion(1);
		itemDO.setStockNum(0);
		itemDO.setSubTitle("");
		itemDO.setOneWord("");
		itemDO.setDescription("");
		itemDO.setDetailUrl("");
		itemDO.setItemFeature(new ItemFeature(null));
		return modifyItemDO(itemDO);
	}

	/**
	 * 修改ItemDO
	 * 
	 * @return
	 */
	public ItemDO modifyItemDO(ItemDO itemDO) {
		itemDO.setSellerId(this.getSellerId());
		ItemFeature itemFeature = itemDO.getItemFeature();
		itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT, this.priceInfo.getLimitBySecond());
		itemFeature.put(ItemFeatureKey.AGREEMENT, this.priceInfo.getImportantInfosCode());
		itemFeature.put(ItemFeatureKey.LINE_ADULT_VID, LINE_ADULT_VID);
		itemFeature.put(ItemFeatureKey.LINE_SINGLE_ROOM_VID, LINE_SINGLE_ROOM_VID);
		itemDO.setItemFeature(itemFeature);
		itemDO.setItemType(getItemType());
		itemDO.setTitle(this.baseInfo.getName());
		itemDO.setPrice(this.baseInfo.getPrice());
		if (StringUtils.isNotBlank(this.baseInfo.getProductImageApp())) {
			itemDO.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, this.baseInfo.getProductImageApp());
		}
		if (StringUtils.isNotBlank(this.baseInfo.getTripImage())) {
			itemDO.addPicUrls(ItemPicUrlsKey.COVER_PICS, this.baseInfo.getTripImage());
		}
		if (StringUtils.isNotBlank(this.baseInfo.getOrderImage())) {
			itemDO.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, this.baseInfo.getOrderImage());
		}
		return itemDO;
	}

	protected long getSellerId() {
		long sellerId = Constant.YIMAY_OFFICIAL_ID;
		/*
		 * if (this.baseInfo != null) { sellerId =
		 * this.baseInfo.getPublisherId(); }
		 */
		return sellerId;
	}

	protected abstract void setRouteInfo(LinePublishDTO dto);

	protected abstract void modifyRouteInfo(LinePublishDTO dto, LineResult lineResult);

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getOptions() {
		return options;
	}

	public void setOptions(long options) {
		this.options = options;
	}

	protected abstract int getItemType();

	public boolean isReadonly() {
		return readonly;
	}
}
