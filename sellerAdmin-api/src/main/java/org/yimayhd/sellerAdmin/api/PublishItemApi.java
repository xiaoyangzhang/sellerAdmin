package org.yimayhd.sellerAdmin.api;

import java.util.List;

import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.entity.ConsultCategoryInfo;
import org.yimayhd.sellerAdmin.entity.ItemDetail;
import org.yimayhd.sellerAdmin.entity.ItemListPage;
import org.yimayhd.sellerAdmin.entity.ItemManagement;
import org.yimayhd.sellerAdmin.entity.ItemProperty;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;


import org.yimayhd.sellerAdmin.query.ItemQueryParam;
import org.yimayhd.sellerAdmin.result.ItemApiResult;

import net.pocrd.annotation.ApiAutowired;
import net.pocrd.annotation.ApiGroup;
import net.pocrd.annotation.ApiParameter;
import net.pocrd.annotation.DesignedErrorCode;
import net.pocrd.annotation.HttpApi;
import net.pocrd.define.CommonParameter;
import net.pocrd.define.SecurityType;


@ApiGroup(name = "sellerAdmin", minCode = 23000000, maxCode = 25000000, codeDefine = SellerReturnCode.class, owner = "zhangxy")
public interface PublishItemApi {

	@HttpApi(name = "sellerAdmin.publishService", desc = "发布商品", security = SecurityType.UserLogin, owner = "zhangxy")
    @DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
    public boolean publishService(
            @ApiAutowired(CommonParameter.applicationId) int appId,
            @ApiAutowired(CommonParameter.domainId) int domainId,
            @ApiAutowired(CommonParameter.deviceId) long deviceId,
            @ApiAutowired(CommonParameter.userId) long userId,
            @ApiAutowired(CommonParameter.versionCode) int versionCode,
            @ApiParameter(required = true, name = "publishServiceDO", desc = "发布的服务信息") PublishServiceDO publishServiceDO
    );
	
	@HttpApi(name = "sellerAdmin.getItemList", desc = "获取商品管理列表", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
	public ItemApiResult getItemList(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			@ApiParameter(required = true, name = "itemQueryParam", desc = "商品查询参数") ItemQueryParam itemQueryParam
			);
	
	@HttpApi(name = "sellerAdmin.getItemDetailInfo", desc = "商品查询参数", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
	public ItemApiResult getItemDetailInfo(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			@ApiParameter(required = true, name = "itemQueryParam", desc = "商品查询参数") ItemQueryParam itemQueryParam
			);
	
	@HttpApi(name = "sellerAdmin.checkWhiteList", desc = "发布服务白名单", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
	public boolean checkWhiteList(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode
			
			);
	
	@HttpApi(name = "sellerAdmin.updateState", desc = "更新商品状态", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C,SellerReturnCode.ON_SALE_ERROR_C,SellerReturnCode.ON_SALE_SUCCESS_C,
						SellerReturnCode.OFF_SALE_ERROR_C,SellerReturnCode.OFF_SALE_SUCCESS_C,SellerReturnCode.DELETE_ERROR_C,
						SellerReturnCode.DELETE_SUCCESS_C})
	public boolean updateState(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			@ApiParameter(required = true, name = "itemQueryParam", desc = "商品查询参数") ItemQueryParam itemQueryParam
			
			);
	
	@HttpApi(name = "sellerAdmin.getPublishItemInfo", desc = "查询商品", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
	public PublishServiceDO getPublishItemInfo(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			/*@ApiParameter(required = true,name="itemId",desc="商品id")long itemId,
			@ApiParameter(required = true,name="categoryId",desc="类目id")long categoryId*/
			@ApiParameter(required = true, name = "itemQueryParam", desc = "商品查询参数") ItemQueryParam itemQueryParam
			
			);
	
	@HttpApi(name = "sellerAdmin.getConsultItemProperties", desc = "获取咨询商品属性", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C,SellerReturnCode.QUERY_PROPERTY_ERROR_C})
	public ConsultCategoryInfo getConsultItemProperties(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode
			
			);
	
	
}
