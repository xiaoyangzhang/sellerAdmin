package com.yimayhd.sellerAdmin.controller;

import java.util.List;

import com.yimayhd.sellerAdmin.base.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.model.ThemeVo;
import com.yimayhd.sellerAdmin.model.query.ThemeVoQuery;
import com.yimayhd.sellerAdmin.service.ThemeService;

/** 
* @ClassName: ThemeManageController 
* @Description: 主题管理。包含俱乐部主题和活动主题的管理及设置
* @author create by yushengwei @ 2015年12月1日 上午10:07:56 
*/
@Controller
@RequestMapping("/B2C/themeManage")
public class ThemeManageController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeManageController.class);
	
	@Autowired
	private ThemeService themeService;
	
	/**
	* @Title: list 
	* @Description:(主题列表) 
	* @author create by yushengwei @ 2015年12月1日 上午10:10:15 
	* @param @param model
	* @param @param query
	* @param @return
	* @param @throws Exception 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, ThemeVoQuery query){
		try {
			PageVO<ComTagDO> pageVo = themeService.getPageTheme(query);
			model.addAttribute("themeList", pageVo.getItemList());
			model.addAttribute("pageVo", pageVo);
			return "/system/theme/list";
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
			return "/error";
		}
	}
	
	@RequestMapping(value = "/list/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo jsonList(int type){
			try {
				List<ComTagDO> list = themeService.getListTheme(type);
				if (CollectionUtils.isNotEmpty(list)) {
					return new ResponseVo(list);
				} 
			} catch (Exception e) {
				LOGGER.error("jsonList error,query="+type,e);
			}
				return new ResponseVo(ResponseStatus.ERROR);
	}
	
	
	/**
	* @Title: detail 
	* @Description:(主题详情) 
	* @author create by yushengwei @ 2015年12月1日 下午4:39:22 
	* @param @param model
	* @param @param id
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(Model model, @PathVariable(value = "id") long id) {
		try {
			model.addAttribute("theme", themeService.getById(1));
			return "/system/theme/detail";
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
			return "/error";
		}
	}
	
	/**
	* @Title: toEdit 
	* @Description:(编辑主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:39:57 
	* @param @param model
	* @param @param id
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id){
		try {
			ThemeVo theme = themeService.getById(id);
			model.addAttribute("theme", theme);
			return "/system/theme/edit";
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
			return "/error";
		}
	}
	
	/**
	* @Title: edit 
	* @Description:(保存编辑主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:40:19 
	* @param @param themeVo
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String edit(ThemeVo themeVo, @PathVariable(value = "id") long id){
		try {
			themeVo.setId(id);
			ThemeVo dbThemeVo = themeService.saveOrUpdate(themeVo);
			if(null != dbThemeVo ){
				return "/success";	
			}
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
		}
		return "/error";
	}


	/**
	* @Title: toAdd 
	* @Description:(新增主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:40:37 
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "/system/theme/edit";
	}

	/**
	* @Title: add 
	* @Description:(新增主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:40:57 
	* @param @param themeVo
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(ThemeVo themeVo){
		try {
			ThemeVo dbThemeVo = themeService.saveOrUpdate(themeVo);
			if(null != dbThemeVo){
				return "/success";
			}
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
		}
			return "/error";
	}
	
	
}
