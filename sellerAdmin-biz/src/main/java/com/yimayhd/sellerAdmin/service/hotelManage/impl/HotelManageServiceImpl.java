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
import com.yimayhd.sellerAdmin.repo.HotelManageRepo;
import com.yimayhd.sellerAdmin.service.hotelManage.HotelManageService;
import org.apache.commons.collections.CollectionUtils;
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
	@Autowired
	private HotelManageRepo hotelManageRepo;

	private static final Logger log = LoggerFactory.getLogger(HotelManageServiceImpl.class);

	/**
	 * 查询酒店资源信息列表
	 * @param hotelMessageVO
	 * @return
     */
	@Override
	public WebResult<PageVO<HotelMessageVO>> queryHotelMessageVOListByData(final HotelMessageVO hotelMessageVO) {
		HotelManageDomainChecker domain = new HotelManageDomainChecker(hotelMessageVO);
		WebResult<PageVO<HotelMessageVO>> result= new  WebResult<PageVO<HotelMessageVO>>();
		domain.setPageResult(result);
		domain.setHotelMessageVO(hotelMessageVO);
		try{
			WebResult chekResult =  domain.checkHotelMessageVO();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOListByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			// 调用中台接口
			result = hotelManageRepo.queryHotelMessageVOListByDataRepo(domain);
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
			/***1.查询商品**/
			/***2.查询资源**/
			/***3.查询房型**/
			/***4.查询sku**/
		}catch(Exception e){
			e.printStackTrace();
			log.error("queryHotelMessageVOyData 查询商品信息异常");
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
	@Override
	public WebResult<HotelMessageVO> addHotelMessageVOByData(final HotelMessageVO hotelMessageVO){
		HotelManageDomainChecker domain = new HotelManageDomainChecker(hotelMessageVO);
		WebResult<HotelMessageVO> result = new WebResult<HotelMessageVO>();
		domain.setHotelMessageVO(hotelMessageVO);
		domain.setWebResult(result);
		try{
			WebResult chekResult = domain.checkAddHotelMessageVOByData();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.addHotelMessageVOByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}

			result= hotelManageRepo.addHotelMessageVOByData(domain);

		}catch(Exception e){
			e.printStackTrace();
			log.error("HotelManageServiceImpl.addHotelMessageVOByData  call interface exception ");
			return  WebResult.failure(WebReturnCode.SYSTEM_ERROR, "添加酒店商品信息异常");
		}
		return result;

	}



	/**
	 * 更新酒店商品信息
	 * @param hotelMessageVO
	 * @return
     */

	public  WebResult<Long> editHotelMessageVOByData(final HotelMessageVO hotelMessageVO){
		HotelManageDomainChecker domain = new HotelManageDomainChecker(hotelMessageVO);
		WebResult<Long> result = new WebResult<Long>();
		domain.setHotelMessageVO(hotelMessageVO);
		domain.setLongWebResult(result);
		try{
			WebResult chekResult = domain.checkAddHotelMessageVOByData();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.editHotelMessageVOByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			result= hotelManageRepo.editHotelMessageVOByData(domain);
		}catch(Exception e){
			log.error("HotelManageServiceImpl.editHotelMessageVOByData  call interface exception ");
			e.printStackTrace();
			return  WebResult.failure(WebReturnCode.SYSTEM_ERROR, "编辑酒店商品信息异常");
		}
		return result;
	}

	public ItemQueryService getItemQueryServiceRef() {
		return itemQueryServiceRef;
	}

	public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
		this.itemQueryServiceRef = itemQueryServiceRef;
	}
}
