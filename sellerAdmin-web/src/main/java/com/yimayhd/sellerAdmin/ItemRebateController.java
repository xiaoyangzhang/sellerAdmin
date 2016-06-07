package com.yimayhd.sellerAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commission.biz.ItemRebateBiz;
import com.yimayhd.commission.model.query.ItemRebateQuery;
import com.yimayhd.marketing.client.model.domain.ItemRebateDO;
import com.yimayhd.marketing.client.model.param.ItemRebateRateUpdateDTO;
import com.yimayhd.marketing.client.model.result.SpmResult;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/13
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
@Controller 
@RequestMapping(value = "/itemRebate")
public class ItemRebateController extends BaseController {
	
    @Autowired
    private ItemRebateBiz itemRebateBiz;


    @RequestMapping(value = "/queryItemRebate")
    public String queryItemRebate(Model model,ItemRebateQuery itemRebateQuery) throws Exception{

    	try{
    		if(itemRebateQuery == null){
    			itemRebateQuery = new ItemRebateQuery();
    		}
    		if(itemRebateQuery.getPageNo() != null && itemRebateQuery.getPageNo() <= 0){
    			itemRebateQuery.setPageNo(1);
    		}
    		if(itemRebateQuery.getPageSize() != null && itemRebateQuery.getPageSize() <= 0){
    			itemRebateQuery.setPageSize(BaseQuery.DEFAULT_SIZE);
    		}
    		model.addAttribute("itemRebateQuery", itemRebateQuery);
    		PageVO<ItemRebateDO> pageVO = itemRebateBiz.queryItemRebate(itemRebateQuery);
    		model.addAttribute("pageVo",pageVO);
    		
    		return "/system/commission/itemRebateSetting";
    		
    	}catch(Exception e){
    		return "/error";
    	}
    }


    @RequestMapping(value = "/updateItemRebate")
    @ResponseBody
    public ResponseVo updateItemRebateRate(long itemId,int parentRate,int grandpaRate){

        ResponseVo responseVo =  new ResponseVo();

        try{

            System.out.println(itemId);
            System.out.println(parentRate);
            System.out.println(grandpaRate);

            ItemRebateRateUpdateDTO itemRebateRateUpdateDTO = new ItemRebateRateUpdateDTO();
            itemRebateRateUpdateDTO.setItemId(itemId);
            itemRebateRateUpdateDTO.setRebateParentRate(parentRate);
            itemRebateRateUpdateDTO.setRebateGrandpaRate(grandpaRate);
            SpmResult<ItemRebateDO> spmResult = itemRebateBiz.updateItemRebate(itemRebateRateUpdateDTO);
            if(spmResult.isSuccess() && spmResult.getT()!=null){
                responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
                return responseVo;
            }
        }catch (Exception e){
            log.error("ItemRebateController.updateItemRebate error!itemId:{},parentRate:{},grandpaRate:{}",itemId,parentRate,grandpaRate,e);
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
        }
        return responseVo;
    }
}
