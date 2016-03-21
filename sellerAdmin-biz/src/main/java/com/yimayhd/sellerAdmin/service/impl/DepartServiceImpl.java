package com.yimayhd.sellerAdmin.service.impl;

import java.util.List;
import java.util.Map;

import com.yimayhd.sellerAdmin.model.line.KeyValuePair;
import com.yimayhd.sellerAdmin.repo.CityRepo;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.service.DepartService;

public class DepartServiceImpl implements DepartService {
	private CityRepo cityRepo;
	private CommentRepo commentRepo;

	@Override
	public Map<String, List<KeyValuePair<Long, String>>> queryAllDepartGroupByInitial() {
		return null;
	}

}
