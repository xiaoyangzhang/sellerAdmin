package com.yimayhd.sellerAdmin.controller;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.query.VoucherListQuery;
import com.yimayhd.sellerAdmin.model.vo.VoucherTemplateVO;
import com.yimayhd.sellerAdmin.service.VoucherTemplateService;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.voucher.client.enums.EntityType;
import com.yimayhd.voucher.client.enums.VoucherTemplateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

/**
 * 优惠券管理
 * @author xzj
 */
@Controller
@RequestMapping("/GF/voucherManage")
public class VoucherController extends BaseController {

    @Autowired
    private VoucherTemplateService voucherTemplateService;

    @Autowired
    private SessionManager sessionManager;
    /**
     * 优惠券列表
     *
     * @return 优惠券列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, VoucherListQuery voucherListQuery) throws Exception {
        PageVO<VoucherTemplateVO> pageVO = voucherTemplateService.getList(voucherListQuery);
        model.addAttribute("voucherListQuery",voucherListQuery);
        model.addAttribute("voucherTemplateList",pageVO.getItemList());
        model.addAttribute("pageVo",pageVO);
        return "/system/voucherTemplate/list";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd() throws Exception {
        return "/system/voucherTemplate/edit";
    }

    /**
     * 根据优惠券ID获取优惠券详情
     *
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
        VoucherTemplateVO voucherTemplateVO = voucherTemplateService.getById(id);
        model.addAttribute("voucherDO",voucherTemplateVO);
        return "/system/voucherTemplate/edit";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(VoucherTemplateVO voucherTemplateVO) throws Exception {
        UserDO userDO = sessionManager.getUser();
        if (userDO != null){
            voucherTemplateVO.setOperator(userDO.getNickname());
        }
        voucherTemplateVO.setEntityType(EntityType.SHOP.getType());
        voucherTemplateVO.setEntityId(1000);
        voucherTemplateVO.setDomain(1100);
        //新增默认下架状态
        voucherTemplateVO.setStatus(VoucherTemplateStatus.INACTIVE.getStatus());
        voucherTemplateVO.setEndTime(getEndTime(voucherTemplateVO.getEndTime()));
        voucherTemplateService.add(voucherTemplateVO);
        return "/success";
    }

    @RequestMapping("/setJoinStatus")
    @ResponseBody
    public ResponseVo setJoinStatus(long id, int status){
        try {
            VoucherTemplateVO voucherTemplateVO = new VoucherTemplateVO();
            voucherTemplateVO.setId(id);
            if (VoucherTemplateStatus.ACTIVE.getStatus() == status){
                voucherTemplateVO.setStatus(VoucherTemplateStatus.INACTIVE.getStatus());
            }
            if (VoucherTemplateStatus.INACTIVE.getStatus() == status){
                voucherTemplateVO.setStatus(VoucherTemplateStatus.ACTIVE.getStatus());
            }
            UserDO userDO = sessionManager.getUser();
            if (userDO != null){
                voucherTemplateVO.setOperator(userDO.getNickname());
            }
            voucherTemplateService.modify(voucherTemplateVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVo.error(e);
        }
        return ResponseVo.success();
    }
    /**
     * 根据优惠券ID修改优惠券
     *
     * @return 优惠券
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable(value = "id") long id,VoucherTemplateVO voucherTemplateVO) throws Exception {
        UserDO userDO = sessionManager.getUser();
        if (userDO != null){
            voucherTemplateVO.setOperator(userDO.getNickname());
        }
        voucherTemplateVO.setId(id);
        voucherTemplateVO.setEndTime(getEndTime(voucherTemplateVO.getEndTime()));
        voucherTemplateService.modify(voucherTemplateVO);
        return "/success";
    }

    private Date getEndTime(Date endTime) throws ParseException {
        String str = DateUtil.date2StringByDay(endTime);
        str = str + " 23:59:59";
        return DateUtil.convertStringToDate(DateUtil.DATE_TIME_FORMAT,str);
    }
	
}
