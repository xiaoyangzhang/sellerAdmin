package com.yimayhd.sellerAdmin.model;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.snscenter.client.util.ListUtil;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by czf on 2015/12/31.
 */
public class SnsSubjectVO extends SnsSubjectDO {
    private UserDO userDO;
    private long tagId;
    private ComTagDO tag;
    private List<String> picList;
    private String picListStr;
    private String gmtCreatedStr;//时间字符串(yyyy-MM-dd HH:mm)
    private Integer commentNum;//评论数
    private Integer supportNum;//点赞数

    public static SnsSubjectVO getSnsSubjectVO(SnsSubjectDO snsSubjectDO){
        SnsSubjectVO snsSubjectVO = new SnsSubjectVO();
        BeanUtils.copyProperties(snsSubjectDO, snsSubjectVO);
        //图片list(以‘|’开头，所以要移除第一个元素)
        if(StringUtils.isNotBlank(snsSubjectDO.getPicContent())) {
            List<String> picList = ListUtil.strToListSplit(snsSubjectDO.getPicContent());
            snsSubjectVO.setPicList(picList);
            //图片预览用
            snsSubjectVO.setPicListStr(JSON.toJSONString(snsSubjectVO.getPicList()));
        }
        //时间
        snsSubjectVO.setGmtCreatedStr(DateUtil.dateToString(snsSubjectDO.getGmtCreated(), DateUtil.DAY_HORU_FORMAT));
        return snsSubjectVO;
    }

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public String getGmtCreatedStr() {
        return gmtCreatedStr;
    }

    public void setGmtCreatedStr(String gmtCreatedStr) {
        this.gmtCreatedStr = gmtCreatedStr;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public ComTagDO getTag() {
        return tag;
    }

    public void setTag(ComTagDO tag) {
        this.tag = tag;
    }

    public String getPicListStr() {
        return picListStr;
    }

    public void setPicListStr(String picListStr) {
        this.picListStr = picListStr;
    }
}
