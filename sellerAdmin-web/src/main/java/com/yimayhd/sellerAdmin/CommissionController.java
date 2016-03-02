package com.yimayhd.sellerAdmin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commission.biz.CommissionBiz;
import com.yimayhd.commission.client.enums.Domain;
import com.yimayhd.commission.client.param.AmountObtainDTO;
import com.yimayhd.commission.client.result.comm.AmountDetailDTO;
import com.yimayhd.commission.client.result.comm.AmountTotalDTO;
import com.yimayhd.commission.convert.CommissionAmoutConvert;
import com.yimayhd.commission.model.param.ExtractDTO;
import com.yimayhd.commission.model.query.CommissionDetailQuery;
import com.yimayhd.commission.model.query.CommissionListQuery;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/commission")
public class CommissionController  extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CommissionController.class);

    @Autowired
    private CommissionBiz commissionBiz;

    /**
     * 根据会员ID查询会员提现记录
     * @param model
     * @param commissionDetailQuery
     * @return
     */
    @RequestMapping(value = "/queryCommissionDetail")
    public String queryCommissionDetail(Model model,CommissionDetailQuery commissionDetailQuery){

        try{

            long userId = commissionDetailQuery.getUserId();
            int pageNo = commissionDetailQuery.getPageNumber();
            int pageSize = commissionDetailQuery.getPageSize();

            PageVO<AmountDetailDTO> pageVO =  commissionBiz.queryExtractDetailByUserId(userId, pageNo, pageSize);
			model.addAttribute("totalAmount",0);

			if (pageVO != null) {

				List<AmountDetailDTO> commissionList =  pageVO.getItemList();
				model.addAttribute("commissionDetailQuery", commissionDetailQuery);
				model.addAttribute("pageVo",pageVO);
				model.addAttribute("commissionList",commissionList);

				if(!CollectionUtils.isEmpty(commissionList)){
					AmountDetailDTO amountDetailDTO = commissionList.get(0);
					model.addAttribute("totalAmount",amountDetailDTO.getTotalMoney());
					model.addAttribute("userName",amountDetailDTO.getUserName());
				}
			}

            return "/system/commission/commissionDetail";
        }catch (Exception e){
			e.printStackTrace();
            logger.error("CommissionController.queryCommissionDetail error!params:{}", JSONObject.toJSONString(commissionDetailQuery),e);
            return "/error";
        }
    }
    
	
	@RequestMapping(value="/queryList", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getCommissionList(Model model,CommissionListQuery query){
		try {
			if(query == null){
				query = new CommissionListQuery();
			}
			query.setDomainId(Domain.AZ.getType());
			PageVO<AmountTotalDTO> pageVO = commissionBiz.getCommissionList(query);
			model.addAttribute("commissionListQuery",query);
			model.addAttribute("pageVo", pageVO);
			return "/system/commission/commissionList";
		} catch (Exception e) {
			logger.error("CommissionController.getCommissionList error happens,ex:" , e);
			return "/error";
		}
	}
	
	@RequestMapping(value="/amountExtract",method = RequestMethod.POST)
	@ResponseBody
	public String amountExtract(Model model,ExtractDTO extractDTO){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		try{
			if(extractDTO == null){
				logger.error("CommissionController.amountExtract param null");
				map.put("success", false);
				map.put("desc", "传入对象为空");
				return JSON.toJSONString(map);
			}
			extractDTO.setDomainId(Domain.AZ.getType());
			
			AmountObtainDTO dto = new AmountObtainDTO();
			CommissionAmoutConvert.extractConvert(extractDTO, dto);
			if(dto.getUserId() <= 0 || StringUtils.isBlank(dto.getPayeeAccount()) || StringUtils.isBlank(dto.getUserName()) || dto.getCommissionAmt() < 0){
				logger.error("CommissionController.amountExtract param error,param:" + JSON.toJSONString(dto));
				map.put("success", false);
				map.put("desc", "对象参数错误");
				return JSON.toJSONString(map);
			}
			return commissionBiz.amountExtract(dto);
		}catch(Exception e){
			logger.error("CommissionController.amountExtract error happens,param:{},ex:",JSON.toJSONString(extractDTO), e);
			map.put("success", false);
			map.put("desc", "服务请求发生异常");
			return JSON.toJSONString(map);
		}
	}
}
