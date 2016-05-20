package com.yimayhd.sellerAdmin.service.hotelManage.impl;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.RoomQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
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

import java.util.List;
 

/**
 * 酒店信息接口
 * Created by wangdi on 2015/11/2.
 */
public class HotelManageServiceImpl implements HotelManageService {
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private ItemPublishService itemPublishServiceRef;

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
			if(callBack==null){
				throw new  Exception("查询pageQueryHotel返回结果异常");
			}
			PageVO<HotelDO> pageModel = new PageVO<HotelDO>(callBack.getPageNo(),callBack.getPageSize(),callBack.getTotalCount(),callBack.getList());
			result.setValue(pageModel);
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
			WebResult chekResult = check.checkQueryHotelMessageVOyData();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOyData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			// 参数?ItemOptionDTO,返回?result
			ItemResult result= itemQueryServiceRef.getItem(hotelMessageVO.getItemId(), new ItemOptionDTO());
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
	@Override
	public WebResult<List<RoomDO>> queryRoomTypeListByData(final HotelMessageVO hotelMessageVO) {
		HotelManageDomainChecker check = new HotelManageDomainChecker(hotelMessageVO);
		WebResult<List<RoomDO>> roomResult = new WebResult<List<RoomDO>>();
		try{
			WebResult chekResult = check.checkQueryHotelMessageInfo();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryRoomTypeListByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			RoomQuery roomQuery = new RoomQuery();
			roomQuery.setHotelId(hotelMessageVO.getHotelId());
			ICResult<List<RoomDO>> result= itemQueryServiceRef.queryAllRoom( roomQuery);
			roomResult.setValue(result.getModule());
		}catch(Exception e){
			e.printStackTrace();
			log.error("queryRoomTypeListByData 查询酒店房型异常");

		}
		return roomResult;
	}

	/**
	 * 酒店商品信息插入
	 * @param hotelMessageVO
	 * @return
     */
	public WebResult<HotelMessageVO> addHotelMessageVOByData(final HotelMessageVO hotelMessageVO){
		HotelManageDomainChecker check = new HotelManageDomainChecker(hotelMessageVO);
		ItemPubResult result = itemPublishServiceRef.publishCommonItem(new CommonItemPublishDTO());
		return null;

	}



	/**
	 * 更新酒店商品信息
	 * @param hotelMessageVO
	 * @return
     */
	public  WebResult<Boolean> editHotelMessageVOByData(final HotelMessageVO hotelMessageVO){
		ItemPubResult result = itemPublishServiceRef.updatePublishCommonItem(new CommonItemPublishDTO());
		return null;
	}

	public ItemQueryService getItemQueryServiceRef() {
		return itemQueryServiceRef;
	}

	public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
		this.itemQueryServiceRef = itemQueryServiceRef;
	}
}
