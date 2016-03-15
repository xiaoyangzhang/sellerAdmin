package com.yimayhd.sellerAdmin.model.line;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;

/**
 * 线路上下文配置
 * 
 * @author yebin
 *
 */
public class LineContextConfig {
	private List<ComTagDO> themes;
	private int options;
	private CategoryPropertyDO persionProperty;
	private CategoryPropertyDO packageProperty;
	private CategoryPropertyDO dateProperty;
	private List<CategoryValueDO> persionPropertyValues;

	public List<ComTagDO> getThemes() {
		return themes;
	}

	public void setThemes(List<ComTagDO> themes) {
		this.themes = themes;
	}

	public int getOptions() {
		return options;
	}

	public void setOptions(int options) {
		this.options = options;
	}

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
