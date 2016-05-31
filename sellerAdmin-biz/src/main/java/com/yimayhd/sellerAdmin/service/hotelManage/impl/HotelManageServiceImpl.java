package com.yimayhd.sellerAdmin.service.hotelManage.impl;

import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.HotelManageDomainChecker;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;
import com.yimayhd.sellerAdmin.repo.HotelManageRepo;
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
			System.out.println(3);
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOListByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			System.out.println(4);
			// 调用中台接口
			result = hotelManageRepo.queryHotelMessageVOListByDataRepo(domain);
		}catch(Exception e){
			e.printStackTrace();
			log.error("查询酒店资源信息错误",e);
			result.failure(WebReturnCode.SYSTEM_ERROR,"查询酒店资源列表系统异常");
		}
		return result;
	}

	/**
	 * 查询酒店商品信息
	 * @param hotelMessageVO
	 * @return
     */
	@Override
	public WebResult<HotelMessageVO> queryHotelMessageVOyData(final HotelMessageVO hotelMessageVO){
		HotelManageDomainChecker domain = new HotelManageDomainChecker(hotelMessageVO);
		WebResult<HotelMessageVO>  result = new WebResult<HotelMessageVO>();
		domain.setWebResult(result);
		try{
			WebResult chekResult = domain.checkQueryHotelMessageVOyData();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOyData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			 result = hotelManageRepo.queryHotelMessageVOyData(domain);
			if(!result.isSuccess()){
				log.error("HotelManageServiceImpl.queryHotelMessageVOyData is fail. code={}, message={} ",
						result.getErrorCode(), result.getResultMsg());
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("queryHotelMessageVOyData 查询商品信息异常",e);
		}

		return result;

	}

	/**
	 *
	 * 酒店房型列表
	 * @param hotelMessageVO
	 * @return
     */
	@Override
	public WebResult<List<RoomMessageVO>> queryRoomTypeListByData(final HotelMessageVO hotelMessageVO) {
		HotelManageDomainChecker domain = new HotelManageDomainChecker(hotelMessageVO);
		WebResult<List<RoomMessageVO>> roomResult = new WebResult<List<RoomMessageVO>>();
		domain.setListRoomMessageVOResult(roomResult);
		domain.setHotelMessageVO(hotelMessageVO);
		try{
			WebResult chekResult = domain.checkQueryHotelMessageInfo();
			if(!chekResult.isSuccess()){
				log.error("HotelManageServiceImpl.queryRoomTypeListByData is fail. code={}, message={} ",
						chekResult.getErrorCode(), chekResult.getResultMsg());
				return chekResult;
			}
			roomResult = hotelManageRepo.queryRoomTypeListByData(domain);
		}catch(Exception e){
			e.printStackTrace();
			log.error("queryRoomTypeListByData 查询酒店房型异常",e);
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
			log.error("HotelManageServiceImpl.addHotelMessageVOByData  call interface exception ",e);
			return  WebResult.failure(WebReturnCode.SYSTEM_ERROR, "添加酒店商品信息异常");
		}
		return result;

	}



	/**
	 * 更新酒店商品信息
	 * @param hotelMessageVO
	 * @return
     */
	@Override
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
			log.error("HotelManageServiceImpl.editHotelMessageVOByData  call interface exception ",e);
			e.printStackTrace();
			return  WebResult.failure(WebReturnCode.SYSTEM_ERROR, "编辑酒店商品信息异常");
		}
		return result;
	}

}
