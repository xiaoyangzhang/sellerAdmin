package com.yimayhd.sellerAdmin.controller;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.SnsSubjectVO;
import com.yimayhd.sellerAdmin.model.SubjectInfoAddVO;
import com.yimayhd.sellerAdmin.model.query.LiveListQuery;
import com.yimayhd.sellerAdmin.repo.CommentRepo;
import com.yimayhd.sellerAdmin.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播管理
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/liveManage")
public class LiveManageController extends BaseController {
    @Autowired
	private LiveService liveService;

    @Autowired
    private CommentRepo tagRepo;

    /**
     * 直播列表
     *
     * @return 直播列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, LiveListQuery liveListQuery) throws Exception {
        PageVO<SnsSubjectVO> pageVO = liveService.getList(liveListQuery);
        model.addAttribute("pageVo", pageVO);
        model.addAttribute("liveListQuery", liveListQuery);
        model.addAttribute("liveList", pageVO.getItemList());
        return "/system/live/list";
    }

    /**
     * 新增直播
     * @return 直播详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model) throws Exception {
        List<ComTagDO> comTagDOList = tagRepo.getTagListByTagType(TagType.LIVESUPTAG);
        model.addAttribute("comTagList",comTagDOList);
        return "/system/live/edit";
    }

    /**
     * 根据直播ID获取直播详情
     *
     * @return 直播详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
        SnsSubjectVO snsSubjectVO = liveService.getById(id);
        List<ComTagDO> comTagDOList = tagRepo.getTagListByTagType(TagType.LIVESUPTAG);
        model.addAttribute("comTagList",comTagDOList);
        model.addAttribute("live", snsSubjectVO);
        return "/system/live/edit";
    }

    /**
     * 新增直播
     * @return 直播详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(SubjectInfoAddVO subjectInfoAddVO) throws Exception {
        liveService.add(subjectInfoAddVO);
        return "/success";
    }

    /**
     * 根据直播ID获取直播详情
     *
     * @return 直播详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable(value = "id") long id,SubjectInfoAddVO subjectInfoAddVO) throws Exception {
        subjectInfoAddVO.setId(id);
        liveService.modify(subjectInfoAddVO);
        return "/success";
    }

    /**
     * 直播违规
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/violation/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo publish(@PathVariable("id") long id) throws Exception {
        liveService.violation(id);
        return new ResponseVo();
    }

    /**
     * 直播恢复
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/regain/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo close(@PathVariable("id") long id) throws Exception {
        liveService.regain(id);
        return new ResponseVo();
    }

    /**
     * 直播违规(批量)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchViolation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo batchPublish(@RequestParam("liveIdList[]") ArrayList<Long> liveIdList)
            throws Exception {
        liveService.batchViolation(liveIdList);
        return new ResponseVo();
    }

    /**
     * 根据id获取动态图片列表
     *
     * @return 图片列表
     * @throws Exception
     *//*
    @RequestMapping(value = "/picture/{trendId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo getPictureById(@PathVariable("trendId") Long trendId) throws Exception {
        List<String> pictureUrlList = liveService.getById(trendId).getPictureUrlList();
        return new ResponseVo(pictureUrlList);
    }*/
   
   
	
}
