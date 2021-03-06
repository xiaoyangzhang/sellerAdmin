package org.yimayhd.sellerAdmin.api;

import net.pocrd.annotation.ApiAutowired;
import net.pocrd.annotation.ApiGroup;
import net.pocrd.annotation.ApiParameter;
import net.pocrd.annotation.DesignedErrorCode;
import net.pocrd.annotation.HttpApi;
import net.pocrd.define.CommonParameter;
import net.pocrd.define.SecurityType;

import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.entity.merchant.HomePage;
import org.yimayhd.sellerAdmin.entity.merchant.MerchantDesc;
import org.yimayhd.sellerAdmin.entity.merchant.MerchantInfo;
import org.yimayhd.sellerAdmin.entity.merchant.Qualification;
import org.yimayhd.sellerAdmin.query.ItemQueryParam;

@ApiGroup(name = "sellerAdmin", minCode = 23000000, maxCode = 25000000, codeDefine = SellerReturnCode.class, owner = "zhangxy")
public interface MerchantInfoApi {

	/*@HttpApi(name = "sellerAdmin.queryHomePage", desc = "查询店铺主页信息", security = SecurityType.UserLogin, owner = "zhangxy")
    @DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C,SellerReturnCode.QUERY_MERCHANT_ERROR_C})
    public HomePage queryHomePage(
            @ApiAutowired(CommonParameter.applicationId) int appId,
            @ApiAutowired(CommonParameter.domainId) int domainId,
            @ApiAutowired(CommonParameter.deviceId) long deviceId,
            @ApiAutowired(CommonParameter.userId) long userId,
            @ApiAutowired(CommonParameter.versionCode) int versionCode,
            @ApiParameter(required = true, name = "sellerId", desc = "要查看的店铺的id") long sellerId
            
    );*/
	@HttpApi(name = "sellerAdmin.queryMerchantInfo", desc = "查询店铺信息", security = SecurityType.None, owner = "zhangxy")
	//FIXME @DesignedErrorCode申明的错误应该是调用方需要处理的错误，他们处理不了SellerReturnCode.QUERY_MERCHANT_ERROR_C
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C,SellerReturnCode.QUERY_MERCHANT_ERROR_C})
	public MerchantInfo queryMerchantInfo(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			 @ApiParameter(required = true, name = "sellerId", desc = "要查看的店铺的id") long sellerId
			
			);
	
	//FIXME 为什么和queryMerchantInfo分开？
	@HttpApi(name = "sellerAdmin.queryMerchantDesc", desc = "查询店铺简介信息", security = SecurityType.None, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C,SellerReturnCode.QUERY_MERCHANT_ERROR_C})
	public MerchantDesc queryMerchantDesc(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			 @ApiParameter(required = true, name = "sellerId", desc = "要查看的店铺的id") long sellerId
			
			);
	@HttpApi(name = "sellerAdmin.queryMerchantQualification", desc = "查询店铺资质信息", security = SecurityType.None, owner = "zhangxy")
	@DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C,SellerReturnCode.QUERY_MERCHANT_QUALIFICATION_ERROR_C})
	public Qualification queryMerchantQualification(
			@ApiAutowired(CommonParameter.applicationId) int appId,
			@ApiAutowired(CommonParameter.domainId) int domainId,
			@ApiAutowired(CommonParameter.deviceId) long deviceId,
			@ApiAutowired(CommonParameter.userId) long userId,
			@ApiAutowired(CommonParameter.versionCode) int versionCode,
			 @ApiParameter(required = true, name = "sellerId", desc = "要查看的店铺的id") long sellerId
			
			);
}
