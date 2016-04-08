package com.yimayhd.sellerAdmin.service;

import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.line.CityVO;
import com.yimayhd.sellerAdmin.model.line.TagDTO;

import java.util.List;

/**
 * 标签服务
 * 
 * @author yebin
 *
 */
public interface TagService {

	/**
	 * 获取全部主题
	 * 
	 * @return
	 */
	WebResult<List<TagDTO>> getAllThemes(TagType tagType);

	/**
	 * 获取全部出发地
	 * 
	 * @return
	 */
	WebResult<List<CityVO>> getAllDeparts();

	/**
	 * 获取全部目的地
	 * 
	 * @return
	 */
	WebResult<List<CityVO>> getAllDests();

}
