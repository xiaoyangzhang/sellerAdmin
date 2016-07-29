package com.yimayhd.sellerAdmin.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * Created by czf on 2015/12/19.
 */
public class CategoryVO extends CategoryDO {
    private List<CategoryPropertyValueVO> sellCategoryPropertyVOs;
    private List<ItemSkuVO> itemSkuVOListAll;//所有组合的list
    
    private String integralType;//积分商品类型

    /**
     * 根据categoryDO转化为categoryVO
     * @param categoryDO
     * @return
     */
    public static CategoryVO getCategoryVO(CategoryDO categoryDO){
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(categoryDO, categoryVO);
        //拷贝CategoryPropertyValueVO
        if(CollectionUtils.isNotEmpty(categoryVO.getSellCategoryPropertyDOs())){
            List<CategoryPropertyValueVO> categoryPropertyValueVOList = new ArrayList<CategoryPropertyValueVO>();
            for (CategoryPropertyValueDO categoryPropertyValueDO : categoryVO.getSellCategoryPropertyDOs()){
                CategoryPropertyValueVO categoryPropertyValueVO = CategoryPropertyValueVO.getCategoryPropertyValueVO(categoryPropertyValueDO);
                categoryPropertyValueVOList.add(categoryPropertyValueVO);
            }
            categoryVO.setSellCategoryPropertyVOs(categoryPropertyValueVOList);
            //构建所有的属性组合
            categoryVO.setItemSkuVOListAll(getItemSkuVOListAll(categoryVO));
        }

        return categoryVO;
    }
    /*public static CategoryDO getCategoryDO(CategoryVO categoryVO){
        CategoryDO categoryDO = new CategoryDO();
        BeanUtils.copyProperties(categoryVO,categoryDO);
        return categoryDO;
    }*/

    /**
     * 根据分类获取所有可能的sku组合
     * @param categoryVO
     * @return
     */
    public static List<ItemSkuVO> getItemSkuVOListAll(CategoryVO categoryVO){
        //构建所有的属性组合
        List<ItemSkuVO> itemSkuVOListAll = new ArrayList<ItemSkuVO>();
        for (int i = 0;i < categoryVO.getSellCategoryPropertyVOs().size();i++){
            CategoryPropertyValueVO categoryPropertyValueVOTran = categoryVO.getSellCategoryPropertyVOs().get(i);
            if(CollectionUtils.isNotEmpty(itemSkuVOListAll)){
                List<ItemSkuVO> itemSkuVOListAllNew = new ArrayList<ItemSkuVO>();
                List<CategoryValueDO> categoryValueDOList = categoryPropertyValueVOTran.getCategoryPropertyDO().getCategoryValueDOs();
                if(!CollectionUtils.isEmpty(categoryValueDOList)) {
                    for (int j = 0; j < categoryValueDOList.size(); j++) {
                        CategoryValueDO categoryValueDO = categoryValueDOList.get(j);
                        ItemSkuPVPair itemSkuPVPair = new ItemSkuPVPair();
                        itemSkuPVPair.setPId(categoryPropertyValueVOTran.getCategoryPropertyDO().getId());
                        itemSkuPVPair.setPTxt(categoryPropertyValueVOTran.getCategoryPropertyDO().getText());
                        itemSkuPVPair.setPType(categoryPropertyValueVOTran.getCategoryPropertyDO().getType());
                        itemSkuPVPair.setVId(categoryValueDO.getId());
                        itemSkuPVPair.setVTxt(categoryValueDO.getText());
                        for (int k = 0; k < itemSkuVOListAll.size(); k++) {
                            ItemSkuVO itemSkuVO = new ItemSkuVO();
                            BeanUtils.copyProperties(itemSkuVOListAll.get(k),itemSkuVO);
                            List<ItemSkuPVPair> itemSkuPVPairList = itemSkuVO.getItemSkuPVPairList();
                            List<ItemSkuPVPair> itemSkuPVPairListNew = new ArrayList<ItemSkuPVPair>();
                            for (int l = 0; l < itemSkuPVPairList.size(); l++) {
                                itemSkuPVPairListNew.add(itemSkuPVPairList.get(l));
                            }
                            itemSkuPVPairListNew.add(itemSkuPVPair);
                            itemSkuVO.setItemSkuPVPairList(itemSkuPVPairListNew);
                            itemSkuVOListAllNew.add(itemSkuVO);
                        }
                    }
                }
                itemSkuVOListAll = itemSkuVOListAllNew;
            }else{
                List<CategoryValueDO> categoryValueDOList = categoryPropertyValueVOTran.getCategoryPropertyDO().getCategoryValueDOs();
                if(!CollectionUtils.isEmpty(categoryValueDOList)) {
                    for (int j = 0; j < categoryValueDOList.size(); j++) {
                        CategoryValueDO categoryValueDO = categoryValueDOList.get(j);

                        ItemSkuVO itemSkuVO = new ItemSkuVO();
                        List<ItemSkuPVPair> itemSkuPVPairList = new ArrayList<ItemSkuPVPair>();

                        ItemSkuPVPair itemSkuPVPair = new ItemSkuPVPair();
                        itemSkuPVPair.setPId(categoryPropertyValueVOTran.getCategoryPropertyDO().getId());
                        itemSkuPVPair.setPTxt(categoryPropertyValueVOTran.getCategoryPropertyDO().getText());
                        itemSkuPVPair.setPType(categoryPropertyValueVOTran.getCategoryPropertyDO().getType());
                        itemSkuPVPair.setVId(categoryValueDO.getId());
                        itemSkuPVPair.setVTxt(categoryValueDO.getText());

                        itemSkuPVPairList.add(itemSkuPVPair);
                        itemSkuVO.setItemSkuPVPairList(itemSkuPVPairList);

                        itemSkuVOListAll.add(itemSkuVO);
                    }
                }
            }
        }
        //排序
        Collections.sort(itemSkuVOListAll,new ItemSkuVO.ItemSkuVOSort());
        return itemSkuVOListAll;
    }
    //供vm页面用
    public String getItemSkuVOListAllStr(){
        if (CollectionUtils.isEmpty(this.itemSkuVOListAll)) {
            return null;
        }
        return JSON.toJSONString(this.itemSkuVOListAll, SerializerFeature.DisableCircularReferenceDetect);
    }
    public List<CategoryPropertyValueVO> getSellCategoryPropertyVOs() {
        return sellCategoryPropertyVOs;
    }

    public void setSellCategoryPropertyVOs(List<CategoryPropertyValueVO> sellCategoryPropertyVOs) {
        this.sellCategoryPropertyVOs = sellCategoryPropertyVOs;
    }

    public List<ItemSkuVO> getItemSkuVOListAll() {
        return itemSkuVOListAll;
    }

    public void setItemSkuVOListAll(List<ItemSkuVO> itemSkuVOListAll) {
        this.itemSkuVOListAll = itemSkuVOListAll;
    }

	public String getIntegralType() {
		return integralType;
	}

	public void setIntegralType(String integralType) {
		this.integralType = integralType;
	}
    
}
