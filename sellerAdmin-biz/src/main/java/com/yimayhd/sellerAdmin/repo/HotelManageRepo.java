package com.yimayhd.sellerAdmin.repo;

import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
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
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "查询pageQueryHotel返回结果异常");
		}
		List<HotelDO> callBackList = callBack.getList();
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
		CommonItemPublishDTO commonItemPublishDTO = domain.getBizCommonItemPublishDTO();
		ItemPubResult result = itemPublishServiceRef.publishCommonItem(commonItemPublishDTO);
		if(!result.isSuccess()){
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

		return null;

	}
	public ItemQueryService getItemQueryServiceRef() {
		return itemQueryServiceRef;
	}

	public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
		this.itemQueryServiceRef = itemQueryServiceRef;
	}


}
