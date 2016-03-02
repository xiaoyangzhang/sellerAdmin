package com.yimayhd.sellerAdmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.PageQuery;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.HaRoleMenuDO;
import com.yimayhd.sellerAdmin.service.HaRoleMenuService;

/**
 * 角色菜单表
 * @author czf
 */
@Controller
@RequestMapping("haRoleMenu")
public class HaRoleMenuController extends BaseController{

    @Autowired
    private HaRoleMenuService haRoleMenuService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo getList(HaRoleMenuDO haRoleMenuDO, PageQuery<HaRoleMenuDO> vo) throws Exception {
        vo.setEntity(haRoleMenuDO);
        vo.setList(haRoleMenuService.getList(vo));
        vo.setTotalSum(haRoleMenuService.getCount(haRoleMenuDO));
        return new ResponseVo(vo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo get(@PathVariable("id") long id) throws Exception {
        return new ResponseVo(haRoleMenuService.getById(id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseVo save(@RequestBody HaRoleMenuDO haRoleMenuDO) throws Exception {
        haRoleMenuService.add(haRoleMenuDO);
        return new ResponseVo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseVo update(@PathVariable("id")long id, @RequestBody HaRoleMenuDO haRoleMenuDO) throws Exception {
        haRoleMenuDO.setId(id);
        haRoleMenuService.modify(haRoleMenuDO);
        return new ResponseVo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo delete(@PathVariable("id") long id) throws Exception {
        haRoleMenuService.delete(id);
        return new ResponseVo();
    }
}
