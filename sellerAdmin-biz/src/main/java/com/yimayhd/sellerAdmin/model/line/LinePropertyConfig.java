package com.yimayhd.sellerAdmin.model.line;

import java.util.List;

import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;

/**
 * 线路Property配置
 * 
 * @author yebin
 *
 */
public class LinePropertyConfig {
	private CategoryPropertyDO persionProperty;
	private CategoryPropertyDO packageProperty;
	private CategoryPropertyDO dateProperty;
	private List<CategoryValueDO> persionPropertyValues;

	public CategoryPropertyDO getPersionProperty() {
		return persionProperty;
	}

	public void setPersionProperty(CategoryPropertyDO persionProperty) {
		this.persionProperty = persionProperty;
	}

	public CategoryPropertyDO getPackageProperty() {
		return packageProperty;
	}

	public void setPackageProperty(CategoryPropertyDO packageProperty) {
		this.packageProperty = packageProperty;
	}

	public CategoryPropertyDO getDateProperty() {
		return dateProperty;
	}

	public void setDateProperty(CategoryPropertyDO dateProperty) {
		this.dateProperty = dateProperty;
	}

	public List<CategoryValueDO> getPersionPropertyValues() {
		return persionPropertyValues;
	}

	public void setPersionPropertyValues(List<CategoryValueDO> persionPropertyValues) {
		this.persionPropertyValues = persionPropertyValues;
	}
}
