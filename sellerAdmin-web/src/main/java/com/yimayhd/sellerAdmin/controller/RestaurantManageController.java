package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.PictureVO;
import com.yimayhd.sellerAdmin.model.RestaurantVO;
import com.yimayhd.sellerAdmin.model.query.RestaurantListQuery;
import com.yimayhd.sellerAdmin.service.PictureService;
import com.yimayhd.sellerAdmin.service.RestaurantService;

/**
 * 资源管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/restaurantManage")
public class RestaurantManageController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private PictureService pictureService;

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String restaurant(@PathVariable("id") Long id) throws Exception {
		RestaurantDO restaurant = restaurantService.getRestaurantById(id);
		put("restaurant", restaurant);
		return "/system/restaurant/Info";
	}

	/**
	 * 新增餐厅（资源）
	 * 
	 * @return 餐厅（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/restaurant/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) {
		RestaurantDO restaurantDO = restaurantService.getRestaurantById(id);
		MasterRecommend recommend = restaurantDO.getRecommend();
		model.addAttribute("restaurant", restaurantDO);
		model.addAttribute("recommend", recommend);
		List<String> pictures = restaurantDO.getPictures();
		if (CollectionUtils.isNotEmpty(pictures)) {
			String coverPics = StringUtils.join(restaurantDO.getPictures(), "|");
			model.addAttribute("coverPics", coverPics);
		}
		List<PicturesDO> picturesDOs = pictureService.queryAllPictures(PictureOutType.RESTAURANT, id);
		List<PictureVO> pictureVOList = new ArrayList<PictureVO>();
		if (CollectionUtils.isNotEmpty(picturesDOs)) {
			for (PicturesDO picturesDO : picturesDOs) {
				PictureVO pictureVO = new PictureVO();
				pictureVO.setId(picturesDO.getId());
				pictureVO.setName(picturesDO.getName());
				pictureVO.setValue(picturesDO.getPath());
				pictureVO.setIsTop(picturesDO.isTop());
				pictureVOList.add(pictureVO);

			}
		}
		model.addAttribute("pictureList", pictureVOList);
		return "/system/restaurant/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(RestaurantVO restaurantVO) throws Exception {
		try {
			restaurantService.publish(restaurantVO);
			return ResponseVo.success();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String restaurantList(RestaurantListQuery query) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantService.pageQueryRestaurant(query);
		put("pageVo", pageVo);
		put("query", query);
		return "/system/restaurant/list";
	}

	@RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateRoleStatus(@PathVariable("id") int id, int restaurantStatus) throws Exception {
		try {
			restaurantService.changeStatus(id, restaurantStatus);
			return ResponseVo.success();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 动态状态变更(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchUpdateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchUpdateStatus(@RequestParam("restaurantIdList[]") List<Integer> restaurantIdList,
			int restaurantStatus) throws Exception {
		try {
			for (int id : restaurantIdList) {
				restaurantService.changeStatus(id, restaurantStatus);
			}
			return ResponseVo.success();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
}
