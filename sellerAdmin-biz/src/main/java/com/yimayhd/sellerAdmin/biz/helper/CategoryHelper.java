package com.yimayhd.sellerAdmin.biz.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;

public class CategoryHelper {
	
	
	
	
	/**
	 * 通过accessCategoryIds计算出在指定类目集合下，所有可访问的属性
	 * <li>此接口支持的categoryDOs集合必须是层级关系的集合
	 * @param categoryDOs
	 * @param accessCategoryIds
	 * @param currentCategoryId
	 * @return
	 */
	public static List<CategoryDO> getChildrenCategories(List<CategoryDO> categoryDOs, List<Long> accessCategoryIds, long currentCategoryId){
		if( CollectionUtils.isEmpty(categoryDOs) || CollectionUtils.isEmpty(accessCategoryIds) ){
			return null;
		}
		//找出当前类目的子类目
		List<CategoryDO> children = getCategoryChildren(categoryDOs, currentCategoryId);
		if( CollectionUtils.isEmpty(children) ){
			return null ;
		}
		List<CategoryDO> list = new ArrayList<>() ;
		for( CategoryDO child : children ){
			List<Long> leaftNodeIds = getCategoryLeaftNodeIds(child);
			if( !CollectionUtils.isEmpty(leaftNodeIds) ){
				for( Long nodeId : leaftNodeIds ){
					if( accessCategoryIds.contains(nodeId) ){
						list.add(child);
						break;
					}
				}
			}
			
		}
		return list ;
	}
	
	/**
	 * 获取当前类目的所有子类目，此接口适用于categoryDOs为树形结构的类目
	 * @param categoryDOs
	 * @param currentCategoryId
	 * @return
	 */
	public static List<CategoryDO> getCategoryChildren(List<CategoryDO> categoryDOs, long currentCategoryId){
		if( CollectionUtils.isEmpty(categoryDOs) ){
			return null;
		}
		for( CategoryDO categoryDO : categoryDOs ){
			long categoryId = categoryDO.getId() ;
			if( currentCategoryId == categoryId ){
				return categoryDO.getChildren() ;
			}
		}
		return null;
	}
	
	/**
	 * 获取类目下所有叶子类目的id
	 * @param categoryDO
	 * @return
	 */
	public static List<Long> getCategoryLeaftNodeIds(CategoryDO categoryDO){
		if( null == categoryDO ){
			return null ;
		}
		List<Long> nodeIds = new ArrayList<Long>() ;
		List<CategoryDO> children = categoryDO.getChildren() ;
		for( CategoryDO child : children ){
			boolean leaft = categoryDO.getLeaf() ;
			if( leaft ){
				nodeIds.add(child.getId());
			}else{
				List<Long> nIds = getCategoryLeaftNodeIds(categoryDO);
				if( !CollectionUtils.isEmpty(nIds) ){
					nodeIds.addAll(nIds) ;
				}
			}
		}
		return nodeIds ;
	}
//	
//	/**
//	 * 适用于平铺的类目结构
//	 * @param categoryDOs
//	 * @param accessCategoryIds
//	 * @param currentCategoryId
//	 * @return
//	 */
//	public static List<CategoryDO> getCategories(List<CategoryDO> categoryDOs, List<Long> accessCategoryIds, long currentCategoryId){
//		if( CollectionUtils.isEmpty(categoryDOs) || CollectionUtils.isEmpty(accessCategoryIds) ){
//			return null;
//		}
//		
//		List<CategoryDO> list = new ArrayList<>() ;
//		Map<Long, CategoryDO> map = mapCategories(categoryDOs);
//		for( Long accessCategoryId : accessCategoryIds ){
//			CategoryDO parent = map.get(accessCategoryId);
//			if( parent != null ){
//				long id = parent.getId() ;
//				if( currentCategoryId == id ){
//					
//				}else{
//					
//				}
//				long grantParentId = parent.getParentId() ;
//			}
//		}
////		List<CategoryDO> children = getChildrenCategory(categoryDOs, currentCategoryId);
////		for( CategoryDO child : children ){
////			List<Long> leaftNodeIds = getCategoryLeaftNodeIds(child);
////			if( !CollectionUtils.isEmpty(leaftNodeIds) ){
////				for( Long nodeId : leaftNodeIds ){
////					if( accessCategoryIds.contains(nodeId) ){
////						list.add(child);
////						break;
////					}
////				}
////			}
////			
////		}
//		return list ;
//	}
//	
//	
//	public static CategoryDO getTargetCategory(Map<Long, CategoryDO> map, long curentCategoryId){
//		if( map == null ){
//			return null;
//		}
//		
//	}
//	
//	
//	
//	
//	public static boolean isChildCategory(List<CategoryDO> categoryDOs, long currentCategoryId){
//		if( CollectionUtils.isEmpty(categoryDOs) ){
//			return false ;
//		}
//		for( CategoryDO categoryDO : categoryDOs ){
//			if( currentCategoryId == categoryDO.getParentId() ){
//				return true;
//			}
//		}
//		return true ;
//	}
//	 
//	
////	public static List<CategoryDO> getChildrenCategory(List<CategoryDO> categoryDOs, long currentCategoryId){
////		if( CollectionUtils.isEmpty(categoryDOs) ){
////			return null ;
////		}
////		List<CategoryDO> nodes = new ArrayList<CategoryDO>() ;
////		for( CategoryDO categoryDO : categoryDOs ){
////			if( currentCategoryId == categoryDO.getParentId() ){
////				nodes.add(categoryDO);
////			}
////		}
////		return nodes ;
////	}
//	
//	public static Map<Long, CategoryDO> mapCategories(List<CategoryDO> categoryDOs){
//		if( CollectionUtils.isEmpty(categoryDOs) ){
//			return null ;
//		}
//		Map<Long, CategoryDO> map = new HashMap<>() ;
//		for( CategoryDO categoryDO : categoryDOs ){
//			long parentId = categoryDO.getParentId() ;
//			CategoryDO parent = null;
//			if( parentId > 0 ){
//				parent = getCategory(categoryDOs, parentId);
//			}
//			map.put(categoryDO.getId(), parent);
//		}
//		return map ;
//	}
//	
//	
//	public static CategoryDO getCategory(List<CategoryDO> categoryDOs, long categoryId){
//		if( CollectionUtils.isEmpty(categoryDOs) ){
//			return null;
//		}
//		for( CategoryDO categoryDO : categoryDOs ){
//			long cId = categoryDO.getId() ;
//			if( cId == categoryId ){
//				return categoryDO ;
//			}
//		}
//		return null;
//	}
//	
	
}
