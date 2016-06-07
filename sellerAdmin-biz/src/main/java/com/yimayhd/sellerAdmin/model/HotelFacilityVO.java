package com.yimayhd.sellerAdmin.model;

import com.yimayhd.ic.client.model.domain.FacilityIconDO;

public class HotelFacilityVO extends FacilityIconDO implements Comparable<HotelFacilityVO> {

	private static final long serialVersionUID = 6110740194677588502L;
	private boolean checked;

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public int compareTo(HotelFacilityVO o) {
		
		return o.getNumber();
	}
	
}
