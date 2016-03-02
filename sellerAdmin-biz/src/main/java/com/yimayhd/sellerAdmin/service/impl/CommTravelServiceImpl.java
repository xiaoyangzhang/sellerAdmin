package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.checker.LineChecker;
import com.yimayhd.sellerAdmin.checker.result.CheckResult;
import com.yimayhd.sellerAdmin.model.travel.BaseTravel;
import com.yimayhd.sellerAdmin.repo.LineRepo;
import com.yimayhd.sellerAdmin.repo.TagRepo;
import com.yimayhd.sellerAdmin.service.CommTravelService;

public class CommTravelServiceImpl implements CommTravelService {
	protected static final int PICTURE_TOP_SIZE = 6;
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected LineRepo lineRepo;
	@Autowired
	protected TagRepo tagRepo;

	@Override
	public <T extends BaseTravel> T getById(long id, Class<T> clazz) {
		// TODO YEBIN 通过ID获取跟团游对象
		if (id <= 0) {
			return null;
		}
		LineResult lineResult = lineRepo.getLineById(id);
		List<ComTagDO> tags = tagRepo.findAllTag(id, TagType.LINETAG);
		T travel = null;
		try {
			travel = clazz.newInstance();
			travel.init(lineResult, tags);
		} catch (Exception e) {
			log.error("解析线路信息失败", e);
			throw new BaseException("解析线路信息失败");
		}
		return travel;
	}

	public <T extends BaseTravel> void beforePublish(T travel) {

	}

	public <T extends BaseTravel> long publishLine(T travel) {
		beforePublish(travel);
		LinePublishResult publishLine = null;
		long lineId = travel.getBaseInfo().getId();
		if (lineId > 0) {
			CheckResult checkForSave = LineChecker.checkForUpdate(travel);
			if (checkForSave.isSuccess()) {
				LineResult lineResult = lineRepo.getLineById(lineId);
				publishLine = lineRepo.updateLine(travel.toLinePublishDTOForUpdate(lineResult));
			} else {
				throw new BaseException(checkForSave.getMsg());
			}
		} else {
			CheckResult checkForUpdate = LineChecker.checkForSave(travel);
			if (checkForUpdate.isSuccess()) {
				publishLine = lineRepo.saveLine(travel.toLinePublishDTOForSave());
			} else {
				throw new BaseException(checkForUpdate.getMsg());
			}
		}
		if (publishLine.getLineId() > 0) {
			tagRepo.addTagRelation(publishLine.getLineId(), TagType.LINETAG, travel.getTagIdList(),
					publishLine.getCreateTime());
		}
		return publishLine.getLineId();

	}

	@Override
	public PageVO<LineDO> pageQueryLine(LinePageQuery query) {
		return lineRepo.pageQueryLine(query);
	}
}
