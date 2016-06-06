package com.yimayhd.sellerAdmin.enums;

import com.yimayhd.ic.client.model.enums.PayMode;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xushubing on 2016/6/6.
 */
public enum BizPayMode {
    ONLINE_PAY(PayMode.ONLINE_PAY.getType(), "在线支付"),
    OFFLINE_PAY(PayMode.OFFLINE_PAY.getType(), "到店支付");

    private static Map<Integer, BizPayMode> typeMap;
    private int type;
    private String desc;

    private BizPayMode(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getType() {
        return this.type;
    }

    public static BizPayMode getByType(int type) {
        return (BizPayMode) typeMap.get(Integer.valueOf(type));
    }

    public static boolean containsType(int type) {
        return typeMap.containsKey(Integer.valueOf(type));
    }

    public static BizPayMode getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        } else {
            try {
                return valueOf(name);
            } catch (Throwable var2) {
                return null;
            }
        }
    }

    static {
        typeMap = new HashMap();
        BizPayMode[] arr$ = values();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            BizPayMode bizPayMode = arr$[i$];
            typeMap.put(Integer.valueOf(bizPayMode.getType()), bizPayMode);
        }

    }
}
