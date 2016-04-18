package com.yimayhd.sellerAdmin.checker;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.enums.PictureTextItemType;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;

public class PictureTextChecker {
	public static WebCheckResult checkPictureText(PictureTextVO pictureTextVO) {
		if (pictureTextVO == null) {
			WebCheckResult.error("图文详情不能为空");
		}
		List<PictureTextItemVo> pictureTextItems = pictureTextVO.getPictureTextItems();
		if (CollectionUtils.isNotEmpty(pictureTextItems)) {
			for (PictureTextItemVo pictureTextItemVo : pictureTextItems) {
				WebCheckResult checkPictureTextItem = checkPictureTextItem(pictureTextItemVo);
				if (!checkPictureTextItem.isSuccess()) {
					return checkPictureTextItem;
				}
			}
		} else {
			WebCheckResult.error("至少需输入一段文字，或一张图片");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkPictureTextItem(PictureTextItemVo pictureTextItem) {
		if (pictureTextItem == null) {
			WebCheckResult.error("图文详情项不能为空");
		}
		String type = pictureTextItem.getType();
		PictureTextItemType pictureTextItemType = PictureTextItemType.findByName(type);
		if (pictureTextItemType == null) {
			WebCheckResult.error("错误图文详情项类型: " + type);
		}
		if (StringUtils.isBlank(pictureTextItem.getValue())) {
			WebCheckResult.error("图文详情项内容不能为空");
		}
		return WebCheckResult.success();
	}
}
