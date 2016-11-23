package com.yimayhd.sellerAdmin.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdi on 16/9/9.
 */
public enum ExpressCompanyEnum {

    ems(1,"ems", "EMS"),
    huitongkuaidi(2,"huitongkuaidi", "百世汇通"),
    shentong(3,"shentong", "申通快递"),
    shunfeng(4,"shunfeng", "顺丰速运"),
    yuantong(5,"yuantong", "圆通速递"),
    yunda(6,"yunda", "韵达快递"),
    zhongtong(7,"zhongtong", "中通快递"),
    tiantian(8,"tiantian", "天天快递"),

    ;

    private static final Map<String, ExpressCompanyEnum> codeMap = new HashMap<String, ExpressCompanyEnum>((int)(ExpressCompanyEnum.values().length/0.75)+1);

    static{
        for(ExpressCompanyEnum expressCompanyEnum: values()){
            codeMap.put(expressCompanyEnum.getName(), expressCompanyEnum);
        }
    }



    /**
     * 根据code获取枚举值
     * @param name 编码
     * @return 对应的枚举
     */
    public static ExpressCompanyEnum valueOfName(String name){
        return codeMap.get(name);
    }

    private int code; // 编码

    private String name; //客户端显示的错误提示

    private String desc; // 描述

    ExpressCompanyEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    ExpressCompanyEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
