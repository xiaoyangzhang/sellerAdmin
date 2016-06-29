package com.yimayhd.sellerAdmin.checker;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.RestaurantVO;

/**
 * 餐厅 checker
 * 
 * @author yebin
 *
 */
public class RestaurantChecker {
	private static final Logger log = LoggerFactory.getLogger(RestaurantChecker.class);

	public static WebCheckResult checkRestaurantVOForSave(RestaurantVO restaurantVO) {
		String temp = "餐厅信息验证失败: {}";
		if (StringUtils.isBlank(restaurantVO.getName())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("餐厅名称不能为空");
		} else if (restaurantVO.getName().length() > 20) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("餐厅名称长度不能超过20个字");
		}
		if (restaurantVO.getLocationProvinceId() <= 0) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("无效的省信息");
		}
		if (StringUtils.isBlank(restaurantVO.getLocationProvinceName())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("省名称不能为空");
		}
		if (restaurantVO.getLocationCityId() <= 0) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("无效的市信息");
		}
		if (StringUtils.isBlank(restaurantVO.getLocationCityName())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("市名称不能为空");
		}
		if (restaurantVO.getLocationTownId() <= 0) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("无效的区信息");
		}
		if (StringUtils.isBlank(restaurantVO.getLocationTownName())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("区名称不能为空");
		}
		if (StringUtils.isBlank(restaurantVO.getLocationText())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("详细地址不能为空");
		}
		double locationX = restaurantVO.getLocationX();
		if (locationX < -180 || locationX > 180) {
			return WebCheckResult.error("无效经度");
		}
		double locationY = restaurantVO.getLocationY();
		if (locationY < -90 || locationY > 90) {
			return WebCheckResult.error("无效纬度");
		}
		if (StringUtils.isBlank(restaurantVO.getOneword())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("餐厅简介不能为空");
		} else if (restaurantVO.getOneword().length() > 100) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("餐厅简介不能超过100字");
		}
		if (StringUtils.isBlank(restaurantVO.getDescription())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("特色描述不能为空");
		} else if (restaurantVO.getDescription().length() > 500) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("特色描述不能超过500字");
		}
		if (StringUtils.isBlank(restaurantVO.getLogoUrl())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("列表页展示图不能为空");
		}
		if (StringUtils.isBlank(restaurantVO.getCoverPics())) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("详情页展示图不能为空");
		}
		return WebCheckResult.success();
	}

	public static WebCheckResult checkRestaurantVOForUpdate(RestaurantVO restaurantVO) {
		String temp = "餐厅信息验证失败: {}";
		if (restaurantVO.getId() <= 0) {
			log.error(temp, JSON.toJSONString(restaurantVO));
			return WebCheckResult.error("无效标签ID");
		}
		return checkRestaurantVOForSave(restaurantVO);
	}
}
