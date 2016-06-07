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
//import com.yimayhd.sellerAdmin.model.HaMenuDO;
//import com.yimayhd.sellerAdmin.service.HaMenuService;
//
///**
// * 菜单表
// * @author czf
// */
//@Controller
//@RequestMapping("haMenu")
//public class HaMenuController extends BaseController{
//
//    @Autowired
//    private HaMenuService haMenuService;
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseVo getList(HaMenuDO haMenuDO, PageQuery<HaMenuDO> vo) throws Exception {
//        vo.setEntity(haMenuDO);
//        vo.setList(haMenuService.getList(vo));
//        vo.setTotalSum(haMenuService.getCount(haMenuDO));
//        return new ResponseVo(vo);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseVo get(@PathVariable("id") long id) throws Exception {
//        return new ResponseVo(haMenuService.getById(id));
//    }
//
//    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseVo save(@RequestBody HaMenuDO haMenuDO) throws Exception {
//        haMenuService.add(haMenuDO);
//        return new ResponseVo();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public ResponseVo update(@PathVariable("id")long id, @RequestBody HaMenuDO haMenuDO) throws Exception {
//        haMenuDO.setId(id);
//        haMenuService.modify(haMenuDO);
//        return new ResponseVo();
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public ResponseVo delete(@PathVariable("id") long id) throws Exception {
//        haMenuService.delete(id);
//        return new ResponseVo();
//    }
//}
