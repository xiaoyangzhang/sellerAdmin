package com.yimayhd.sellerAdmin.repo;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.CategoryFeature;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.HotelPublishAddDTO;
import com.yimayhd.ic.client.model.param.item.HotelPublishUpdateDTO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.RoomQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.HotelManageDomainChecker;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wzf
 *
 */
public class HotelManageRepo {
	private Logger log = LoggerFactory.getLogger("hotelManage-business.log");
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private ItemPublishService itemPublishServiceRef;
	@Autowired
	private CategoryService categoryServiceRef;


	/**
	 * 酒店列表查询
	 * @param domain
	 * @return
	 * @throws Exception
     */
	@MethodLogger
	public WebResult <PageVO<HotelMessageVO>>  queryHotelMessageVOListByDataRepo(HotelManageDomainChecker domain) throws Exception {
		HotelMessageVO hotelMessageVO =domain.getHotelMessageVO();
		WebResult<PageVO<HotelMessageVO>> result = domain.getPageResult();
		HotelPageQuery hotelPageQuery = domain.getBizQueryModel();
		hotelPageQuery.setDomain(Constant.DOMAIN_JIUXIU);
		hotelPageQuery.setStatus(ItemStatus.valid.getValue());
		hotelPageQuery.setNeedCount(true);
		log.info("queryHotelMessageVOListByDataRepo.pageQueryHotel 入参: hotelPageQuery="+CommonJsonUtil.objectToJson(hotelPageQuery,HotelPageQuery.class));
		ICPageResult<HotelDO> callBack = itemQueryServiceRef.pageQueryHotel(hotelPageQuery);
		if (callBack == null) {
			log.error("查询pageQueryHotel返回结果异常");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "查询pageQueryHotel返回结果异常");
		}
		List<HotelDO> callBackList = callBack.getList();
		//System.out.println(CommonJsonUtil.objectToJson(callBackList,List.class));
		log.info("callBackList:"+CommonJsonUtil.objectToJson(callBackList,List.class));
		System.out.println("pageNo:"+callBack.getPageNo()+",pageSize:"+callBack.getPageSize()+",totalCount:"+callBack.getTotalCount());
		List<HotelMessageVO> modelList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(callBackList)) {
			for (HotelDO _do : callBackList) {
				modelList.add(domain.doToModel(_do));
			}
		}
		PageVO<HotelMessageVO> pageModel = new PageVO<HotelMessageVO>(hotelMessageVO.getPage(), hotelMessageVO.getPageSize(), callBack.getTotalCount(), modelList);
		result.setValue(pageModel);

		return result;
	}

	/**
	 * 添加酒店商品信息
	 * @param domain
	 * @return
     */
	public WebResult<HotelMessageVO>  addHotelMessageVOByData(HotelManageDomainChecker domain) throws Exception{
		WebResult<HotelMessageVO> webResult = domain.getWebResult();

		ICResult<HotelDO> hotelResult =  itemQueryServiceRef.getHotel(domain.getHotelMessageVO().getHotelId());
		if(!hotelResult.isSuccess()||hotelResult.getModule()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "getHotel,查询酒店资源信息错误");
		}
		log.info("addHotelMessageVOByData.getHotel 回参: hotelDO="+CommonJsonUtil.objectToJson(hotelResult.getModule(),HotelDO.class));
		domain.setHotelDO(hotelResult.getModule());

		CategoryResult categoryResult = categoryServiceRef.getCategory(domain.getHotelMessageVO().getCategoryId());
		if(!categoryResult.isSuccess()||categoryResult.getCategroyDO()==null){
			log.error("类目信息错误,categoryId:"+domain.getHotelMessageVO().getCategoryId());
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目信息错误");
		}
		domain.setCategoryDO(categoryResult.getCategroyDO());
		if(CollectionUtils.isEmpty(categoryResult.getCategroyDO().getSellCategoryPropertyDOs())){
			log.error("类目销售属性信息错误,categoryId:"+domain.getHotelMessageVO().getCategoryId());
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目销售属性信息错误");
		}
		CategoryPropertyValueDO sellDO = categoryResult.getCategroyDO().getSellCategoryPropertyDOs().get(0);
		/**类目销售属性**/

		domain.setCategoryPropertyValueDO(sellDO);
		HotelPublishAddDTO hotelPublishAddDTO = domain.getBizHotelPublishAddDTO();
		if(hotelPublishAddDTO==null){
			log.error( "拼装commonItemPublishDTO商品信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "拼装hotelPublishAddDTO商品信息错误");
		}
		ItemPubResult result = itemPublishServiceRef.addPublishHotel(hotelPublishAddDTO);
		if(!result.isSuccess()){
			log.error("添加酒店商品信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "添加酒店商品信息错误");
		}
		webResult.setValue(domain.getHotelMessageVO());
		return webResult;
	}

	/**
	 * 编辑商品
	 * @param domain
	 * @return
	 * @throws Exception
     */
	public WebResult<Long> editHotelMessageVOByData(HotelManageDomainChecker domain) throws Exception{
		WebResult<Long> webResult = domain.getLongWebResult();
		ICResult<HotelDO> hotelResult =  itemQueryServiceRef.getHotel(domain.getHotelMessageVO().getHotelId());
		if(!hotelResult.isSuccess()||hotelResult.getModule()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "getHotel,查询酒店资源信息错误");
		}
		log.info("editHotelMessageVOByData.getHotel 回参: hotelDO="+CommonJsonUtil.objectToJson(hotelResult.getModule(),HotelDO.class));
		domain.setHotelDO(hotelResult.getModule());
		long categoryId = domain.getHotelMessageVO().getCategoryId();
		log.info("editHotelMessageVOByData.getCategory 入参: categoryId="+categoryId);
		CategoryResult categoryResult = categoryServiceRef.getCategory(categoryId);
		if(!categoryResult.isSuccess()||categoryResult.getCategroyDO()==null){
			log.error("类目信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目信息错误");
		}
		if(CollectionUtils.isEmpty(categoryResult.getCategroyDO().getSellCategoryPropertyDOs())){
			log.error("类目销售属性信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目销售属性信息错误");
		}
		CategoryDO categoryDO = categoryResult.getCategroyDO();
		domain.setCategoryDO(categoryDO);
		log.info("editHotelMessageVOByData.getCategory 回参: categoryDO="+ JSON.toJSONString(categoryDO));
		CategoryPropertyValueDO sellDO = categoryDO.getSellCategoryPropertyDOs().get(0);
		log.info("editHotelMessageVOByData.getCategory 回参: sellDO="+ JSON.toJSONString(sellDO));
		domain.setCategoryPropertyValueDO(sellDO);
		HotelPublishUpdateDTO hotelPublishUpdateDTO = domain.getBizHotelPublishUpdateDTO();
		log.info("editHotelMessageVOByData.updatePublishCommonItem 入参: commonItemPublishDTO="+JSON.toJSONString(hotelPublishUpdateDTO));
		ItemPubResult result = itemPublishServiceRef.updatePublishHotel(hotelPublishUpdateDTO);
		if (!result.isSuccess()){
			  return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "编辑酒店商品信息错误");
		}
		log.info("editHotelMessageVOByData.updatePublishCommonItem 回参: itemId="+result.getItemId());
		/***设置商品ID***/
		webResult.setValue(result.getItemId());
		return webResult;
	}

	/**
	 * 查询商品信息
	 * @param domain
	 * @return
	 * @throws Exception
     */
	public  WebResult<HotelMessageVO> queryHotelMessageVOyData(HotelManageDomainChecker domain) throws Exception{
		WebResult<HotelMessageVO> hotelMessageVOWebResult = domain.getWebResult();
		HotelMessageVO model = domain.getHotelMessageVO();
		/**商品信息**/
		ItemOptionDTO itemOptionDTO  = new ItemOptionDTO();
		itemOptionDTO.setNeedSku(true);
		itemOptionDTO.setNeedCategory(true);
		long itemId = model.getItemId();
		log.info("queryHotelMessageVOyData.getItem 入参: itemId="+itemId+",itemOptionDTO="+CommonJsonUtil.objectToJson(itemOptionDTO,ItemOptionDTO.class));
		ItemResult itemResult= itemQueryServiceRef.getItem(itemId, itemOptionDTO);
		if(!itemResult.isSuccess()||itemResult.getItem()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "getItem,查询商品信息错误");
		}
		log.info("queryHotelMessageVOyData.getItem 回参: itemDO="+CommonJsonUtil.objectToJson(itemResult.getItem(),ItemDO.class));
		domain.setItemDO(itemResult.getItem());
		CategoryDO categoryDO = itemResult.getCategory();
		domain.setCategoryDO(categoryDO);
		log.info("queryHotelMessageVOyData.getItem 回参: categoryDO="+ JSON.toJSONString(categoryDO));
		List<ItemSkuDO> itemSkuDOList =itemResult.getItemSkuDOList();
		domain.setItemSkuDOList(itemSkuDOList);
		//Long roomId = item.getItemFeature().getRoomId();//当前商品绑定的酒店信息
		/***酒店资源**/
		log.info("queryHotelMessageVOyData.getItem 回参: itemSkuDOList="+CommonJsonUtil.objectToJson(itemSkuDOList,List.class));

		log.info("queryHotelMessageVOyData.getHotel 入参: outId="+itemResult.getItem().getOutId());
		ICResult<HotelDO> hotelResult =  itemQueryServiceRef.getHotel(itemResult.getItem().getOutId());
		if(!hotelResult.isSuccess()||hotelResult.getModule()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "getHotel,查询酒店资源信息错误");
		}
		log.info("queryHotelMessageVOyData.getHotel 回参: hotelDO="+CommonJsonUtil.objectToJson(hotelResult.getModule(),HotelDO.class));
		domain.setHotelDO(hotelResult.getModule());
		/***酒店房型**/
		RoomQuery roomQuery = new RoomQuery();
		roomQuery.setHotelId(itemResult.getItem().getOutId());
		long roomId = domain.getItemDO().getItemFeature().getRoomId();
		if(roomId==0){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "getItem,查询酒店房型错误");
		}
		log.info("queryHotelMessageVOyData.queryAllRoom 入参: outId="+itemResult.getItem().getOutId());
		ICResult<List<RoomDO>> roomResult= itemQueryServiceRef.queryAllRoom(roomQuery);
		//ICResult<RoomDO> roomResult=   itemQueryServiceRef.getRoom(roomId);
		if(!roomResult.isSuccess()||roomResult.getModule()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "queryAllRoom,查询酒店房型信息错误");
		}
		log.info("queryHotelMessageVOyData.queryAllRoom 回参: roomList="+CommonJsonUtil.objectToJson(roomResult.getModule(),List.class));
		domain.setListRoomDO(roomResult.getModule());
		//domain.setRoomDO(roomResult.getModule());
		/**获取酒店/商品/sku/房型**/
		HotelMessageVO hotelMessageVO = domain.getBizQueryHotelMessageVOyData();
		hotelMessageVOWebResult.setValue(hotelMessageVO);
		return hotelMessageVOWebResult ;

	}

	/**
	 * 酒店房型列表
 	 * @return
     */
	public WebResult<List<RoomMessageVO>>  queryRoomTypeListByData(HotelManageDomainChecker domain){
		WebResult<List<RoomMessageVO>> result = domain.getListRoomMessageVOResult();
		HotelMessageVO hotelMessageVO = domain.getHotelMessageVO();
		RoomQuery roomQuery = new RoomQuery();
		roomQuery.setStatus(ItemStatus.valid.getValue());
		roomQuery.setHotelId(hotelMessageVO.getHotelId());
		ICResult<List<RoomDO>> callBack= itemQueryServiceRef.queryAllRoom( roomQuery);
		if(!callBack.isSuccess() || callBack.getModule()==null ){
			log.error("酒店房型列表查询错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "酒店房型列表查询错误");
		}
		List<RoomDO> roomList = callBack.getModule();
		domain.setListRoomDO(roomList);
		List<RoomMessageVO> modelList = domain.getRoomMessageVOList();//domain 进行do 转model
		log.info("roomList:"+CommonJsonUtil.objectToJson(modelList,List.class));
		result.setValue(modelList);
		return  result;

	}
	public ItemQueryService getItemQueryServiceRef() {
		return itemQueryServiceRef;
	}

	public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
		this.itemQueryServiceRef = itemQueryServiceRef;
	}

	public ItemPublishService getItemPublishServiceRef() {
		return itemPublishServiceRef;
	}

	public void setItemPublishServiceRef(ItemPublishService itemPublishServiceRef) {
		this.itemPublishServiceRef = itemPublishServiceRef;
	}

	public CategoryService getCategoryServiceRef() {
		return categoryServiceRef;
	}

	public void setCategoryServiceRef(CategoryService categoryServiceRef) {
		this.categoryServiceRef = categoryServiceRef;
	}
}
