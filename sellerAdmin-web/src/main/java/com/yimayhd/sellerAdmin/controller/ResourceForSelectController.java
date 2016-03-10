package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yimayhd.sellerAdmin.model.query.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.service.ActivityService;
import com.yimayhd.sellerAdmin.service.CommLineService;
import com.yimayhd.sellerAdmin.service.CommodityService;
import com.yimayhd.sellerAdmin.service.HotelRPCService;
import com.yimayhd.sellerAdmin.service.RestaurantService;
import com.yimayhd.sellerAdmin.service.ScenicService;
import com.yimayhd.sellerAdmin.service.TripService;
import com.yimayhd.sellerAdmin.service.UserRPCService;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.snscenter.client.enums.BaseStatus;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.enums.UserOptions;

/**
 * 资源选择理
 *
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceForSelect")
public class ResourceForSelectController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private ScenicService scenicService;
	@Autowired
	private HotelRPCService hotelService;
	@Autowired
	private UserRPCService userService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private TripService tripService;
	@Resource
	private CommLineService travelService;
	@Autowired
	private ActivityService activityService;

	/**
	 * 选择景点
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRestaurantForSelect")
	public @ResponseBody ResponseVo queryRestaurantForSelect(RestaurantListQuery query) {
		try {
			PageVO<RestaurantDO> pageVo = restaurantService.pageQueryRestaurant(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择餐厅
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectRestaurant")
	public String selectRstaurant() {
		return "/system/resource/forSelect/selectRestaurant";
	}

	/**
	 * 选择景区
	 *
	 * @return
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryScenicForSelect")
	public @ResponseBody ResponseVo queryScenicForSelect(Model model, ScenicListQuery scenicPageQuery)
			throws Exception {
		try {
			PageVO<ScenicDO> pageVo = scenicService.getList(scenicPageQuery);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", scenicPageQuery);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择景区
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectScenic")
	public String selectScenic() {
		return "/system/resource/forSelect/selectScenic";
	}

	/**
	 * 选择景区
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneScenic")
	public String selectOneScenic() {
		return "/system/resource/forSelect/selectOneScenic";
	}

	/**
	 * 选择活动商品
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneActivityComm")
	public String selectOneActivityComm(Model model) {
		List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
		model.addAttribute("itemTypeList", itemTypeList);
		return "/system/resource/forSelect/selectOneActivityComm";
	}

	/**
	 * 选择活动商品
	 *
	 * @return
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryActivityComm")
	public @ResponseBody ResponseVo queryScenicForSelect(Model model, CommodityListQuery commodityListQuery)
			throws Exception {
		try {
			PageVO<ItemVO> pageVO = commodityService.getList(commodityListQuery);
			List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("itemTypeList", itemTypeList);
			result.put("pageVo", pageVO);
			result.put("query", commodityListQuery);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择酒店
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelForSelect")
	public @ResponseBody ResponseVo queryHotelForSelect(HotelListQuery query) {
		try {
			PageVO<HotelDO> pageVo = hotelService.pageQueryHotel(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择酒店
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHotel")
	public String selectHotel() {
		return "/system/resource/forSelect/selectHotel";
	}

	/**
	 * 选择用户
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserForSelect")
	public @ResponseBody ResponseVo queryUserForSelect(UserDOPageQuery query, Integer pageNumber) {
		try {
			if (pageNumber != null) {
				query.setPageNo(pageNumber);
			}
			PageVO<UserDO> pageVo = userService.getUserListByPage(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择一个用户
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneUser")
	public String selectOneUser() {
		return "/system/resource/forSelect/selectOneUser";
	}

	/**
	 * 选择用户
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUser")
	public String selectUser() {
		return "/system/resource/forSelect/selectUser";
	}

	/**
	 * 选择旅游咖
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTravelKaForSelect")
	public @ResponseBody ResponseVo queryTravelKaForSelect(UserDOPageQuery query,Integer pageNumber) {
		try {
			if(pageNumber != null){
				query.setPageNo(pageNumber);
			}
			List<Long> optionList = new ArrayList<Long>();
			optionList.add(UserOptions.TRAVEL_KA.getLong());
			query.setOptionsList(optionList);
			PageVO<UserDO>  pageVo = userService.getTravelKaListByPage(query);
			query.setOptionsList(null);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择旅游咖
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneTravelKa")
	public String selectOneTravelKa() {
		return "/system/resource/forSelect/selectOneTravelKa";
	}

	/**
	 * 选择一个旅游咖
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTravelKa")
	public String selectTravelKa() {
		return "/system/resource/forSelect/selectTravelKa";
	}

	@RequestMapping(value = "/selectMustBuy")
	public String selectMustBuy() {
		return "/system/resource/forSelect/selectMustBuy";
	}

	/**
	 * @Title: listMustBuy @Description:(获取买必推荐列表) @author create by
	 *         yushengwei @ 2015年12月27日 下午4:23:41 @param @param
	 *         regionIntroduceQuery @param @return @param @throws
	 *         Exception @return ResponseVo 返回类型 @throws
	 */
	@RequestMapping(value = "/listMustBuy")
	public @ResponseBody ResponseVo listMustBuy(RegionIntroduceQuery query, Integer pageNumber, Integer pageSize)
			throws Exception {
		try {
			if (pageNumber != null) {
				query.setPageNo(pageNumber);
			} else {
				query.setPageNo(BaseQuery.DEFAULT_PAGE);
			}
			if (pageSize != null) {
				query.setPageSize(pageSize);
			} else {
				query.setPageSize(BaseQuery.DEFAULT_SIZE);
			}
			PageVO<RegionIntroduceDO> pageVo = tripService.getPageRegionIntroduceDO(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	@RequestMapping(value = "/selectLive")
	public String selectLive() {
		return "/system/resource/forSelect/selectLive";
	}

	@RequestMapping(value = "/listLive")
	public @ResponseBody ResponseVo listLive(LiveListQuery query) throws Exception {
		try {

			PageVO<SnsSubjectDO> pageVo = tripService.getPageSnsSubjectDO(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择单个旅游产品
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectline")
	public String selectOneTravelProduct() {
		return "/system/resource/forSelect/selectLine";
	}

	/**
	 *
	 * 查询旅游产品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTravelProductForSelect")
	public @ResponseBody ResponseVo queryTravelProductForSelect(LinePageQuery query) {
		try {
			Integer pageNumber = getInteger("pageNumber");
			if (pageNumber != null) {
				query.setPageNo(pageNumber);
			}
			if (StringUtils.isEmpty(query.getTags())) {
				query.setTags(null);
			}
			// PageVO<TravelKaVO> pageVo =
			// userService.getTravelKaListByPage(query);
			query.setNeedCount(true);
			query.setStatus(ItemStatus.valid.getValue());
			PageVO<LineDO> pageVo = travelService.pageQueryLine(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 选择单个活动
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneActivity")
	public String selectOneActivity() {
		return "/system/resource/forSelect/selectOneActivity";
	}

	/**
	 * 选择多活动
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectActivity")
	public String selectActivity() {
		return "/system/resource/forSelect/selectActivity";
	}

	/**
	 * 查询活动
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryActivityForSelect")
	public @ResponseBody ResponseVo queryActivityForSelect(ActivityListQuery query) {
		try {
			//只查询启用状态的数据
			query.setState(BaseStatus.AVAILABLE.getType());
			PageVO<SnsActivityDO> pageVo = activityService.pageQueryActivities(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageVo);
			result.put("query", query);
			return new ResponseVo(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	@RequestMapping(value = "/selectLightSpot")
	public String selectLightSpot() {
		return "/system/resource/forSelect/selectLightSpot";
	}

}
