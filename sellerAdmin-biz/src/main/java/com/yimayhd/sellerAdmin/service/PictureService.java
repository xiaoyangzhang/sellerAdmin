package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;

/**
 * 图片服务
 * 
 * @author yebin
 *
 */
public interface PictureService {
	/**
	 * 分页查询图片
	 * 
	 * @param query
	 * @return
	 */
	PageVO<PicturesDO> pageQueryPictures(PicturesPageQuery query);

	/**
	 * 查询置顶图片
	 * 
	 * @param outType
	 * @param outId
	 * @param limit
	 * @return
	 */
	List<PicturesDO> queryTopPictures(PictureOutType outType, long outId, int limit);

	/**
	 * 查询所有的图片
	 * 
	 * @param outType
	 * @param outId
	 * @return
	 */
	List<PicturesDO> queryAllPictures(PictureOutType outType, long outId);
}
