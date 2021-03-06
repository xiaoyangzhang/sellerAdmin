package com.yimayhd.sellerAdmin.util;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.sellerAdmin.enums.BizItemStatus;
import com.yimayhd.sellerAdmin.enums.BizItemType;
import com.yimayhd.sellerAdmin.enums.OrderSearchType;
import com.yimayhd.sellerAdmin.model.enums.ItemOperate;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;

/**
 * 商品工具类
 *
 * @author yebinL
 */
public class ItemUtil {
    public static List<String> getItemOperates(int itemtype, int status) {
        List<String> operates = new ArrayList<String>();
        if (ItemStatus.create.getValue() == status) {
            operates.add(ItemOperate.VIEW.name());
            operates.add(ItemOperate.EDIT.name());
            operates.add(ItemOperate.SHELVE.name());
            operates.add(ItemOperate.DELETE.name());
        } else if (ItemStatus.invalid.getValue() == status) {
            operates.add(ItemOperate.VIEW.name());
            operates.add(ItemOperate.EDIT.name());
            operates.add(ItemOperate.SHELVE.name());
            operates.add(ItemOperate.DELETE.name());
        } else if (ItemStatus.valid.getValue() == status&&(itemtype==ItemType.FREE_LINE.getValue()||itemtype==ItemType.FREE_LINE_ABOARD.getValue()||itemtype==ItemType.TOUR_LINE.getValue()||itemtype==ItemType.TOUR_LINE_ABOARD.getValue()||itemtype==ItemType.CITY_ACTIVITY.getValue()||itemtype==ItemType.POINT_MALL.getValue()||itemtype==ItemType.NORMAL.getValue())) {
            operates.add(ItemOperate.VIEW.name());
            operates.add(ItemOperate.EDIT.name());
            operates.add(ItemOperate.UNSHELVE.name());
        } else {
            operates.add(ItemOperate.VIEW.name());
            operates.add(ItemOperate.UNSHELVE.name());
        }
        return operates;
    }

    public static String getItemTypeName(int itemType) {
        if (itemType <= 0) {
            return null;
        }
        BizItemType it = BizItemType.get(itemType);
        if (it != null) {
            return it.getText();
        }
        return "未知类型";
    }

    public static String getOrderItemName(int itemType) {
        if (itemType <= 0) {
            return null;
        }
        OrderSearchType it = OrderSearchType.get(itemType);
        if (it != null) {
            return it.getText();
        }
        return "未知类型";
    }

    public static String getItemStatusName(int status) {
        if (status <= 0) {
            return null;
        }
        BizItemStatus is = BizItemStatus.get(status);
        if (is != null) {
            return is.getText();
        }
        return "异常状态";
    }

    public static boolean isFreeLine(ItemType itemType) {
        if (itemType==null){
            return false;
        }
        return ItemType.FREE_LINE.equals(itemType) || ItemType.FREE_LINE_ABOARD.equals(itemType);
    }

    public static boolean isAboardLine(ItemType itemType) {
        if (itemType==null){
            return false;
        }
        return ItemType.FREE_LINE_ABOARD.equals(itemType) || ItemType.TOUR_LINE_ABOARD.equals(itemType);
    }

    public static boolean isLine(ItemType itemType) {
        if (itemType==null){
            return false;
        }
        return ItemType.FREE_LINE_ABOARD.equals(itemType) || ItemType.TOUR_LINE_ABOARD.equals(itemType) || ItemType.FREE_LINE.equals(itemType) || ItemType.TOUR_LINE.equals(itemType);
    }
}
