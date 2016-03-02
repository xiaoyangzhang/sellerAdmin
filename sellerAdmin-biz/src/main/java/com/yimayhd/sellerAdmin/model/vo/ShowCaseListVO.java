package com.yimayhd.sellerAdmin.model.vo;

import java.io.Serializable;
import java.util.List;

public class ShowCaseListVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7146155235171433481L;
	
	
	private List<ShowCaseVO> showCaseList;

	public List<ShowCaseVO> getShowCaseList() {
		return showCaseList;
	}

	public void setShowCaseList(List<ShowCaseVO> showCaseList) {
		this.showCaseList = showCaseList;
	}
	
	
}
