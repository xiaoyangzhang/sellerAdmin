package com.yimayhd.sellerAdmin.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 编码样式：【CCCBBOOXX】
 * 编码示例说明：
 * CCC 中心编码&业务系统 (996只需996)
 * BB  业务类型（00-默认） (01xx商品,02xx酒店)
 * OO  操作类型（00-默认） 00：新增，01：更新，02：查询，03：删除，04：取消，05：无效，06：关闭，07：冻结，08：解冻, 10：拣货, 12：签收，13：拒收）
 * XX  具体编码（00-成功,01-失败,02-参数错误,                   99-系统错误）
 *
 *
 * @Title: SellerBaseCodeEnum.java
 * @Description: TODO(仅用于center业务校验)
 * @version $Id: SellerBaseCodeEnum.java, v 0.1 2016年1月21日 下午2:29:38 wd Exp $
 */
public enum SellerBaseCodeEnum {

    /** 系统默认 */
    SUCCESS(100000000, "成功", "success"),
    FAILED(100000001, "失败", "failed"),
    PARAM_INVALID(100000002, "参数错误", "invalid parameter"),
    NO_DATA(100000003, "没有数据", "find no data"),
    NULL_ERROR(100000004,"参数为空","parameter is null"),
    SYSTEM_ERROR(10000005, "系统错误", "system error"),
    PAGE_PARAM_INVALID(10000006, "分页参数", "page param error"),
    /*****************酒店商品*********************/

            ;

    private static final Map<Integer, SellerBaseCodeEnum> codeMap = new HashMap<Integer, SellerBaseCodeEnum>((int)(SellerBaseCodeEnum.values().length/0.75)+1);

    static{
        for(SellerBaseCodeEnum sellerBaseCodeEnum: values()){
            codeMap.put(sellerBaseCodeEnum.getCode(), sellerBaseCodeEnum);
        }
    }

    private int code;
    private String desc ;
    private String msg;

    SellerBaseCodeEnum(int code, String desc,String msg) {
        this.code = code;
        this.desc = desc;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
