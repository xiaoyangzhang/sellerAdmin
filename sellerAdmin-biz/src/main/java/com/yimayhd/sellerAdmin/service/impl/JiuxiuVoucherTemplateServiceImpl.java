package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.query.JiuxiuVoucherListQuery;
import com.yimayhd.sellerAdmin.model.vo.VoucherTemplateVO;
import com.yimayhd.sellerAdmin.repo.JixiuVoucherRepo;
import com.yimayhd.sellerAdmin.repo.MerchantApplyRepo;
import com.yimayhd.sellerAdmin.service.JiuxiuVoucherTemplateService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.voucher.client.domain.VoucherTemplateDO;
import com.yimayhd.voucher.client.enums.VoucherStatus;
import com.yimayhd.voucher.client.param.VoucherDTO;
import com.yimayhd.voucher.client.param.VoucherTemplateDeleteDTO;
import com.yimayhd.voucher.client.query.VoucherTemplateQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.voucher.client.result.VcResultSupport;
import com.yimayhd.voucher.client.service.VoucherClientService;

/**
 * 
 */
public class JiuxiuVoucherTemplateServiceImpl implements JiuxiuVoucherTemplateService {
	
    @Autowired
    private VoucherClientService voucherClientServiceRef;
    @Autowired
    private MerchantApplyRepo merchantApplyRepo;
    @Autowired 
    private JixiuVoucherRepo jixiuVoucherRepo;
    
    @Override
    public PageVO<VoucherTemplateVO> getList(JiuxiuVoucherListQuery voucherListQuery) throws Exception {
    	
    	VoucherTemplateQuery voucherTemplateQuery = new VoucherTemplateQuery();
    	voucherTemplateQuery.setDomain(Constant.DOMAIN_JIUXIU);
    	voucherTemplateQuery.setStatus(voucherListQuery.getStatus());
		voucherTemplateQuery.setPutStatus(voucherListQuery.getPutStatus());
		voucherTemplateQuery.setUseStatus(voucherListQuery.getUseStatus());
		voucherTemplateQuery.setPageNo(voucherListQuery.getPageNo());
		voucherTemplateQuery.setPageSize(voucherListQuery.getPageSize());
		voucherTemplateQuery.setUserId(voucherListQuery.getUserId());
		voucherTemplateQuery.setNeedCount(true);
		if(null!=voucherListQuery.getIds() && voucherListQuery.getIds().size()>0){
			voucherTemplateQuery.setIds(voucherListQuery.getIds());
		}
   	 
        VcBasePageResult<VoucherTemplateDO> result = jixiuVoucherRepo.queryVoucherTemplates(voucherTemplateQuery);
        List<VoucherTemplateVO> voucherTemplateVOs = Lists.newArrayList();
        PageVO<VoucherTemplateVO> pageVO = null;
        if (result != null && result.isSuccess() && !CollectionUtils.isEmpty(result.getList())){
            for (VoucherTemplateDO voucherTemplateDO : result.getList()) {
                VoucherTemplateVO voucherVO = new VoucherTemplateVO();
                BeanUtils.copyProperties(voucherTemplateDO, voucherVO);
                if(voucherVO.getStatus() == VoucherStatus.INIT.getStatus()){
                	voucherVO.setVoucherStatus(VoucherStatus.INIT.name());
                }else if(voucherVO.getStatus() == VoucherStatus.ACTIVE.getStatus()){
                	voucherVO.setVoucherStatus(VoucherStatus.ACTIVE.name());
                }else if(voucherVO.getStatus() == VoucherStatus.INACTIVE.getStatus()){
                	voucherVO.setVoucherStatus(VoucherStatus.INACTIVE.name());
                }
                voucherVO.setRequirement_((double)voucherTemplateDO.getRequirement()/100);
                voucherVO.setValue_((double)voucherTemplateDO.getValue()/100);
                voucherTemplateVOs.add(voucherVO);
            }
            pageVO = new PageVO<VoucherTemplateVO>(voucherListQuery.getPageNo(), voucherListQuery.getPageSize(),
                                                   result.getTotalCount(), voucherTemplateVOs);
        }else{
            pageVO = new PageVO<VoucherTemplateVO>(voucherListQuery.getPageNo(), voucherListQuery.getPageSize(),
                                                   0, voucherTemplateVOs);
        }

        return pageVO;
    }

    @Override
    public WebResultSupport modify(VoucherTemplateVO entity,String edtType) throws Exception {
    	WebResultSupport webResult = new WebResultSupport();
    	if(!edtType.equals("ACTIVE")){
    		//判断入参是否正确
    		webResult = chargeParam(entity, webResult);
    		
    		entity.setTitle(entity.getTitle().trim());
        	entity.setRequirement(Math.round(entity.getRequirement_()*100));
        	entity.setValue(Math.round(entity.getValue_()*100));
        	entity.setPutStartTime(entity.getPutStartTime());
        	entity.setStartTime(entity.getStartTime());
        	entity.setEndTime(DateUtil.formatMaxTimeForDate(entity.getEndTime()));
        	entity.setVoucherCount(entity.getVoucherCount());
        	
    	}
    	if(null!=webResult && !webResult.isSuccess()){
    		return webResult;
    	}
    	entity.setDomain(Constant.DOMAIN_JIUXIU);
    	entity.setPutEndTime(DateUtil.formatMaxTimeForDate(entity.getPutEndTime()));
    	entity.setTotalNum(entity.getTotalNum());
    	
    	VcBaseResult<Boolean> result = jixiuVoucherRepo.updateVoucherTemplate(entity);
    	 if(null==result || !result.isSuccess()){
         	webResult.setWebReturnCode(WebReturnCode.VOUVHER_EDIT_ERROR);
     		return webResult;
         }
         return webResult;
    }

    @Override
    public  WebResultSupport add(VoucherTemplateVO entity) throws Exception {
    	WebResultSupport webResult = new WebResultSupport();
//    	BaseResult<MerchantDO> merchantResult = merchantApplyRepo.getMerchantBySellerId(entity.getUserId());
//    	if(null != merchantResult && null != merchantResult.getValue()){
    		entity.setEntityId(entity.getUserId());
//    	}
    	//判断入参是否正确
    	webResult = chargeParam(entity, webResult);
    	if(null!=webResult && !webResult.isSuccess()){
    		return webResult;
    	}
    	entity.setDomain(Constant.DOMAIN_JIUXIU);
    	entity.setTitle(entity.getTitle().trim());
    	entity.setRequirement(Math.round(entity.getRequirement_() * 100));
    	entity.setValue(Math.round(entity.getValue_() * 100));
    	entity.setPutStartTime(entity.getPutStartTime());
    	entity.setPutEndTime(DateUtil.formatMaxTimeForDate(entity.getPutEndTime()));
    	entity.setStartTime(entity.getStartTime());
    	entity.setEndTime(DateUtil.formatMaxTimeForDate(entity.getEndTime()));
    	entity.setVoucherCount(entity.getVoucherCount());
    	entity.setTotalNum(entity.getTotalNum());
    	
        VcBaseResult<Long> result = jixiuVoucherRepo.publishVoucherTemplate(entity);
        if(null==result || !result.isSuccess()){
        	webResult.setWebReturnCode(WebReturnCode.VOUVHER_ADD_ERROR);
    		return webResult;
        }
        return webResult;
    }
    
	@Override
	public WebResultSupport deleteVoucherTemplate(VoucherTemplateVO voucherTemplateVO) {
		WebResultSupport webResult = new WebResultSupport();
		VoucherTemplateDeleteDTO dto = new VoucherTemplateDeleteDTO();
		dto.setDomain(Constant.DOMAIN_JIUXIU);
		dto.setTemplateId(voucherTemplateVO.getId());
		dto.setUserId(voucherTemplateVO.getUserId());
		VcBaseResult<?> result = jixiuVoucherRepo.deleteVoucherTemplate(dto);
		if(null==result || !result.isSuccess()){
        	webResult.setWebReturnCode(WebReturnCode.VOUVHER_DEL_ERROR);
    		return webResult;
        }
        return webResult;
	}
	
	@Override
	public WebResultSupport enableVoucher(VoucherTemplateVO voucherTemplateVO) {
		WebResultSupport webResult = new WebResultSupport();
		VoucherDTO dto = new VoucherDTO();
		dto.setDomain(Constant.DOMAIN_JIUXIU);
		dto.setTemplateId(voucherTemplateVO.getId());
		dto.setUserId(voucherTemplateVO.getUserId());
		VcResultSupport result = jixiuVoucherRepo.enableVoucher(dto);
		if(null==result || !result.isSuccess()){
        	webResult.setWebReturnCode(WebReturnCode.VOUVHER_PUT_ERROR);
    		return webResult;
        }
        return webResult;
	}
	
	@Override
	public WebResultSupport disableVoucher(VoucherTemplateVO voucherTemplateVO) {
		WebResultSupport webResult = new WebResultSupport();
		VoucherDTO dto = new VoucherDTO();
		dto.setDomain(Constant.DOMAIN_JIUXIU);
		dto.setTemplateId(voucherTemplateVO.getId());
		dto.setUserId(voucherTemplateVO.getUserId());
		VcResultSupport result = jixiuVoucherRepo.disableVoucher(dto);
		if(null==result || !result.isSuccess()){
        	webResult.setWebReturnCode(WebReturnCode.VOUVHER_GET_ERROR);
    		return webResult;
        }
        return webResult;
	}
    
    private  WebResultSupport chargeParam(VoucherTemplateVO entity,WebResultSupport webResult) throws Exception{
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

    @Override
    public VoucherTemplateVO getById(long id) throws Exception {
        VoucherTemplateVO voucherTemplateVO = new VoucherTemplateVO();
        BeanUtils.copyProperties(voucherClientServiceRef.getVoucherTemplateById(id), voucherTemplateVO);
        return voucherTemplateVO;
    }

	


}
