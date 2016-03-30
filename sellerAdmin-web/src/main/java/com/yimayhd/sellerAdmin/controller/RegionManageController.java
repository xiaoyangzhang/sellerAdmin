package com.yimayhd.sellerAdmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.Region;
import com.yimayhd.sellerAdmin.service.RegionService;

/**
 * Created by Administrator on 2015/11/13.
 */
@Controller
@RequestMapping("/B2C/regionManage")
public class RegionManageController extends BaseController {
    @Autowired
    private RegionService regionService;

    /**
     * 地区列表
     * @return 地区列表
     * @throws Exception
     */
    @RequestMapping(value = "/regionList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo list(Long id) throws Exception {
        List<Region> regionList;
        if(id == null){
            regionList = regionService.getProvince();
        }else{
            regionList = regionService.getRegionByParentId(id);
        }
        return new ResponseVo(regionList);
    }
    /**
     * 根据父id获取地区列表
     * @return 地区列表
     * @throws Exception
     */
    @RequestMapping(value = "/regionList/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo listById(@PathVariable("id") Long id) throws Exception {
        List<Region> regionList;
        if(id == null){
            regionList = regionService.getProvince();
        }else{
            regionList = regionService.getRegionByParentId(id);
        }
        return new ResponseVo(regionList);
    }
}
