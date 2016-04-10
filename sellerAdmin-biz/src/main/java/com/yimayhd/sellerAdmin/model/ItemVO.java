package com.yimayhd.sellerAdmin.model;

import java.text.SimpleDateFormat;
import java.util.*;

import com.yimayhd.sellerAdmin.model.line.pictxt.PictureTextVO;
import com.yimayhd.sellerAdmin.util.DateFormat;
import net.pocrd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.sellerAdmin.util.NumUtil;

/**
 * Created by czf on 2015/12/17.
 */
public class ItemVO extends ItemDO {

    private static final long serialVersionUID = -1394982462767288880L;
    private String itemSkuVOStr;//sku列表 jsonString

    private double priceY;//价格（单位:元）

    private List<ItemSkuVO> itemSkuVOList;//

    private List<ItemSkuVO> itemSkuVOListAll;//所有组合的list

    private List<Integer> skuTdRowNumList;

    private int sort = 1;//商品排序字段(默认为1)

    private Long endBookTimeLimit;//酒店可入住时间限制(存feature中)
    private Long startBookTimeDays;//景区规则提前几天
    private Long startBookTimeHours;//景区规则提前几点
    private Integer grade;//评分(存feature中)

    private String smallListPic;//方形小列表图，主要用于订单
    private String bigListPic;//扁长大列表图，主要用于伴手礼等商品列表
    private String coverPics;//封面大图String
    private List<String> picList;//封面大图List
    private String pcDetail;//pc版详情H5
    private PictureTextVO pictureTextVO;//图文详情
    private String pictureTextVOStr;

    private Integer reduceType = ReduceType.NONE.getBizType();//减库存方式

    private List<String> openTimeList;//酒店的可最晚可到店时间

    private String endDateStr; //截止日期
    private List<String> itemMainPics; //商品图列表
    private Double longitudeVO; //经度
    private Double latitudeVO; //纬度

    //新增商品提交时调用
    public static ItemDO getItemDO(ItemVO itemVO)throws Exception{
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemVO, itemDO);
        //元转分
        itemDO.setPrice(NumUtil.doubleToLong(itemVO.getPriceY()));

        //新增的时候设置skuDOList（注：修改时走setItemSkuDOListCommonItemPublishDTO）
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListByStr())){
            List<ItemSkuDO> itemSkuDOList = new ArrayList<ItemSkuDO>();
            long price = 0;
            int stockNum = 0;
            for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
                if(itemSkuVO.isChecked()) {
                    ItemSkuDO itemSkuDO = ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO);
                    itemSkuDOList.add(itemSkuDO);
                    //有sku时取最小价格
                    if(0 == price){
                        price = itemSkuDO.getPrice();
                    }else if(price > itemSkuDO.getPrice()){
                        price = itemSkuDO.getPrice();
                    }
                    //有sku时库存求和
                    stockNum += itemSkuDO.getStockNum();
                }
            }
            if(price > 0){
                itemDO.setPrice(price);
            }
            if(stockNum > 0){
                itemDO.setStockNum(stockNum);
            }
            itemDO.setItemSkuDOList(itemSkuDOList);
        }

        ItemFeature itemFeature = itemDO.getItemFeature();
        if (itemDO.getItemFeature() == null) {
            itemFeature = new ItemFeature(null);
            itemDO.setItemFeature(itemFeature);
        }
        //提前预定时间(暂时只有酒店用)
        if (null != itemVO.getEndBookTimeLimit()) {
            itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, itemVO.getEndBookTimeLimit() * 24 * 3600);
        }
        
        //最晚到店时间列表(暂时只有酒店用)
        if(CollectionUtils.isNotEmpty(itemVO.getOpenTimeList())){
            itemFeature.put(ItemFeatureKey.LATEST_ARRIVE_TIME,itemVO.getOpenTimeList());
        }
        //picUrls转换
        if(StringUtils.isNotBlank(itemVO.getSmallListPic())){
            itemDO.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, itemVO.getSmallListPic());
        }
        if(StringUtils.isNotBlank(itemVO.getBigListPic())){
            itemDO.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, itemVO.getBigListPic());
        }
        if(StringUtils.isNotBlank(itemVO.getCoverPics())){
            itemDO.addPicUrls(ItemPicUrlsKey.COVER_PICS, itemVO.getCoverPics());
        }
        if(CollectionUtils.isNotEmpty(itemVO.getItemMainPics())) {
            itemDO.addPicUrls(ItemPicUrlsKey.ITEM_MAIN_PICS, PicUrlsUtil.parsePicsString(itemVO.getItemMainPics()));
        }
        itemDO.setPicUrlsString(itemDO.getPicUrlsString());
        //评分（暂时普通商品用）
        if(null != itemVO.getGrade()){
            itemFeature.put(ItemFeatureKey.GRADE, itemVO.getGrade());
        }
        //减库存方式
        itemFeature.put(ItemFeatureKey.REDUCE_TYPE, itemVO.getReduceType());
        //自定义属性(itemDO中会自动set，所以注释掉了)
        //itemDO.setItemProperties(itemVO.getItemProperties());

        //商品编码
        itemDO.setCode(itemVO.getCode());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        if(StringUtils.isNotBlank(itemVO.getEndDateStr())) {
            Date endDate = dateFormat.parse(itemVO.getEndDateStr());
            itemDO.setEndDate(endDate);
        }
        if(itemVO.getLatitudeVO() != null) {
            itemDO.setLatitude(itemVO.getLatitudeVO());
        }
        if(itemVO.getLongitudeVO() != null) {
            itemDO.setLongitude(itemVO.getLongitude());
        }
        return itemDO;
    }
    /**
     * 修改提交时设置skuDOlist
     * @param commonItemPublishDTO
     * @param itemVO
     * @return
     * @throws Exception
     */
    public static CommonItemPublishDTO setItemSkuDOListCommonItemPublishDTO(CommonItemPublishDTO commonItemPublishDTO,ItemVO itemVO)throws Exception{
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListByStr())){
            //insert操作时的数组
            List<ItemSkuDO> itemSkuDOList = new ArrayList<ItemSkuDO>();
            //新增sku数组
            List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>();
            //删除sku数组
            List<Long> delItemSkuDOIdList = new ArrayList<Long>();
            //修改sku数组
            List<ItemSkuDO> updItemSkuDOList = new ArrayList<ItemSkuDO>();
            for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
                itemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                if(0 == itemSkuVO.getId()){
                    if(itemSkuVO.isChecked()){//没有id，有checked是新增
                        addItemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                    }
                }else{
                    if(!itemSkuVO.isChecked()){//有id，没有checked是删除
                        delItemSkuDOIdList.add(itemSkuVO.getId());
                    }else{
                        if(itemSkuVO.isModifyStatus()){//有id，有checked，有modifayStatus是修改
                            updItemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                        }
                    }
                }
            }
            commonItemPublishDTO.setItemSkuDOList(itemSkuDOList);
            commonItemPublishDTO.setAddItemSkuDOList(addItemSkuDOList);
            commonItemPublishDTO.setDelItemSkuDOList(delItemSkuDOIdList);
            commonItemPublishDTO.setUpdItemSkuDOList(updItemSkuDOList);
        }
        return commonItemPublishDTO;

    }
    /**
     * 获得sku的总库存（暂时就用于活动商品）
     * @param itemVO
     * @return
     * @throws Exception
     */
    public static int getCountStockNum(ItemVO itemVO)throws Exception{
    	int stockNum =0;
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListByStr())){
            for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
                if(itemSkuVO.isChecked()){
                	stockNum += itemSkuVO.getStockNum();
                }
            }
         
        }
        return stockNum;

    }
    
    
    
    public static ItemVO getItemVO(ItemDO itemDO,CategoryVO categoryVO)throws Exception{
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemDO, itemVO);
        //分转元
        itemVO.setPriceY(NumUtil.moneyTransformDouble(itemVO.getPrice()));
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuDOList())){
            List<ItemSkuVO> itemSkuVOList = new ArrayList<ItemSkuVO>();
            for (ItemSkuDO itemSkuDO : itemVO.getItemSkuDOList()){
                itemSkuVOList.add(ItemSkuVO.getItemSkuVO(itemSkuDO));
            }
            itemVO.setItemSkuVOList(itemSkuVOList);
        }

        if(null != itemVO.getItemFeature()){
            //提前预定时间(暂时酒店用)
            itemVO.setEndBookTimeLimit((long) (itemVO.getItemFeature().getEndBookTimeLimit() / (24 * 3600)));
           
            if(itemVO.getItemFeature().getStartBookTimeLimit()!=0){
            	 //入园规则提前几天（暂时景区用）
                long startBookTimeLimit = itemVO.getItemFeature().getStartBookTimeLimit() ;
                //入园规则提前几点（暂时景区用）
                long days = startBookTimeLimit / ( 60 * 60 * 24);  
                //入园规则提前几点（暂时景区用）
                long hours = (24  - (startBookTimeLimit % ( 60 * 60 * 24)) / ( 60 * 60));  
                itemVO.setStartBookTimeDays(days);
                itemVO.setStartBookTimeHours(hours);
            }

            //评分（暂时普通商品用）
            itemVO.setGrade(itemVO.getItemFeature().getGrade());
            //库存方式
            itemVO.setReduceType(itemVO.getItemFeature().getReduceType().getBizType());
            //最晚到店时间列表(暂时只有酒店用)
            itemVO.setOpenTimeList(itemVO.getItemFeature().getLatestArriveTime());

        }
        //picUrls转对应的list
        if(StringUtils.isNotBlank(itemVO.getPicUrlsString())){
            itemVO.setSmallListPic(PicUrlsUtil.getSmallListPic(itemDO));
            itemVO.setBigListPic(PicUrlsUtil.getBigListPic(itemDO));
            itemVO.setPicList(PicUrlsUtil.getPicList(itemDO));
            itemVO.setItemMainPics(PicUrlsUtil.getItemMainPics(itemDO));
        }
        //截止时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        if(itemDO.getEndDate() != null) {
            itemVO.setEndDateStr(dateFormat.format(itemDO.getEndDate()));
        }
        itemVO.setLongitudeVO(itemDO.getLongitude());
        itemVO.setLatitudeVO(itemDO.getLatitude());
        //个性化处理,构建sku表格所用结构
        List<Set<String>> tranSetList = new ArrayList<Set<String>>();
        //sku属性td的rowspan
        List<Integer> skuTdRowNumList = new ArrayList<Integer>();
        //构建所有的属性组合
        List<ItemSkuVO> itemSkuVOListAll = new ArrayList<ItemSkuVO>();
        //自定义属性和非自定义属性列表（非sku）
        //List<ItemSkuPVPair> itemPropertyList = itemDO.getItemPropertyList();
        //TODO 有自定义属性的时候需要修改
        if(CollectionUtils.isNotEmpty(categoryVO.getSellCategoryPropertyVOs())){
            //构建属性列表
            for(CategoryPropertyValueVO categoryPropertyValueVO : categoryVO.getSellCategoryPropertyVOs()){
                tranSetList.add(new HashSet<String>());
                skuTdRowNumList.add(0);
            }
            List<ItemSkuVO> itemSkuVOListTran = itemVO.getItemSkuVOList();
            //填充属性列表
            for (int i = 0; i <itemSkuVOListTran.size(); i++) {
                List<ItemSkuPVPair> itemSkuPVPairListTran = itemSkuVOListTran.get(i).getItemSkuPVPairList();
                for (int j = 0; j < itemSkuPVPairListTran.size(); j++) {
                    //pId + vId + vTxt决定唯一
                    String str = String.valueOf(itemSkuPVPairListTran.get(j).getPId()) + String.valueOf(itemSkuPVPairListTran.get(j).getVId()) + null == itemSkuPVPairListTran.get(j).getVTxt() ? "" : itemSkuPVPairListTran.get(j).getVTxt();
                    tranSetList.get(j).add(str);
                }
            }
            //计算skuTdRowNumList
            int len = tranSetList.size();
            for (int i = 0; i < len; i++) {
                if(i == len - 1){
                    skuTdRowNumList.set(i,1);
                }else if(i == len - 2){
                    skuTdRowNumList.set(i,tranSetList.get(i + 1).size());
                }else{
                    int rowNum = 1;
                    for (int j = i + 1; j < len; j++) {
                        rowNum = rowNum * tranSetList.get(j).size();
                    }
                    skuTdRowNumList.set(i,rowNum);
                }
            }

            //构建所有的属性组合
            itemSkuVOListAll = CategoryVO.getItemSkuVOListAll(categoryVO);
            itemVO.setItemSkuVOListAll(itemSkuVOListAll);
            //根据check状态设置check
            List<ItemSkuVO> itemSkuVOList = itemVO.getItemSkuVOList();
            for (int i = 0; i < itemSkuVOList.size(); i++) {
                for (int j = 0; j < itemSkuVOListAll.size(); j++) {
                    if(isEqual(itemSkuVOList.get(i).getItemSkuPVPairList(),itemSkuVOListAll.get(j).getItemSkuPVPairList())){
                        itemSkuVOListAll.get(j).setChecked(true);
                        itemSkuVOListAll.get(j).setId(itemSkuVOList.get(i).getId());
                        itemSkuVOListAll.get(j).setStockNum(itemSkuVOList.get(i).getStockNum());
                        itemSkuVOListAll.get(j).setPriceY(itemSkuVOList.get(i).getPriceY());
                        itemSkuVOListAll.get(j).setVersion(itemSkuVOList.get(i).getVersion());
                        //GF用
                        itemSkuVOListAll.get(j).setMainPic(itemSkuVOList.get(i).getMainPic());
                        break;
                    }

                }
                //顺便把category中的check属性设置了（以后去掉参数categoryVO的时候，可以挪出去）
                for (ItemSkuPVPair itemSkuPVPair : itemSkuVOList.get(i).getItemSkuPVPairList()){
                    for (CategoryPropertyValueVO categoryPropertyValueVO : categoryVO.getSellCategoryPropertyVOs()){
                        //pId相同时
                        if(itemSkuPVPair.getPId() == categoryPropertyValueVO.getCategoryPropertyVO().getId()){
                            for (CategoryValueVO categoryValueVO : categoryPropertyValueVO.getCategoryPropertyVO().getCategoryValueVOs()){
                                //vId和vTxt相同时
                                if(itemSkuPVPair.getVId() == categoryValueVO.getId() && itemSkuPVPair.getVTxt().equals(categoryValueVO.getText())){
                                    categoryValueVO.setChecked(true);
                                }
                            }
                        }
                    }
                }
            }
        }
        itemVO.setSkuTdRowNumList(skuTdRowNumList);
        //sku排序
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOList())) {
            Collections.sort(itemVO.getItemSkuVOList(), new ItemSkuVO.ItemSkuVOSort());
        }
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListAll())) {
            Collections.sort(itemVO.getItemSkuVOListAll(), new ItemSkuVO.ItemSkuVOSort());
        }

        return itemVO;
    }

    //供vm页面用
    public String getItemSkuVOListAllStr(){
        return JSON.toJSONString(this.itemSkuVOListAll, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static boolean isEqual(List<ItemSkuPVPair> list1,List<ItemSkuPVPair> list2){
        if(CollectionUtils.isNotEmpty(list1) && CollectionUtils.isNotEmpty(list2) && list1.size() == list2.size()){
            for (int i = 0; i < list1.size(); i++) {
                if(list1.get(i).getPId() != list2.get(i).getPId() || !list1.get(i).getPTxt().equals(list2.get(i).getPTxt()) || list1.get(i).getVId() != list2.get(i).getVId() || !list1.get(i).getVTxt().equals(list2.get(i).getVTxt())){
                    return false;
                }
            }
            return true;

        }else{
            return false;
        }
    }

    /**
     * sku jsonStr转list对象
     * @return
     */
    public List<ItemSkuVO> getItemSkuVOListByStr(){
        if(StringUtils.isBlank(this.itemSkuVOStr)){
            return null;
        }
        List<ItemSkuVO> itemSkuVOList = JSON.parseArray(this.itemSkuVOStr,ItemSkuVO.class);
        return itemSkuVOList;
    }

//    /**
//     * sku jsonStr转list对象
//     * @return
//     */
//    public List<ItemSkuDO> getItemSkuDOList(){
//        if(CollectionUtils.isEmpty(itemSkuVOListAll)){
//            itemSkuVOListAll = getItemSkuVOListByStr();
//            if(CollectionUtils.isEmpty(itemSkuVOListAll)) {
//                return null;
//            }
//        }
//        List<ItemSkuDO> itemSkuDOList = new ArrayList<>();
//        for(ItemSkuVO itemSkuVO : itemSkuVOListAll) {
//            ItemSkuDO itemSkuDO = ItemSkuVO.getItemSkuDO(this, itemSkuVO);
//            itemSkuDOList.add(itemSkuDO);
//        }
//        return itemSkuDOList;
//    }

    public String getItemSkuVOStr() {
        return itemSkuVOStr;
    }

    public void setItemSkuVOStr(String itemSkuVOStr) {
        this.itemSkuVOStr = itemSkuVOStr;
    }

    public List<ItemSkuVO> getItemSkuVOList() {
        return itemSkuVOList;
    }

    public void setItemSkuVOList(List<ItemSkuVO> itemSkuVOList) {
        this.itemSkuVOList = itemSkuVOList;
    }

    public double getPriceY() {
        return priceY;
    }

    public void setPriceY(double priceY) {
        this.priceY = priceY;
    }

    public List<Integer> getSkuTdRowNumList() {
        return skuTdRowNumList;
    }

    public void setSkuTdRowNumList(List<Integer> skuTdRowNumList) {
        this.skuTdRowNumList = skuTdRowNumList;
    }

    public List<ItemSkuVO> getItemSkuVOListAll() {
        return itemSkuVOListAll;
    }

    public void setItemSkuVOListAll(List<ItemSkuVO> itemSkuVOListAll) {
        this.itemSkuVOListAll = itemSkuVOListAll;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getEndBookTimeLimit() {
        return endBookTimeLimit;
    }

    public void setEndBookTimeLimit(Long endBookTimeLimit) {
        this.endBookTimeLimit = endBookTimeLimit;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSmallListPic() {
        return smallListPic;
    }

    public void setSmallListPic(String smallListPic) {
        this.smallListPic = smallListPic;
    }

    public String getBigListPic() {
        return bigListPic;
    }

    public void setBigListPic(String bigListPic) {
        this.bigListPic = bigListPic;
    }

    public String getCoverPics() {
        return coverPics;
    }

    public void setCoverPics(String coverPics) {
        this.coverPics = coverPics;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public Integer getReduceType() {
        return reduceType;
    }

    public void setReduceType(Integer reduceType) {
        this.reduceType = reduceType;
    }

    public List<String> getOpenTimeList() {
        return openTimeList;
    }

    public void setOpenTimeList(List<String> openTimeList) {
        this.openTimeList = openTimeList;
    }
	public Long getStartBookTimeDays() {
		return startBookTimeDays;
	}
	public void setStartBookTimeDays(Long startBookTimeDays) {
		this.startBookTimeDays = startBookTimeDays;
	}
	public Long getStartBookTimeHours() {
		return startBookTimeHours;
	}
	public void setStartBookTimeHours(Long startBookTimeHours) {
		this.startBookTimeHours = startBookTimeHours;
	}

    public String getPcDetail() {
        return pcDetail;
    }

    public void setPcDetail(String pcDetail) {
        this.pcDetail = pcDetail;
    }

    public PictureTextVO getPictureTextVO() {
        return pictureTextVO;
    }

    public void setPictureTextVO(PictureTextVO pictureTextVO) {
        this.pictureTextVO = pictureTextVO;
    }

    public String getPictureTextVOStr() {
        return pictureTextVOStr;
    }

    public void setPictureTextVOStr(String pictureTextVOStr) {
        this.pictureTextVOStr = pictureTextVOStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public List<String> getItemMainPics() {
        return itemMainPics;
    }

    public void setItemMainPics(List<String> itemMainPics) {
        this.itemMainPics = itemMainPics;
    }

    public Double getLatitudeVO() {
        return latitudeVO;
    }

    public void setLatitudeVO(Double latitudeVO) {
        this.latitudeVO = latitudeVO;
    }

    public Double getLongitudeVO() {
        return longitudeVO;
    }

    public void setLongitudeVO(Double longitudeVO) {
        this.longitudeVO = longitudeVO;
    }
}
