package com.yimayhd.sellerAdmin.repo;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.sellerAdmin.model.vo.VoucherTemplateVO;
import com.yimayhd.voucher.client.domain.VoucherTemplateDO;
import com.yimayhd.voucher.client.param.VoucherDTO;
import com.yimayhd.voucher.client.param.VoucherTemplateDeleteDTO;
import com.yimayhd.voucher.client.query.VoucherTemplateQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.voucher.client.result.VcResultSupport;
import com.yimayhd.voucher.client.service.VoucherClientService;

public class JixiuVoucherRepo {
	@Autowired
    private VoucherClientService voucherClientServiceRef;
	
	/**
	 * 新增优惠劵
	 * @param entity
	 * @return
	 */
	@MethodLogger
	public VcBaseResult<Long> publishVoucherTemplate(VoucherTemplateVO entity){
		return voucherClientServiceRef.publishVoucherTemplate(entity);
	}
	
	/**
	 * 修改优惠劵
	 * @param entity
	 * @return
	 */
	@MethodLogger
	public VcBaseResult<Boolean> updateVoucherTemplate(VoucherTemplateVO entity){
		return voucherClientServiceRef.updateVoucherTemplate(entity);
	}
	
	/**
	 * 查询优惠劵
	 * @param voucherTemplateQuery
	 * @return
	 */
	@MethodLogger
	public VcBasePageResult<VoucherTemplateDO> queryVoucherTemplates(VoucherTemplateQuery voucherTemplateQuery){
		return voucherClientServiceRef.queryVoucherTemplates(voucherTemplateQuery);
	}
	
	/**
	 * 删除优惠劵
	 * @param dto
	 * @return
	 */
	@MethodLogger
	public VcBaseResult<?> deleteVoucherTemplate(VoucherTemplateDeleteDTO dto){
		return voucherClientServiceRef.deleteVoucherTemplate(dto);
	}
	/**
	 * 上架优惠劵
	 * @param dto
	 * @return
	 */
	@MethodLogger
	public VcResultSupport enableVoucher(VoucherDTO dto){
		return voucherClientServiceRef.enableVoucher(dto);
	}
	
	/**
	 * 下架优惠劵
	 * @param dto
	 * @return
	 */
	@MethodLogger
	public VcResultSupport disableVoucher(VoucherDTO dto){
		return voucherClientServiceRef.disableVoucher(dto);
	}
}
