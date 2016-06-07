package com.yimayhd.sellerAdmin.checker;

import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.ItemVO;

/**
 * Created by czf on 2016/4/7.
 */
public class BarterItemChecker {
	// 实物商品表单验证
	public static WebCheckResult BarterItemCheck(ItemVO itemVO) {
		if (itemVO.getTitle() == null)
			return WebCheckResult.error("商品名称不能为空");
		if (itemVO.getTitle().length() > 38)
			return WebCheckResult.error("商品名称不能超过38个字");
		if (itemVO.getCode() != null && itemVO.getCode().length() > 38)
			return WebCheckResult.error("商品编码不能超过38个字");
		return WebCheckResult.success();
	}
}
