package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public class PictureRepo {
	private static final int PICTURE_MAX_SIZE = 500;
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	public PageVO<PicturesDO> pageQueryPictures(PicturesPageQuery query) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.queryPictures", query);
		ICPageResult<PicturesDO> queryPicturesResult = itemQueryServiceRef.queryPictures(query);
		RepoUtils.resultLog(log, "itemQueryServiceRef.queryPictures", queryPicturesResult);
		int totalCount = queryPicturesResult.getTotalCount();
		List<PicturesDO> itemList = queryPicturesResult.getList();
		if (itemList == null) {
			itemList = new ArrayList<PicturesDO>();
		}
		return new PageVO<PicturesDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	public List<PicturesDO> queryTopPictures(PictureOutType outType, long outId, int limit) {
		PicturesPageQuery ppq = new PicturesPageQuery();
		ppq.setOutId(outId);
		ppq.setIsTop(true);
		ppq.setOutType(outType.getValue());
		ppq.setPageNo(1);
		ppq.setPageSize(limit);
		ppq.setStatus(BaseStatus.AVAILABLE.getType());
		return pageQueryPictures(ppq).getResultList();
	}

	public List<PicturesDO> queryAllPictures(PictureOutType outType, long outId) {
		PicturesPageQuery ppq = new PicturesPageQuery();
		ppq.setOutId(outId);
		ppq.setOutType(outType.getValue());
		ppq.setPageNo(1);
		ppq.setPageSize(PICTURE_MAX_SIZE);
		ppq.setStatus(BaseStatus.AVAILABLE.getType());
		return pageQueryPictures(ppq).getResultList();
	}
}
