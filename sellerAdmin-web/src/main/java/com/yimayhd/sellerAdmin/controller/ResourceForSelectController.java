package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.BaseQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.ItemVO;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.query.ActivityListQuery;
import com.yimayhd.sellerAdmin.model.query.CommodityListQuery;
import com.yimayhd.sellerAdmin.model.query.LiveListQuery;
import com.yimayhd.sellerAdmin.service.ActivityService;
import com.yimayhd.sellerAdmin.service.CommodityService;
import com.yimayhd.sellerAdmin.service.TripService;
import com.yimayhd.sellerAdmin.service.UserRPCService;
import com.yimayhd.sellerAdmin.service.item.LineService;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.snscenter.client.enums.BaseStatus;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.dto.CityDTO;
import com.yimayhd.user.client.enums.UserOptions;

/**
 * 资源选择理
 *
 * @author yebin
 *
 */
@Controller
@RequestMapping("/resourceForSelect")
public class ResourceForSelectController extends BaseController {
	@Autowired
	private UserRPCService userService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private TripService tripService;
	@Resource
	private LineService commLineService;
	@Autowired
	private ActivityService activityService;

	/**
	 * 选择出发地
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDeparts")
	public String selectDeparts() {
		WebResult<List<CityVO>> result = commLineService.getAllLineDeparts();
		if (result.isSuccess()) {
			Map<String, List<CityVO>> departMap = new LinkedHashMap<String, List<CityVO>>();
			List<CityVO> allLineDeparts = result.getValue();
			if (CollectionUtils.isNotEmpty(allLineDeparts)) {
				for (CityVO cityVO : allLineDeparts) {
					CityDTO city = cityVO.getCity();
					String firstLetter = city.getFirstLetter();
					if (departMap.containsKey(firstLetter)) {
						departMap.get(firstLetter).add(cityVO);
					} else {
						List<CityVO> cityVOs = new ArrayList<CityVO>();
						cityVOs.add(cityVO);
						departMap.put(firstLetter, cityVOs);
					}
				}
			}
			put("departMap", departMap);
			return "/system/resource/forSelect/selectDeparts";
		} else {
			throw new BaseException("选择出发地失败");
		}
	}

	/**
	 * 选择景区
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDests")
	public String selectDests() {
		WebResult<List<CityVO>> result = commLineService.getAllLineDests();
		if (result.isSuccess()) {
			Map<String, List<CityVO>> destMap = new LinkedHashMap<String, List<CityVO>>();
			List<CityVO> allLineDests = result.getValue();
			if (CollectionUtils.isNotEmpty(allLineDests)) {
				for (CityVO cityVO : allLineDests) {
					CityDTO city = cityVO.getCity();
					String firstLetter = city.getFirstLetter();
					if (destMap.containsKey(firstLetter)) {
						destMap.get(firstLetter).add(cityVO);
					} else {
						List<CityVO> cityVOs = new ArrayList<CityVO>();
						cityVOs.add(cityVO);
						destMap.put(firstLetter, cityVOs);
					}
				}
			}
			put("destMap", destMap);
			return "/system/resource/forSelect/selectDests";
		} else {
			throw new BaseException("选择出发地失败");
		}
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
	public @ResponseBody ResponseVo queryTravelKaForSelect(UserDOPageQuery query, Integer pageNumber) {
		try {
			if (pageNumber != null) {
				query.setPageNo(pageNumber);
			}
			List<Long> optionList = new ArrayList<Long>();
			optionList.add(UserOptions.TRAVEL_KA.getLong());
			query.setOptionsList(optionList);
			PageVO<UserDO> pageVo = userService.getTravelKaListByPage(query);
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
			WebResult<PageVO<LineDO>> pageQueryLine = commLineService.pageQueryLine(query);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("pageVo", pageQueryLine.getValue());
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
			// 只查询启用状态的数据
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
