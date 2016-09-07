package com.yimayhd.sellerAdmin.enums;

/*
 * FileName: ExaminType.java
 * Author:   liubb
 * Date:     2016年3月26日 下午3:04:12
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author liubb
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum MerchantNameType {

    TALENT("旅游线路达人", 1,""),
    MERCHANT("店铺", 2,""),
    TOUR_COR("旅游企业", 3,"《九休开放平台旅行社管理规定》"),
    HOTEL("酒店", 4,"《九休开放平台酒店管理规定》"),
    SCENIC("景区", 5,"《九休开放平台景区管理规定》"),
    CITY_COR("同城活动企业", 6,"《九休开放平台同城管理规定》"),
    //以下为旅游企业类型的两个身份
    TRAVEL_AGENCY("旅行社", 7,""),
    TRAVLE_SERVICE("旅游商务服务公司", 8,"")
    ;

    private String name;
    private int type;
    private String scheme;

    MerchantNameType(String name, int type,String scheme) {
        this.name = name;
        this.type = type;
        this.scheme = scheme;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
    public String getScheme(){
        return scheme;
    }

    public static MerchantNameType getByType(int type){
        for(MerchantNameType examineType : MerchantNameType.values() ){
            if( examineType.getType() == type ){
                return examineType ;
            }
        }
        return null ;
    }
    public static boolean has(int id) {
        for (MerchantNameType eXType : values()) {
            if (eXType.getType() == id) {
                return true;
            }
        }
        return false;
    }
}
