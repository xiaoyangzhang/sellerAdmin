package com.yimayhd.sellerAdmin.biz.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;

public class CategoryHelper {
	
	public static List<CategoryDO> getCategories(List<CategoryDO> categories, List<Long> accessCategoryIds){
		if( CollectionUtils.isEmpty(categories) || CollectionUtils.isEmpty(accessCategoryIds) ){
			return null;
		}
		List<CategoryDO> accessCategories = new ArrayList<>() ;
		for( CategoryDO categoryDO : categories ){
			long id = categoryDO.getId() ;
			boolean leaf = categoryDO.getLeaf() ;
			if( !leaf ){
				//非叶子类目
				List<CategoryDO> children = categoryDO.getChildren() ;
				List<CategoryDO> accessChildren = getCategories(children, accessCategoryIds);
				if( !CollectionUtils.isEmpty(accessChildren)) {
					accessCategories.add(categoryDO);
				}
			}else{
				//叶子类目
				if( accessCategoryIds.contains(id) ){
					accessCategories.add(categoryDO);
				}
			}
		}
		return accessCategories ;
	}
	
//	
//	
//	
//	/**
//	 * 通过accessCategoryIds计算出在指定类目集合下，所有可访问的属性
//	 * <li>此接口支持的categoryDOs集合必须是层级关系的集合
//	 * @param categoryDOs
//	 * @param accessCategoryIds
//	 * @param currentCategoryId
//	 * @return
//	 */
//	public static List<CategoryDO> getChildrenCategories(List<CategoryDO> categoryDOs, List<Long> accessCategoryIds, long currentCategoryId){
//		if( CollectionUtils.isEmpty(categoryDOs) || CollectionUtils.isEmpty(accessCategoryIds) ){
//			return null;
//		}
//		//找出当前类目的子类目
//		List<CategoryDO> children = getCategoryChildren(categoryDOs, currentCategoryId);
//		if( CollectionUtils.isEmpty(children) ){
//			return null ;
//		}
//		List<CategoryDO> list = new ArrayList<>() ;
//		for( CategoryDO child : children ){
//			List<Long> leaftNodeIds = getCategoryLeaftNodeIds(child);
//			if( !CollectionUtils.isEmpty(leaftNodeIds) ){
//				for( Long nodeId : leaftNodeIds ){
//					if( accessCategoryIds.contains(nodeId) ){
//						list.add(child);
//						break;
//					}
//				}
//			}
//			
//		}
//		return list ;
//	}
//	
//	/**
//	 * 获取当前类目的所有子类目，此接口适用于categoryDOs为树形结构的类目
//	 * @param categoryDOs
//	 * @param currentCategoryId
//	 * @return
//	 */
//	public static List<CategoryDO> getCategoryChildren(List<CategoryDO> categoryDOs, long currentCategoryId){
//		if( CollectionUtils.isEmpty(categoryDOs) ){
//			return null;
//		}
//		for( CategoryDO categoryDO : categoryDOs ){
//			long categoryId = categoryDO.getId() ;
//			if( currentCategoryId == categoryId ){
//				return categoryDO.getChildren() ;
//			}
//		}
//		return null;
//	}
//	
//	/**
//	 * 获取类目下所有叶子类目的id
//	 * @param categoryDO
//	 * @return
//	 */
//	public static List<Long> getCategoryLeaftNodeIds(CategoryDO categoryDO){
//		if( null == categoryDO ){
//			return null ;
//		}
//		List<Long> nodeIds = new ArrayList<Long>() ;
//		List<CategoryDO> children = categoryDO.getChildren() ;
//		for( CategoryDO child : children ){
//			boolean leaft = categoryDO.getLeaf() ;
//			if( leaft ){
//				nodeIds.add(child.getId());
//			}else{
//				List<Long> nIds = getCategoryLeaftNodeIds(categoryDO);
//				if( !CollectionUtils.isEmpty(nIds) ){
//					nodeIds.addAll(nIds) ;
//				}
//			}
//		}
//		return nodeIds ;
//	}
	
}
