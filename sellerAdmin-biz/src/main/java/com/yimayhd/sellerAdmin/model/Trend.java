package com.yimayhd.sellerAdmin.model;

import java.util.Date;
import java.util.List;

import com.yimayhd.sellerAdmin.base.BaseModel;

/**
 * Created by Administrator on 2015/11/10.
 */
public class Trend extends BaseModel{
    private User user;
    private String content;
    private List<String> pictureUrlList;
    private Date publishDate;
    private Integer trendStatus;
    private List<String> auditHistoryList;
    private Integer commentNum;
    private Integer praiseNum;
    private String ip;
    private String summary;//审核备注

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPictureUrlList() {
        return pictureUrlList;
    }

    public void setPictureUrlList(List<String> pictureUrlList) {
        this.pictureUrlList = pictureUrlList;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getTrendStatus() {
        return trendStatus;
    }

    public void setTrendStatus(Integer trendStatus) {
        this.trendStatus = trendStatus;
    }

    public List<String> getAuditHistoryList() {
        return auditHistoryList;
    }

    public void setAuditHistoryList(List<String> auditHistoryList) {
        this.auditHistoryList = auditHistoryList;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
