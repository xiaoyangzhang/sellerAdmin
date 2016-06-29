package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by czf on 2015/12/31.
 */
public class EvaluationListQuery extends BaseQuery {
    private static final long serialVersionUID = -6563354872196057114L;
    private String beginDate;
    private String endDate;
    private String tel;
    private String nickName;
    private int sector;
    private int evaluationStatus;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(int evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }
}
