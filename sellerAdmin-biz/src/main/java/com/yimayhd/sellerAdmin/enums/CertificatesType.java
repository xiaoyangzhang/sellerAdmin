package com.yimayhd.sellerAdmin.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxiaopeng on 16/11/17.
 */
public enum CertificatesType {

    CARD(1, "身份证"),
    PASSPORT(2, "护照"),
    SOLDIERCARD(3, "军人证"),
    HMPASS(4, "港澳通行证");


    private static Map<Integer, CertificatesType> typeMap;
    private int type;
    private String desc;

    private CertificatesType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getType() {
        return this.type;
    }

    public static CertificatesType getByType(int type) {
        return  typeMap.get(Integer.valueOf(type));
    }

    public static boolean containsType(int type) {
        return typeMap.containsKey(Integer.valueOf(type));
    }

    static {
        typeMap = new HashMap();
        CertificatesType[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            CertificatesType certificatesType = arr$[i$];
            typeMap.put(Integer.valueOf(certificatesType.getType()), certificatesType);
        }

    }
}
