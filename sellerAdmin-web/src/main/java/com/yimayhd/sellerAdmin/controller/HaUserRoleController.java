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
//import com.yimayhd.sellerAdmin.model.HaUserRoleDO;
//import com.yimayhd.sellerAdmin.service.HaUserRoleService;
//
///**
// * 用户角色表
// * @author czf
// */
//@Controller
//@RequestMapping("haUserRole")
//public class HaUserRoleController extends BaseController{
//
//    @Autowired
//    private HaUserRoleService haUserRoleService;
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseVo getList(HaUserRoleDO haUserRoleDO, PageQuery<HaUserRoleDO> vo) throws Exception {
//        vo.setEntity(haUserRoleDO);
//        vo.setList(haUserRoleService.getList(vo));
//        vo.setTotalSum(haUserRoleService.getCount(haUserRoleDO));
//        return new ResponseVo(vo);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseVo get(@PathVariable("id") long id) throws Exception {
//        return new ResponseVo(haUserRoleService.getById(id));
//    }
//
//    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseVo save(@RequestBody HaUserRoleDO haUserRoleDO) throws Exception {
//        haUserRoleService.add(haUserRoleDO);
//        return new ResponseVo();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseVo update(@PathVariable("id")long id, @RequestBody HaUserRoleDO haUserRoleDO) throws Exception {
//        haUserRoleDO.setId(id);
//        haUserRoleService.modify(haUserRoleDO);
//        return new ResponseVo();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public ResponseVo delete(@PathVariable("id") long id) throws Exception {
//        haUserRoleService.delete(id);
//        return new ResponseVo();
//    }
//}
