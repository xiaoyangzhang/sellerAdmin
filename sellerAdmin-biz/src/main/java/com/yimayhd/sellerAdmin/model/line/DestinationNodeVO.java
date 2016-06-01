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
public class DestinationNodeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DestinationVO destinationVO;
	private List<DestinationNodeVO> child;

	
	public DestinationNodeVO() {
		super();
	}

	public DestinationVO getDestinationVO() {
		return destinationVO;
	}

	public void setDestinationVO(DestinationVO destinationVO) {
		this.destinationVO = destinationVO;
	}

	public List<DestinationNodeVO> getChild() {
		return child;
	}

	public void setChild(List<DestinationNodeVO> child) {
		this.child = child;
	}

}
