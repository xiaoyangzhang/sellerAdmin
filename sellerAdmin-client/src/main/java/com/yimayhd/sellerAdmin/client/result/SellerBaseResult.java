package com.yimayhd.sellerAdmin.client.result;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by wangdi on 16/6/7.
 */
public abstract class SellerBaseResult implements Serializable {

    private static final long serialVersionUID = -3601242652215410749L;

    public SellerBaseResult() {
    }

    public String toString() {
        try {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        } catch (Exception var2) {
            return super.toString();
        }
    }
}
