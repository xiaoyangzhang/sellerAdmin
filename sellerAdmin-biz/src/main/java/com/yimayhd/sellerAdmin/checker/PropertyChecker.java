package com.yimayhd.sellerAdmin.checker;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;

/**
 * Property checker
 * 
 * @author yebin
 *
 */
public class PropertyChecker {
	private static final Logger log = LoggerFactory.getLogger(PropertyChecker.class);

	public static WebCheckResult checkProperty(long pId, int pType, String pTxt) {
		String temp = "Property验证失败: pId={},pType={},pTxt={}";
		if (pId <= 0) {
			log.error(temp, pId, pType, pTxt);
			return WebCheckResult.error("无效PropertyID");
		}
		if (pType <= 0) {
			log.error(temp, pId, pType, pTxt);
			return WebCheckResult.error("无效Property类型");
		}
		if (StringUtils.isBlank(pTxt)) {
			log.error(temp, pId, pType, pTxt);
			return WebCheckResult.error("Property名称不能为空");
		}
		return WebCheckResult.success();
	}
}
