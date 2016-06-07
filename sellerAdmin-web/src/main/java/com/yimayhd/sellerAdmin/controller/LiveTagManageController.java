package com.yimayhd.sellerAdmin.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoDTO;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.LiveTagVO;
import com.yimayhd.sellerAdmin.service.LiveTagService;

/**
 * 直播（标签）话题管理
 * 
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/liveTagManage")
public class LiveTagManageController extends BaseController {
	@Autowired
	private LiveTagService liveTagService;

	/**
	 * 直播管理列表
	 * 
	 * @return 直播管理列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(TagInfoDTO query) throws Exception {
		Integer pageNo = getInteger("pageNumber");
		if (pageNo != null) {
			query.setPageNo(pageNo);
		}
		PageVO<ComTagDO> pageVo = liveTagService.pageQueryLiveTag(query);
		put("pageVo", pageVo);
		put("query", query);
		return "/system/liveTag/list";
	}

	/**
	 * 保存
	 * 
	 * @return 编辑
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(LiveTagVO liveTagVO) throws Exception {
		try {
			liveTagService.save(liveTagVO);
			return ResponseVo.success();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 添加
	 * 
	 * @return 添加
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd")
	public String toAdd() throws Exception {
		return "/system/liveTag/add";
	}

	/**
	 * 编辑
	 * 
	 * @return 编辑
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit/{id}")
	public String toEdit(@PathVariable(value = "id") long id) throws Exception {
		ComTagDO tag = liveTagService.getLveTagById(id);
		put("tag", tag);
		return "/system/liveTag/add";
	}

	/**
	 * 禁用
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toDisable/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo toDisable(@PathVariable(value = "id") long id) throws Exception {
		try {
			liveTagService.disableLiveTagById(id);
			return new ResponseVo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 启用
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEnable/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo toEnable(@PathVariable(value = "id") long id) throws Exception {
		try {
			liveTagService.enableLiveTagById(id);
			return new ResponseVo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 批量禁用
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toBatchDisable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo toBatchDisable(@RequestParam("ids[]") List<Long> ids) throws Exception {
		try {
			if (CollectionUtils.isNotEmpty(ids)) {
				liveTagService.disableLiveTagByIdList(ids);
			}
			return new ResponseVo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * 批量启用
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toBatchEnable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo toBatchEnable(@RequestParam("ids[]") List<Long> ids) throws Exception {
		try {
			if (CollectionUtils.isNotEmpty(ids)) {
				liveTagService.enableLiveTagByIdList(ids);
			}
			return new ResponseVo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
}
