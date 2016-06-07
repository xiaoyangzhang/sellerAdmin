package com.yimayhd.sellerAdmin.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.commentcenter.client.domain.ComCommentDO;
import com.yimayhd.user.client.domain.UserDO;

/**
 * Created by czf on 2015/12/31.
 */
public class ComCommentVO extends ComCommentDO {
    private UserDO userDO;

    public static ComCommentVO getComCommentVO(ComCommentDO comCommentDO){
        ComCommentVO comCommentVO = new ComCommentVO();
        BeanUtils.copyProperties(comCommentDO,comCommentVO);
        return comCommentVO;
    }

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }
}
