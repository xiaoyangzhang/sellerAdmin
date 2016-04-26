package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.line.LinePubAddDTO;
import com.yimayhd.ic.client.model.param.item.line.LinePubUpdateDTO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.line.LinePublishService;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 * 线路Repo
 * 
 * @author yebin
 *
 */
public class LineRepo {
	private Logger				log	= LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService	itemQueryServiceRef;
	@Autowired
	private LinePublishService	linePublishServiceRef;

	/**
	 * ID查询
	 * 
	 * @param id
	 * @return
	 */
	public LineResult getLineByItemId(long sellerId, long itemId) {
		if (itemId <= 0) {
			return null;
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.getLineResult", itemId);
		LineResult lineResult = itemQueryServiceRef.getLineResultByItemId(itemId);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getLineResult", lineResult);
		ItemDO itemDO = lineResult.getItemDO();
		return itemDO.getSellerId() == sellerId ? lineResult : null;
	}

	/**
	 * 更新
	 * 
	 * @param travel
	 * @return
	 */
	public LinePublishResult updateLine(LinePubUpdateDTO linePubUpdateDTO) {
		long lineId = linePubUpdateDTO.getLine().getId();
		if (lineId <= 0) {
			throw new BaseException("更新线路时线路ID不能为空");
		}
		RepoUtils.requestLog(log, "linePublishServiceRef.update");
		LinePublishResult linePublishResult = linePublishServiceRef.update(Constant.DOMAIN_JIUXIU, linePubUpdateDTO);
		RepoUtils.resultLog(log, "linePublishServiceRef.update", linePublishResult);
		return linePublishResult;
	}

	/**
	 * 保存
	 * 
	 * @param travel
	 * @return
	 */
	public LinePublishResult saveLine(LinePubAddDTO linePubAddDTO) {
		RepoUtils.requestLog(log, "linePublishServiceRef.add");
		LinePublishResult linePublishResult = linePublishServiceRef.add(Constant.DOMAIN_JIUXIU, linePubAddDTO);
		RepoUtils.resultLog(log, "linePublishServiceRef.add", linePublishResult);
		return linePublishResult;
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
