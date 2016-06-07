package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.membercenter.client.service.TravelKaService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.CityInfo;
import com.yimayhd.resourcecenter.model.enums.BoothJumpType;
import com.yimayhd.resourcecenter.model.enums.ColumnType;
import com.yimayhd.resourcecenter.model.enums.ErrorCode;
import com.yimayhd.resourcecenter.model.enums.ShowcaseShowType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.resource.LineInfo;
import com.yimayhd.resourcecenter.model.resource.TravelSpecialInfo;
import com.yimayhd.resourcecenter.model.resource.UserInfo;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import com.yimayhd.sellerAdmin.model.vo.CfgBaseVO;
import com.yimayhd.sellerAdmin.model.vo.CfgResultInfo;
import com.yimayhd.sellerAdmin.model.vo.CfgResultVO;
import com.yimayhd.sellerAdmin.model.vo.ShowCaseVO;
import com.yimayhd.sellerAdmin.repo.ActivityRepo;
import com.yimayhd.sellerAdmin.repo.LineRepo;
import com.yimayhd.sellerAdmin.service.HomeCfgService;
import com.yimayhd.sellerAdmin.service.ServiceResult;
import com.yimayhd.sellerAdmin.service.UserRPCService;
import com.yimayhd.sellerAdmin.util.Common;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.user.client.domain.UserDO;

public class HomeCfgServiceImpl implements HomeCfgService {
	private static final Logger				LOGGER								= LoggerFactory
			.getLogger(HomeCfgServiceImpl.class);

	private static final String				BOOT_HOME_ADVERTISE_CODE			= "HOME_RECOMMENT";
	private static final String				BOOT_RECOMMEND_CODE					= "GREAT_RECOMMENT";
	private static final String				BOOT_TRAVEKA_CODE					= "TRAVE_MASTER";

	/**
	 * 会员专享boothid
	 */
	private static final long				HOME_CONFIG_VIP_BOOTH_ID			= 60;

	/**
	 * 大咖带你玩boothid
	 */
	private static final long				HOME_CONFIG_LINE_BOOTH_ID			= 61;

	/**
	 * 金牌旅游咖boothid
	 */
	private static final long				HOME_CONFIG_TRAVEL_KA_BOOTH_ID		= 62;

	/**
	 * 目的地boothid
	 */
	private static final long				HOME_CONFIG_CITY_BOOTH_ID			= 63;

	/**
	 * 品质游记boothid
	 */
	private static final long				HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID	= 64;

	private static final String				img									= "T1xthTB4YT1R4cSCrK.png";

	private static final BoothClientServer	showcaseCilentServer				= null;

	@Autowired
	private ShowcaseClientServer			showCaseClientServer;

	@Autowired
	private BoothClientServer				boothCilentServer;

	@Autowired
	private UserRPCService					userRPCService;

	@Autowired
	BoothClientServer						boothClientServerRef;

	@Autowired
	private SnsCenterService				snsCenterService;

	@Autowired
	private TravelKaService					travelKaService;

	@Autowired
	private ActivityRepo					activityRepo;

	@Autowired
	private LineRepo						lineRepo;

	@Override
	public RcResult<Boolean> addVipList(CfgBaseVO cfgBaseVO) {
		RcResult<Boolean> batchInsertShowcase = new RcResult<Boolean>();
		if (null == cfgBaseVO) {
			batchInsertShowcase.setErrorCode(ErrorCode.PARAM_ERROR);
		}

		BoothDO boothDO = setBoothInfo(cfgBaseVO);

		List<ShowcaseDO> showcaseList = setShowCaseListBase(cfgBaseVO);

		batchInsertShowcase = showCaseClientServer.batchInsertShowcase(boothDO, showcaseList);

		return batchInsertShowcase;
	}

	private BoothDO setBoothInfo(CfgBaseVO cfgBaseVO) {

		BoothDO boothDO = new BoothDO();
		boothDO.setDesc(cfgBaseVO.getSubTitle());
		boothDO.setCode(cfgBaseVO.getBoothCode());
		boothDO.setId(cfgBaseVO.getBoothId());

		return boothDO;
	}

	private List<ShowcaseDO> setShowCaseListBase(CfgBaseVO cfgBaseVO) {

		List<ShowcaseDO> showcaseList = new ArrayList<ShowcaseDO>();

		ShowcaseDO showcaseDO = null;

		long[] vipIds = cfgBaseVO.getItemIds();

		for (int i = 0; i < vipIds.length; i++) {

			showcaseDO = new ShowcaseDO();
			// TODO
			if (cfgBaseVO.getBoothId() > 1) {
				showcaseDO.setBoothId(cfgBaseVO.getBoothId());
			}
			if (!Common.isEmptyArray(cfgBaseVO.getItemTitle())) {
				showcaseDO.setTitle(cfgBaseVO.getItemTitle()[i]);
			}
			if (!Common.isEmptyArray(cfgBaseVO.getImgUrl())) {
				showcaseDO.setImgUrl(cfgBaseVO.getImgUrl()[i]);
			}
			if (!Common.isEmptyArray(cfgBaseVO.getDescription())) {
				showcaseDO.setSummary(cfgBaseVO.getDescription()[i]);
			}
			if (!Common.isEmptyArray(cfgBaseVO.getItemIds())) {
				showcaseDO.setOperationId(cfgBaseVO.getItemIds()[i]);
			}

			showcaseList.add(showcaseDO);
		}

		return showcaseList;
	}

	@Override
	public RcResult<Boolean> addLineList(CfgBaseVO cfgBaseVO) {

		LineInfo lineInfo = new LineInfo();
		lineInfo.setName("线路1");
		lineInfo.setPrice(223);
		lineInfo.setId(1222);
		lineInfo.setLogo_pic(img);
		List<String> tags = new ArrayList<String>();
		tags.add("蜜月之旅");
		tags.add("深呼吸系列");

		UserInfo userInfo = new UserInfo();
		userInfo.setId(233);
		userInfo.setName("sam");
		userInfo.setAge(23);
		userInfo.setSignature("云南旅游达人");
		userInfo.setGender("MALE");
		userInfo.setAvatar(img);
		lineInfo.setUserInfo(userInfo);
		System.out.println(JSON.toJSONString(lineInfo));

		List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
		BoothDO boothDO = new BoothDO();
		RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);

		return insertStatus;
	}

	@Override
	public RcResult<Boolean> addTravelKaList(CfgBaseVO cfgBaseVO) {
		RcResult rcResult = new RcResult();
		if (StringUtils.isEmpty(cfgBaseVO.getAddItemIds())) {
			return null;
		}
		String itemIds = cfgBaseVO.getAddItemIds();
		String arr[] = itemIds.split(",");
		String code = ColumnType.TRAVE_MASTER.name(); // 大咖code
		BoothDO boothDO = getBothByCode(code);
		List<ShowcaseDO> showcaseDOs = new ArrayList<ShowcaseDO>();
		for (String id : arr) {
			ShowcaseDO showcaseDO = new ShowcaseDO();
			showcaseDO.setTitle(ColumnType.TRAVE_MASTER.getCode());
			showcaseDO.setStatus(ShowcaseStauts.ONLINE.getStatus());
			showcaseDO.setShowType(ShowcaseShowType.DEFAULT.getShowType());
			showcaseDO.setOperationContent(id);
			showcaseDO.setBoothId(boothDO.getId());
			showcaseDOs.add(showcaseDO);
		}
		boolean flag = showCaseClientServer.batchSaveShowcase(showcaseDOs); // 批量保存
		if (flag) {
			rcResult.setSuccess(true);
			return rcResult;
		}
		rcResult.setSuccess(false);
		return rcResult;
	}

	@Override
	public RcResult<Boolean> addCityList(CfgBaseVO cfgBaseVO) {
		List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
		BoothDO boothDO = new BoothDO();
		RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);
		return insertStatus;
	}

	@Override
	public RcResult<Boolean> addTravelSpecialList(CfgBaseVO cfgBaseVO) {

		TravelSpecialInfo travelSpecialInfo = new TravelSpecialInfo();
		travelSpecialInfo.setTitle("穿梭在云端的日子");
		travelSpecialInfo.setBackImg(img);
		UserInfo userInfo = new UserInfo();
		userInfo.setId(233);
		userInfo.setName("sam");
		userInfo.setAge(23);
		userInfo.setSignature("云南旅游达人");
		userInfo.setGender("FEMALE");
		userInfo.setAvatar(img);
		travelSpecialInfo.setUserInfo(userInfo);
		travelSpecialInfo.setSupportNum(234);
		travelSpecialInfo.setRedNum(333);

		System.out.println(JSON.toJSONString(travelSpecialInfo));

		List<ShowcaseDO> showcaseDOList = new ArrayList<ShowcaseDO>();
		BoothDO boothDO = new BoothDO();
		RcResult<Boolean> insertStatus = showCaseClientServer.batchInsertShowcase(boothDO, showcaseDOList);

		return insertStatus;
	}

	@Override
	public CfgResultVO getVipList() {

		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_VIP_BOOTH_ID);

		if (boothResult.isSuccess()) {

			CfgResultVO homeResultVO = new CfgResultVO();

			ShowcaseQuery showCaseQuery = new ShowcaseQuery();

			showCaseQuery.setBoothId(HOME_CONFIG_VIP_BOOTH_ID);

			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);

			BoothDO boothDO = boothResult.getT();

			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_VIP_BOOTH_ID);

			if (showcaseResult.isSuccess()) {

				List<CfgResultInfo> homeCfgInfoList = setBaseShowCase(showcaseResult.getList());

				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}

			return homeResultVO;
		}

		return null;
	}

	private List<CfgResultInfo> setBaseShowCase(List<ShowCaseResult> showCaseList) {

		if (null == showCaseList) {
			return null;
		}

		List<CfgResultInfo> homeResultInfos = new ArrayList<CfgResultInfo>();

		CfgResultInfo homeResultInfo = null;

		for (ShowCaseResult showCaseResult : showCaseList) {

			ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
			homeResultInfo = new CfgResultInfo();
			homeResultInfo.setShowCaseId(showcaseDO.getId());
			homeResultInfo.setItemId(showcaseDO.getOperationId());
			homeResultInfo.setItemImg(showcaseDO.getImgUrl());
			homeResultInfo.setItemTitle(showcaseDO.getTitle());
			homeResultInfo.setItemDesc(showcaseDO.getSummary());

			homeResultInfos.add(homeResultInfo);
		}

		return homeResultInfos;
	}

	@Override
	public CfgResultVO getLineList() {
		String code = ColumnType.GREAT_RECOMMENT.name(); // 精彩推荐
		BoothDO boothDO = getBothByCode(code);
		if (StringUtils.isEmpty(boothDO)) {
			return null;
		}

		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(boothDO.getId());

		if (boothResult.isSuccess()) {

			CfgResultVO homeResultVO = new CfgResultVO();

			ShowcaseQuery showCaseQuery = new ShowcaseQuery();

			showCaseQuery.setBoothId(boothDO.getId());

			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);

			// BoothDO boothDO = boothResult.getT();

			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(boothDO.getId());

			if (showcaseResult.isSuccess()) {

				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();

				CfgResultInfo homeResultInfo = null;

				List<ShowCaseResult> showCaseList = showcaseResult.getList();

				for (ShowCaseResult showCaseResult : showCaseList) {

					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();

					String operationContent = showcaseDO.getOperationContent();

					long targetId = 0;
					if (!StringUtils.isEmpty(operationContent)) {
						targetId = Long.parseLong(operationContent);
					}
					BaseResult<SnsActivityDO> snsActivityDOBaseResult = snsCenterService
							.getActivityInfoByActivityId(targetId);
					// LineInfo lineInfo =
					// JSON.parseObject(showcaseDO.getOperationContent(),LineInfo.class);

					// homeResultInfo.setItemId(lineInfo.getId());
					// homeResultInfo.setItemTitle(lineInfo.getName());
					// homeResultInfo.setItemPrice(lineInfo.getPrice());;
					// homeResultInfo.setExtImg(lineInfo.getUserInfo().getAvatar());
					// homeResultInfo.setExtName(lineInfo.getUserInfo().getNickname());

					if (snsActivityDOBaseResult != null && snsActivityDOBaseResult.isSuccess()) {
						SnsActivityDO snsActivityDO = snsActivityDOBaseResult.getValue();
						homeResultInfo.setItemId(snsActivityDO.getId());
						homeResultInfo.setItemTitle(snsActivityDO.getTitle());
						homeResultInfo.setItemPrice(snsActivityDO.getPreferentialPrice());
						;
					}
					homeCfgInfoList.add(homeResultInfo);
				}
				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}

			return homeResultVO;
		}

		return null;
	}

	@Override
	public CfgResultVO getTravelKaList() {
		String code = ColumnType.TRAVE_MASTER.name(); // 大咖code
		BoothDO boothDO = getBothByCode(code);
		if (StringUtils.isEmpty(boothDO)) {
			return null;
		}
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(boothDO.getId());

		if (boothResult.isSuccess()) {

			CfgResultVO homeResultVO = new CfgResultVO();

			ShowcaseQuery showCaseQuery = new ShowcaseQuery();

			showCaseQuery.setBoothId(boothDO.getId());

			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);

			// BoothDO boothDO = boothResult.getT();

			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(boothDO.getId());

			if (showcaseResult.isSuccess()) {

				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();

				CfgResultInfo homeResultInfo = null;

				List<ShowCaseResult> showCaseList = showcaseResult.getList();

				for (ShowCaseResult showCaseResult : showCaseList) {

					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();
					String operationContent = showcaseDO.getOperationContent();
					long userId = 0;
					if (!StringUtils.isEmpty(operationContent)) {
						userId = Long.parseLong(operationContent);
					}

					UserDO userDO = userRPCService.getUserById(userId);

					// TravelKaItem travelKaItem =
					// JSON.parseObject(showcaseDO.getOperationContent(),
					// TravelKaItem.class);

					// homeResultInfo.setItemId(travelKaItem.getId());
					// homeResultInfo.setItemTitle(travelKaItem.getNickname());
					// homeResultInfo.setItemImg(travelKaItem.getAvatar());

					homeResultInfo.setItemId(userDO.getId());
					homeResultInfo.setItemTitle(userDO.getNickname());
					homeResultInfo.setItemImg(userDO.getAvatar());

					homeCfgInfoList.add(homeResultInfo);
				}

				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}

			return homeResultVO;
		}

		return null;
	}

	@Override
	public CfgResultVO getCityList() {

		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_CITY_BOOTH_ID);

		if (boothResult.isSuccess()) {

			CfgResultVO homeResultVO = new CfgResultVO();

			ShowcaseQuery showCaseQuery = new ShowcaseQuery();

			showCaseQuery.setBoothId(HOME_CONFIG_CITY_BOOTH_ID);

			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);

			BoothDO boothDO = boothResult.getT();

			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_CITY_BOOTH_ID);

			if (showcaseResult.isSuccess()) {

				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();

				CfgResultInfo homeResultInfo = null;

				List<ShowCaseResult> showCaseList = showcaseResult.getList();

				for (ShowCaseResult showCaseResult : showCaseList) {

					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();

					CityInfo cityInfo = JSON.parseObject(showcaseDO.getOperationContent(), CityInfo.class);

					homeResultInfo.setItemId(cityInfo.id);
					homeResultInfo.setItemTitle(cityInfo.name);
					homeResultInfo.setItemImg(cityInfo.url);

					homeCfgInfoList.add(homeResultInfo);
				}

				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}

			return homeResultVO;
		}
		return null;
	}

	@Override
	public CfgResultVO getTravelSpecialList() {

		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);

		if (boothResult.isSuccess()) {

			CfgResultVO homeResultVO = new CfgResultVO();

			ShowcaseQuery showCaseQuery = new ShowcaseQuery();

			showCaseQuery.setBoothId(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);

			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);

			BoothDO boothDO = boothResult.getT();

			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(HOME_CONFIG_TRAVEL_SPECIAL_BOOTH_ID);

			if (showcaseResult.isSuccess()) {

				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();

				CfgResultInfo homeResultInfo = null;

				List<ShowCaseResult> showCaseList = showcaseResult.getList();

				for (ShowCaseResult showCaseResult : showCaseList) {

					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();

					TravelSpecialInfo travelSpecialInfo = JSON.parseObject(showcaseDO.getOperationContent(),
							TravelSpecialInfo.class);

					homeResultInfo.setItemId(travelSpecialInfo.getId());
					homeResultInfo.setItemTitle(travelSpecialInfo.getTitle());
					homeResultInfo.setItemImg(travelSpecialInfo.getBackImg());
					homeResultInfo.setExtName(travelSpecialInfo.getUserInfo().getNickname());
					homeResultInfo.setExtImg(travelSpecialInfo.getUserInfo().getAvatar());

					homeCfgInfoList.add(homeResultInfo);
				}

				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}

			return homeResultVO;
		}

		return null;
	}

	private BoothDO getBothByCode(String code) {
		BoothDO boothDO = null;
		boothDO = boothClientServerRef.getBoothDoByCode(code);
		if (null == boothDO) {
			return null;
		}
		return boothDO;
	}

	/**
	 * 查询精彩推荐
	 * 
	 * @return
	 */
	public CfgResultVO getGreatRecommentList() {
		String code = ColumnType.GREAT_RECOMMENT.name(); // 精彩推荐
		BoothDO boothDO = getBothByCode(code);
		if (StringUtils.isEmpty(boothDO)) {
			return null;
		}
		RcResult<BoothDO> boothResult = boothCilentServer.getBoothById(boothDO.getId());

		if (boothResult.isSuccess()) {

			CfgResultVO homeResultVO = new CfgResultVO();

			ShowcaseQuery showCaseQuery = new ShowcaseQuery();

			showCaseQuery.setBoothId(boothDO.getId());

			showCaseQuery.setPageSize(100);
			RCPageResult<ShowCaseResult> showcaseResult = showCaseClientServer.getShowcaseResult(showCaseQuery);

			homeResultVO.setBoothCode(boothDO.getCode());
			homeResultVO.setBoothDesc(boothDO.getDesc());
			homeResultVO.setBoothId(boothDO.getId());

			if (showcaseResult.isSuccess()) {

				List<CfgResultInfo> homeCfgInfoList = new ArrayList<CfgResultInfo>();

				CfgResultInfo homeResultInfo = null;

				List<ShowCaseResult> showCaseList = showcaseResult.getList();

				for (ShowCaseResult showCaseResult : showCaseList) {

					ShowcaseDO showcaseDO = showCaseResult.getShowcaseDO();
					homeResultInfo = new CfgResultInfo();
					String operationContent = showcaseDO.getOperationContent();
					String businessCode = showcaseDO.getBusinessCode();
					long targetId = 0;
					if (!StringUtils.isEmpty(operationContent)) {
						targetId = Long.parseLong(operationContent);
					}

					if (businessCode.equals("ACTIVITY")) { // TODO 需要 询问 使用枚举
						// TODO 调用活动接口
					} else {
						// todo 调用 线路 接口
					}
					homeResultInfo.setItemId(1);
					homeResultInfo.setItemTitle("昆大理");
					homeResultInfo.setItemDesc("180起");
					homeCfgInfoList.add(homeResultInfo);
				}

				homeResultVO.setCfgInfoList(homeCfgInfoList);
			}

			return homeResultVO;
		}

		return null;
	}

	@Override
	public ServiceResult<Boolean> multiShowCaseOperate(List<ShowCaseVO> showcaseVOList, String boothCode) {
		LOGGER.debug("showcaseVOList={},boothCode={}", JSONObject.toJSONString(showcaseVOList), boothCode);
		// 根据boothcode查询rc_booth
		BoothDO boothResult = boothCilentServer.getBoothDoByCode(boothCode);
		boolean result = true;
		long boothId = boothResult.getId();

		// FIXME 此处考虑批量操作
		if (!CollectionUtils.isEmpty(showcaseVOList)) {
			List<ShowcaseDO> addList = new ArrayList<ShowcaseDO>();
			List<ShowcaseDO> updateList = new ArrayList<ShowcaseDO>();
			// 拼接do对象
			for (ShowCaseVO showCaseVO : showcaseVOList) {
				ShowcaseDO showcaseDO = new ShowcaseDO();
				if (showCaseVO.getShowcaseId() != null) {
					showcaseDO.setId(showCaseVO.getShowcaseId());
				}

				showcaseDO.setTitle(showCaseVO.getTitle());
				if (showCaseVO.getSerialNo() != null) {
					showcaseDO.setSerialNo(showCaseVO.getSerialNo());
				}
				showcaseDO.setSummary(showCaseVO.getSummary());
				// FIXME 设置成枚举
				if ("LINE".equals(showCaseVO.getBusinessCode()) && showCaseVO.getLineType() != null) {
					int lineType = showCaseVO.getLineType();
					if (lineType == BoothJumpType.REGULAR_LINE.getType()) {
						showcaseDO.setBusinessCode("REGULAR_LINE");
					} else if (lineType == BoothJumpType.HOTEL.getType()) {
						showcaseDO.setBusinessCode("REGULAR_LINE");
					} else if (lineType == BoothJumpType.SCENIC.getType()) {
						showcaseDO.setBusinessCode("SCENIC");
					}
				} else {
					showcaseDO.setBusinessCode(showCaseVO.getBusinessCode());
				}

				if (showCaseVO.getOperationContent() != null) {
					showcaseDO.setOperationContent(showCaseVO.getOperationContent());
				}

				showcaseDO.setBoothId(boothId);
				showcaseDO.setImgUrl(showCaseVO.getImgUrl());
				if (showCaseVO.getVersion() != null) {
					showcaseDO.setVersion(showCaseVO.getVersion());
				} else {
					showcaseDO.setVersion(0);
				}

				// FIXME 对于错误的处理机制
				if ("add".equals(showCaseVO.getOperation())) {// 更新操作
					showcaseDO.setStatus(ShowcaseStauts.ONLINE.getStatus());
					addList.add(showcaseDO);
					// RcResult<Boolean> insertResult =
					// showCaseClientServer.insert(showcaseDO);
					// LOGGER.debug("insertResult={}",JSONObject.toJSONString(insertResult));
				} else if ("del".equals(showCaseVO.getOperation())) {
					showcaseDO.setStatus(ShowcaseStauts.OFFLINE.getStatus());
					updateList.add(showcaseDO);
					// RcResult<Boolean> deleteResult =
					// showCaseClientServer.update(showcaseDO);
					// LOGGER.debug("deleteResult={}",JSONObject.toJSONString(deleteResult));
				} else if (showcaseDO.getId() != null) {
					showcaseDO.setStatus(ShowcaseStauts.ONLINE.getStatus());
					updateList.add(showcaseDO);
					// RcResult<Boolean> updateResult =
					// showCaseClientServer.update(showcaseDO);
					// LOGGER.debug("updateResult={}",JSONObject.toJSON(updateResult));
				} else {
					showcaseDO.setStatus(ShowcaseStauts.ONLINE.getStatus());
					showcaseDO.setVersion(0);
					addList.add(showcaseDO);
					// RcResult<Boolean> insertResult =
					// showCaseClientServer.insert(showcaseDO);
					// LOGGER.debug("insertResult={}",JSONObject.toJSON(insertResult));
				}
			}

			if (!CollectionUtils.isEmpty(addList)) {
				ServiceResult<Boolean> addResult = this.addShowCaseBatch(addList);
				LOGGER.debug("addResult={}", JSONObject.toJSONString(addResult));
				if (addResult.isSuccess() == false) {
					return addResult;
				}
			}

			if (!CollectionUtils.isEmpty(updateList)) {
				ServiceResult<Boolean> updateResult = this.updateShowCaseBatch(updateList);
				LOGGER.debug("updateResult={}", JSONObject.toJSONString(updateResult));
				if (updateResult.isSuccess() == false) {
					return updateResult;
				}
			}
		}

		return new ServiceResult<Boolean>(true);
	}

	@Override
	public ServiceResult<Boolean> addAdvertise(List<ShowCaseVO> list) {
		return multiShowCaseOperate(list, BOOT_HOME_ADVERTISE_CODE);
	}

	@Override
	public ServiceResult<List<ShowcaseDO>> getShowCases(String boothCode) {
		BoothDO boothResult = boothCilentServer.getBoothDoByCode(boothCode);
		long boothId = boothResult.getId();
		List<ShowcaseDO> showcaseList = showCaseClientServer.getShowcaseByBoothId(boothId);
		LOGGER.debug("showcaseList={}", JSONObject.toJSONString(showcaseList));

		ServiceResult<List<ShowcaseDO>> queryResult = new ServiceResult<List<ShowcaseDO>>(true);
		queryResult.setValue(showcaseList);
		return queryResult;
	}

	@Override
	public ServiceResult<List<ShowcaseDO>> getAdvertiseShowcase() {
		return getShowCases(BOOT_HOME_ADVERTISE_CODE);
	}

	@Override
	public ServiceResult<Boolean> addRecommends(List<ShowCaseVO> list) {

		return multiShowCaseOperate(list, BOOT_RECOMMEND_CODE);
	}

	@Override
	public ServiceResult<List<ShowcaseDO>> getRecommends() {

		return getShowCases(BOOT_RECOMMEND_CODE);
	}

	@Override
	public ServiceResult<List<ShowcaseDO>> getTravelKa() {
		return getShowCases(BOOT_TRAVEKA_CODE);
	}

	@Override
	public ServiceResult<Boolean> addTravelKaList(List<ShowCaseVO> list) {
		return multiShowCaseOperate(list, BOOT_TRAVEKA_CODE);
	}

	@Override
	public ServiceResult<Boolean> updateShowCaseBatch(List<ShowcaseDO> list) {
		LOGGER.debug("list={}", list);
		// FIXME 此处需要resourcecenter提供批量更新接口
		RcResult<Boolean> finalResult = null;
		if (!CollectionUtils.isEmpty(list)) {
			for (ShowcaseDO showcase : list) {
				RcResult<Boolean> updateResult = showCaseClientServer.update(showcase);
				LOGGER.debug("updateResult={}", JSONObject.toJSONString(updateResult));
				if (updateResult.isSuccess() == false) {
					finalResult = updateResult;
					break;
				}
			}
		}

		if (finalResult != null) {
			return new ServiceResult<Boolean>(false, finalResult.getResultMsg(), finalResult.getErrorCode() + "");
		}

		return new ServiceResult<Boolean>(true);
	}

	@Override
	public ServiceResult<Boolean> addShowCaseBatch(List<ShowcaseDO> list) {
		RcResult<Boolean> finalResult = null;
		// FIXME 此处需要resourcecenter明确哪个是批量新增接口
		if (!CollectionUtils.isEmpty(list)) {
			for (ShowcaseDO showcase : list) {
				RcResult<Boolean> insertResult = showCaseClientServer.insert(showcase);
				LOGGER.debug("updateResult={}", JSONObject.toJSONString(insertResult));
				if (insertResult.isSuccess() == false) {
					finalResult = insertResult;
					break;
				}
			}
		}

		if (finalResult != null) {
			return new ServiceResult<Boolean>(false, finalResult.getResultMsg(), finalResult.getErrorCode() + "");
		}

		return new ServiceResult<Boolean>(true);
	}

	@Override
	public ServiceResult<LineDO> getLineDetail(Long id) {
		ServiceResult<LineDO> result = new ServiceResult<LineDO>(false);

		LineResult lineResult = lineRepo.getLineByItemId(0, id);
		if (lineResult.isSuccess()) {
			result.setResult(true);
			result.setValue(lineResult.getLineDO());
		} else {
			result.setErrorMsg("getLineById error");
			LOGGER.error("getLineById error,id={},lineResult={}", id, lineResult);
		}

		return result;
	}

	@Override
	public ServiceResult<SnsActivityDO> getActivityDetail(Long id) {
		ServiceResult<SnsActivityDO> serviceResult = new ServiceResult<SnsActivityDO>(false);
		SnsActivityDO activity = activityRepo.getActivityById(id);
		if (activity != null) {
			serviceResult.setResult(true);
			serviceResult.setValue(activity);
		} else {
			serviceResult.setErrorMsg("getById error");
			LOGGER.error("getById error,result={},id={}", activity, id);
		}
		return serviceResult;
	}

	@Override
	public ServiceResult<UserDO> getTravelKaDetail(Long id) {
		ServiceResult<UserDO> serviceResult = new ServiceResult<UserDO>(false);

		UserDO userDO = userRPCService.getUserById(id);
		if (userDO != null) {
			serviceResult.setResult(true);
			serviceResult.setValue(userDO);
		} else {
			serviceResult.setErrorMsg("getTravelKaDetail error");
			LOGGER.error("getTravelKaDetail error,userDO={},id={}", userDO, id);
		}

		return serviceResult;
	}

}
