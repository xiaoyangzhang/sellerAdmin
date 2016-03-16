package com.yimayhd.sellerAdmin.repo;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.user.client.cache.CityDataCacheClient;
import com.yimayhd.user.client.dto.CityDTO;

/**
 * 出发地
 * 
 * @author yebin
 *
 */
public class CityRepo {
	@Autowired
	private CityDataCacheClient cityDataCacheClientRef;

	/**
	 * 通过城市code查询城市名称
	 * 
	 * @param code
	 * @return
	 */
	public CityDTO getNameByCode(String code) {
		return cityDataCacheClientRef.getCityByCode(code);
	}
}
