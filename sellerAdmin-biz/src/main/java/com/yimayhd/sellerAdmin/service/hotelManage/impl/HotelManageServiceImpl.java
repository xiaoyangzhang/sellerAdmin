package com.yimayhd.sellerAdmin.service.hotelManage.impl;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.HotelManageDomainChecker;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.service.hotelManage.HotelManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 酒店信息接口
 * Created by wangdi on 2015/11/2.
 */
public class HotelManageServiceImpl implements HotelManageService {
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	private static final Logger log = LoggerFactory.getLogger(HotelManageServiceImpl.class);

	/**
	 * 查询酒店资源信息列表
	 * @param hotelMessageVO
	 * @return
     */
	@Override
	public WebResult<PageVO<HotelDO>> queryHotelMessageVOListByData(final HotelMessageVO hotelMessageVO) {
		HotelManageDomainChecker check = new HotelManageDomainChecker(hotelMessageVO);
		WebResult <PageVO<HotelDO>> result =new WebResult<PageVO<HotelDO>>();
		try{
			WebResult chekResult =  check.checkHotelMessageVO();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOListByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			HotelPageQuery hotelPageQuery = check.getBizQueryModel();
			ICPageResult<HotelDO> callBack= itemQueryServiceRef.pageQueryHotel(hotelPageQuery);

			PageVO<HotelDO> pagetModel = new PageVO<HotelDO>(callBack.getPageNo(),callBack.getPageSize(),callBack.getTotalCount(),callBack.getList());
			result.setValue(pagetModel);
		}catch(Exception e){
			e.printStackTrace();
			result.failure(WebReturnCode.SYSTEM_ERROR,"查询酒店资源列表系统异常");
		}
		return result;
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

	/**
	 * 酒店房型列表
	 * @param hotelMessageVO
	 * @return
     */
	public WebResult<PageVO<HotelDO>> queryRoomTypeListByData(final HotelMessageVO hotelMessageVO) {
		HotelManageDomainChecker check = new HotelManageDomainChecker(hotelMessageVO);
		try{
			WebResult chekResult = check.checkQueryHotelMessageVOInfo();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryRoomTypeListByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 酒店商品信息插入
	 * @param hotelMessageVO
	 * @return
     */
	public WebResult<HotelMessageVO> addHotelMessageVOByData(final HotelMessageVO hotelMessageVO){
		HotelManageDomainChecker check = new HotelManageDomainChecker(hotelMessageVO);

		return null;

	}



	/**
	 * 更新酒店商品信息
	 * @param hotelMessageVO
	 * @return
     */
	public  WebResult<Boolean> editHotelMessageVOByData(final HotelMessageVO hotelMessageVO){
		return null;
	}

	public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
		this.itemQueryServiceRef = itemQueryServiceRef;
	}
}
