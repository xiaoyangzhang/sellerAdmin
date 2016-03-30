package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.param.item.PictureUpdateDTO;
import com.yimayhd.ic.client.model.query.RestaurantPageQuery;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.checker.RestaurantChecker;
import com.yimayhd.sellerAdmin.checker.result.CheckResult;
import com.yimayhd.sellerAdmin.model.PictureVO;
import com.yimayhd.sellerAdmin.model.RestaurantVO;
import com.yimayhd.sellerAdmin.model.query.RestaurantListQuery;
import com.yimayhd.sellerAdmin.repo.PictureRepo;
import com.yimayhd.sellerAdmin.repo.RestaurantRepo;
import com.yimayhd.sellerAdmin.service.RestaurantService;
import com.yimayhd.sellerAdmin.util.DateUtil;

public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	private RestaurantRepo restaurantRepo;
	@Autowired
	private PictureRepo pictureRepo;

	@Override
	public PageVO<RestaurantDO> pageQueryRestaurant(RestaurantListQuery restaurantListQuery) {

		RestaurantPageQuery restaurantPageQuery = new RestaurantPageQuery();
		restaurantPageQuery.setNeedCount(true);
		restaurantPageQuery.setPageNo(restaurantListQuery.getPageNo());
		restaurantPageQuery.setPageSize(restaurantListQuery.getPageSize());
		// 酒店名称
		restaurantPageQuery.setName(restaurantListQuery.getName());
		// 状态
		restaurantPageQuery.setStatus(restaurantListQuery.getStatus());
		// // 创建时间
		if (!StringUtils.isBlank(restaurantListQuery.getBeginTime())) {
			Date startTime = DateUtil.parseDate(restaurantListQuery.getBeginTime());
			restaurantPageQuery.setStartTime(startTime);
		}
		// // 结束时间
		if (!StringUtils.isBlank(restaurantListQuery.getEndTime())) {
			Date endTime = DateUtil.parseDate(restaurantListQuery.getEndTime());
			restaurantPageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		return restaurantRepo.pageQueryRestaurant(restaurantPageQuery);

	}

	@Override
	public RestaurantDO getRestaurantById(long id) {
		return restaurantRepo.getRestaurantById(id);
	}

	@Override
	public void publish(RestaurantVO restaurantVO) {
		long id = restaurantVO.getId();
		if (id > 0) {
			CheckResult checkResult = RestaurantChecker.checkRestaurantVOForUpdate(restaurantVO);
			if(!checkResult.isSuccess()) {
				throw new BaseException(checkResult.getMsg());
			}
			RestaurantDO restaurantDO = getRestaurantById(id);
			RestaurantDO restaurantDTO = restaurantVO.toRestaurantDO(restaurantDO);
			boolean updateRestaurant = restaurantRepo.updateRestaurant(restaurantDTO);
			if (!updateRestaurant) {
				throw new BaseException("餐厅资源更新失败,未知错误");
			}
			List<PicturesDO> picturesDOList = pictureRepo.queryAllPictures(PictureOutType.RESTAURANT, id);
			String picListStr = restaurantVO.getPicListStr();
			if (StringUtils.isNotBlank(picListStr)) {
				List<PictureVO> pictureVOList = JSON.parseArray(picListStr, PictureVO.class);
				PictureUpdateDTO pictureUpdateDTO = new PictureUpdateDTO();
				PictureUpdateDTO setPictureListPictureUpdateDTO = PictureVO.setPictureListPictureUpdateDTO(id, PictureOutType.RESTAURANT, pictureUpdateDTO,
						picturesDOList, pictureVOList);
				if (setPictureListPictureUpdateDTO != null) {
					boolean updatePictures = restaurantRepo.updatePictures(pictureUpdateDTO);
					if (!updatePictures) {
						throw new BaseException("餐厅资源保存成功，图片集保存失败");
					}
				}
			}
		} else {
			CheckResult checkResult = RestaurantChecker.checkRestaurantVOForSave(restaurantVO);
			if(!checkResult.isSuccess()) {
				throw new BaseException(checkResult.getMsg());
			}
			RestaurantDO restaurantDO = restaurantVO.toRestaurantDO();
			RestaurantDO addedRestaurant = restaurantRepo.addRestaurant(restaurantDO);
			if (addedRestaurant == null) {
				throw new BaseException("餐厅资源保存失败, 接口返回值为空");
			}
			String picListStr = restaurantVO.getPicListStr();
			if (StringUtils.isNotBlank(picListStr)) {
				List<PictureVO> pictureVOList = JSON.parseArray(picListStr, PictureVO.class);
				List<PicturesDO> picList = new ArrayList<PicturesDO>();
				for (PictureVO pictureVO : pictureVOList) {
					PicturesDO picturesDO = pictureVO.toPicturesDO();
					picturesDO.setOutId(addedRestaurant.getId());
					picturesDO.setOutType(PictureOutType.RESTAURANT.getValue());
					picList.add(picturesDO);
				}
				if (CollectionUtils.isNotEmpty(picList)) {
					boolean addPictures = restaurantRepo.addPictures(picList);
					if (!addPictures) {
						throw new BaseException("餐厅资源保存成功，图片集保存失败");
					}
				}
			}
		}
	}

	@Override
	public void changeStatus(long id, int status) {
		// TODO 暂不实现
	}
}
