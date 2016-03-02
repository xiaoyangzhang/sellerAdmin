package com.yimayhd.sellerAdmin.model.vo;

import java.io.Serializable;

/**
 * 展位信息
 * 
 * @author zhangjian
 *
 */
public class ShowCaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3390100230242318148L;

	private Long showcaseId;

	private String title;

	private String businessCode;

	private String summary;

	private Long operationId;
	// delete,add
	private String operation;

	private String imgUrl;

	private Integer serialNo;

	private Integer version;

	private String operationContent;

	private Integer lineType;

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public String getOperationContent() {
		return operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getTitle() {
		return title;
	}

	public Long getShowcaseId() {
		return showcaseId;
	}

	public void setShowcaseId(Long showcaseId) {
		this.showcaseId = showcaseId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
