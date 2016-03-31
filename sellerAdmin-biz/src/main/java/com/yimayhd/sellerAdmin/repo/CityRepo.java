package com.yimayhd.sellerAdmin.repo;

import java.util.List;
import java.util.Map;

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
	public CityDTO getCityByCode(String code) {
		return cityDataCacheClientRef.getCityByCode(code);
	}

	public Map<String, CityDTO> getCitiesByCodes(List<String> codes) {
		return cityDataCacheClientRef.getCities(codes);
	}
}
