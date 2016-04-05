package com.yimayhd.sellerAdmin.controller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.BaseLineController;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.LineChecker;
import com.yimayhd.sellerAdmin.checker.result.WebCheckResult;
import com.yimayhd.sellerAdmin.model.line.LineVO;
import com.yimayhd.sellerAdmin.model.line.base.BaseInfoVO;
import com.yimayhd.sellerAdmin.service.item.LineService;

/**
 * 线路商品
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/line")
public class LineController extends BaseLineController {
	@Autowired
	private LineService commLineService;

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "id") long id) throws Exception {
		initBaseInfo();
		if (id > 0) {
			WebResult<LineVO> result = commLineService.getByItemId(id);
			if (result.isSuccess()) {
				LineVO gt = result.getValue();
				BaseInfoVO baseInfo = gt.getBaseInfo();
				if (baseInfo != null) {
					initLinePropertyTypes(baseInfo.getCategoryId());
				}
				put("product", gt);
				return "/system/comm/line/detail";
			} else {
				throw new BaseException(result.getResultMsg());
			}
		} else {
			throw new BaseException("参数错误");
		}
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
	public @ResponseBody WebResultSupport save(String json) {
		try {
			/*
			 * long sellerId = getCurrentUserId(); if (sellerId <= 0) {
			 * log.warn("未登录"); return
			 * WebOperateResult.failure(WebReturnCode.USER_NOT_FOUND); }
			 */
			long sellerId = 12800;
			LineVO gt = (LineVO) JSONObject.parseObject(json, LineVO.class);
			WebCheckResult checkLine = LineChecker.checkLine(gt);
			if (!checkLine.isSuccess()) {
				log.warn(checkLine.getResultMsg());
				return checkLine;
			}
			long itemId = gt.getBaseInfo().getItemId();
			if (itemId > 0) {
				return commLineService.update(sellerId, gt);
			} else {
				return commLineService.save(sellerId, gt);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR, e.getMessage());
		}
	}
}
