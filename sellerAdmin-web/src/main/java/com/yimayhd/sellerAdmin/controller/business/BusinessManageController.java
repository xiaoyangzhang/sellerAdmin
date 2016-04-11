package com.yimayhd.sellerAdmin.controller.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.membercenter.client.query.examine.ExaminePageQueryDTO;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
/**
 * 商家列表
 * 
 * @author zhangxy
 * 
 *
 */
@Controller
@RequestMapping("businessManage")
public class BusinessManageController extends BaseController {

	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ExamineDealService examineDealService;
	@Autowired
	private TalentBiz talentBiz;
	
	@RequestMapping(value="toBusinessList",method=RequestMethod.GET)
	public String toBusinessList(Model model,ExaminePageQueryDTO dto) {
		ExaminePageQueryDTO examinQueryDTO = new ExaminePageQueryDTO();
		examinQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
//		examinQueryDTO.setSellerId(sessionManager.getUserId());
//		examinQueryDTO.setType(type);
		//examinQueryDTO.setPageNo(Constant.);
		model.addAttribute("examInfoList", examineDealService.queryMerchantExamineByPage(examinQueryDTO));
		model.addAttribute("examInfoDTO", dto);
		model.addAttribute("talentBiz", talentBiz);
		return "system/businessManage/businesslist";
		
	}
}
