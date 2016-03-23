package com.yimayhd.sellerAdmin.service;

import java.util.List;
import java.util.Map;

import com.yimayhd.sellerAdmin.model.line.KeyValuePair;

/**
 * 出发地服务
 * 
 * @author yebin
 *
 */
public interface DepartService {
	/**
	 * 查询全部出发地并用首字母聚类
	 * 
	 * @return
	 */
	public Map<String, List<KeyValuePair<Long, String>>> queryAllDepartGroupByInitial();
}
