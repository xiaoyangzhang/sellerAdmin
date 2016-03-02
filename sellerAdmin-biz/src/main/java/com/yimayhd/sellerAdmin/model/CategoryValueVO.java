package com.yimayhd.sellerAdmin.model;

import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2015/12/21.
 */
public class CategoryValueVO extends CategoryValueDO {
    private boolean checked = false;//该属性是否已选择

    public static CategoryValueVO getCategoryValueVO(CategoryValueDO categoryValueDO){
        CategoryValueVO categoryValueVO = new CategoryValueVO();
        BeanUtils.copyProperties(categoryValueDO,categoryValueVO);
        return categoryValueVO;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
