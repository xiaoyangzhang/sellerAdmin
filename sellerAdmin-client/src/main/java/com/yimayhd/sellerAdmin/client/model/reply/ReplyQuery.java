package com.yimayhd.sellerAdmin.client.model.reply;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/11/15.
 */
public class ReplyQuery implements Serializable {

    private static final long serialVersionUID = 1586535845451420202L;
    private long sellerId; //商家id  比传

    private long id;  //id 比传

    private long gmtModified;

    private List<String> backPicUrlList;   //评论图片集

    private String backContent; //比传

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public List<String> getBackPicUrlList() {
        return backPicUrlList;
    }

    public void setBackPicUrlList(List<String> backPicUrlList) {
        this.backPicUrlList = backPicUrlList;
    }

    public String getBackContent() {
        return backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }
}
