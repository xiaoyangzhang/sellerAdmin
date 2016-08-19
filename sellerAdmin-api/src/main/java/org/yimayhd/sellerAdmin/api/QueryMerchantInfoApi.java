package org.yimayhd.sellerAdmin.api;

import net.pocrd.annotation.ApiAutowired;
import net.pocrd.annotation.ApiGroup;
import net.pocrd.annotation.DesignedErrorCode;
import net.pocrd.annotation.HttpApi;
import net.pocrd.define.CommonParameter;
import net.pocrd.define.SecurityType;

import org.yimayhd.sellerAdmin.SellerReturnCode;
import org.yimayhd.sellerAdmin.entity.MerchantInfo;

@ApiGroup(name = "sellerAdmin", minCode = 23000000, maxCode = 25000000, codeDefine = SellerReturnCode.class, owner = "zhangxy")
public interface QueryMerchantInfoApi {

	@HttpApi(name = "sellerAdmin.queryMerchantInfo", desc = "查询店铺信息", security = SecurityType.UserLogin, owner = "zhangxy")
    @DesignedErrorCode({SellerReturnCode.PARAM_ERROR_C})
    public MerchantInfo queryMerchantInfo(
            @ApiAutowired(CommonParameter.applicationId) int appId,
            @ApiAutowired(CommonParameter.domainId) int domainId,
            @ApiAutowired(CommonParameter.deviceId) long deviceId,
            @ApiAutowired(CommonParameter.userId) long userId,
            @ApiAutowired(CommonParameter.versionCode) int versionCode
            
    );
}
