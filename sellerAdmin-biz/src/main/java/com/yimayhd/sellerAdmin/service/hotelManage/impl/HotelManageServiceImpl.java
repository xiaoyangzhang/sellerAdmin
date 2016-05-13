package com.yimayhd.sellerAdmin.service.hotelManage.impl;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.checker.HotelManageDomainChecker;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.service.hotelManage.HotelManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 酒店信息接口
 * Created by Administrator on 2015/11/2.
 */
public class HotelManageServiceImpl implements HotelManageService {
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	private static final Logger log = LoggerFactory.getLogger(HotelManageServiceImpl.class);

	/**
	 * 查询酒店信息列表
	 * @param hotelMessageVO
	 * @return
     */
	@Override
	public WebResult<PageVO<HotelMessageVO>> queryHotelMessageVOListByData(final HotelMessageVO hotelMessageVO) {
		HotelManageDomainChecker check = new HotelManageDomainChecker(hotelMessageVO);
		//WebResult <PageVO<HotelMessageVO>> result =new WebResult<PageVO<HotelMessageVO>>();
		try{
			WebResult chekResult =  check.checkHotelMessageVO();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOListByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			HotelPageQuery hotelPageQuery = check.getBizQueryModel();
			ICPageResult<HotelDO> callBack= itemQueryServiceRef.pageQueryHotel(hotelPageQuery);
			//PageVO<HotelMessageVO> pagetModel = new PageVO<HotelMessageVO>(callBack.getPageNo(),callBack.getPageSize(),callBack.getTotalCount(),callBack.getList());

		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询酒店商品信息
	 * @param hotelMessageVO
	 * @return
     */
	public WebResult<HotelMessageVO> queryHotelMessageVOyData(final HotelMessageVO hotelMessageVO){
		HotelManageDomainChecker check = new HotelManageDomainChecker(hotelMessageVO);
		try{
			WebResult chekResult = check.checkQueryHotelMessageVOInfo();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOyData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			//itemQueryServiceRef.getItem(long var1, ItemOptionDTO var3)
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;

	}

	public ItemQueryService getItemQueryServiceRef() {
		return itemQueryServiceRef;
	}

	public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
		this.itemQueryServiceRef = itemQueryServiceRef;
	}
}
