package com.yimayhd.sellerAdmin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.User;
import com.yimayhd.sellerAdmin.model.query.TradeMemberQuery;
import com.yimayhd.sellerAdmin.repo.TravelKaRepo;
import com.yimayhd.sellerAdmin.service.UserRPCService;
import com.yimayhd.sellerAdmin.util.PhoneUtil;
import com.yimayhd.membercenter.client.domain.TravelKaVO;
import com.yimayhd.membercenter.client.result.MemPageResult;
import com.yimayhd.membercenter.client.service.MerchantService;
import com.yimayhd.membercenter.client.vo.MerchantPageQueryVO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.service.UserService;

/**
 * @author czf
 */
public class UserRPCServiceImpl implements UserRPCService {
	private static Logger log = LoggerFactory.getLogger(UserRPCServiceImpl.class);

	@Autowired
	private MerchantService merchantServiceRef;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private TravelKaRepo travelKaRepo;

	@Override
	public List<User> getClubMemberListByClubId(long clubId) {
		List<User> userList = new ArrayList<User>();
		for (int i = 0; i < 2; i++) {
			User user = new User();
			user.setTel("1803926207" + i);
			user.setName("tiny" + i);
			user.setUserName("李四" + i);
			user.setGmtCreated(new Date());
			userList.add(user);
		}
		return userList;
	}

	@Override
	public PageVO<UserDO> getMemberByUserId(long sellerId, TradeMemberQuery tradeMemberQuery) throws Exception {
		// 查询条件转换
		PageVO<UserDO> pageVO = new PageVO<UserDO>(tradeMemberQuery.getPageNo(), tradeMemberQuery.getPageSize(), 0);
		MerchantPageQueryVO merchantPageQueryVO = new MerchantPageQueryVO();
		merchantPageQueryVO.setMerchantUserId(sellerId);
		merchantPageQueryVO.setPageNo(tradeMemberQuery.getPageNo());
		merchantPageQueryVO.setPageSize(tradeMemberQuery.getPageSize());
		if (!StringUtils.isBlank(tradeMemberQuery.getNickName())) {
			merchantPageQueryVO.setNickName(tradeMemberQuery.getNickName());
		}
		if (!StringUtils.isBlank(tradeMemberQuery.getCityName())) {
			merchantPageQueryVO.setCity(tradeMemberQuery.getCityName());
		}
		if (!StringUtils.isBlank(tradeMemberQuery.getTel())) {
			merchantPageQueryVO.setMobile(tradeMemberQuery.getTel());
		}
		MemPageResult<UserDO> memResult = merchantServiceRef.findPageUsersByMerchant(merchantPageQueryVO);
		List<UserDO> userDOList = null;
		if (null != memResult && memResult.isSuccess()) {
			userDOList = memResult.getList();
			if (CollectionUtils.isNotEmpty(userDOList)) {
				for (UserDO userDO : userDOList) {
					// 用户电话
					userDO.setMobileNo(PhoneUtil.phoneFormat(userDO.getMobileNo()));
				}
				// TODO 总条数
				pageVO = new PageVO<UserDO>(tradeMemberQuery.getPageNo(), tradeMemberQuery.getPageSize(),
						memResult.getTotalCount(), userDOList);
			}
		} else {
			log.error("检索会员信息失败：sellerId={},query={}", sellerId, JSON.toJSONString(tradeMemberQuery));
			log.error("检索会员信息失败：result={}", JSON.toJSONString(memResult));
			throw new BaseException("检索会员信息失败");
		}
		return pageVO;
	}

	@Override
	public PageVO<UserDO> getUserListByPage(UserDOPageQuery query)throws Exception{
		UserDOPageQuery userDOPageQuery = new UserDOPageQuery();
		userDOPageQuery.setPageSize(query.getPageSize());
		userDOPageQuery.setPageNo(query.getPageNo());
		if(StringUtils.isNotBlank(query.getNickname())){
			userDOPageQuery.setNickname(query.getNickname());
		}
		BasePageResult<UserDO> result = userServiceRef.findPageResultByCondition(userDOPageQuery);
		int totalCount = 0;
		List<UserDO> itemList = new ArrayList<UserDO>();
		if (result != null && result.isSuccess()) {
			totalCount = result.getTotalCount();
			if (CollectionUtils.isNotEmpty(result.getList())) {
				itemList.addAll(result.getList());
			}
		} else {
			log.error("查询用户列表失败：query={}", JSON.toJSONString(query));
			log.error("查询用户列表失败：result={}", JSON.toJSONString(result));
			throw new BaseException("查询用户列表失败");
		}
		return new PageVO<UserDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public UserDO getUserById(long id) {
		UserDO user = userServiceRef.getUserDOById(id);
		return user;
	}

	@Override
	public  PageVO<UserDO>  getTravelKaListByPage(UserDOPageQuery query) throws Exception{
		UserDOPageQuery userDOPageQuery = new UserDOPageQuery();
		userDOPageQuery.setPageSize(query.getPageSize());
		//userDOPageQuery.setStartIndex(new Long(query.getPageSize()));
		userDOPageQuery.setPageNo(query.getPageNo());
		if(StringUtils.isNotBlank(query.getNickname())){
			userDOPageQuery.setLikeNickname(query.getNickname());
		}
		userDOPageQuery.setOptionsList(query.getOptionsList());
		
		BasePageResult<UserDO> result = userServiceRef.findPageResultByCondition(userDOPageQuery);
		int totalCount = 0;
		List<UserDO> itemList = new ArrayList<UserDO>();
		if (result != null && result.isSuccess()) {
			totalCount = result.getTotalCount();
			if (CollectionUtils.isNotEmpty(result.getList())) {
				itemList.addAll(result.getList());
			}
		} else {
			log.error("查询用户列表失败：query={}", JSON.toJSONString(query));
			log.error("查询用户列表失败：result={}", JSON.toJSONString(result));
			throw new BaseException("查询用户列表失败");
		}
		return new PageVO<UserDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public TravelKaVO getTravelKaById(long id) {
		return travelKaRepo.getTravelKaById(id);
	}

}
