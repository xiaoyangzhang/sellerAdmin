package com.yimayhd.sellerAdmin.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 标签Repo
 * 
 * @author yebin
 *
 */
public class CategoryRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private CategoryService categoryServiceRef;

	public CategoryDO getCategoryById(long id) {
		RepoUtils.requestLog(log, "categoryServiceRef.getCategory", id);
		CategoryResult categoryResult = categoryServiceRef.getCategory(id);
		RepoUtils.resultLog(log, "categoryServiceRef.getCategory", categoryResult);
		return categoryResult.getCategroyDO();
	}

}
