package com.yimayhd.sellerAdmin.repo;

import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ScenicPublishAddDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
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
import com.yimayhd.sellerAdmin.checker.ScenicManageDomainChecker;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
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
public class ScenicManageRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private ItemPublishService itemPublishServiceRef;
	@Autowired
	private CategoryService categoryServiceRef;

	/**
	 * 查询景区资源列表
	 * @param domain
	 * @return
     */
	public WebResult<PageVO<ScenicManageVO>> queryScenicManageVOListByData(ScenicManageDomainChecker domain) throws Exception{
		WebResult<PageVO<ScenicManageVO>> result = domain.getWebPageResult();
		ScenicPageQuery pageQuery = domain.getBizQueryModel();
		ICPageResult<ScenicDO> callBack = itemQueryServiceRef.pageQueryScenic(pageQuery);
		if(!callBack.isSuccess()||callBack==null){
			log.error("查询pageQueryScenic接口返回参数为null");
		}
		List<ScenicDO> ScenicDOList = callBack.getList();
		List<ScenicManageVO> resultList = new ArrayList<ScenicManageVO>();
		if(CollectionUtils.isNotEmpty(ScenicDOList)){
			for(ScenicDO _do:ScenicDOList){
				resultList.add(domain.doToModel(_do));
			}
		}
		PageVO<ScenicManageVO> pageModel = new PageVO<ScenicManageVO>(callBack.getPageNo(),callBack.getPageSize(),callBack.getTotalCount(),resultList);
		result.setValue(pageModel);
		return result;
	}

	/**
	 * 查询景区资源详情
	 * @param domain
	 * @return
     */
	public WebResult<ScenicManageVO> queryScenicManageVOByData(ScenicManageDomainChecker domain) throws Exception{
		WebResult<ScenicManageVO> result = domain.getWebResult();
		/**类目销售属性**/
		/**景区商品**/
		ItemResult itemResult= itemQueryServiceRef.getItem(domain.getScenicManageVO().getItemId(), new ItemOptionDTO());
		if(!itemResult.isSuccess()||itemResult.getItem()==null){
			log.error("查询景区商品信息错误");
			return WebResult.failure(WebReturnCode.PARAM_ERROR, "查询景区商品信息错误");
		}
		ItemDO itemDO = itemResult.getItem();
		CategoryDO categoryDO = itemResult.getCategory();
		domain.setItemDO(itemDO);//景区商品信息
		domain.setItemSkuDOList(itemResult.getItemSkuDOList());// 价格日历
		domain.setCategory(categoryDO);
		domain.setCategoryPropertyValueDO(categoryDO.getSellCategoryPropertyDOs().get(0));//价格日历销售属性
		ICResult<ScenicDO> scenicResult = itemQueryServiceRef.getScenic(itemDO.getOutId());
		if(scenicResult==null||scenicResult.getModule()==null){
			log.error("查询景区资源信息错误");
			return WebResult.failure(WebReturnCode.PARAM_ERROR, "景区资源信息错误");
		}
		domain.setScenicDO(scenicResult.getModule());//酒店资源信息
		ScenicManageVO scenicManageVO = domain.getBizScenicManageVO();
		result.setValue(scenicManageVO);
		return  result;


	}

	/**
	 * 添加景区商品信息
	 * @param domain
	 * @return
     */
	public WebResult<ScenicManageVO> addScenicManageVOByDdata(ScenicManageDomainChecker domain) throws Exception{
		WebResult<ScenicManageVO> result = domain.getWebResult();
		ScenicManageVO scenicManageVO = domain.getScenicManageVO();
		CategoryResult categoryResult = categoryServiceRef.getCategory(domain.getScenicManageVO().getCategoryId());
		if(!categoryResult.isSuccess()||categoryResult.getCategroyDO()==null){
			log.error("类目信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目信息错误");
		}
		domain.setCategory(categoryResult.getCategroyDO());
		if(CollectionUtils.isEmpty(categoryResult.getCategroyDO().getSellCategoryPropertyDOs())){
			log.error("类目销售属性信息错误");
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "类目销售属性信息错误");
		}
		CategoryPropertyValueDO sellDO = categoryResult.getCategroyDO().getSellCategoryPropertyDOs().get(0);
		/**类目销售属性**/

		domain.setCategoryPropertyValueDO(sellDO);
		ScenicPublishAddDTO scenicPublishAddDTO = new ScenicPublishAddDTO();
		scenicPublishAddDTO.setItemDO(domain.getBizScenicPublishAddDTO());//商品
		scenicPublishAddDTO.setItemSkuList(domain.getAddBizItemSkuDOList());//价格日历
		ItemPubResult itemResult = itemPublishServiceRef.addPublishScenic(scenicPublishAddDTO);
		log.info("scenicPublishAddDTO:"+ CommonJsonUtil.objectToJson(scenicPublishAddDTO,ScenicPublishAddDTO.class));
		if(!itemResult.isSuccess()){
			log.error("添加景区商品错误");
			System.out.println(itemResult.getErrorCode()+","+itemResult.getResultCode()+","+itemResult.getResultMsg());
			return WebResult.failure(WebReturnCode.PARAM_ERROR, "添加景区商品错误");
		}
		scenicManageVO.setItemId(itemResult.getItemId());
		result.setValue(scenicManageVO);
		return result;

	}

	/**
	 * 编辑景区商品
	 * @param domain
	 * @return
	 * @throws Exception
     */
	public WebResult<ScenicManageVO> editScenicManageVOByDdata(ScenicManageDomainChecker domain) throws Exception{
		WebResult<ScenicManageVO> result = domain.getWebResult();
		ScenicManageVO scenicManageVO = domain.getScenicManageVO();
		CategoryResult categoryResult = categoryServiceRef.getCategory(domain.getScenicManageVO().getCategoryId());
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
		ItemPubResult itemResult =itemPublishServiceRef.updatePublishScenic(domain.getBizScenicPublishUpdateDTO());
		scenicManageVO.setItemId(itemResult.getItemId());
		return result;

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
