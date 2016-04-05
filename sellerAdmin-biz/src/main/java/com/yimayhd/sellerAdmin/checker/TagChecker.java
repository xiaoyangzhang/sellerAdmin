package com.yimayhd.sellerAdmin.checker;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.LiveTagVO;

/**
 * 标签checker
 * 
 * @author yebin
 *
 */
public class TagChecker {
	private static final Logger log = LoggerFactory.getLogger(TagChecker.class);

	public static WebCheckResult checkLiveTagVOForSave(LiveTagVO tag) {
		String temp = "标签验证失败: {}";
		if (StringUtils.isBlank(tag.getName())) {
			log.error(temp, JSON.toJSONString(tag));
			return WebCheckResult.error("标签名称不能为空");
		}
		if (tag.getScore() <= 0) {
			log.error(temp, JSON.toJSONString(tag));
			return WebCheckResult.error("无效标签序号");
		}
		if (StringUtils.isBlank(tag.getName())) {
			log.error(temp, JSON.toJSONString(tag));
			return WebCheckResult.error("标签名称不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkLiveTagVOForUpdate(LiveTagVO tag) {
		String temp = "标签验证失败: {}";
		if (tag.getId() <= 0) {
			log.error(temp, JSON.toJSONString(tag));
			return WebCheckResult.error("无效标签ID");
		}
		return checkLiveTagVOForSave(tag);
	}
}
