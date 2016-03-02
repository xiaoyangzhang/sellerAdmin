package com.yimayhd.sellerAdmin.service;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.ThemeVo;
import com.yimayhd.sellerAdmin.model.query.ThemeVoQuery;

/** 
* @ClassName: ThemeService 
* @Description: 主题管理
* @author create by yushengwei @ 2015年12月1日 上午10:17:51 
*/
public interface ThemeService {
	/**
     * 获取主题列表(可带查询条件)
     * @return 主题列表
     */
	PageVO<ComTagDO> getPageTheme(ThemeVoQuery themeVoQuery )throws Exception;
	
	/**
	* @Title: getListTheme 
	* @Description:(获取话题 list) 
	* @author create by yushengwei @ 2015年12月27日 下午4:01:22 
	* @param @param themeVoQuery
	* @param @return
	* @param @throws Exception 
	* @return List<ComTagDO> 返回类型 
	* @throws
	 */
	List<ComTagDO> getListTheme(int type )throws Exception;
	
    /**
     * 获取主题详情
     * @return 主题详情
     */
    ThemeVo getById(long id)throws Exception;

    /**
     * 添加主题
     * @param activity
     * @return
     * @throws Exception
     */
    ThemeVo saveOrUpdate(ThemeVo themeVo)throws Exception;

    /**
     * 修改主题
     * @param activity
     * @throws Exception
     */
    void modify(ThemeVo themeVo)throws Exception;
    
    /**
     * 删除主题
     * @param activity
     * @throws Exception
     */
    void delete(long id)throws Exception;
}
