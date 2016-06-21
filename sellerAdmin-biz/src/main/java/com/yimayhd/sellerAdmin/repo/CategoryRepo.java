package com.yimayhd.sellerAdmin.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.result.item.CategoryQryResult;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.model.result.item.CategoryTreeResult;
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

	@MethodLogger
	public CategoryDO getCategoryById(long id) {
		RepoUtils.requestLog(log, "categoryServiceRef.getCategory", id);
		CategoryResult categoryResult = categoryServiceRef.getCategory(id);
		RepoUtils.resultLog(log, "categoryServiceRef.getCategory", categoryResult);
		return categoryResult.getCategroyDO();
	}

	/**
	 * 查询类目列表
	 * @parameter
	 * @return
	 * @throws
	 */
	@MethodLogger
	public CategoryDO getCategoryByDomainId(int domainId){
		RepoUtils.requestLog(log, "categoryServiceRef.getCategoryTreeByDomain", domainId);
		CategoryTreeResult ret = categoryServiceRef.getCategoryTreeByDomain(domainId);
		RepoUtils.resultLog(log, "categoryServiceRef.getCategoryTreeByDomain", ret);
		return ret.getTree();
	}
	
	/**
	 * 获取二级类目
	 * @return
	 */
	@MethodLogger
	public List<CategoryDO> getCategoryChildrenByDomain(int domain){
		RepoUtils.requestLog(log, "categoryServiceRef.getCategoryChildrenByDomain", domain);
		CategoryQryResult ret = categoryServiceRef.getCategoryChildrenByDomain(domain);
		RepoUtils.resultLog(log, "categoryServiceRef.getCategoryChildrenByDomain", ret);
		return ret.getCategroyDOList();
	}
}
