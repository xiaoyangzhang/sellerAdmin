package com.yimayhd.sellerAdmin.repo;

import com.google.gson.reflect.TypeToken;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
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
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import org.apache.commons.collections.CollectionUtils;
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
	private Logger log = LoggerFactory.getLogger(getClass());
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
		WebResult<PageVO<HotelMessageVO>> result = domain.getPageResult();
		HotelPageQuery hotelPageQuery = domain.getBizQueryModel();
		ICPageResult<HotelDO> callBack = itemQueryServiceRef.pageQueryHotel(hotelPageQuery);
		if (callBack == null) {
			log.error("查询pageQueryHotel返回结果异常");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "查询pageQueryHotel返回结果异常");
		}
		List<HotelDO> callBackList = callBack.getList();
		System.out.println(CommonJsonUtil.objectToJson(callBackList,List.class));
		log.info("result:"+CommonJsonUtil.objectToJson(callBackList,List.class));
		System.out.println("pageNo:"+callBack.getPageNo()+",pageSize:"+callBack.getPageSize()+",totalCount:"+callBack.getTotalCount());
		List<HotelMessageVO> modelList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(callBackList)) {
			for (HotelDO _do : callBackList) {
				modelList.add(domain.doToModel(_do));
			}
		}
		PageVO<HotelMessageVO> pageModel = new PageVO<HotelMessageVO>(callBack.getPageNo(), callBack.getPageSize(), callBack.getTotalCount(), modelList);
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
		CategoryResult categoryResult = categoryServiceRef.getCategory(domain.getHotelMessageVO().getCategoryId());
		if(!categoryResult.isSuccess()||categoryResult.getCategroyDO()==null){
			log.error("类目信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目信息错误");
		}
		if(CollectionUtils.isEmpty(categoryResult.getCategroyDO().getSellCategoryPropertyDOs())){
			log.error("类目销售属性信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目销售属性信息错误");
		}
		CategoryPropertyValueDO sellDO = categoryResult.getCategroyDO().getSellCategoryPropertyDOs().get(0);
		/**类目销售属性**/
		domain.setCategoryPropertyValueDO(sellDO);
		CommonItemPublishDTO commonItemPublishDTO = domain.getBizCommonItemPublishDTO();
		if(commonItemPublishDTO==null){
			log.error( "拼装commonItemPublishDTO商品信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "拼装commonItemPublishDTO商品信息错误");
		}
		ItemPubResult result = itemPublishServiceRef.publishCommonItem(commonItemPublishDTO);
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
		CategoryResult categoryResult = categoryServiceRef.getCategory(domain.getHotelMessageVO().getCategoryId());
		if(!categoryResult.isSuccess()||categoryResult.getCategroyDO()==null){
			log.error("类目信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目信息错误");
		}
		if(CollectionUtils.isEmpty(categoryResult.getCategroyDO().getSellCategoryPropertyDOs())){
			log.error("类目销售属性信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目销售属性信息错误");
		}
		CategoryPropertyValueDO sellDO = categoryResult.getCategroyDO().getSellCategoryPropertyDOs().get(0);
		domain.setCategoryPropertyValueDO(sellDO);
		CommonItemPublishDTO commonItemPublishDTO = domain.getBizCommonItemPublishDTO();
		ItemPubResult result = itemPublishServiceRef.updatePublishCommonItem(commonItemPublishDTO);
		if (!result.isSuccess()){
			  return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "编辑酒店商品信息错误");
		}
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
		ItemResult itemResult= itemQueryServiceRef.getItem(model.getItemId(), new ItemOptionDTO());
		if(!itemResult.isSuccess()||itemResult.getItem()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "getItem,查询商品信息错误");
		}

		domain.setItemDO(itemResult.getItem());
		domain.setItemSkuDOList(itemResult.getItemSkuDOList());
		//Long roomId = item.getItemFeature().getRoomId();//当前商品绑定的酒店信息
		/***酒店资源**/

		ICResult<HotelDO> hotelResult =  itemQueryServiceRef.getHotel(itemResult.getItem().getOutId());
		if(!hotelResult.isSuccess()||hotelResult.getModule()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "getHotel,查询酒店资源信息错误");
		}
		domain.setHotelDO(hotelResult.getModule());
		/***酒店房型**/
		RoomQuery roomQuery = new RoomQuery();
		roomQuery.setHotelId(itemResult.getItem().getOutId());
		ICResult<List<RoomDO>> roomResult= itemQueryServiceRef.queryAllRoom( roomQuery);
		if(!roomResult.isSuccess()||roomResult.getModule()==null){
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "queryAllRoom,查询酒店房型信息错误");
		}
		domain.setListRoomDO(roomResult.getModule());
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
		roomQuery.setHotelId(hotelMessageVO.getHotelId());
		ICResult<List<RoomDO>> callBack= itemQueryServiceRef.queryAllRoom( roomQuery);
		if(!callBack.isSuccess() || callBack.getModule()==null ){
			log.error("酒店房型列表查询错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "酒店房型列表查询错误");
		}
		List<RoomDO> roomList = callBack.getModule();
		domain.setListRoomDO(roomList);
		List<RoomMessageVO> modelList = domain.getRoomMessageVOList();//domain 进行do 转model
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
