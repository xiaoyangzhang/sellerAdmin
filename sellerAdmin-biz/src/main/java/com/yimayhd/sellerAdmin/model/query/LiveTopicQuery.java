package com.yimayhd.sellerAdmin.model.query;

import com.yimayhd.sellerAdmin.base.BaseQuery;

/**
 * Created by Administrator on 2015/12/23.
 */
public class LiveTopicQuery  extends BaseQuery {

    /**类型*/
    private Integer TopicType;

    /**话题名称*/
    private String topicName;

    public Integer getTopicType() {
        return TopicType;
    }

    public void setTopicType(Integer topicType) {
        TopicType = topicType;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
