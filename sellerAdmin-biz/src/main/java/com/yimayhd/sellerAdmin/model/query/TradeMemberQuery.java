package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/27.
 */
public class TradeMemberQuery extends BaseQuery {
    private String nickName;
    private String tel;
    private String cityName;
    private Long merchantUserId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(Long merchantUserId) {
        this.merchantUserId = merchantUserId;
    }
}
