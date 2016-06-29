//package com.yimayhd.sellerAdmin.controller;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.yimayhd.sellerAdmin.base.BaseController;
//import com.yimayhd.sellerAdmin.base.PageVO;
//import com.yimayhd.sellerAdmin.base.ResponseVo;
//import com.yimayhd.sellerAdmin.constant.ResponseStatus;
//import com.yimayhd.sellerAdmin.model.HaRoleDO;
//import com.yimayhd.sellerAdmin.model.HaRoleDetail;
//import com.yimayhd.sellerAdmin.model.query.RoleListQuery;
//import com.yimayhd.sellerAdmin.service.OrderService;
//import com.yimayhd.sellerAdmin.service.SystemManageService;
//
//@Controller
//@RequestMapping("/systemManage")
//public class SystemManageController extends BaseController {
//
//	private static final Logger logger = LoggerFactory
//			.getLogger(SystemManageController.class);
//
//	@Autowired
//	private OrderService orderService;
//
//	@Autowired
//	SystemManageService systemManageService;
//
//	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
//	public String roleListRedirect(Model model) throws Exception {
//
//		RoleListQuery roleListQuery = new RoleListQuery();
//
//		if (roleListQuery.getPageNumber() != null) {
//
//			int pageNumber = roleListQuery.getPageNumber();
//			int pageSize = roleListQuery.getPageSize();
//			int pageBegin = (pageNumber - 1) * pageSize;
//			roleListQuery.setPageBegin(pageBegin);
//
//		}
//
//		PageVO<HaRoleDO> pageVo = systemManageService.getListNew(roleListQuery);
//		List<HaRoleDO> roleList = pageVo.getItemList();
//		model.addAttribute("pageVo", pageVo);
//		model.addAttribute("roleList", roleList);
//
//		return "/system/systemManage/roleList";
//	}
//
//	@RequestMapping(value = "/roleList/updateStatus/{id}", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseVo updateRoleStatus(
//			@PathVariable("id") int id,
//			@RequestParam(value = "roleStatus", required = true) Integer roleStatus)
//			throws Exception {
//
//		HaRoleDO haRoleDO = new HaRoleDO();
//		haRoleDO.setId(id);
//		haRoleDO.setStatus(roleStatus);
//		boolean result = systemManageService.updateRoleStatus(haRoleDO);
//
//		ResponseVo responseVo = new ResponseVo();
//		if (!result) {
//			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
//		}
//
//		return responseVo;
//	}
//
//	@RequestMapping(value = "/roleListPOST", method = RequestMethod.POST)
//	public String roleListPost(Model model, RoleListQuery roleListQuery)
//			throws Exception {
//
//		if (roleListQuery.getPageNumber() != null) {
//
//			int pageNumber = roleListQuery.getPageNumber();
//			int pageSize = roleListQuery.getPageSize();
//			int pageBegin = (pageNumber - 1) * pageSize;
//			roleListQuery.setPageBegin(pageBegin);
//
//		}
//
//		PageVO<HaRoleDO> pageVo = systemManageService.getListNew(roleListQuery);
//		model.addAttribute("pageVo", pageVo);
//		model.addAttribute("roleListQuery", roleListQuery);
//		model.addAttribute("roleList", pageVo.getItemList());
//		return "/system/systemManage/roleList";
//	}
//
//	@RequestMapping(value = "/roleDetail/{roleId}", method = RequestMethod.GET)
//	public String roleDetail(Model model,
//			@PathVariable(value = "roleId") long roleId,
//			RoleListQuery roleListQuery) throws Exception {
//
//		if (roleListQuery.getPageNumber() != null) {
//
//			int pageNumber = roleListQuery.getPageNumber();
//			int pageSize = roleListQuery.getPageSize();
//			int pageBegin = (pageNumber - 1) * pageSize;
//			roleListQuery.setPageBegin(pageBegin);
//
//		}
//
//		roleListQuery.setRoleId(roleId);
//		PageVO<HaRoleDetail> pageVo = systemManageService
//				.roleDetailById(roleListQuery);
//
//		model.addAttribute("roleId", roleId);
//		model.addAttribute("pageVo", pageVo);
//		model.addAttribute("roleListQuery", roleListQuery);
//		model.addAttribute("roleDetailList", pageVo.getItemList());
//
//		return "/system/systemManage/roleDetail";
//	}
//
//	@RequestMapping(value = "/roleDetail/updateStatus/{roleMenuId}", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseVo updateRoleDetailStatus(@PathVariable("roleMenuId") int roleMenuId,
//					@RequestParam(value = "roleStatus", required = true) Integer roleStatus,
//					@RequestParam(value = "roleId", required = true) Integer roleId) throws Exception {
//
//		boolean result = systemManageService.addOrUpdateRoleDetaiStatus(roleMenuId, roleStatus, roleId);
//
//		ResponseVo responseVo = new ResponseVo();
//		if (!result) {
//			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
//		}
//
//		return responseVo;
//	}
//}
