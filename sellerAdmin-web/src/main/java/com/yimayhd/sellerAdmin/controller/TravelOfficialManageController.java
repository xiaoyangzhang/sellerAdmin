package com.yimayhd.sellerAdmin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.model.BatchSetUpParameter;
import com.yimayhd.sellerAdmin.model.TravelOfficial;
import com.yimayhd.sellerAdmin.model.query.TravelOfficialListQuery;
import com.yimayhd.sellerAdmin.model.vo.TravelOfficialVO;
import com.yimayhd.sellerAdmin.service.TravelOfficialService;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;

@Controller
@RequestMapping("/B2C/travelOfficialManage")
public class TravelOfficialManageController extends BaseController {
	@Autowired
	private TravelOfficialService travelOfficialService;

	/**
	 * 官方游记列表
	 * 
	 * @return 官方游记列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, TravelOfficialListQuery travelOfficialListQuery) throws Exception {
		TravelOfficialVO travelOfficialVO = new TravelOfficialVO();
		travelOfficialVO.setTravelOfficialListQuery(travelOfficialListQuery);
		PageVO<SnsTravelSpecialtyDO> pageVo = travelOfficialService.getList(travelOfficialListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("travelOfficialListQuery", travelOfficialListQuery);
		model.addAttribute("travelOfficialList", pageVo.getResultList());
		return "/system/travelOfficial/list";
	}



	/**
	 * 根据官方游记ID获取官方游记详情
	 * 
	 * @return 官方游记详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(Model model, @PathVariable(value = "id") long id) throws Exception {
		TravelOfficial travelOfficial = travelOfficialService.getById(id);
		model.addAttribute("travelOfficial", travelOfficial);
		return "/system/travelOfficial/detail";
	}

	/**
	 * 新增官方游记
	 * 
	 * @return 官方游记详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/travelOfficial/detail";
	}

	/**
	 * 新增官方游记
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo add(TravelOfficial travelOfficial) throws Exception {
		TravelOfficial db = travelOfficialService.add(travelOfficial);
		System.out.println(db);
		if(null == db ){
			return new ResponseVo(ResponseStatus.ERROR);	
		}
		return new ResponseVo(ResponseStatus.SUCCESS);
	}

	/**
	 * 编辑官方游记
	 * 
	 * @return 官方游记详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		TravelOfficial travelOfficial = travelOfficialService.getById(id);
		model.addAttribute("travelOfficial", travelOfficial);
		return "/system/travelOfficial/detail";
	}

	/**
	 * 编辑官方游记
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo edit(@PathVariable(value = "id") long id,TravelOfficial travelOfficial) throws Exception {
		travelOfficial.setId(id);
		boolean flag = travelOfficialService.modify(travelOfficial);
		if(flag){
			return new ResponseVo(ResponseStatus.SUCCESS);
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 官方游记状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setJoinStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setJoinStatus(BatchSetUpParameter batchSetUpParameter,int travelStatus,HttpServletRequest request) throws Exception {
		List<Long> ids = null;
		if(null == batchSetUpParameter || CollectionUtils.isEmpty(batchSetUpParameter.getIds()) ){
			return new ResponseVo(ResponseStatus.INVALID_DATA);
		}
		ids = batchSetUpParameter.getIds();
		boolean flag = travelOfficialService.batchUpOrDownStatus(ids, travelStatus);
		if(flag){
			return new ResponseVo(ResponseStatus.SUCCESS);
		}
			return new ResponseVo(ResponseStatus.ERROR);
	}

}
