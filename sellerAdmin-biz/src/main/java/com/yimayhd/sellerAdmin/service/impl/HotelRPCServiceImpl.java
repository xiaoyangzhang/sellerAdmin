package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.HotelFacilityVO;
import com.yimayhd.sellerAdmin.model.HotelVO;
import com.yimayhd.sellerAdmin.model.PictureVO;
import com.yimayhd.sellerAdmin.model.query.HotelListQuery;
import com.yimayhd.sellerAdmin.service.HotelRPCService;
import com.yimayhd.sellerAdmin.service.TfsService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.ic.client.model.domain.FacilityIconDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.param.item.PictureUpdateDTO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.HotelService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HotelRPCServiceImpl implements HotelRPCService {

	private final static int PIC_PAGE_SIZE = 500;
	private final static int PIC_PAGE_NO = 1;
	private static final Logger log = LoggerFactory.getLogger(HotelRPCServiceImpl.class);

    @Autowired
    private ItemQueryService itemQueryServiceRef;
	
    @Autowired
    private HotelService hotelServiceRef;

	@Autowired
	private ResourcePublishService resourcePublishServiceRef;

	@Autowired
	private TfsService tfsService;

	@Override
	public PageVO<HotelDO> pageQueryHotel(HotelListQuery hotelListQuery)throws Exception {
		
		HotelPageQuery hotelPageQuery = new HotelPageQuery();
    	hotelPageQuery.setNeedCount(true);
		
		if (hotelListQuery.getPageNumber() != null) {
			int pageNumber = hotelListQuery.getPageNumber();
			int pageSize = hotelListQuery.getPageSize();
			hotelPageQuery.setPageNo(pageNumber);
			hotelPageQuery.setPageSize(pageSize);
		}
		
		//酒店名称
		if (!StringUtils.isBlank(hotelListQuery.getName())) {
			hotelPageQuery.setTags(hotelListQuery.getName());			
		}
		
		//酒店状态
		if (hotelListQuery.getHotelStatus() != 0) {			
			hotelPageQuery.setStatus(hotelListQuery.getHotelStatus());
		}
		
		//开始时间
		if (!StringUtils.isBlank(hotelListQuery.getBeginDate())) {
			Date startTime = DateUtil.parseDate(hotelListQuery.getBeginDate());
			hotelPageQuery.setStartTime(startTime);
		}
		
		//结束时间
		if (!StringUtils.isBlank(hotelListQuery.getEndDate())) {
			Date endTime = DateUtil.parseDate(hotelListQuery.getEndDate());
			hotelPageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		
		ICPageResult<HotelDO> icPageResult = itemQueryServiceRef.pageQueryHotel(hotelPageQuery);
    	List<HotelDO> hotelDOList = icPageResult.getList();
    	
		return new PageVO<HotelDO>(hotelPageQuery.getPageNo(), hotelPageQuery.getPageSize(), icPageResult.getTotalCount(), hotelDOList);
	}

	@Override
	public ICResult<Boolean> updateHotelStatus(HotelDO hotelDO)throws Exception {
		ICResult<Boolean> result = hotelServiceRef.updateHotelStatus(hotelDO);
		if(result == null){
			log.error("HotelRPCServiceImpl.updateHotelStatus-hotelService.updateHotelStatus result is null and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("酒店状态修改失败");
		}else if(!result.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotelStatus-hotelService.updateHotelStatus error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("酒店状态修改失败，" + result.getResultMsg());
		}
		return result;
	}

	@Override
	public ICResult<Boolean> addHotel(HotelVO hotelVO)throws Exception{


		ICResult<Boolean> result = new ICResult<Boolean>();

		HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);
		//needKonw中的extraInfoUrl(富文本编辑)
		if(StringUtils.isNotBlank(hotelDO.getNeedKnow().getExtraInfoUrl())){
			hotelDO.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(hotelDO.getNeedKnow().getExtraInfoUrl()));
		}

		ICResult<HotelDO> icResult = hotelServiceRef.addHotel(hotelDO);
		if(icResult == null){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel result is null and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("返回结果为空，酒店资源新增失败");
		}else if(!icResult.isSuccess()){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel error:" + JSON.toJSONString(icResult) + "and parame: " + JSON.toJSONString(hotelDO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果错误，酒店资源新增失败，" + icResult.getResultMsg());
		}
		//图片集insert
		if(StringUtils.isNotBlank(hotelVO.getPicListStr())){
			List<PictureVO> pictureVOList = JSON.parseArray(hotelVO.getPicListStr(),PictureVO.class);
			List<PicturesDO> picList = new ArrayList<PicturesDO>();
			for (PictureVO pictureVO:pictureVOList){
				PicturesDO picturesDO = new PicturesDO();
				picturesDO.setPath(pictureVO.getValue());
				picturesDO.setName(pictureVO.getName());
				picturesDO.setOutId(icResult.getModule().getId());
				picturesDO.setOutType(PictureOutType.HOTEL.getValue());
				//TODO picturesDO.setOrderNum(pictureVO.getIndex());
				picturesDO.setIsTop(pictureVO.isTop());
				picList.add(picturesDO);
			}
			ICResult<Boolean> icResultPic =  resourcePublishServiceRef.addPictures(picList);
			if(null == icResultPic){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addPictures result is null and parame: " + JSON.toJSONString(picList));
				throw new BaseException("酒店资源保存成功，图片集保存返回结果为空，保存失败");
			} else if(!icResultPic.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addPictures error:" + JSON.toJSONString(icResultPic) + "and parame: " + JSON.toJSONString(picList) + "and hotelVO:" + JSON.toJSONString(hotelVO));
				throw new BaseException("酒店资源保存成功，图片集保存失败" + icResultPic.getResultMsg());
			}
		}
		result.setModule(icResult.isSuccess());
		return result;
	}

	@Override
	public ICResult<Boolean> updateHotel(HotelVO hotelVO)throws Exception {
		//获取酒店资源
		ICResult<HotelDO> icResultDB = itemQueryServiceRef.getHotel(hotelVO.getId());
		if(icResultDB == null){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.getHotel result is null and parame: " + hotelVO.getId());
			throw new BaseException("返回结果为空，酒店资源修改失败");
		}else if(!icResultDB.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.getHotel error:" + JSON.toJSONString(icResultDB) + "and parame: " + hotelVO.getId());
			throw new BaseException("返回结果错误，酒店资源修改失败，" + icResultDB.getResultMsg());
		}
		HotelDO hotelDB = icResultDB.getModule();

		//数据转换
		HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);
		//对接

		//酒店名称
		hotelDB.setName(hotelDO.getName());
		//选择地址
		hotelDB.setLocationProvinceId(hotelDO.getLocationProvinceId());
		hotelDB.setLocationProvinceName(hotelDO.getLocationProvinceName());
		hotelDB.setLocationCityId(hotelDO.getLocationCityId());
		hotelDB.setLocationCityName(hotelDO.getLocationCityName());
		hotelDB.setLocationTownId(hotelDO.getLocationTownId());
		hotelDB.setLocationTownName(hotelDO.getLocationTownName());
		//星级
		hotelDB.setLevel(hotelDO.getLevel());
		//地址经纬度
		hotelDB.setLocationX(hotelDO.getLocationX());
		hotelDB.setLocationY(hotelDO.getLocationY());
		//酒店电话
		hotelDB.setPhoneNum(hotelDO.getPhoneNum());
		//酒店简介
		hotelDB.setDescription(hotelDO.getDescription());
		//特色描述
		hotelDB.setOneword(hotelDO.getOneword());
		//基础价格
		hotelDB.setPrice(hotelDO.getPrice());
		//酒店联系人
		hotelDB.setContactPerson(hotelDO.getContactPerson());
		hotelDB.setContactPhone(hotelDO.getContactPhone());
		//房间设施
		hotelDB.setRoomFacility(hotelDO.getRoomFacility());
		//特色服务
		hotelDB.setRoomService(hotelDO.getRoomService());
		//酒店设施
		hotelDB.setHotelFacility(hotelDO.getHotelFacility());
		//最晚到店时间
		hotelDB.setOpenTime(hotelDO.getOpenTime());
		//入住须知
		hotelDB.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(hotelDO.getNeedKnow().getExtraInfoUrl()));
		hotelDB.getNeedKnow().setFrontNeedKnow(hotelDO.getNeedKnow().getFrontNeedKnow());
		//推荐理由
		hotelDB.setRecommend(hotelDO.getRecommend());

		//列表页展示图
		hotelDB.setLogoUrl(hotelDO.getLogoUrl());
		//详情页展示图
		hotelDB.setPicturesString(hotelVO.getPicturesStr());
		ICResult<Boolean> icResultUpdate = hotelServiceRef.updateHotel(hotelDO);
		if(icResultUpdate == null){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.updateHotel result is null and parame: " + JSON.toJSONString(hotelDB));
			throw new BaseException("返回结果为空，酒店资源修改失败");
		}else if(!icResultUpdate.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.updateHotel error:" + JSON.toJSONString(icResultUpdate) + "and parame: " + JSON.toJSONString(hotelDB) + "and hotelVO:" + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果错误，酒店资源修改失败，" + icResultUpdate.getResultMsg());
		}
		if(StringUtils.isNotBlank(hotelVO.getPicListStr())){
			hotelVO.setPictureList(JSON.parseArray(hotelVO.getPicListStr(),PictureVO.class));
		}
		if(CollectionUtils.isNotEmpty(hotelVO.getPictureList())) {
			//获取图片
			PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
			picturesPageQuery.setOutId(hotelVO.getId());
			picturesPageQuery.setPageNo(PIC_PAGE_NO);
			picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
			picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
			picturesPageQuery.setOutType(PictureOutType.HOTEL.getValue());
			ICPageResult<PicturesDO> icPageResult = itemQueryServiceRef.queryPictures(picturesPageQuery);
			if (icPageResult == null) {
				log.error("HotelRPCServiceImpl.updateHotel-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
				throw new BaseException("返回结果为空，获取酒店资源图片失败");
			} else if (!icPageResult.isSuccess()) {
				log.error("HotelRPCServiceImpl.updateHotel-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + hotelVO.getId());
				throw new BaseException("返回结果错误，获取酒店资源图片失败，" + icPageResult.getResultMsg());
			}
			List<PicturesDO> picturesDOList = icPageResult.getList();
			//图片集合处理
			PictureUpdateDTO pictureUpdateDTO = new PictureUpdateDTO();
			if(PictureVO.setPictureListPictureUpdateDTO(hotelVO.getId(),PictureOutType.HOTEL,pictureUpdateDTO, picturesDOList,hotelVO.getPictureList()) != null){
				ICResult<Boolean> updatePictrueResult = resourcePublishServiceRef.updatePictures(pictureUpdateDTO);
				if(null == updatePictrueResult){
					log.error("HotelRPCServiceImpl.save-ResourcePublishService.updatePictures result is null and parame: " + JSON.toJSONString(pictureUpdateDTO));
					throw new BaseException("酒店资源保存成功，图片集保存返回结果为空，保存失败");
				} else if(!updatePictrueResult.isSuccess()){
					log.error("HotelRPCServiceImpl.save-ResourcePublishService.updatePictures error:" + JSON.toJSONString(updatePictrueResult) + "and parame: " + JSON.toJSONString(pictureUpdateDTO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
					throw new BaseException("酒店资源保存成功，图片集保存失败" + updatePictrueResult.getResultMsg());
				}
			}
		}
		return null;
	}

	@Override
	public HotelVO getHotel(long id)throws Exception {
		
		ICResult<HotelDO> icResult = itemQueryServiceRef.getHotel(id);
		if(icResult == null){
			log.error("HotelRPCServiceImpl.getHotel-hotelService.getHotel result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取酒店资源失败");
		}else if(!icResult.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-hotelService.getHotel error:" + JSON.toJSONString(icResult) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取酒店资源失败，" + icResult.getResultMsg());
		}
		HotelDO hotelDO = icResult.getModule();
		//获取图片
		PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
		picturesPageQuery.setOutId(id);
		picturesPageQuery.setPageNo(PIC_PAGE_NO);
		picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
		picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
		picturesPageQuery.setOutType(PictureOutType.HOTEL.getValue());
		ICPageResult<PicturesDO> icPageResult = itemQueryServiceRef.queryPictures(picturesPageQuery);
		if(icPageResult == null){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
			throw new BaseException("返回结果为空，获取酒店资源图片失败");
		}else if(!icPageResult.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + id);
			throw new BaseException("返回结果错误，获取酒店资源图片失败，" + icPageResult.getResultMsg());
		}
		List<PicturesDO> picturesDOList = icPageResult.getList();
		List<PictureVO> pictureVOList = new ArrayList<PictureVO>();
		if(CollectionUtils.isNotEmpty(picturesDOList)){
			for(PicturesDO picturesDO : picturesDOList){
				PictureVO pictureVO = new PictureVO();
				pictureVO.setId(picturesDO.getId());
				pictureVO.setName(picturesDO.getName());
				pictureVO.setValue(picturesDO.getPath());
				pictureVO.setIsTop(picturesDO.isTop());
				pictureVOList.add(pictureVO);

			}
		}
		HotelVO hotelVO = HotelVO.getHotelVO(hotelDO);
		hotelVO.setPictureList(pictureVOList);
		return hotelVO;
	}

	@Override
	public List<HotelFacilityVO> queryFacilities(int type) throws Exception{
		
		ICPageResult<FacilityIconDO> icPageResult = itemQueryServiceRef.queryFacilities(type);
		List<FacilityIconDO> list = icPageResult.getList();		
		List<HotelFacilityVO> resultList = new ArrayList<HotelFacilityVO>();
		Iterator<FacilityIconDO> it = list.iterator();
		
		while (it.hasNext()) {			
			FacilityIconDO temp = it.next();
			HotelFacilityVO vTemp = new HotelFacilityVO();
			vTemp.setName(temp.getName());
			vTemp.setNumber(temp.getNumber());
			resultList.add(vTemp);
		}
		
		return resultList;
	}

	@Override
	public void setHotelStatusList(List<Long> idList, int hotelStatus) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHotelStatus(long id, int hotelStatus) throws Exception {
		// TODO Auto-generated method stub
		
	}	
	
}
