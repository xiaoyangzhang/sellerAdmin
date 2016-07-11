package com.yimayhd.sellerAdmin.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;







import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.query.JiuxiuVoucherListQuery;
import com.yimayhd.sellerAdmin.model.vo.VoucherTemplateVO;
import com.yimayhd.sellerAdmin.service.JiuxiuVoucherTemplateService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.voucher.client.enums.EntityType;
import com.yimayhd.voucher.client.enums.IssueType;
import com.yimayhd.voucher.client.enums.VoucherTemplateStatus;
import com.yimayhd.voucher.client.enums.VoucherTemplateUseStatus;
import com.yimayhd.voucher.client.enums.VoucherType;

/**
 * 优惠券管理-九休
 * 
 */
@Controller
@RequestMapping("/jiuxiu/voucher")
public class JiuxiuVoucherController extends BaseController {

    @Autowired
    private JiuxiuVoucherTemplateService jiuxiuVoucherTemplateService;

    @Autowired
    private SessionManager sessionManager;
    
    @Autowired
	private CacheManager cacheManager ;
    /**
     * 优惠券列表
     *
     * @return 优惠券列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, JiuxiuVoucherListQuery voucherListQuery) throws Exception {
    	voucherListQuery.setUserId(sessionManager.getUserId());
        PageVO<VoucherTemplateVO> pageVO = jiuxiuVoucherTemplateService.getList(voucherListQuery);
        model.addAttribute("voucherListQuery",voucherListQuery);
        model.addAttribute("voucherTemplateList",pageVO.getResultList());
        model.addAttribute("pageVo",pageVO);
        return "/system/voucher/list";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model,boolean isEdit) throws Exception {
    	model.addAttribute("UUID",UUID.randomUUID().toString());
        return "/system/voucher/edit";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public WebResultSupport add(VoucherTemplateVO voucherTemplateVO,String uuid) throws Exception {
    	WebResultSupport result = new WebResultSupport();
    	String key = Constant.APP+"_voucher_"+sessionManager.getUserId()+uuid;
		boolean rs = cacheManager.addToTair(key, true , 2, 24*60*60);
//		if(rs){
			UserDO userDO = sessionManager.getUser();
			voucherTemplateVO.setDomain(Constant.DOMAIN_JIUXIU);
			voucherTemplateVO.setVoucherType(VoucherType.SUM_REDUCE.getType());
			voucherTemplateVO.setEntityType(EntityType.SHOP.getType());
			voucherTemplateVO.setUserId(userDO.getId());
			voucherTemplateVO.setIssueType(IssueType.GENERATE_BY_BINDING.getType());
			
			try {
				result = jiuxiuVoucherTemplateService.add(voucherTemplateVO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
				return result;
			}
//		}else{
//			result.setWebReturnCode(WebReturnCode.VOUVHER_ADD_REPET_ERROR);
//		}
        
        return result;
    }
    /**
     * 编辑优惠劵
     * @param model
     * @param voucherListQuery
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value = "/toEdit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id,String edtType) throws Exception {
    	JiuxiuVoucherListQuery voucherListQuery = new JiuxiuVoucherListQuery();
    	List<Integer> ids = new ArrayList<Integer>();
    	ids.add((int)id);
    	voucherListQuery.setIds(ids);
    	voucherListQuery.setUserId(sessionManager.getUserId());
        PageVO<VoucherTemplateVO> pageVO = jiuxiuVoucherTemplateService.getList(voucherListQuery);
        model.addAttribute("voucherTemplate",pageVO.getResultList().get(0));
        model.addAttribute("editType", "edit");
        model.addAttribute("edtType", edtType);
        return "/system/voucher/edit";
    }
    /**
     * 根据优惠券ID修改优惠券
     *
     * @return 优惠券
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    @ResponseBody
    public WebResultSupport edit(@PathVariable(value = "id") long id,VoucherTemplateVO voucherTemplateVO,String edtType) throws Exception {
    	WebResultSupport result = new WebResultSupport();
    	JiuxiuVoucherListQuery voucherListQuery = new JiuxiuVoucherListQuery();
    	List<Integer> ids = new ArrayList<Integer>();
    	ids.add((int)id);
    	voucherListQuery.setIds(ids);
    	voucherListQuery.setUserId(sessionManager.getUserId());
    	try {
	        PageVO<VoucherTemplateVO> pageVO = jiuxiuVoucherTemplateService.getList(voucherListQuery);
	        if(null!=pageVO && pageVO.getResultList().size()>0 && pageVO.getResultList().get(0).getSendNum() > voucherTemplateVO.getTotalNum()){
	        	result.setWebReturnCode(WebReturnCode.VOUVHER_PUT_SET_ERROR);
				return result;
	        }
	        
	        voucherTemplateVO.setId(id);
	        voucherTemplateVO.setUserId(sessionManager.getUserId());
        	result = jiuxiuVoucherTemplateService.modify(voucherTemplateVO,edtType);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
			return result;
		}
        
        return result;
    }
    /**
     * 删除优惠劵
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    @ResponseBody
    public WebResultSupport deleteVoucherTemplate(@PathVariable(value = "id") long id,VoucherTemplateVO voucherTemplateVO){
    	UserDO userDO = sessionManager.getUser();
    	voucherTemplateVO.setId(id);
    	voucherTemplateVO.setUserId(userDO.getId());
    	WebResultSupport result = new WebResultSupport();
        try {
         	result = jiuxiuVoucherTemplateService.deleteVoucherTemplate(voucherTemplateVO);
 		} catch (Exception e) {
 			log.error(e.getMessage(), e);
 			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
 			return result;
 		}
        return result;
    }
    
    /**
     * 上架优惠劵
     * @param id
     * @return
     */
    @RequestMapping(value = "/enableVoucher/{id}", method = RequestMethod.POST)
    @ResponseBody
    public WebResultSupport enableVoucher(@PathVariable(value = "id") long id,VoucherTemplateVO voucherTemplateVO){
    	UserDO userDO = sessionManager.getUser();
    	JiuxiuVoucherListQuery  jiuxiuVoucherListQuery = new JiuxiuVoucherListQuery();
    	jiuxiuVoucherListQuery.setPageSize(Integer.MAX_VALUE);
    	jiuxiuVoucherListQuery.setStatus(VoucherTemplateStatus.ACTIVE.getStatus());
    	jiuxiuVoucherListQuery.setUserId(userDO.getId());
    	WebResultSupport result = new WebResultSupport();
    	try {
	    	PageVO<VoucherTemplateVO> pageVO = jiuxiuVoucherTemplateService.getList(jiuxiuVoucherListQuery);
	    	if(null!=pageVO.getResultList() && pageVO.getResultList().size()>=5){
	    		result.setWebReturnCode(WebReturnCode.VOUVHER_LIMIT_ERROR);
	    		return result;
	    	}
	    	voucherTemplateVO.setId(id);
	    	voucherTemplateVO.setUserId(userDO.getId());
         	result = jiuxiuVoucherTemplateService.enableVoucher(voucherTemplateVO);
 		} catch (Exception e) {
 			log.error(e.getMessage(), e);
 			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
 			return result;
 		}
        return result;
    }
    
    /**
     * 下架优惠劵
     * @param id
     * @return
     */
    @RequestMapping(value = "/disableVoucher/{id}", method = RequestMethod.POST)
    @ResponseBody
    public WebResultSupport disableVoucher(@PathVariable(value = "id") long id,VoucherTemplateVO voucherTemplateVO){
    	UserDO userDO = sessionManager.getUser();
    	voucherTemplateVO.setId(id);
    	voucherTemplateVO.setUserId(userDO.getId());
    	WebResultSupport result = new WebResultSupport();
        try {
         	result = jiuxiuVoucherTemplateService.disableVoucher(voucherTemplateVO);
 		} catch (Exception e) {
 			log.error(e.getMessage(), e);
 			result.setWebReturnCode(WebReturnCode.REMOTE_CALL_FAILED);
 			return result;
 		}
        return result;
    }

    private Date getEndTime(Date endTime) throws ParseException {
        String str = DateUtil.date2StringByDay(endTime);
        str = str + " 23:59:59";
        return DateUtil.convertStringToDate(DateUtil.DATE_TIME_FORMAT,str);
    }

}
