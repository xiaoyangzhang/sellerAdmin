package com.yimayhd.sellerAdmin.model;

import com.yimayhd.ic.client.model.domain.CategoryPropertyDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czf on 2015/12/21.
 */
public class CategoryPropertyVO extends CategoryPropertyDO {
    private List<CategoryValueVO> categoryValueVOs;
    public static CategoryPropertyVO getCategoryPropertyVO(CategoryPropertyDO categoryPropertyDO){
        CategoryPropertyVO categoryPropertyVO = new CategoryPropertyVO();
        BeanUtils.copyProperties(categoryPropertyDO,categoryPropertyVO);
        if(CollectionUtils.isNotEmpty(categoryPropertyVO.getCategoryValueDOs())){
            List<CategoryValueVO> categoryValueVOList = new ArrayList<CategoryValueVO>();
            for (CategoryValueDO categoryValueDO : categoryPropertyVO.getCategoryValueDOs()){
                CategoryValueVO categoryValueVO = CategoryValueVO.getCategoryValueVO(categoryValueDO);
                categoryValueVOList.add(categoryValueVO);
            }
            categoryPropertyVO.setCategoryValueVOs(categoryValueVOList);
        }
        return categoryPropertyVO;
    }

    public List<CategoryValueVO> getCategoryValueVOs() {
        return categoryValueVOs;
    }

    public void setCategoryValueVOs(List<CategoryValueVO> categoryValueVOs) {
        this.categoryValueVOs = categoryValueVOs;
    }
}
