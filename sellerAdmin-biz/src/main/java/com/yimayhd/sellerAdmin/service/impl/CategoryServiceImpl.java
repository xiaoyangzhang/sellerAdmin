package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.result.item.CategoryQryResult;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.model.result.item.CategoryTreeResult;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.CategoryVO;
import com.yimayhd.sellerAdmin.repo.CategoryRepo;
import com.yimayhd.sellerAdmin.repo.ItemRepo;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * Created by Administrator on 2015/11/25.
 */
public class CategoryServiceImpl implements CategoryService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private com.yimayhd.ic.client.service.item.CategoryService categoryServiceRef;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ItemRepo itemRepo;

	@Override
	public List<CategoryDO> getCategoryDOList() {
		RepoUtils.requestLog(log, "categoryServiceRef.getCategoryList");
		CategoryQryResult categoryQryResult = categoryServiceRef.getCategoryList();
		RepoUtils.resultLog(log, "categoryServiceRef.getCategoryList", categoryQryResult);
		// TODO
		return categoryQryResult.getCategroyDOList();
		/*
		 * List<CategoryDO> categoryDOList = new ArrayList<CategoryDO>(); for
		 * (int i = 0; i < 4; i++) { CategoryDO categoryDOData = new
		 * CategoryDO(); categoryDOData.setId(i); categoryDOData.setName("普通商品"
		 * + i); categoryDOData.setLeaf(false); categoryDOData.setLevel(1);
		 * categoryDOData.setParentId(0); categoryDOList.add(categoryDOData); }
		 * return categoryDOList;
		 */
	}

	@Override
	public List<CategoryDO> getCategoryDOList(long parentId) {
		RepoUtils.requestLog(log, "categoryServiceRef.getCategoryChildren", parentId);
		CategoryResult categoryResult = categoryServiceRef.getCategory(parentId);
		RepoUtils.resultLog(log, "categoryServiceRef.getCategoryChildren", categoryResult);
		if (categoryResult == null || categoryResult.getCategroyDO() == null) {
			return null;
		}
		return categoryResult.categroyDO.getChildren();
		/*
		 * List<CategoryDO> categoryDOList = new ArrayList<CategoryDO>(); for
		 * (int i = 0; i < 4; i++) { CategoryDO categoryDOData = new
		 * CategoryDO(); categoryDOData.setId(10 + i);
		 * categoryDOData.setName("详细商品" + i); categoryDOData.setLeaf(0 == i % 2
		 * ? false:true); categoryDOData.setLevel(2);
		 * categoryDOData.setParentId(parentId);
		 * categoryDOList.add(categoryDOData); } return categoryDOList;
		 */
	}

	@Override
	public CategoryDO getCategoryDOById(long id)throws Exception{
		return categoryRepo.getCategoryById(id);
	}

	@Override
	public CategoryVO getCategoryVOById(long id) throws Exception {
		CategoryDO category = getCategoryDOById(id);
		if (category != null) {
			return CategoryVO.getCategoryVO(category);
		} else {
			return null;
		}
	}
	
	@Override
	public WebResult<CategoryDO> getCategoryByDomainId(int domainId) {
		WebResult<CategoryDO> ret = new WebResult<CategoryDO>();
		CategoryDO categoryDO = categoryRepo.getCategoryByDomainId(domainId);
		if( null == categoryDO ){
			return ret;
		}
		ret.setValue(categoryDO);
		return ret;
	}

	@Override
	public WebResult<CategoryDO> getCategoryById(int parentId) {
		WebResult<CategoryDO> ret = new WebResult<CategoryDO>();
		CategoryDO categoryDO = categoryRepo.getCategoryById(parentId);
		if( null == categoryDO ){
			return ret;
		}
		ret.setValue(categoryDO);
		return ret;
	}

	@Override
	public WebResult<CategoryDO> getCategoryTreeByDomainId(int domainId) {
		WebResult<CategoryDO> result = new WebResult<CategoryDO>();
		CategoryTreeResult categoryTreeResult = categoryServiceRef.getCategoryTreeByDomain(domainId);
		if (categoryTreeResult == null || !categoryTreeResult.isSuccess()) {
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR);
		}
		result.setValue(categoryTreeResult.getTree());
		return result;
	}

	@Override
	public MemResult<List<MerchantItemCategoryDO>> getMerchantItemCategory(
			int domainId, long categoryId, long sellerId) {
		return itemRepo.getMerchantItemCategory(domainId, categoryId, sellerId);
	}
}
