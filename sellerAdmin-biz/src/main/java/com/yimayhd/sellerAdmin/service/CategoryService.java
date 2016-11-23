package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.result.item.CategoryTreeResult;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.CategoryVO;

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
	
	/**
	 * 
	 * @parameter
	 * @return
	 * @throws
	 */
	WebResult<CategoryDO> getCategoryByDomainId(int domainId);
	
	/**
	 * 
	 * @parameter
	 * @return
	 * @throws
	 */
	WebResult<CategoryDO> getCategoryById(long parentId);
	
	/**
	 * 查询子类目
	 * @param categoryId
	 * @param domainId
	 * @return
	 */
	List<CategoryDO> getChildrenCategories(long categoryId, int domainId);
	/**
	 * 查询用户可访问的商品类目
	 * @param sellerId
	 * @param categoryId
	 * @param domainId
	 * @return
	 */
	List<CategoryDO> getUserItemCategories(long sellerId, long categoryId, int domainId);
	
	List<CategoryDO> getTalentCategories(long selectedCategoryId, List<Long> accessCategoryIds, int domainId);
	/**
	 * 
	* created by zhangxy
	* @date 2016年6月28日
	* @Title: getCategoryTreeByDomainId 
	* @Description: TODO
	* @param @param domainId
	* @param @return    设定文件 
	* @return CategoryTreeResult    返回类型 
	* @throws
	 */
	WebResult<CategoryDO> getCategoryTreeByDomainId(int domainId);
	
	/**
	 * 查看是否有积分商品权限
	 * @param domainId
	 * @param categoryId
	 * @param sellerId
	 * @return
	 */
	MemResult<MerchantItemCategoryDO> getMerchantItemCategory(int domainId,long categoryId , long sellerId);

}
