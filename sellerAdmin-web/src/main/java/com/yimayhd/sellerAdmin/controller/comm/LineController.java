package com.yimayhd.sellerAdmin.controller.comm;

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
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.service.CommLineService;

/**
 * 线路商品
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/line")
public class LineController extends BaseTravelController {
	@Autowired
	private CommLineService commLineService;

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
			LineVO gt = commLineService.getById(id);
			put("product", gt);
			put("lineType", LineType.getByType(gt.getBaseInfo().getType()));
		}
		return "/system/comm/line/detail";
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
		put("lineType", LineType.TOUR_LINE);
		return "/system/comm/line/detail";
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
		return "/system/comm/line/batchInsert";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody ResponseVo save(String json) {
		try {
			long sellerId = 1000;
			LineVO gt = (LineVO) JSONObject.parseObject(json, LineVO.class);
			long lineId = gt.getBaseInfo().getLineId();
			if (lineId > 0) {
				commLineService.update(sellerId, gt);
			} else {
				lineId = commLineService.save(sellerId, gt);
			}
			return ResponseVo.success(lineId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
}
