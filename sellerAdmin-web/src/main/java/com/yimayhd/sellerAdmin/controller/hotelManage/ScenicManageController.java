package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.model.HotelManage.ScenicManageVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangdi on 16/5/17.
 */
@RequestMapping("/scenic")
public class ScenicManageController  extends BaseController {
    /**
     * 查询景区资源列表
     * @param model
     * @param scenicManageVO
     * @return
     */
    public  String queryScenicManageVOListByData(Model model, ScenicManageVO scenicManageVO){
        return "";

    }

    /**
     * 查询景区详情
     * @param model
     * @param scenicManageVO
     * @return
     */
    public String queryScenicManageVOByData(Model model, ScenicManageVO scenicManageVO){
        return "";
    }

    /**
     * 添加景区商品
     * @param model
     * @param scenicManageVO
     * @return
     */
    public String addScenicManageVOByDdata(Model model, ScenicManageVO scenicManageVO){
        return "";
    }

    /**
     * 编辑景区商品
     * @param model
     * @param scenicManageVO
     * @return
     */
    public String editScenicManageVOByDdata(Model model, ScenicManageVO scenicManageVO){
        return "";
    }
}
