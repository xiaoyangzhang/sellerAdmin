package org.yimayhd.sellerAdmin.api;

import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.entity.ItemDetail;
import org.yimayhd.sellerAdmin.entity.ItemManagement;
import org.yimayhd.sellerAdmin.entity.PublishServiceDO;


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
	
	@HttpApi(name = "sellerAdmin.getGoodsManagementInfo", desc = "获取商品管理页面信息", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
	public ItemApiResult getGoodsManagementInfo(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			@ApiParameter(required = true, name = "goodsManagement", desc = "商品管理页面信息") ItemManagement goodsManagement
			);
	
	@HttpApi(name = "sellerAdmin.getGoodsDetailInfo", desc = "获取商品详情页面信息", security = SecurityType.UserLogin, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
	public ItemApiResult getGoodsDetailInfo(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			@ApiParameter(required = true, name = "goodsDetail", desc = "商品详情页面信息") ItemDetail goodsDetail
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
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
	public boolean updateState(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			@ApiParameter(required = true,name="goodsManagement",desc="更新商品状态")ItemManagement goodsManagement
			
			);
	
	
}
