package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;

/**
 * Created by Administrator on 2015/11/25.
 */
public interface CategoryService {
	/**
	 * 获取品类根列表
	 * 
	 * @return 品类根列表
	 * @throws Exception
	 */
	List<CategoryDO> getCategoryDOList();

	/**
	 * 根据父id获取子品类列表
	 * 
	 * @param parentId
	 *            品类ID
	 * @return 品类列表
	 * @throws Exception
	 */
	List<CategoryDO> getCategoryDOList(long parentId);

	/**
	 * 根据品类id获取品类
	 * 
	 * @param id
	 *            品类ID
	 * @return 品类
	 * @throws Exception
	 */
	CategoryVO getCategoryVOById(long id) throws Exception;

	/**
	 * 根据品类id获取品类
	 * 
	 * @param id
	 *            品类ID
	 * @return 品类
	 * @throws Exception
	 */
	CategoryDO getCategoryDOById(long id) throws Exception;

}
