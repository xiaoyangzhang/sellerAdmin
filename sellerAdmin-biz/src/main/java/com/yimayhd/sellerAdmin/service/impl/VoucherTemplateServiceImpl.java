package com.yimayhd.sellerAdmin.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.query.VoucherListQuery;
import com.yimayhd.sellerAdmin.model.vo.VoucherTemplateVO;
import com.yimayhd.sellerAdmin.service.VoucherTemplateService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.voucher.client.domain.VoucherTemplateDO;
import com.yimayhd.voucher.client.query.VoucherTemplateQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.voucher.client.service.VoucherClientService;

/**
 * Created by czf on 2016/1/11.
 */
public class VoucherTemplateServiceImpl implements VoucherTemplateService {

    @Autowired
    private VoucherClientService voucherClientServiceRef;

    @Override
    public PageVO<VoucherTemplateVO> getList(VoucherListQuery voucherListQuery) throws Exception {
        VoucherTemplateQuery voucherTemplateQuery = new VoucherTemplateQuery();
        if (StringUtils.isNotEmpty(voucherListQuery.getEndDate())){
            Date endDate = DateUtil.formatMaxTimeForDate(voucherListQuery.getEndDate());
            voucherTemplateQuery.setEndTime(DateUtil.date2String(endDate));
        }
        voucherTemplateQuery.setStartTime(voucherListQuery.getBeginDate());
        voucherTemplateQuery.setTitle(voucherListQuery.getTitle());
        voucherTemplateQuery.setStatus(voucherListQuery.getStatus());
        voucherTemplateQuery.setPageSize(voucherListQuery.getPageSize());
        voucherTemplateQuery.setPageNo(voucherListQuery.getPageNo());
        voucherTemplateQuery.setNeedCount(true);

        VcBasePageResult<VoucherTemplateDO> result = voucherClientServiceRef.queryVoucherTemplates(voucherTemplateQuery);
        List<VoucherTemplateVO> voucherTemplateVOs = Lists.newArrayList();
        PageVO<VoucherTemplateVO> pageVO = null;
        if (result != null && result.isSuccess() && !CollectionUtils.isEmpty(result.getList())){
            for (VoucherTemplateDO voucherTemplateDO : result.getList()) {
                VoucherTemplateVO voucherVO = new VoucherTemplateVO();
                BeanUtils.copyProperties(voucherTemplateDO, voucherVO);
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
    public void modify(VoucherTemplateVO entity) throws Exception {
        voucherClientServiceRef.updateVoucherTemplate(entity);
    }

    @Override
    public boolean add(VoucherTemplateVO entity) throws Exception {
        VcBaseResult<Long> result = voucherClientServiceRef.publishVoucherTemplate(entity);
        if(result.isSuccess()){
        	return true;
        }else{
        	return false;
        }
    }

    @Override
    public VoucherTemplateVO getById(long id) throws Exception {
        VoucherTemplateVO voucherTemplateVO = new VoucherTemplateVO();
        BeanUtils.copyProperties(voucherClientServiceRef.getVoucherTemplateById(id), voucherTemplateVO);
        return voucherTemplateVO;
    }
}
