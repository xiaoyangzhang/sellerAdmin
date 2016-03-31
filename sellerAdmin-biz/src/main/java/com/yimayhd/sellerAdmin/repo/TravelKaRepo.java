package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.membercenter.client.domain.TravelKaVO;
import com.yimayhd.membercenter.client.query.TravelkaPageQuery;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.TravelKaService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.util.RepoUtils;

public class TravelKaRepo {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TravelKaService travelKaServiceRef;

	public PageVO<TravelKaVO> pageQueryTravelKas(TravelkaPageQuery query) {
		RepoUtils.requestLog(log, "travelKaServiceRef.getTravelKaListManagerPage", query);
		MemPageResult<TravelKaVO> memResult = travelKaServiceRef.getTravelKaListManagerPage(query);
		RepoUtils.resultLog(log, "travelKaServiceRef.getTravelKaListManagerPage", memResult);
		List<TravelKaVO> itemList = memResult.getList();
		int totalCount = memResult.getTotalCount();
		if (itemList == null) {
			itemList = new ArrayList<TravelKaVO>();
		}
		return new PageVO<TravelKaVO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	public TravelKaVO getTravelKaById(long id) {
		RepoUtils.requestLog(log, "travelKaServiceRef.getTravelKaDetail", id);
		MemResult<TravelKaVO> memResult = travelKaServiceRef.getTravelKaDetail(id);
		RepoUtils.resultLog(log, "travelKaServiceRef.getTravelKaDetail", memResult);
		return memResult.getValue();
	}
}
