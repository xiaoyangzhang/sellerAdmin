package com.yimayhd.sellerAdmin.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * 编码样式：【CCCBBOOXX】
 * 编码示例说明：
 * CCC 中心编码&业务系统 (998只需998)
 * BB  业务类型（00-默认） (09日志,02XX)
 * OO  操作类型（00-默认） 00：新增，01：更新，02：查询，03：删除，04：取消，05：无效，06：关闭，07：冻结，08：解冻, 10：拣货, 12：签收，13：拒收）
 * XX  具体编码（00-成功,01-失败,02-参数错误,                   99-系统错误）
 *
 *
 * @Title: YzCampEnhanceCodeEnum.java
 * @Description: TODO(仅用于center业务校验)
 * @version $Id: YzCampEnhanceCodeEnum.java, v 0.1 2016年1月21日 下午2:29:38 wd Exp $
 */
public enum ItemCodeEnum {
    SYS_START_LOG(998090101,"调试日志开始", " start view "),
    SYS_PARAM_ERROR(998010202,"商户ID为空", "param is null"),

    DATA_CHECK_SUCCESS(998010000,"参数验证成功", "data check success"),
    DATA_CHECK_ERROR(998010002,"参数验证失败", "param check error"),
            ;

    private static final Map<Integer, ItemCodeEnum> codeMap = new HashMap<Integer, ItemCodeEnum>((int)(ItemCodeEnum.values().length/0.75)+1);

    static{
        for(ItemCodeEnum itemCodeEnum: values()){
            codeMap.put(itemCodeEnum.getCode(), itemCodeEnum);
        }
    }



    /**
     * 根据code获取枚举值
     * @param code 编码
     * @return 对应的枚举
     */
    public static ItemCodeEnum valueOfCode(int code){
        return codeMap.get(code);
    }

    private int code; // 编码

    private String msg; //客户端显示的错误提示

    private String desc; // 描述

    ItemCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    ItemCodeEnum(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
