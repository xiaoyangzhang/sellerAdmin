package com.yimayhd.sellerAdmin.model.line;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.ic.client.model.domain.DestinationDO;
import com.yimayhd.resourcecenter.dto.DestinationNode;
import com.yimayhd.user.client.dto.CityDTO;

/**
 * 目的地VO
 * 
 * @author xiemingna
 *
 */
public class DestinationVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private int code;
	private String simpleCode;
	private boolean selected;

	public DestinationVO() {
		super();
	}

	public DestinationVO(Long id, String name, int code, String simpleCode) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.simpleCode = simpleCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSimpleCode() {
		return simpleCode;
	}

	public void setSimpleCode(String simpleCode) {
		this.simpleCode = simpleCode;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
