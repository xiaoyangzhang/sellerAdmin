package com.yimayhd.sellerAdmin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.sellerAdmin.base.BaseTravelController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.line.free.FreeLineVO;
import com.yimayhd.sellerAdmin.service.CommFreeLineService;
import com.yimayhd.sellerAdmin.service.FlightRPCService;

/**
 * 商品-自由行
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/selfServiceTravel")
public class CommFreeLineController extends BaseTravelController {
	@Resource
	private CommFreeLineService freeLineService;
	@Autowired
	private FlightRPCService flightRPCService;

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "id") long id, long categoryId) throws Exception {
		initBaseInfo();
		initLinePropertyTypes(categoryId);
		if (id > 0) {
			FreeLineVO sst = freeLineService.getById(id);
			put("product", sst);
			put("lineType", LineType.getByType(sst.getBaseInfo().getType()));
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
		put("lineType", LineType.FLIGHT_HOTEL);
		return "/system/comm/travel/detail";
	}

	/**
	 * 创建跟团游
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchInsert")
	public String batchInsert(long categoryId) throws Exception {
		initLinePropertyTypes(categoryId);
		return "/system/comm/travel/selfServiceTravel/detail";
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHotel")
	public String selectHotel() throws Exception {
		return "/system/comm/travel/selectHotel";
	}

	/**
	 * 添加航班
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFlightDetail")
	public String addFlightDetail() throws Exception {
		put("flightCompanys", flightRPCService.findAllFlightCompany());
		return "/system/comm/travel/selfServiceTravel/addFlightDetail";
	}

	/**
	 * 填写航班信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setFlightInfo")
	public String setFlightInfo() throws Exception {
		return "/system/comm/travel/selfServiceTravel/setFlightInfo";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody ResponseVo save(String json) throws Exception {
		try {
			FreeLineVO sst = JSON.parseObject(json, FreeLineVO.class);
			long id = freeLineService.publishLine(sst);
			return ResponseVo.success(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
}
