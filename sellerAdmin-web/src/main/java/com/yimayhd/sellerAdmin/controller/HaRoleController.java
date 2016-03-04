//package com.yimayhd.sellerAdmin.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.yimayhd.sellerAdmin.base.BaseController;
//import com.yimayhd.sellerAdmin.base.PageQuery;
//import com.yimayhd.sellerAdmin.base.ResponseVo;
//import com.yimayhd.sellerAdmin.model.HaRoleDO;
//import com.yimayhd.sellerAdmin.service.HaRoleService;
//
///**
// * 角色表（菜单）
// * @author czf
// */
//@Controller
//@RequestMapping("haRole")
//public class HaRoleController extends BaseController{
//
//    @Autowired
//    private HaRoleService haRoleService;
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseVo getList(HaRoleDO haRoleDO, PageQuery<HaRoleDO> vo) throws Exception {
//        vo.setEntity(haRoleDO);
//        vo.setList(haRoleService.getList(vo));
//        vo.setTotalSum(haRoleService.getCount(haRoleDO));
//        return new ResponseVo(vo);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseVo get(@PathVariable("id") long id) throws Exception {
//        return new ResponseVo(haRoleService.getById(id));
//    }
//
//    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseVo save(@RequestBody HaRoleDO haRoleDO) throws Exception {
//        haRoleService.add(haRoleDO);
//        return new ResponseVo();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseVo update(@PathVariable("id")long id, @RequestBody HaRoleDO haRoleDO) throws Exception {
//        haRoleDO.setId(id);
//        haRoleService.modify(haRoleDO);
//        return new ResponseVo();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public ResponseVo delete(@PathVariable("id") long id) throws Exception {
//        haRoleService.delete(id);
//        return new ResponseVo();
//    }
//}
