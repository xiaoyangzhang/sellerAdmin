package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;

/**
 * 资源-景点Repo
 * 
 * @author yebin
 *
 */
public class ScenicRepo extends ResourceRepo {

	public PageVO<ScenicDO> pageQueryScenic(ScenicPageQuery query) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.pageQueryScenic", query);
		ICPageResult<ScenicDO> icPageResult = itemQueryServiceRef.pageQueryScenic(query);
		RepoUtils.resultLog(log, "itemQueryServiceRef.pageQueryScenic", icPageResult);
		int totalCount = icPageResult.getTotalCount();
		List<ScenicDO> scenicDOList = icPageResult.getList();
		if (scenicDOList == null) {
			scenicDOList = new ArrayList<ScenicDO>();
		}
		return new PageVO<ScenicDO>(query.getPageNo(), query.getPageSize(), totalCount, scenicDOList);

	}

	public ScenicDO updateScenic(ScenicAddNewDTO scenicaddnewdto) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.updateScenicNew");
		ICResult<ScenicDO> icResult = resourcePublishServiceRef.updateScenicNew(scenicaddnewdto);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.updateScenicNew", icResult);
		return icResult.getModule();
	}

	public ScenicDO getScenicById(long id) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.getScenic", id);
		ICResult<ScenicDO> icResult = itemQueryServiceRef.getScenic(id);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getScenic", icResult);
		return icResult.getModule();
	}

	public ScenicDO addScenic(ScenicAddNewDTO scenicadddto) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.addScenicNew");
		ICResult<ScenicDO> icResult = resourcePublishServiceRef.addScenicNew(scenicadddto);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.addScenicNew", icResult);
		return icResult.getModule();
	}
}
