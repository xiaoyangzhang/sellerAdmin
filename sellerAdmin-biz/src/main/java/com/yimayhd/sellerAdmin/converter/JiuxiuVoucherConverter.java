package com.yimayhd.sellerAdmin.converter;

import org.apache.commons.lang.StringUtils;

import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.vo.VoucherTemplateVO;
import com.yimayhd.sellerAdmin.util.DateUtil;

public class JiuxiuVoucherConverter {
	
	 public static  WebResultSupport chargeParam(VoucherTemplateVO entity,WebResultSupport webResult) throws Exception{
	    	if(StringUtils.isEmpty(entity.getTitle()) || entity.getTitle().trim().length() > Constant.VOUCHET_TITILE_LIMIT){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_TITLE_ERROR);
	    		return webResult;
	    	}
	    	if(entity.getRequirement_() <= 0 || entity.getValue_() <= 0 || entity.getRequirement_() <= entity.getValue_() ){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_REQUERMENT_ERROR);
	    		return webResult;
	    	}
	    	if(entity.getVoucherCount() <= 0 || entity.getVoucherCount() > Constant.VOUCHET_GET_MAX_LIMIT){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_COUNT_ERROR);
	    		return webResult;
	    	}
	    	if(entity.getTotalNum() <= 0 || entity.getTotalNum() > Constant.VOUCHET_PUT_MAX_LIMIT){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_TOTAL_NUM_ERROR);
	    		return webResult;
	    	}
	    	if(null == entity.getPutStartTime() || null == entity.getPutEndTime()){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_PUT_TIME_ERROR);
	    		return webResult;
	    	}
	    	long dateCharge = DateUtil.daySubtraction(entity.getPutStartTime(),entity.getPutEndTime());
	    	if(dateCharge > Constant.VOUCHET_TIME_LIMIT || dateCharge < 0){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_PUT_TIME_ERROR);
	    		return webResult;
	    	}
	    	if(null == entity.getStartTime() || null == entity.getEndTime()){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_PUT_TIME_ERROR);
	    		return webResult;
	    	}
	    	long dateCharge2 = DateUtil.daySubtraction(entity.getStartTime(),entity.getEndTime());
	    	if(dateCharge2 > Constant.VOUCHET_TIME_LIMIT || dateCharge < 0){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_USE_TIME_ERROR);
	    		return webResult;
	    	}
	    	long dateCharge3 = DateUtil.daySubtraction(entity.getPutStartTime(),entity.getStartTime());
	    	long dateCharge4 = DateUtil.daySubtraction(entity.getPutEndTime(),entity.getEndTime());
	    	if(dateCharge3 < 0 || dateCharge4 <0){
	    		webResult.setWebReturnCode(WebReturnCode.VOUVHER_USE_PUT_TIME_ERROR);
	    		return webResult;
	    	}
			return webResult;
	    }
}
