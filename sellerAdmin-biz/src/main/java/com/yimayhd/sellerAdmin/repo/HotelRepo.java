package com.yimayhd.sellerAdmin.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.HotelService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.util.RepoUtils;

/**
 */
public class HotelRepo extends ResourceRepo {
	@Autowired
	private HotelService hotelServiceRef;

	public PageVO<HotelDO> pageQueryHotel(HotelPageQuery query) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.pageQueryHotel", query);
		ICPageResult<HotelDO> icPageResult = itemQueryServiceRef.pageQueryHotel(query);
		RepoUtils.resultLog(log, "itemQueryServiceRef.pageQueryHotel", icPageResult);
		int totalCount = icPageResult.getTotalCount();
		List<HotelDO> hotelDOList = icPageResult.getList();
		if (hotelDOList == null) {
			hotelDOList = new ArrayList<HotelDO>();
		}
		return new PageVO<HotelDO>(query.getPageNo(), query.getPageSize(), totalCount, hotelDOList);
	}

	public boolean updateHotel(HotelDO hotelDO) {
		RepoUtils.requestLog(log, "hotelServiceRef.updateHotel");
		ICResult<Boolean> icResult = hotelServiceRef.updateHotel(hotelDO);
		RepoUtils.resultLog(log, "hotelServiceRef.updateHotel", icResult);
		return icResult.getModule();
	}

	public HotelDO getHotelById(long id) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.getHotel", id);
		ICResult<HotelDO> icResult = itemQueryServiceRef.getHotel(id);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getHotel", icResult);
		return icResult.getModule();
	}

	public HotelDO addHotel(HotelDO hotelDO) {
		RepoUtils.requestLog(log, "hotelServiceRef.addHotel");
		ICResult<HotelDO> icResult = hotelServiceRef.addHotel(hotelDO);
		RepoUtils.resultLog(log, "hotelServiceRef.addHotel", icResult);
		return icResult.getModule();
	}
}
