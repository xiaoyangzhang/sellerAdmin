package com.yimayhd.sellerAdmin;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoAddDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.gf.biz.GFTagBiz;
import com.yimayhd.gf.model.query.GFTagVoQuery;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;

/**
 * @author : zhangchao
 * @date : 2015年12月29日 下午6:05:54
 * @Description: GF商品标签管理
 */
@Controller
@RequestMapping("/GF/TagManager")
public class GFTagManagerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GFTagManagerController.class);
	
	@Autowired
	private GFTagBiz gfTagBiz;
	
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月29日 下午6:06:36
	 * @Description: 标签列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String gfTagList(Model model,GFTagVoQuery gfTagVoQuery){
		try {
			gfTagVoQuery.setType(TagType.GFPRODUCTTAG.getType());
			PageVO<ComTagDO> pageVo = gfTagBiz.getPageTag(gfTagVoQuery);
			model.addAttribute("themeList", pageVo.getResultList());
			model.addAttribute("pageVo", pageVo);
			return "/system/gfTag/list";
		} catch (Exception e) {
			LOGGER.error("gfTagList:gfTagVoQuery={}",JSON.toJSONString(gfTagVoQuery), e);
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月30日 下午3:51:13
	 * @Description: 标签查询
	 */
	@RequestMapping(value = "/listSearch", method = RequestMethod.POST)
	public String listSearch(Model model, GFTagVoQuery gfTagVoQuery ){
		try {
			gfTagVoQuery.setType(TagType.GFPRODUCTTAG.getType());
			PageVO<ComTagDO> pageVo = gfTagBiz.getPageTag(gfTagVoQuery);
			model.addAttribute("gfTagVoQuery", gfTagVoQuery);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("themeList", pageVo.getResultList());
			return "/system/gfTag/list";
		} catch (Exception e) {
			LOGGER.error("gfTagList:gfTagVoQuery={}",JSON.toJSONString(gfTagVoQuery), e);
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月30日 上午9:56:53
	 * @Description: 添加标签跳转
	 */
	@RequestMapping(value = "/toAddTag", method = RequestMethod.GET)
	public String toAddTag(){
		return "/system/gfTag/edit";
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月30日 上午10:05:57
	 * @Description: 添加GF商品标签
	 */
	@RequestMapping(value = "/addTag", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo addTag(TagInfoAddDTO tagInfoAddDTO,ModelMap modelMap){
		try {
			tagInfoAddDTO.setOutType(TagType.GFPRODUCTTAG.getType());
			BaseResult<ComTagDO> baseResult = gfTagBiz.addComTagInfo(tagInfoAddDTO);
			
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("addTag:tagInfoAddDTO={}",JSON.toJSONString(tagInfoAddDTO), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月30日 上午10:58:42
	 * @Description: 商品标签的修改跳转
	 */
	@RequestMapping(value = "/toEditTag/{id}", method = RequestMethod.GET)
	public String toEditTag(Model model, @PathVariable(value = "id") long id){
		try {
			BaseResult<ComTagDO> baseResult = gfTagBiz.selectTagInfoById(id);
			model.addAttribute("theme", baseResult.getValue());
			return "/system/gfTag/edit";
		} catch (Exception e) {
			LOGGER.error("toEditTag:id={}",id, e);
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月30日 上午11:03:53
	 * @Description: 商品标签的修改
	 */
	@RequestMapping(value = "/editTag/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo editTag(ModelMap model,@PathVariable(value = "id") long id,TagInfoAddDTO tagInfoAddDTO){
		try {
			if(id<=0){
				return new ResponseVo(ResponseStatus.ERROR);
			}
			tagInfoAddDTO.setTagId(id);
			tagInfoAddDTO.setOutType(TagType.GFPRODUCTTAG.getType());
			BaseResult<ComTagDO> baseResult = gfTagBiz.updateComTagInfo(tagInfoAddDTO);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("tagInfoAddDTO={}",JSON.toJSONString(tagInfoAddDTO), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月31日 上午10:34:41
	 * @Description: 标签批量上下架---启用
	 */
	@RequestMapping(value = "/batchEnableStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchEnableStatus(@RequestParam("themeIdList[]") ArrayList<Long> themeIdList) throws Exception {
		try {
			BaseResult<Boolean> baseResult = gfTagBiz.batchEnableStatus(themeIdList);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("themeIdList={}",JSON.toJSONString(themeIdList), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	/**
	 * @author : zhangchao
	 * @date : 2015年12月31日 上午10:50:24
	 * @Description: 标签批量上下架---禁用
	 */
	@RequestMapping(value = "/batchDisableStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchDisableStatus(@RequestParam("themeIdList[]") ArrayList<Long> themeIdList) throws Exception {
		try {
			BaseResult<Boolean> baseResult = gfTagBiz.batchDisableStatus(themeIdList);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("themeIdList={}",JSON.toJSONString(themeIdList), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	/**
	 * enableStatus
	 * 启用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enableStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo enableStatus(@PathVariable("id") long id) throws Exception {
		try {
			BaseResult<Boolean> baseResult = gfTagBiz.enableScenicItem(id);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("id={}",JSON.toJSONString(id), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	/**
	 * 停用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/disableStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo disableStatus(@PathVariable("id") Long id) throws Exception {
		try {
			BaseResult<Boolean> baseResult = gfTagBiz.disableScenicItem(id);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("id={}",JSON.toJSONString(id), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}

}
