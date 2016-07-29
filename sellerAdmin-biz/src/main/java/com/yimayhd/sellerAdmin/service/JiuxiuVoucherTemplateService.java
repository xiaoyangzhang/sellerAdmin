package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.model.query.JiuxiuVoucherListQuery;
import com.yimayhd.sellerAdmin.model.query.VoucherListQuery;
import com.yimayhd.sellerAdmin.model.vo.VoucherTemplateVO;
import com.yimayhd.voucher.client.result.VcBaseResult;

/**
 * 
 */
public interface JiuxiuVoucherTemplateService {
    /**
     * 根据ID查询
     *
     * @param voucherListQuery
     *            查询条件
     * @throws Exception
     */
	PageVO<VoucherTemplateVO> getList(JiuxiuVoucherListQuery voucherListQuery)
			throws Exception;

    /**
     * 修改优惠券模板
     *
     * @param entity
     *            需要修改的字段和主键
     * @return 
     * @throws Exception
     */
    WebResultSupport modify(VoucherTemplateVO entity,String edtType) throws Exception;

    /**
     * 添加优惠券模板
     *
     * @return 优惠券模板
     * @param entity
     *            数据实体
     * @throws Exception
     */
     WebResultSupport add(VoucherTemplateVO entity) throws Exception;

    /**
     * 根据主键获取优惠券模板
     *
     * @param id
     *            主键long类型的
     * @throws Exception
     */
    VoucherTemplateVO getById(long id) throws Exception;
    /**
     * 删除优惠劵
     * @param voucherTemplateVO
     * @return
     */
	WebResultSupport deleteVoucherTemplate(VoucherTemplateVO voucherTemplateVO);
	/**
	 * 上架优惠劵
	 * @param voucherTemplateVO
	 * @return
	 */
	WebResultSupport enableVoucher(VoucherTemplateVO voucherTemplateVO);
	/**
	 * 下架优惠劵
	 * @param voucherTemplateVO
	 * @return
	 */
	WebResultSupport disableVoucher(VoucherTemplateVO voucherTemplateVO);

	
}
