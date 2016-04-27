package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.fhtd.utils.PictureUtil;
import com.yimayhd.ic.client.model.domain.CityActivityDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RouteDO;
import com.yimayhd.ic.client.model.domain.RouteItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.*;
import com.yimayhd.ic.client.model.enums.*;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.param.item.ItemSkuPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubAddDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityUpdateDTO;
import com.yimayhd.ic.client.model.param.item.line.*;
import com.yimayhd.ic.client.model.result.item.CityActivityResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.resourcecenter.entity.item.SalesPropertyVO;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.*;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.price.*;
import com.yimayhd.sellerAdmin.model.line.route.*;
import com.yimayhd.sellerAdmin.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author weiwenliang@20160406
 *
 */
public class CityActivityConverter {

	public static CityActivityItemVO convertItemVO(CityActivityResult cityActivityResult, List<Long> themeIds, List<CityVO> destCitys, PicTextResult picTextResult) throws Exception{
		CityActivityItemVO cityActivityItemVO = new CityActivityItemVO();
		if(cityActivityResult == null || !cityActivityResult.isSuccess() || cityActivityResult.getItemDO() == null
				|| CollectionUtils.isEmpty(cityActivityResult.getItemSkuDOList())) {
			return null;
		}
		try {
			CategoryVO categoryVO = CategoryVO.getCategoryVO(cityActivityResult.getCategoryDO());
			cityActivityResult.getItemDO().setItemSkuDOList(cityActivityResult.getItemSkuDOList());
			cityActivityItemVO.setItemVO(ItemConverter.convertItemVO(cityActivityResult.getItemDO(), categoryVO));
			fillCategoryValueVOs(categoryVO, cityActivityResult.getItemSkuDOList());
			cityActivityItemVO.setCategoryVO(categoryVO);
			cityActivityItemVO.setCityActivityVO(convertVO(cityActivityResult.getCityActivityDO()));
			if(!CollectionUtils.isEmpty(themeIds)) {
				cityActivityItemVO.setThemes(themeIds);
			}
			if(!CollectionUtils.isEmpty(destCitys)) {
				//目前同城活动只有唯一目的地
				cityActivityItemVO.setDest(destCitys.get(0));
			}
			cityActivityItemVO.setPictureTextVO(PictureTextConverter.toPictureTextVO(picTextResult));
			cityActivityItemVO.setNeedKnowVO(cityActivityItemVO.getCityActivityVO().getNeedKnowVO());
		} catch (Exception e) {
			throw e;
		}
		return cityActivityItemVO;
	}

	public static CityActivityVO convertVO(CityActivityDO cityActivityDO) {
		if(cityActivityDO == null) {
			return null;
		}
		CityActivityVO cityActivityVO = new CityActivityVO();
		BeanUtils.copyProperties(cityActivityDO, cityActivityVO);
		cityActivityVO.setNeedKnowVO(NeedKnowConverter.convertNeedKnowVO(cityActivityDO.getNeedKnow()));
		return cityActivityVO;
	}

	public static CityActivityDO convertDO(CityActivityVO cityActivityVO){
		CityActivityDO cityActivityDO = new CityActivityDO();
		BeanUtils.copyProperties(cityActivityVO, cityActivityDO);
		cityActivityDO.setNeedKnow(NeedKnowConverter.convertNeedKnow(cityActivityVO.getNeedKnowVO()));
		return cityActivityDO;
	}

	public static CityActivityPubUpdateDTO convertPubUpdateDTO(CityActivityItemVO cityActivityItemVO) throws Exception{
		if(cityActivityItemVO == null) {
			return null;
		}
		CityActivityPubUpdateDTO cityActivityPubUpdateDTO = null;
		try {
			cityActivityPubUpdateDTO = new CityActivityPubUpdateDTO();
			cityActivityPubUpdateDTO.setCityActivity(convertUpdateDTO(cityActivityItemVO.getCityActivityVO()));
			cityActivityPubUpdateDTO.setItem(convertItemUpdateDTO(cityActivityItemVO.getItemVO()));
			setItemSkuDOList(cityActivityPubUpdateDTO, cityActivityItemVO.getItemVO());
			cityActivityPubUpdateDTO.setItemId(cityActivityItemVO.getItemVO().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityActivityPubUpdateDTO;
	}

	public static CityActivityUpdateDTO convertUpdateDTO(CityActivityVO cityActivityVO) {
		if(cityActivityVO == null) {
			return null;
		}
		CityActivityUpdateDTO cityActivityUpdateDTO = new CityActivityUpdateDTO();
		BeanUtils.copyProperties(cityActivityVO, cityActivityUpdateDTO);
		cityActivityUpdateDTO.setNeedKnow(NeedKnowConverter.convertNeedKnow(cityActivityVO.getNeedKnowVO()));
		return cityActivityUpdateDTO;
	}

	public static ItemPubUpdateDTO convertItemUpdateDTO(ItemVO itemVO) throws Exception {
		if(itemVO == null) {
			return null;
		}
		ItemPubUpdateDTO itemPubUpdateDTO = new ItemPubUpdateDTO();

		BeanUtils.copyProperties(itemVO, itemPubUpdateDTO);
		if(itemVO.getLongitudeVO() != null) {
			itemPubUpdateDTO.setLongitude(itemVO.getLongitudeVO());
		}
		if(itemVO.getLatitudeVO() != null) {
			itemPubUpdateDTO.setLatitude(itemVO.getLatitudeVO());
		}
		if(StringUtils.isNotBlank(itemVO.getEndDateStr())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			itemPubUpdateDTO.setEndDate(dateFormat.parse(itemVO.getEndDateStr()));
		}
		if(!CollectionUtils.isEmpty(itemVO.getItemMainPics())) {
			itemPubUpdateDTO.setItemMainPics(itemVO.getItemMainPics());
		}
		return itemPubUpdateDTO;
	}

	private static void setItemSkuDOList(CityActivityPubUpdateDTO cityActivityPubUpdateDTO,ItemVO itemVO){
		if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListByStr())){
			//insert操作时的数组
			List<ItemSkuDO> itemSkuDOList = new ArrayList<ItemSkuDO>();
			//新增sku数组
			List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>();
			//删除sku数组
			List<Long> delItemSkuDOIdList = new ArrayList<Long>();
			//修改sku数组
			List<ItemSkuPubUpdateDTO> updItemSkuDOList = new ArrayList<ItemSkuPubUpdateDTO>();
			for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
				itemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
				if(0 == itemSkuVO.getId()){
					if(itemSkuVO.isChecked()){//没有id，有checked是新增
						addItemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
					}
				}else{
					if(!itemSkuVO.isChecked()){//有id，没有checked是删除
						delItemSkuDOIdList.add(itemSkuVO.getId());
					}else{
						if(itemSkuVO.isModifyStatus()){//有id，有checked，有modifayStatus是修改
							updItemSkuDOList.add(ItemSkuConverter.convertPubUpdateDTO(itemSkuVO));
						}
					}
				}
			}
			cityActivityPubUpdateDTO.setAddItemSkuList(addItemSkuDOList);
			cityActivityPubUpdateDTO.setDelItemSkuList(delItemSkuDOIdList);
			cityActivityPubUpdateDTO.setUpdItemSkuList(updItemSkuDOList);
		}
	}

	private static void fillCategoryValueVOs(CategoryVO categoryVO, List<ItemSkuDO> skuDOList) {
		if(CollectionUtils.isEmpty(skuDOList)) {
			return;
		}
		Map<Long, CategoryPropertyVO> salesPropertyVOMap = new HashMap<>();
		for(CategoryPropertyValueVO categoryPropertyValueVO : categoryVO.getSellCategoryPropertyVOs()) {
			salesPropertyVOMap.put(categoryPropertyValueVO.getPropertyId(), categoryPropertyValueVO.getCategoryPropertyVO());
		}
		for(ItemSkuDO skuDO : skuDOList) {
			for(ItemSkuPVPair skuPVPair : skuDO.getItemSkuPVPairList()) {
				CategoryPropertyVO propertyVO = salesPropertyVOMap.get(skuPVPair.getPId());
				if(propertyVO == null) {
					continue;
				}
				List<CategoryValueVO> categoryValueVOList = propertyVO.getCategoryValueVOs();
				if(categoryValueVOList == null) {
					categoryValueVOList = new ArrayList<>();
					propertyVO.setCategoryValueVOs(categoryValueVOList);
				}
				boolean hasValue = false;
				if(!CollectionUtils.isEmpty(categoryValueVOList)) {
					for(CategoryValueVO valueVO : categoryValueVOList) {
						if(valueVO.getId() == skuPVPair.getVId()) {
							hasValue = true;
							break;
						}
					}
				}
				if(!hasValue) {	//如果没有该value
					CategoryValueVO categoryValueVO = new CategoryValueVO();
					categoryValueVO.setId(skuPVPair.getVId());
					categoryValueVO.setText(skuPVPair.getVTxt());
					categoryValueVO.setType(skuPVPair.getPType());
					categoryValueVO.setChecked(true);

					categoryValueVOList.add(categoryValueVO);
				}
			}
		}
	}

	public static CityActivityPubAddDTO convertPubAddDTO(CityActivityItemVO cityActivityItemVO) throws Exception {
		CityActivityPubAddDTO cityActivityPubAddDTO = new CityActivityPubAddDTO();
		ItemDO itemDO = ItemVO.getItemDO(cityActivityItemVO.getItemVO());
		fillItemDO(itemDO, cityActivityItemVO.getCategoryVO());
		CityActivityDO cityActivityDO = CityActivityConverter.convertDO(cityActivityItemVO.getCityActivityVO());
		cityActivityPubAddDTO.setDomain(Constant.DOMAIN_JIUXIU);
		cityActivityPubAddDTO.setItem(itemDO);
		cityActivityPubAddDTO.setCityActivity(cityActivityDO);
		cityActivityPubAddDTO.setItemSkuList(itemDO.getItemSkuDOList());
		return cityActivityPubAddDTO;
	}


	public static void fillItemDO(ItemDO itemDO, CategoryVO categoryVO) {
		itemDO.setCategoryId(categoryVO.getId());
		itemDO.setRootCategoryId(categoryVO.getParentId());
		itemDO.setDomain(Constant.DOMAIN_JIUXIU);
		itemDO.setItemType(ItemType.CITY_ACTIVITY.getValue());
		itemDO.setOptions(ItemOptions.HAS_SKU.getType());
	}
}
