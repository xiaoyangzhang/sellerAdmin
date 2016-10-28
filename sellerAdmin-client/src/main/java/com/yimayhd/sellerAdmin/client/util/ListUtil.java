package com.yimayhd.sellerAdmin.client.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by menhaihao on 15/5/22.
 *
 */
public class ListUtil {
    public static String listToStr(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String pic : list) {
            sb.append("|").append(pic);
        }
        sb.append("|");
        return sb.toString();
    }

    public static List<String> strToList(String str) {
        if (StringUtils.isNotBlank(str)) {
            String[] list = StringUtils.split(str, "|");
            if (list != null && list.length > 0) {
                return Arrays.asList(list);
            }
        }
        return null;
    }

    public static String longListToStr(List<Long> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Long pic : list) {
            sb.append("|").append(pic);
        }
        sb.append("|");
        return sb.toString();
    }
}
