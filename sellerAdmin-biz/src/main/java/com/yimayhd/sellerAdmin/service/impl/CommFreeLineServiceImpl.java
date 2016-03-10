package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.checker.LineChecker;
import com.yimayhd.sellerAdmin.checker.result.CheckResult;
import com.yimayhd.sellerAdmin.converter.LineConverter;
import com.yimayhd.sellerAdmin.model.line.tour.TourLineVO;
import com.yimayhd.sellerAdmin.repo.HotelRepo;
import com.yimayhd.sellerAdmin.repo.LineRepo;
import com.yimayhd.sellerAdmin.repo.PictureRepo;
import com.yimayhd.sellerAdmin.repo.RestaurantRepo;
import com.yimayhd.sellerAdmin.repo.ScenicRepo;
import com.yimayhd.sellerAdmin.repo.TagRepo;
import com.yimayhd.sellerAdmin.service.CommTourLineService;

/**
 * 跟团游
 * 
 * @author yebin
 *
 */
public class CommFreeLineServiceImpl implements CommTourLineService {
	protected static final int PICTURE_TOP_SIZE = 6;
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected LineRepo lineRepo;
	@Autowired
	protected TagRepo tagRepo;
	@Autowired
	protected RestaurantRepo restaurantRepo;
	@Autowired
	protected HotelRepo hotelRepo;
	@Autowired
	protected ScenicRepo scenicRepo;
	@Autowired
	protected PictureRepo pictureRepo;

	@Override
	public TourLineVO getById(long id) {
		// TODO YEBIN 通过ID获取跟团游对象
		if (id <= 0) {
			return null;
		}
		LineResult lineResult = lineRepo.getLineById(id);
		List<ComTagDO> tags = tagRepo.findAllTag(id, TagType.LINETAG);
		return LineConverter.toTourLineVO(lineResult, tags);
	}

	@Override
	public long publishLine(TourLineVO line) {
		LinePublishResult publishLine = null;
		long lineId = line.getBaseInfo().getLineId();
		if (lineId > 0) {
			/*CheckResult checkForSave = LineChecker.checkForUpdate(line);
			if (checkForSave.isSuccess()) {*/
				LineResult lineResult = lineRepo.getLineById(lineId);
				LinePublishDTO linePublishDTOForUpdate = LineConverter.toLinePublishDTOForUpdate(line, lineResult);
				publishLine = lineRepo.updateLine(linePublishDTOForUpdate);
			/*} else {
				throw new BaseException(checkForSave.getMsg());
			}*/
		} else {
			/*CheckResult checkForUpdate = LineChecker.checkForSave(line);
			if (checkForUpdate.isSuccess()) {*/
				LinePublishDTO linePublishDTOForSave = LineConverter.toLinePublishDTOForSave(line);
				publishLine = lineRepo.saveLine(linePublishDTOForSave);
			/*} else {
				throw new BaseException(checkForUpdate.getMsg());
			}*/
		}
		if (publishLine.getLineId() > 0) {
			List<Long> themes = line.getBaseInfo().getThemes();
			tagRepo.addTagRelation(publishLine.getLineId(), TagType.LINETAG, themes, publishLine.getCreateTime());
		}
		return publishLine.getLineId();
	}
}
