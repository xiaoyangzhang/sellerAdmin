package com.yimayhd.sellerAdmin.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.checker.HotelManageDomainChecker;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.*;
import com.yimayhd.user.client.enums.security.RevivePasswordStep;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.result.ResultSupport;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.client.service.UserSecurityService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.entity.UserContact;
import com.yimayhd.user.errorcode.UserServiceHttpCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
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
	public WebResult<HotelMessageVO>  addHotelMessageVOByData(HotelManageDomainChecker domain){
		//WebResult<PageVO<HotelMessageVO>> result = domain.getPageResult();

		return null;
	}

	public ItemQueryService getItemQueryServiceRef() {
		return itemQueryServiceRef;
	}

	public void setItemQueryServiceRef(ItemQueryService itemQueryServiceRef) {
		this.itemQueryServiceRef = itemQueryServiceRef;
	}
}
