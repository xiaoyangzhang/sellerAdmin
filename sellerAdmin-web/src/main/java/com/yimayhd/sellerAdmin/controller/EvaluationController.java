package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.ComCommentVO;
import com.yimayhd.sellerAdmin.model.query.EvaluationListQuery;
import com.yimayhd.sellerAdmin.service.EvaluationService;

/**
 *评价管理
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/evaluationManage")
public class EvaluationController extends BaseController {
	 @Autowired
	 private EvaluationService evaluationService;
	
	 /**
     *评价管理列表
     * @return 评价管理列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,EvaluationListQuery evaluationListQuery)  throws Exception {
        PageVO<ComCommentVO> pageVO = evaluationService.getList(evaluationListQuery);
    	model.addAttribute("evaluationList", pageVO.getResultList());
    	model.addAttribute("pageVo", pageVO);
    	 model.addAttribute("evaluationListQuery", evaluationListQuery);
    	return "/system/evaluate/list";
    }

    /**
     * 评价违规
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/violation/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo publish(@PathVariable("id") long id) throws Exception {
        evaluationService.violation(id);
        return new ResponseVo();
    }

    /**
     * 评价恢复
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/regain/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo close(@PathVariable("id") long id) throws Exception {
        evaluationService.regain(id);
        return new ResponseVo();
    }

    /**
     * 评价违规(批量)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchViolation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo batchPublish(@RequestParam("evaluationIdList[]") ArrayList<Long> evaluationIdList)
            throws Exception {
        evaluationService.batchViolation(evaluationIdList);
        return new ResponseVo();
    }
   
   
	
}
