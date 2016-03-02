package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.util.RepoUtils;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;

/**
 * 线路Repo
 * 
 * @author yebin
 *
 */
public class LineRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private ItemQueryService itemQueryServiceRef;
	@Resource
	private ItemPublishService itemPublishServiceRef;

	/**
	 * ID查询
	 * 
	 * @param id
	 * @return
	 */
	public LineResult getLineById(long id) {
		if (id <= 0) {
			return null;
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.getLineResult", id);
		LineResult lineResult = itemQueryServiceRef.getLineResult(id);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getLineResult", lineResult);
		return lineResult;
	}

	/**
	 * 更新
	 * 
	 * @param travel
	 * @return
	 */
	public LinePublishResult updateLine(LinePublishDTO linePublishDTO) {
		long lineId = linePublishDTO.getLineDO().getId();
		if (lineId <= 0) {
			throw new BaseException("更新线路时线路ID不能为空");
		}
		RepoUtils.requestLog(log, "itemPublishServiceRef.updatePublishLine");
		LinePublishResult publishLine = itemPublishServiceRef.updatePublishLine(linePublishDTO);
		RepoUtils.resultLog(log, "itemPublishServiceRef.updatePublishLine", publishLine);
		return publishLine;
	}

	/**
	 * 保存
	 * 
	 * @param travel
	 * @return
	 */
	public LinePublishResult saveLine(LinePublishDTO linePublishDTO) {
		RepoUtils.requestLog(log, "itemPublishServiceRef.publishLine");
		LinePublishResult publishLine = itemPublishServiceRef.publishLine(linePublishDTO);
		RepoUtils.resultLog(log, "itemPublishServiceRef.publishLine", publishLine);
		return publishLine;
	}

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	public PageVO<LineDO> pageQueryLine(LinePageQuery query) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.pageQueryLine", query);
		ICPageResult<LineDO> pageQueryLine = itemQueryServiceRef.pageQueryLine(query);
		RepoUtils.resultLog(log, "itemQueryServiceRef.pageQueryLine", pageQueryLine);
		int totalCount = pageQueryLine.getTotalCount();
		List<LineDO> itemList = pageQueryLine.getList();
		if (itemList == null) {
			itemList = new ArrayList<LineDO>();
		}
		return new PageVO<LineDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}
}
