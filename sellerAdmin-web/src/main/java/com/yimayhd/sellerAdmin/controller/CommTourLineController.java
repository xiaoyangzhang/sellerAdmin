package com.yimayhd.sellerAdmin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.sellerAdmin.base.BaseTravelController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.line.tour.TourLineVO;
import com.yimayhd.sellerAdmin.model.line.tour.TripTraffic;
import com.yimayhd.sellerAdmin.service.CommTourLineService;

/**
 * 商品-跟团游
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/groupTravel")
public class CommTourLineController extends BaseTravelController {
	@Resource
	private CommTourLineService commTourLineService;

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
			TourLineVO gt = commTourLineService.getById(id);
			put("product", gt);
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
			TourLineVO gt = (TourLineVO) JSONObject.parseObject(json, TourLineVO.class);
			long id = commTourLineService.publishLine(gt);
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