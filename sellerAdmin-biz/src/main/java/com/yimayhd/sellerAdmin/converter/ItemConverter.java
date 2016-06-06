package com.yimayhd.sellerAdmin.converter;

import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemDTO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.PayMode;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.sellerAdmin.enums.BizPayMode;
import com.yimayhd.sellerAdmin.model.*;
import com.yimayhd.sellerAdmin.model.item.*;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.util.ItemUtil;
import com.yimayhd.sellerAdmin.util.NumUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * 商品转换器
 * 
 * @author yebin
 *
 */
public class ItemConverter {

	public static ItemQryDTO toItemQryDTO(long sellerId, ItemListQuery query) {
		if (sellerId <= 0 || query == null) {
			return null;
		}
		ItemQryDTO itemQryDTO = new ItemQryDTO();
		itemQryDTO.setName(query.getName());
		if (StringUtils.isNotBlank(query.getItemId())) {
			if (StringUtils.isNumeric(query.getItemId())) {
				itemQryDTO.setId(Long.parseLong(query.getItemId()));
			} else {
				return null;
			}
		}
		itemQryDTO.setSellerId(sellerId);
		if (query.getStatus() != null) {
			itemQryDTO.setStatus(Arrays.asList(query.getStatus()));
		} else {
			itemQryDTO.setStatus(Arrays.asList(ItemStatus.create.getValue(), ItemStatus.invalid.getValue(),
					ItemStatus.valid.getValue()));
		}
		if (query.getItemType() != null) {
			itemQryDTO.setItemType(query.getItemType());
		}
		itemQryDTO.setBeginDate(query.getBeginDate());
		Date endDate = query.getEndDate();
		if (endDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			itemQryDTO.setEndDate(cal.getTime());
		}
		itemQryDTO.setPageNo(query.getPageNo());
		itemQryDTO.setPageSize(query.getPageSize());
		return itemQryDTO;
	}

	public static ItemListItemVO toItemListItemVO(ItemDO itemDO) {
		if (itemDO == null) {
			return null;
		}
		ItemListItemVO itemListItemVO = new ItemListItemVO();
		itemListItemVO.setId(itemDO.getId());
		List<String> itemMainPics = PicUrlsUtil.getItemMainPics(itemDO);
		if (CollectionUtils.isNotEmpty(itemMainPics)) {
			itemListItemVO.setPicture(itemMainPics.get(0));
		}
		itemListItemVO.setName(itemDO.getTitle());
		itemListItemVO.setCode(itemDO.getCode());
		itemListItemVO.setPrice(itemDO.getPrice());
		itemListItemVO.setType(itemDO.getItemType());
		itemListItemVO.setStatus(itemDO.getStatus());
		itemListItemVO.setOperates(ItemUtil.getItemOperates(itemDO.getItemType(), itemDO.getStatus()));
		itemListItemVO.setPublishDate(itemDO.getGmtCreated());
		return itemListItemVO;
	}
	public static ItemListItemVO toItemListItemVO(ItemInfo itemInfo) {
		if (itemInfo == null) {
			return null;
		}
		ItemDTO itemDO = itemInfo.getItemDTO();
		if(itemDO==null){
			return null;
		}
		ItemListItemVO itemListItemVO = new ItemListItemVO();
		itemListItemVO.setId(itemDO.getId());
//		List<String> itemMainPics = PicUrlsUtil.getItemMainPics(itemDO);
		List<String> itemMainPics =	itemDO.getItemMainPics();
		if (CollectionUtils.isNotEmpty(itemMainPics)) {
			itemListItemVO.setPicture(itemMainPics.get(0));
		}
		itemListItemVO.setName(itemDO.getTitle());
		itemListItemVO.setCode(itemDO.getCode());
		itemListItemVO.setPrice(itemDO.getPrice());
		itemListItemVO.setType(itemDO.getItemType());
		itemListItemVO.setStatus(itemDO.getStatus());
		itemListItemVO.setOperates(ItemUtil.getItemOperates(itemDO.getItemType(), itemDO.getStatus()));
		itemListItemVO.setPublishDate(itemDO.getGmtCreated());
		itemListItemVO.setCategoryId(itemDO.getCategoryId());
		BizPayMode bizPayMode = BizPayMode.getByType(itemDO.getPayMode());
		if(bizPayMode!=null) {
			itemListItemVO.setPayMode(bizPayMode.getDesc());//获取 在线付/到店付
		}
		//酒店信息
		HotelDO hotelDO= itemInfo.getHotelDO();
		RoomDO roomDO= itemInfo.getRoomDO();
		TicketDO ticketDO= itemInfo.getTicketDO();
		if(hotelDO!=null){
			itemListItemVO.setPicture(hotelDO.getLogoUrl());
			itemListItemVO.setItemHotelVO(new ItemHotelVO().setName(hotelDO.getName()));
		}
		//房间型号
		if(roomDO!=null){
			itemListItemVO.setItemRoomVO(new ItemRoomVO().setName(roomDO.getName()));
		}
		//景区门票信息
		if(ticketDO!=null){
			itemListItemVO.setItemTicketVO(new ItemTicketVO().setTitle(ticketDO.getTitle()));
		}
		// 景区信息
		ScenicDO scenicDO =  itemInfo.getScenicDO();
		if(scenicDO!=null){
			itemListItemVO.setPicture(scenicDO.getLogoUrl());
			itemListItemVO.setItemScenicVO(new ItemScenicVO().setName(scenicDO.getName()));
		}

		return itemListItemVO;
	}
	public static ItemVO convertItemVO(ItemDO itemDO, CategoryVO categoryVO) {
		ItemVO itemVO = new ItemVO();
		BeanUtils.copyProperties(itemDO, itemVO);
		// 分转元
		itemVO.setPriceY(NumUtil.moneyTransformDouble(itemVO.getPrice()));
		// if(CollectionUtils.isNotEmpty(itemVO.getItemSkuDOList())){
		// List<ItemSkuVO> itemSkuVOList = new ArrayList<ItemSkuVO>();
		// for (ItemSkuDO itemSkuDO : itemVO.getItemSkuDOList()){
		// itemSkuVOList.add(ItemSkuVO.getItemSkuVO(itemSkuDO));
		// }
		// itemVO.setItemSkuVOList(itemSkuVOList);
		// }

		if (null != itemVO.getItemFeature()) {
			// 提前预定时间(暂时酒店用)
			itemVO.setEndBookTimeLimit((long) (itemVO.getItemFeature().getEndBookTimeLimit() / (24 * 3600)));

			if (itemVO.getItemFeature().getStartBookTimeLimit() != 0) {
				// 入园规则提前几天（暂时景区用）
				long startBookTimeLimit = itemVO.getItemFeature().getStartBookTimeLimit();
				// 入园规则提前几点（暂时景区用）
				long days = startBookTimeLimit / (60 * 60 * 24);
				// 入园规则提前几点（暂时景区用）
				long hours = (24 - (startBookTimeLimit % (60 * 60 * 24)) / (60 * 60));
				itemVO.setStartBookTimeDays(days);
				itemVO.setStartBookTimeHours(hours);
			}

			// 评分（暂时普通商品用）
			itemVO.setGrade(itemVO.getItemFeature().getGrade());
			// 库存方式
			itemVO.setReduceType(itemVO.getItemFeature().getReduceType());
			// 最晚到店时间列表(暂时只有酒店用)
			itemVO.setOpenTimeList(itemVO.getItemFeature().getLatestArriveTime());
		}
		// picUrls转对应的list
		if (StringUtils.isNotBlank(itemVO.getPicUrlsString())) {
			itemVO.setSmallListPic(PicUrlsUtil.getSmallListPic(itemDO));
			itemVO.setBigListPic(PicUrlsUtil.getBigListPic(itemDO));
			itemVO.setPicList(PicUrlsUtil.getPicList(itemDO));
			itemVO.setItemMainPics(PicUrlsUtil.getItemMainPics(itemDO));
		}
		// 截止时间
		if (itemDO.getEndDate() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			itemVO.setEndDateStr(dateFormat.format(itemDO.getEndDate()));
		}
		itemVO.setLongitudeVO(itemDO.getLongitude());
		itemVO.setLatitudeVO(itemDO.getLatitude());

		itemVO.setItemSkuVOListAll(convertItemSkuVOListAll(itemVO.getItemSkuDOList()));

		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListAll())) {
			Collections.sort(itemVO.getItemSkuVOListAll(), new ItemSkuVO.ItemSkuVOSort());
		}

		return itemVO;
	}

	public static List<ItemSkuVO> convertItemSkuVOListAll(List<ItemSkuDO> skuDOList) {
		if (CollectionUtils.isEmpty(skuDOList)) {
			return null;
		}
		List<ItemSkuVO> skuVOList = new ArrayList<>();
		for (ItemSkuDO skuDO : skuDOList) {
			ItemSkuVO skuVO = ItemSkuVO.getItemSkuVO(skuDO);
			skuVO.setChecked(true);
			skuVOList.add(skuVO);
		}
		return skuVOList;
	}
}
