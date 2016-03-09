package com.yimayhd.sellerAdmin.controller;

import javax.annotation.Resource;

import com.yimayhd.sellerAdmin.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.sellerAdmin.base.BaseTravelController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.travel.groupTravel.GroupTravel;
import com.yimayhd.sellerAdmin.model.travel.groupTravel.TripTraffic;
import com.yimayhd.sellerAdmin.service.CommTravelService;
import com.yimayhd.sellerAdmin.service.TfsService;

/**
 * 商品-跟团游
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/groupTravel")
public class CommGroupTravelController extends BaseTravelController {
	@Resource
	private CommTravelService groupTravelService;
	@Autowired
	private TfsService tfsService;

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(long categoryId, @PathVariable(value = "id") long id) throws Exception {
		initBaseInfo();
		initLinePropertyTypes(categoryId);
		if (id > 0) {
			GroupTravel gt = groupTravelService.getById(id, GroupTravel.class);
			put("product", gt);
			String importantInfos = tfsService.readHtml5(gt.getPriceInfo().getImportantInfosCode());
			put("importantInfos", importantInfos);
			String extraInfos = tfsService.readHtml5(gt.getBaseInfo().getNeedKnow().getExtraInfoUrl());
			put("extraInfos", extraInfos);
			put("lineType", LineType.getByType(gt.getBaseInfo().getType()));
		}
		return "/system/comm/travel/detail";
	}

	/**
	 * 创建跟团游
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(long categoryId) throws Exception {
		initBaseInfo();
		initLinePropertyTypes(categoryId);
		put("lineType", LineType.REGULAR_LINE);
		return "/system/comm/travel/detail";
	}

	/**
	 * 批量录入
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchInsert")
	public String batchInsert(long categoryId) throws Exception {
		initLinePropertyTypes(categoryId);
		return "/system/comm/travel/batchInsert";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody ResponseVo save(String json, String importantInfos, String extraInfos) {
		try {
			GroupTravel gt = (GroupTravel) JSONObject.parseObject(json, GroupTravel.class);
			if (StringUtils.isNotBlank(importantInfos)) {
				String importantInfosCode = tfsService.publishHtml5(importantInfos);
				gt.getPriceInfo().setImportantInfosCode(importantInfosCode);
			} else {
				gt.getPriceInfo().setImportantInfosCode(Constant.DEFAULT_CONTRACT_TFS_CODE);
			}
			if (StringUtils.isNotBlank(extraInfos)) {
				String extraInfosCode = tfsService.publishHtml5(extraInfos);
				gt.getBaseInfo().getNeedKnow().setExtraInfoUrl(extraInfosCode);
			}
			long id = groupTravelService.publishLine(gt);
			return ResponseVo.success(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择交通
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTraffic")
	public String selectTraffic() throws Exception {
		put("ways", TripTraffic.ways());
		return "/system/comm/travel/groupTravel/selectTraffic";
	}
}
