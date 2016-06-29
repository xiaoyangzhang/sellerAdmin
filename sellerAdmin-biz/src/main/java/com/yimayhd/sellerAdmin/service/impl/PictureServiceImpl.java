package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.repo.PictureRepo;
import com.yimayhd.sellerAdmin.service.PictureService;

public class PictureServiceImpl implements PictureService {
	@Autowired
	private PictureRepo pictureRepo;

	@Override
	public PageVO<PicturesDO> pageQueryPictures(PicturesPageQuery query) {
		return pictureRepo.pageQueryPictures(query);
	}

	@Override
	public List<PicturesDO> queryTopPictures(PictureOutType outType, long outId, int limit) {
		return pictureRepo.queryTopPictures(outType, outId, limit);
	}

	@Override
	public List<PicturesDO> queryAllPictures(PictureOutType outType, long outId) {
		return pictureRepo.queryAllPictures(outType, outId);
	}

}
