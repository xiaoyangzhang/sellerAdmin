package com.yimayhd.sellerAdmin.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.sellerAdmin.base.AreaService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.model.AreaVO;
import com.yimayhd.sellerAdmin.model.HotelFacilityVO;
import com.yimayhd.sellerAdmin.model.HotelVO;
import com.yimayhd.sellerAdmin.model.query.HotelListQuery;
import com.yimayhd.sellerAdmin.service.HotelRPCService;

/**
 * 酒店管理（资源）
 * 
 * @author czf
 */
@Controller
@RequestMapping("/B2C/hotelManage")
public class HotelManageController extends BaseController {

	@Autowired
	private HotelRPCService hotelRPCService;

	/**
	 * 酒店（资源）列表
	 * 
	 * @return 酒店（资源）列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HotelListQuery hotelListQuery) throws Exception {

		PageVO<HotelDO> pageVo = hotelRPCService.pageQueryHotel(hotelListQuery);
		List<HotelDO> hotelDOList = pageVo.getResultList();

		model.addAttribute("pageVo", pageVo);
		model.addAttribute("hotelListQuery", hotelListQuery);
		model.addAttribute("hotelDOList", hotelDOList);
		return "/system/hotel/list";
	}

	/**
	 * 新增酒店（资源）
	 * 
	 * @return 酒店（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) throws Exception {

		// 房间设施
		List<HotelFacilityVO> roomFacilityList = hotelRPCService.queryFacilities(1);
		// 特色服务
		List<HotelFacilityVO> roomServiceList = hotelRPCService.queryFacilities(2);
		// 酒店设施
		List<HotelFacilityVO> hotelFacilityList = hotelRPCService.queryFacilities(3);
		//省
		List<AreaVO> provinceList= AreaService.getInstance().getAreaByIDAndType("PROVINCE", null);

		model.addAttribute("roomFacilityList", roomFacilityList);
		model.addAttribute("roomServiceList", roomServiceList);
		model.addAttribute("hotelFacilityList", hotelFacilityList);
		model.addAttribute("provinceList", provinceList);
		return "/system/hotel/edit";
	}

	/**
	 * 新增酒店（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HotelVO hotelVO) throws Exception {


		hotelRPCService.addHotel(hotelVO);

		return "/success";
	}

	/**
	 * 酒店（资源）状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setHotelStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setHotelStatus(@PathVariable("id") long id, int hotelStatus) throws Exception {

		HotelDO hotelDO = new HotelDO();
		hotelDO.setId(id);
		hotelDO.setStatus(hotelStatus);

		ICResult<Boolean> icResult = hotelRPCService.updateHotelStatus(hotelDO);

		ResponseVo responseVo = new ResponseVo();
		if (icResult == null || !icResult.getModule()) {
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}

	/**
	 * 检查设施是否被选中
	 */
	private void checkInit(List<HotelFacilityVO> list, char[] arr) {

		for (int i = 0; i < list.size(); i++) {

			if (i <= arr.length - 1) {

				char tempChar = arr[i];
				boolean tempResult = (tempChar == '1');
				list.get(i).setChecked(tempResult);

			} else {
				continue;
			}

		}

	}

	/**
	 * 编辑酒店（资源）
	 * 
	 * @return 酒店（资源）编辑
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {

		HotelVO hotelVO = hotelRPCService.getHotel(id);
		if (hotelVO == null) {

		}
		MasterRecommend recommend = hotelVO.getRecommend();
		long roomFacility = hotelVO.getRoomFacility();
		long hotelFacility = hotelVO.getHotelFacility();
		long roomService = hotelVO.getRoomService();

		/**
		 * 处理酒店设施 开始
		 */
		String roomFacilityStr = Long.toBinaryString(roomFacility);
		String hotelFacilityStr = Long.toBinaryString(hotelFacility);
		String roomServiceStr = Long.toBinaryString(roomService);

		roomFacilityStr = new StringBuffer(roomFacilityStr).reverse().toString();
		hotelFacilityStr = new StringBuffer(hotelFacilityStr).reverse().toString();
		roomServiceStr = new StringBuffer(roomServiceStr).reverse().toString();
		/**
		 * 处理酒店设施 结束
		 */

		char[] roomFacilityArr = roomFacilityStr.toCharArray();
		char[] hotelFacilityArr = hotelFacilityStr.toCharArray();
		char[] roomServiceArr = roomServiceStr.toCharArray();

		// 房间设施
		List<HotelFacilityVO> roomFacilityList = hotelRPCService.queryFacilities(1);
		Collections.sort(roomFacilityList);
		// 特色服务
		List<HotelFacilityVO> roomServiceList = hotelRPCService.queryFacilities(2);
		Collections.sort(roomServiceList);
		// 酒店设施
		List<HotelFacilityVO> hotelFacilityList = hotelRPCService.queryFacilities(3);
		Collections.sort(hotelFacilityList);

		checkInit(roomFacilityList, roomFacilityArr);
		checkInit(roomServiceList, roomServiceArr);
		checkInit(hotelFacilityList, hotelFacilityArr);

		NeedKnow needKnow = hotelVO.getNeedKnow();

		//省
		List<AreaVO> provinceList= AreaService.getInstance().getAreaByIDAndType("PROVINCE", null);
		//市
		List<AreaVO> cityList= AreaService.getInstance().getAreaByIDAndType("CITY", String.valueOf(hotelVO.getLocationProvinceId()));
		//区
		List<AreaVO> townList= AreaService.getInstance().getAreaByIDAndType("COUNTY", String.valueOf(hotelVO.getLocationCityId()));

		model.addAttribute("hotel", hotelVO);
		model.addAttribute("recommend", recommend);
		model.addAttribute("roomFacilityList", roomFacilityList);
		model.addAttribute("roomServiceList", roomServiceList);
		model.addAttribute("hotelFacilityList", hotelFacilityList);
		model.addAttribute("needKnow", needKnow);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("townList", townList);
		model.addAttribute("pictureList", hotelVO.getPictureList());
		return "/system/hotel/edit";
	}


	/**
	 * 选择酒店列表
	 * 
	 * @param model
	 * @param hotelListQuery
	 * @param multiSelect
	 *            是否多选（1：单选；2：多选）
	 * @return 酒店（资源）列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList", method = RequestMethod.GET)
	public String selectList(Model model, HotelListQuery hotelListQuery, int multiSelect) throws Exception {
		PageVO<HotelDO> pageVo = hotelRPCService.pageQueryHotel(hotelListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("hotelListQuery", hotelListQuery);
		model.addAttribute("hotelDOList", pageVo.getResultList());
		model.addAttribute("multiSelect", multiSelect);
		return "/system/hotel/selectList";
	}

	/**
	 * 编辑酒店（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String edit(HotelVO hotelVO, @PathVariable("id") long id) throws Exception {
		hotelVO.setId(id);
		hotelRPCService.updateHotel(hotelVO);
		return "/success";
	}

	/**
	 * 酒店状态变更(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setHotelStatusList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setHotelStatusList(@RequestParam("hotelIdList[]") ArrayList<Long> hotelIdList, int hotelStatus)
			throws Exception {
		hotelRPCService.setHotelStatusList(hotelIdList, hotelStatus);
		return new ResponseVo();
	}

}
