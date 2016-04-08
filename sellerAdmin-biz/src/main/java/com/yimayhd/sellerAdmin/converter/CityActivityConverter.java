package com.yimayhd.sellerAdmin.converter;

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
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityUpdateDTO;
import com.yimayhd.ic.client.model.param.item.line.*;
import com.yimayhd.ic.client.model.result.item.CityActivityResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.sellerAdmin.model.*;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.model.line.price.*;
import com.yimayhd.sellerAdmin.model.line.route.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author weiwenliang@20160406
 *
 */
public class CityActivityConverter {

	public static CityActivityItemVO convertItemVO(CityActivityResult cityActivityResult) throws Exception{
		CityActivityItemVO cityActivityItemVO = new CityActivityItemVO();
		if(cityActivityResult == null || !cityActivityResult.isSuccess() || cityActivityResult.getItemDO() == null
				|| CollectionUtils.isEmpty(cityActivityResult.getItemSkuDOList())) {
			return null;
		}
		try {
			CategoryVO categoryVO = CategoryVO.getCategoryVO(cityActivityResult.getCategoryDO());
			cityActivityItemVO.setItemVO(ItemVO.getItemVO(cityActivityResult.getItemDO(), categoryVO));
			cityActivityItemVO.setCategoryVO(categoryVO);
			cityActivityItemVO.setItemSkuVOList(ItemSkuConverter.convertVOList(cityActivityResult.getItemSkuDOList()));
			cityActivityItemVO.setCityActivityVO(convertVO(cityActivityResult.getCityActivityDO()));
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
		return cityActivityVO;
	}

	public static CityActivityPubUpdateDTO convertPubUpdateDTO(CityActivityItemVO cityActivityItemVO) {
		if(cityActivityItemVO == null) {
			return null;
		}
		CityActivityPubUpdateDTO cityActivityPubUpdateDTO = new CityActivityPubUpdateDTO();
		cityActivityPubUpdateDTO.setCityActivity(convertUpdateDTO(cityActivityItemVO.getCityActivityVO()));
		cityActivityPubUpdateDTO.setItem(convertItemUpdateDTO(cityActivityItemVO.getItemVO()));
		setItemSkuDOList(cityActivityPubUpdateDTO, cityActivityItemVO.getItemVO());
		return cityActivityPubUpdateDTO;
	}

	public static CityActivityUpdateDTO convertUpdateDTO(CityActivityVO cityActivityVO) {
		if(cityActivityVO == null) {
			return null;
		}
		CityActivityUpdateDTO cityActivityUpdateDTO = new CityActivityUpdateDTO();
		BeanUtils.copyProperties(cityActivityVO, cityActivityUpdateDTO);
		return cityActivityUpdateDTO;
	}

	public static ItemPubUpdateDTO convertItemUpdateDTO(ItemVO itemVO) {
		if(itemVO == null) {
			return null;
		}
		ItemPubUpdateDTO itemPubUpdateDTO = new ItemPubUpdateDTO();

		BeanUtils.copyProperties(itemVO, itemPubUpdateDTO);
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

}
