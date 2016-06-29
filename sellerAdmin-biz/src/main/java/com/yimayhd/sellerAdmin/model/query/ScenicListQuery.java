package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3522270147472331376L;
	  	private Integer status;

	    private Integer level;

	    private Long regionId;

	    private Integer itemStatus;

	    private Long subjectId;

	    private String tags;
	    
	    private String startTime;
	    
	    private String endTime;

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		public Long getRegionId() {
			return regionId;
		}

		public void setRegionId(Long regionId) {
			this.regionId = regionId;
		}

		public Integer getItemStatus() {
			return itemStatus;
		}

		public void setItemStatus(Integer itemStatus) {
			this.itemStatus = itemStatus;
		}

		public Long getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(Long subjectId) {
			this.subjectId = subjectId;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

	    
}
