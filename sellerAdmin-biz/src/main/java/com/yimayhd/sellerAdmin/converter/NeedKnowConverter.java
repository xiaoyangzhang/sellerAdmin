package com.yimayhd.sellerAdmin.converter;

import com.yimayhd.fhtd.utils.PictureUtil;
import com.yimayhd.ic.client.model.domain.CityActivityDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.param.item.ItemPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.param.item.ItemSkuPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityPubUpdateDTO;
import com.yimayhd.ic.client.model.param.item.cityactivity.CityActivityUpdateDTO;
import com.yimayhd.ic.client.model.result.item.CityActivityResult;
import com.yimayhd.sellerAdmin.model.*;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowItemVo;
import com.yimayhd.sellerAdmin.model.line.nk.NeedKnowVO;
import com.yimayhd.sellerAdmin.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author weiwenliang@20160406
 *
 */
public class NeedKnowConverter {

	public static NeedKnowVO convertNeedKnowVO(NeedKnow needKnow) {
		if (needKnow == null) {
			return null;
		}
		NeedKnowVO needKnowVO = new NeedKnowVO();
		List<NeedKnowItemVo> needKnowItems = new ArrayList<NeedKnowItemVo>();
		List<TextItem> frontNeedKnows = needKnow.getFrontNeedKnow();
		if (CollectionUtils.isNotEmpty(frontNeedKnows)) {
			for (TextItem frontNeedKnow : frontNeedKnows) {
				NeedKnowItemVo needKnowItemVo = convertNeedKnowItemVo(frontNeedKnow);
				if (needKnowItemVo != null) {
					needKnowItems.add(needKnowItemVo);
				}
			}
		}
		needKnowVO.setNeedKnowItems(needKnowItems);
		return needKnowVO;
	}

	public static NeedKnowItemVo convertNeedKnowItemVo(TextItem frontNeedKnow) {
		if (frontNeedKnow == null) {
			return null;
		}
		NeedKnowItemVo needKnowItemVo = new NeedKnowItemVo();
		needKnowItemVo.setTitle(frontNeedKnow.getTitle());
		needKnowItemVo.setContent(frontNeedKnow.getContent());
		return needKnowItemVo;
	}

	public static NeedKnow convertNeedKnow(NeedKnowVO needKnowVO) {
		if (needKnowVO == null) {
			return null;
		}
		NeedKnow needKnow = new NeedKnow();
		List<TextItem> frontNeedKnows = new ArrayList<TextItem>();
		List<NeedKnowItemVo> needKnowItems = needKnowVO.getNeedKnowItems();
		if (CollectionUtils.isNotEmpty(needKnowItems)) {
			for (NeedKnowItemVo needKnowItem : needKnowItems) {
				TextItem textItem = new TextItem();
				textItem.setTitle(needKnowItem.getTitle());
				textItem.setContent(needKnowItem.getContent());
				frontNeedKnows.add(textItem);
			}
		}
		needKnow.setFrontNeedKnow(frontNeedKnows);
		return needKnow;
	}
}
